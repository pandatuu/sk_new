package com.example.sk_android.mvp.view.activity.onlineresume

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.onlineresume.OnlineResumeApi
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.onlineresume.projectexprience.ProjectExperienceModel
import com.example.sk_android.mvp.store.FetchEditOnlineAsyncAction
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.CommonBottomButton
import com.example.sk_android.mvp.view.fragment.onlineresume.EditProjectExperienceFrag
import com.example.sk_android.mvp.view.fragment.onlineresume.RollChooseFrag
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.HttpException
import zendesk.suas.AsyncMiddleware

class EditProjectExperience : AppCompatActivity(), CommonBottomButton.CommonButton,
    EditProjectExperienceFrag.EditProject, RollChooseFrag.RollToolClick,
    ShadowFragment.ShadowClick {

    private lateinit var editList: EditProjectExperienceFrag
    private var shadowFragment: ShadowFragment? = null
    var actionBarNormalFragment: ActionBarNormalFragment? = null
    private var rollChoose: RollChooseFrag? = null
    private lateinit var baseFragment: FrameLayout
    private var projectId = ""
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!) {
            val toast = Toast.makeText(applicationContext, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        if (intent.getStringExtra("projectId") != null) {
            projectId = intent.getStringExtra("projectId")
        }

        val main = 1
        baseFragment = frameLayout {
            id = main
            verticalLayout {
                val actionBarId = 5
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("プロジェクトの経験を編集")
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                val itemList = 2
                frameLayout {
                    frameLayout {
                        id = itemList
                        editList = EditProjectExperienceFrag.newInstance()
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
                            CommonBottomButton.newInstance("削除", 0, R.drawable.button_shape_red)
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
        StatusBarUtil.setTranslucentForImageView(this@EditProjectExperience, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@EditProjectExperience, ResumeEdit::class.java)
            setResult(RESULT_CANCELED, intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()
        if (projectId != "") {
            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                getJob(projectId)
            }
        }
    }

    override suspend fun btnClick(text: String) {
        thisDialog = DialogUtils.showLoading(this@EditProjectExperience)
        mHandler.postDelayed(r, 12000)
        if (text == "セーブ") {
            //添加
            val userBasic = editList.getProjectExperience()
            if (userBasic != null && projectId != "") {
                addJob(projectId, userBasic)
            } else {
                DialogUtils.hideLoading(thisDialog)
            }
        } else {
            //删除
            if (projectId != "") {
                deleteJob(projectId)
            } else {
                DialogUtils.hideLoading(thisDialog)
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

    // 查询工作经历
    private suspend fun getJob(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@EditProjectExperience, this.getString(R.string.jobUrl))
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .getProjectExperience(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                val model = Gson().fromJson(it.body(), ProjectExperienceModel::class.java)
                editList.setProjectExperience(model)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 更新工作经历
    private suspend fun addJob(id: String, job: Map<String, Any?>?) {
        try {
            // 再更新用户信息
            val userJson = JSON.toJSONString(job)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@EditProjectExperience, this.getString(R.string.jobUrl))
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .updateProjectExperience(id, body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                frush()
                DialogUtils.hideLoading(thisDialog)

                val toast = Toast.makeText(applicationContext, "更新成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                val intent = Intent()
                setResult(102, intent)
                finish()
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println(throwable.code())
            }
        }
    }

    // 删除工作经历
    private suspend fun deleteJob(id: String) {
        try {
            val retrofitUils = RetrofitUtils(this@EditProjectExperience, this.getString(R.string.jobUrl))
            val it = retrofitUils.create(OnlineResumeApi::class.java)
                .deleteProjectExperience(id)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
                frush()
                DialogUtils.hideLoading(thisDialog)

                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
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
            mTransaction.add(baseFragment.id, rollChoose!!)
        }

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

    private fun frush() {
        val application: App? = App.getInstance()
        application?.store?.dispatch(FetchEditOnlineAsyncAction.create(this))
    }
}