package com.example.sk_android.mvp.view.activity.onlineresume

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.AddProjectExperienceFrag
import com.example.sk_android.mvp.view.fragment.onlineresume.CommonBottomButton
import com.example.sk_android.mvp.view.fragment.onlineresume.RollChooseFrag
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.HttpException

class AddProjectExperience : AppCompatActivity(), CommonBottomButton.CommonButton,
    AddProjectExperienceFrag.AddProject, ShadowFragment.ShadowClick,
    RollChooseFrag.RollToolClick{

    lateinit var resumebutton: CommonBottomButton
    lateinit var editList: AddProjectExperienceFrag
    private var shadowFragment: ShadowFragment? = null
    private var rollChoose: RollChooseFrag? = null
    private lateinit var baseFragment: FrameLayout
    var actionBarNormalFragment:ActionBarNormalFragment?=null
    private var resumeId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.getStringExtra("resumeId")!=null){
            resumeId = intent.getStringExtra("resumeId")
        }

        val main = 1
        baseFragment = frameLayout {
            id = main
            verticalLayout {
                val actionBarId=4
                frameLayout{
                    id=actionBarId
                    actionBarNormalFragment= ActionBarNormalFragment.newInstance("プロジェクト経験を追加");
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
                        editList = AddProjectExperienceFrag.newInstance(this@AddProjectExperience)
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
        StatusBarUtil.setTranslucentForImageView(this@AddProjectExperience, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@AddProjectExperience,ResumeEdit::class.java)
            setResult(Activity.RESULT_OK,intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }
    }
    // 底部按钮
    override suspend fun btnClick(text: String) {
        val userBasic = editList.getProjectExperience()
        if (userBasic != null && resumeId != "") {
            addJob(userBasic,resumeId)
        }
    }

    override fun shadowClicked() {
        closeAlertDialog()
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

    // 添加项目经历
    private suspend fun addJob(job: Map<String, Any>?, id: String) {
        try {
            val userJson = JSON.toJSONString(job)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@AddProjectExperience, "https://job.sk.cgland.top/")
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .createProjectExperience(id, body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if(it.code()in 200..299){
                toast("创建成功")
                val intent = Intent(this@AddProjectExperience,ResumeEdit::class.java)
                startActivity(intent)
                                overridePendingTransition(R.anim.right_in, R.anim.left_out)

                finish()
                overridePendingTransition(R.anim.left_in,R.anim.right_out)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.message())
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