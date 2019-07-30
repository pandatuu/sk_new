package com.example.sk_android.mvp.view.activity.onlineresume

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.model.onlineresume.jobexperience.CompanyModel
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.CommonBottomButton
import com.example.sk_android.mvp.view.fragment.onlineresume.EditEduExperienceFrag
import com.example.sk_android.mvp.view.fragment.onlineresume.RollChooseFrag
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.HttpException

class EditEduExperience : AppCompatActivity(), CommonBottomButton.CommonButton,
    EditEduExperienceFrag.EditEdu, RollChooseFrag.RollToolClick,
    ShadowFragment.ShadowClick, BottomSelectDialogFragment.BottomSelectDialogSelect {


    private lateinit var editList: EditEduExperienceFrag
    private var shadowFragment: ShadowFragment? = null
    private var editAlertDialog: BottomSelectDialogFragment? = null
    var actionBarNormalFragment:ActionBarNormalFragment?=null
    private var rollChoose: RollChooseFrag? = null
    private lateinit var baseFragment: FrameLayout
    private var eduId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getStringExtra("eduId") != null) {
            eduId = intent.getStringExtra("eduId")
        }

        val main = 1
        baseFragment = frameLayout {
            id = main
            verticalLayout {
                val actionBarId=5
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("教育経歴を編集する");
                    supportFragmentManager.beginTransaction().replace(id,actionBarNormalFragment!!).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                val itemList = 2
                frameLayout {
                    frameLayout {
                        id = itemList
                        editList = EditEduExperienceFrag.newInstance()
                        supportFragmentManager.beginTransaction().add(itemList, editList).commit()
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        bottomMargin = dip(130)
                    }
                    val button1 = 3
                    val button2 = 4
                    frameLayout {
                        id = button1
                        val resumebutton = CommonBottomButton.newInstance("セーブ", 0, R.drawable.button_shape_orange)
                        supportFragmentManager.beginTransaction().add(button1, resumebutton).commit()
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        bottomMargin = dip(65)
                    }
                    frameLayout {
                        id = button2
                        val resumebutton =
                            CommonBottomButton.newInstance("削除", 0, R.drawable.button_shape_grey)
                        supportFragmentManager.beginTransaction().add(button2, resumebutton).commit()
                    }.lparams {
                        width = matchParent
                        height = matchParent
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
        StatusBarUtil.setTranslucentForImageView(this@EditEduExperience, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent()
            setResult(RESULT_CANCELED,intent)
            finish()//返回
           overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }
    override fun onResume() {
        super.onResume()
        if (eduId != "") {
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                getEdu(eduId)
            }
        }
    }

    override suspend fun btnClick(text: String) {
        if (text == "セーブ") {
            //添加
            val userBasic = editList.getEduExperience()
            if (userBasic != null && eduId != "") {
                addEdu(eduId, userBasic)
            }
        } else {
            //删除
            if (eduId != "") {
                deleteEdu(eduId)
            }
        }
    }

    // 开始时间按钮
    override fun startDate() {
        openDate("start")
    }

    // 结束时间按钮
    override fun endDate() {
        openDate("end")
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

    //透明黑色背景点击
    override fun shadowClicked() {
        closeAlertDialog()
    }

    //选择学历按钮关闭
    override fun getBottomSelectDialogSelect() {
        closeAlertDialog()
    }

    //选择学历弹窗返回
    override fun getback(index: Int, list: MutableList<String>) {
        if (index != -1)
            editList.seteduBack(list[index])

        closeAlertDialog()
    }

    //点击学历选择时
    override fun eduBackground(text: String) {
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

        val title = arrayListOf("中卒", "高卒", "専門卒・短大卒", "大卒", "修士", "博士")
        editAlertDialog = BottomSelectDialogFragment.newInstance("学歴", title)
        mTransaction.add(baseFragment.id, editAlertDialog!!)
        mTransaction.commit()
    }

    // 根据学校名,查询学校信息
    private suspend fun getCompany(companyName: String) {
        try {
            val retrofitUils = RetrofitUtils(this@EditEduExperience, "https://org.sk.cgland.top/")
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
//                    editList.setCompany(company)
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 查询教育经历
    private suspend fun getEdu(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@EditEduExperience, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getEduExperience(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val model = Gson().fromJson(it.body(), EduExperienceModel::class.java)
                editList.setEduExperience(model)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 更新教育经历
    private suspend fun addEdu(id: String, job: Map<String, Any?>?) {
        try {
            // 再更新用户信息
            val userJson = JSON.toJSONString(job)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@EditEduExperience, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .updateEduExperience(id, body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val toast = Toast.makeText(applicationContext, "更新成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
                val intent = Intent()
                setResult(RESULT_OK,intent)
                finish()
                overridePendingTransition(R.anim.left_in,R.anim.right_out)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 删除教育经历
    private suspend fun deleteEdu(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@EditEduExperience, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .deleteEduExperience(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val intent = Intent()
                setResult(RESULT_OK,intent)
                finish()
                overridePendingTransition(R.anim.left_in,R.anim.right_out)
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
        if (editAlertDialog != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog = null
        }
        mTransaction.commit()
    }
}