package com.example.sk_android.mvp.view.activity.privacyset

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.OpenType
import com.example.sk_android.mvp.model.privacySet.UserPrivacySetup
import com.example.sk_android.mvp.view.fragment.common.EditAlertDialog
import com.umeng.message.PushAgent
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.privacyset.CauseChooseDialog
import com.example.sk_android.mvp.view.fragment.privacyset.PrivacyFragment
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.HttpException

class PrivacySetActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    CauseChooseDialog.CauseChoose, EditAlertDialog.EditDialogSelect,
    PrivacyFragment.PrivacyClick {

    // 点击"联系我"按钮
    override suspend fun allowContactClick(checked: Boolean) {
        toast("${checked}")
        val model = privacyUser
        model.attributes.allowContact = checked
        updateUserPrivacy(model)
    }
    // 点击"显示公司全名"按钮
    override suspend fun companyNameClick(checked: Boolean) {
        val model = privacyUser
        model.attributes.companyName = checked
        updateUserPrivacy(model)
    }
    // 点击"简历有效"按钮
    override suspend fun isResumeClick(checked: Boolean) {
        val model = privacyUser
        model.attributes.isResume = checked
        updateUserPrivacy(model)
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
        }else{
            model.attributes.causeText = ""
        }
        model.openType = if(checked) OpenType.PUBLIC else OpenType.PRIVATE
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
        toast(trim)
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
        toast(name)
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

    private var shadowFragment: ShadowFragment? = null
    private var chooseDialog: CauseChooseDialog? = null
    private var editAlertDialog: EditAlertDialog? = null
    private val outside = 1
    private lateinit var privacy: PrivacyFragment
    private lateinit var privacyUser : UserPrivacySetup

    // create完了,进入start,每次重新跳转,也只会从start开始走,不会再走create
    override fun onStart() {
        super.onStart()

        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getUserPrivacy()
        }
    }

    // 页面代码
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()

        frameLayout {
            id = outside
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "プライバシー設定"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerInParent()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
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
            if (body.code() == 200) {
                val json = body.body()!!.asJsonObject
                privacyUser = Gson().fromJson<UserPrivacySetup>(json, UserPrivacySetup::class.java)

                // 修改获取按钮的true或false
                val isPublic = privacyUser.openType == OpenType.PUBLIC // 公开简历
                val isResume = privacyUser.attributes.isResume // ビデオ履歴書有効
                val isCompanyName = privacyUser.attributes.companyName // 就職経験に会社フルネームが表示される
                val isContact = privacyUser.attributes.allowContact // 猟師は私に連絡する
                privacy.setSwitch(isPublic, isResume, isCompanyName, isContact)

            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
        }
    }

    // 更新用户隐私设置
    private suspend fun updateUserPrivacy(model: UserPrivacySetup) {
        try {
            val params = mapOf(
                "Greeting" to model.greeting,
                "GreetingID" to model.greetingId,
                "OpenType" to model.openType,
                "Remind" to model.remind,
                "Attributes" to model.attributes
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@PrivacySetActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(PrivacyApi::class.java)
                .updateUserPrivacy(body)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .awaitSingle()
            if (it.code() == 200) {
                toast("更新成功")
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
    }
}