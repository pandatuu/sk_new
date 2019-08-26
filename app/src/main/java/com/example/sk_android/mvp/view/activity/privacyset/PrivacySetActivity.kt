package com.example.sk_android.mvp.view.activity.privacyset

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.privacyset.PrivacyApi
import com.example.sk_android.mvp.model.privacySet.OpenType
import com.example.sk_android.mvp.model.privacySet.UserPrivacySetup
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.common.ActionBarNormalFragment
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.mvp.view.fragment.common.EditAlertDialog
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.privacyset.CauseChooseDialog
import com.example.sk_android.mvp.view.fragment.privacyset.PrivacyFragment
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

class PrivacySetActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    CauseChooseDialog.CauseChoose, EditAlertDialog.EditDialogSelect,
    PrivacyFragment.PrivacyClick {

    private var shadowFragment: ShadowFragment? = null
    private var chooseDialog: CauseChooseDialog? = null
    private var editAlertDialog: EditAlertDialog? = null
    var actionBarNormalFragment: ActionBarNormalFragment? = null
    private val outside = 1
    private lateinit var privacy: PrivacyFragment
    private lateinit var privacyUser: UserPrivacySetup

    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!)
            toast("ネットワークエラー") //网路出现问题
        DialogUtils.hideLoading(thisDialog)
    }
    // 页面代码
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        frameLayout {
            id = outside
            verticalLayout {
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ActionBarNormalFragment.newInstance("プライバシー設定");
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }
                val frag = 2
                frameLayout {
                    id = frag
                    privacy = PrivacyFragment.newInstance()
                    supportFragmentManager.beginTransaction().add(frag, privacy).commit()
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
        StatusBarUtil.setTranslucentForImageView(this@PrivacySetActivity, 0, actionBarNormalFragment!!.toolbar1)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@PrivacySetActivity, PersonSetActivity::class.java)
            startActivity(intent)
            finish()//返回
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            thisDialog=DialogUtils.showLoading(this@PrivacySetActivity)

            mHandler.postDelayed(r, 20000)
            getUserPrivacy()
            DialogUtils.hideLoading(thisDialog)
        }
    }

    //点击公开简历按钮
    override suspend fun isPublicClick(checked: Boolean) {
        val model = privacyUser
        if (!checked) {
            // 透明黑色背景
            shadowFragment = ShadowFragment.newInstance()
            supportFragmentManager.beginTransaction().add(outside, shadowFragment!!).commit()
            // 原因选择弹窗
            chooseDialog = CauseChooseDialog.newInstance()
            supportFragmentManager.beginTransaction().add(outside, chooseDialog!!).commit()
        }
        model.attributes.causeText = ""
        model.openType = if (checked) OpenType.PUBLIC else OpenType.PRIVATE
        updateUserPrivacy(model)
    }

    //点击进入黑名单按钮
    override fun blacklistClick() {
        jumpBlackList()
    }

    // 手写理由弹窗,取消按钮
    override fun EditCancelSelect() {
        dele()
    }

    // 手写理由弹窗,确定按钮
    override suspend fun EditDefineSelect(trim: String) {
        //其他理由
        val model = privacyUser
        model.attributes.causeText = trim
        updateUserPrivacy(model)
        dele()
    }

    // 选中原因选项弹窗,取消按钮
    override fun cancleClick() {
        dele()
    }

    // 选中原因选项弹窗,确定按钮
    override suspend fun chooseClick(name: String) {
        dele()
        //选择关闭原因
        if (name.equals("その他")) reasonDialog() else {
            val model = privacyUser
            model.attributes.causeText = name
            updateUserPrivacy(model)
        }
    }

    // 关闭所有dialog
    private fun dele() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (chooseDialog != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(chooseDialog!!)
            chooseDialog = null
        }

        if (editAlertDialog != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }
        mTransaction.commit()
    }

    // 点击黑色透明背景
    override fun shadowClicked() {

    }


    // 选择其他后,弹出理由弹窗
    private fun reasonDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(outside, shadowFragment!!)
        }
        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        if (editAlertDialog == null) {
            editAlertDialog = EditAlertDialog.newInstance("理由を入力してください", null, "キャンセル", "確定", 14f)
            mTransaction.add(outside, editAlertDialog!!)
        }
        mTransaction.commit()
    }

    // 获取用户隐私设置
    private suspend fun getUserPrivacy() {
        try {
            val retrofitUils = RetrofitUtils(this@PrivacySetActivity, "https://user.sk.cgland.top/")
            val body = retrofitUils.create(PrivacyApi::class.java)
                .getUserPrivacy()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .awaitSingle()
            if (body.code() in 200..299) {
                val json = body.body()!!.asJsonObject
                privacyUser = Gson().fromJson<UserPrivacySetup>(json, UserPrivacySetup::class.java)

                // 修改获取按钮的true或false
                val isPublic = privacyUser.openType == OpenType.PUBLIC // 公开简历
                privacy.setSwitch(isPublic)
            }
            if(body.code() == 404){
                println("用户从未设置")
                updateUserPrivacy(null)
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
        }
    }

    // 更新用户隐私设置
    private suspend fun updateUserPrivacy(model: UserPrivacySetup?) {
        try {
            val params: Map<String,Any>
            if(model!=null) {
                params = mapOf(
                    "Greeting" to model.greeting,
                    "GreetingID" to model.greetingId,
                    "OpenType" to model.openType,
                    "Remind" to model.remind,
                    "Attributes" to model.attributes
                )
            }else{
                params = mapOf()
            }
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@PrivacySetActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(PrivacyApi::class.java)
                .updateUserPrivacy(body)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .awaitSingle()

            if (it.code() in 200..299) {
                val toast = Toast.makeText(applicationContext, "更新成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
                if(model==null){
                    getUserPrivacy()
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
        }
    }

    // 点击进入黑名单页面
    private fun jumpBlackList() {
        val intent = Intent(this@PrivacySetActivity, BlackListActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            if(editAlertDialog == null && shadowFragment == null && chooseDialog == null){
                val intent = Intent(this@PrivacySetActivity, PersonSetActivity::class.java)
                startActivity(intent)
                finish()//返回
                overridePendingTransition(R.anim.left_in, R.anim.right_out)
                return true
            }else{
                dele()
                return false
            }
        } else {
            return false
        }
    }
}