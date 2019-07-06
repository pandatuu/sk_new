package com.example.sk_android.mvp.view.activity.onlineresume

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.onlineresume.jobexperience.CompanyModel
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.AddJobExperienceFrag
import com.example.sk_android.mvp.view.fragment.onlineresume.CommonBottomButton
import com.example.sk_android.mvp.view.fragment.onlineresume.RollChooseFrag
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import retrofit2.HttpException
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.utils.MimeType
import com.jaeger.library.StatusBarUtil
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick


class AddJobExperience : AppCompatActivity(), CommonBottomButton.CommonButton,
    AddJobExperienceFrag.AddJob, RollChooseFrag.RollToolClick,
    ShadowFragment.ShadowClick {

    private lateinit var resumebutton: CommonBottomButton
    private lateinit var editList: AddJobExperienceFrag
    private var shadowFragment: ShadowFragment? = null
    private var rollChoose: RollChooseFrag? = null
    var actionBarNormalFragment:ActionBarNormalFragment?=null
    private lateinit var baseFragment: FrameLayout
    private var resumeId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.getStringExtra("resumeId")!=null){
            resumeId = intent.getStringExtra("resumeId")
        }

        val mainId = 1
        baseFragment = frameLayout {
            id = mainId
            verticalLayout {
                val actionBarId=4
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("就職経験を追加");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                val itemList = 2
                val button = 3
                frameLayout {
                    frameLayout {
                        id = itemList
                        editList = AddJobExperienceFrag.newInstance(this@AddJobExperience)
                        supportFragmentManager.beginTransaction().add(itemList, editList).commit()
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        bottomMargin = dip(70)
                    }
                    frameLayout {
                        id = button
                        resumebutton = CommonBottomButton.newInstance("セーブ", 0, R.drawable.button_shape_orange)
                        supportFragmentManager.beginTransaction().add(button, resumebutton).commit()
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }

    }
    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@AddJobExperience, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@AddJobExperience,ResumeEdit::class.java)
            setResult(Activity.RESULT_OK,intent)
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val job = data!!.getStringExtra("job")
            editList.setJobType(job)
        }
    }

    override fun addJobType() {
        val intent = Intent(this@AddJobExperience, JobSelectActivity::class.java)
        startActivityForResult(intent,1)
    }

    //透明黑色背景点击
    override fun shadowClicked() {
        closeAlertDialog()
    }

    // 底部按钮
    override suspend fun btnClick(text: String) {
        val userBasic = editList.getJobExperience()
        if (userBasic != null && resumeId != "") {
            addJob(userBasic,resumeId)
        }
    }

    //　日期滚动选择器关闭按钮
    override fun cancelClick() {
        closeAlertDialog()
    }

    //　日期滚动选择器确定按钮
    override fun confirmClick(methodName: String, text: String) {
        if (methodName == "start") {
            editList.setStartDate(text)
        } else {
            editList.setEndDate(text)
        }
        closeAlertDialog()
    }

    // 开始时间按钮
    override fun startDate() {
        openDate("start")
    }

    // 结束时间按钮
    override fun endDate() {
        openDate("end")
    }

    //输入公司名字时
    override fun addText(s: CharSequence?) {
        GlobalScope.launch {
            getCompany(s.toString())
        }
    }

    // 根据公司名,查询公司
    private suspend fun getCompany(companyName: String) {
        try {
            val retrofitUils = RetrofitUtils(this@AddJobExperience, "https://org.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getCompanyByName(companyName)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val page = Gson().fromJson(it.body(), PagedList::class.java)
                val company = mutableListOf<CompanyModel>()
                if (page.data.size > 0) {
                    for (item in page.data) {
                        company.add(Gson().fromJson(item, CompanyModel::class.java))
                    }
                    editList.setCompany(company)
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 添加工作经历
    private suspend fun addJob(job: Map<String, Any?>?, id: String) {
        try {
            // 再更新用户信息
            val userJson = JSON.toJSONString(job)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@AddJobExperience, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .createJobExperience(id,body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                toast("更新成功")
                val intent = Intent(this@AddJobExperience,ResumeEdit::class.java)
                startActivity(intent)
                finish()
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 打开时间弹窗
    private fun openDate(text: String) {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(baseFragment.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )
        if (rollChoose == null) {
            rollChoose = RollChooseFrag.newInstance(text)
        }
        mTransaction.add(baseFragment.id, rollChoose!!)
        mTransaction.commit()
    }

    //关闭弹窗
    private fun closeAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }
        if (rollChoose != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(rollChoose!!)
            rollChoose = null
        }
        mTransaction.commit()
    }
}