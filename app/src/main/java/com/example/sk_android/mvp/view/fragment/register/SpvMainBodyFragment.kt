package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.mvp.view.activity.register.RegisterLoginActivity
import com.example.sk_android.mvp.view.activity.register.SetPasswordVerifyActivity
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.adapter.rxjava2.HttpException
import withTrigger

class SpvMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var verificationCode:EditText
    lateinit var codeErrorMessage:TextView
    lateinit var tool: BaseTool
    lateinit var pcodeTv: TextView
    private var runningDownTimer: Boolean = false
    var phone:String = ""
    var myPhone:String = ""
    var country = ""
    var password = ""
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    private lateinit var myDialog: MyDialog

    companion object {
        fun newInstance(phone:String,country:String,password:String): SpvMainBodyFragment {
            val fragment = SpvMainBodyFragment()
            fragment.phone = phone
            fragment.country = country
            fragment.password = password
            fragment.myPhone = phone.substring(phone.length-4)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = MyDialog.Builder(activity!!)
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()
        mContext = activity
        onPcode()
    }

    override fun onDestroy() {
        super.onDestroy()
        downTimer.onFinish()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView()
    }

    fun createView():View{
        tool=BaseTool()
        return UI {
            verticalLayout {
                backgroundColorResource = R.color.loginBackground
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(10)
                rightPadding = dip(10)
                textView {
                    textResource = R.string.pvIntroduction
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.toolBarTextColor
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(35)
                }

                textView {
                    text = "番号${myPhone}に検証コードを送信しました"
                    textSize = 12f
                    textColorResource = R.color.SmsNotification
                    gravity = Gravity.CENTER
                }.lparams(width = matchParent,height = wrapContent) {
                    topMargin = dip(35)
                }

                verificationCode = editText {
                    backgroundColorResource = R.color.loginBackground
                    hintResource = R.string.verificationCode
                    hintTextColor = Color.parseColor("#B3B3B3")
                    textSize = 15f //sp
                    inputType = InputType.TYPE_CLASS_PHONE
                    filters = arrayOf(InputFilter.LengthFilter(6))
                    singleLine = true
                }.lparams {
                     topMargin = dip(35)

                }

                view {
                    backgroundColorResource = R.color.splitLineColor
                }.lparams(width = matchParent, height = dip(2)) {
//                    topMargin = dip(71)
                }

                codeErrorMessage = textView {
                    textResource = R.string.codeErrorMessage
                    visibility = View.GONE
                    textColorResource = R.color.codeMessageColor
                    textSize = 12f //sp
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(10)
                }

                pcodeTv = textView {
                    text = "もう一回送ります。(10s)"
                    textColor = Color.parseColor("#007AFF")
                    textSize = 12f
                    gravity = Gravity.CENTER
                    this.withTrigger().click { onPcode() }
                }.lparams(width = matchParent,height = wrapContent) {
                    topMargin = dip(10)
                }

                button {
                    backgroundColorResource = R.color.themeColor
                    textResource = R.string.btnCont
                    textColorResource = R.color.white
                    textSize = 18f //sp

                    this.withTrigger().click {
                        submit()
                    }
                }.lparams(width = matchParent, height = dip(47)) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    leftMargin = dip(38)
                    topMargin = dip(35)
                    bottomMargin = dip(36)
                    rightMargin = dip(38)
                }

                onClick {
                    closeKeyfocus()
                }
            }
        }.view
    }

    //验证验证码
    @SuppressLint("CheckResult")
    private fun submit() {
        myDialog.show()
        var code = tool.getEditText(verificationCode)
        if(code == ""){
            codeErrorMessage.textResource = R.string.pvCodeEmpty
            codeErrorMessage.visibility = View.VISIBLE
            myDialog.dismiss()
            return
        }


        val params = mapOf(
            "country" to country,
            "phone" to phone,
            "code" to code,
            "password" to password
        )


        val userJson = JSON.toJSONString(params)

        val body = RequestBody.create(json,userJson)

        var retrofitUils = RetrofitUtils(mContext!!,this.getString(R.string.authUrl))

        retrofitUils.create(RegisterApi::class.java)
            .findPassword(body)
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                if(it.code() == 204){
                    myDialog.dismiss()
                    startActivity<LoginActivity>()
                }else {
                    codeErrorMessage.visibility = View.VISIBLE
                    var result = ""
                    result = when(it.code()){
                        406 -> this.getString(R.string.codeErrorMessage)
                        else -> this.getString(R.string.pcCodeError)
                    }
                    codeErrorMessage.text = result
                    myDialog.dismiss()
                }

            },{
                myDialog.dismiss()
            })

    }


    //发送验证码按钮
    private fun onPcode() {

        //如果60秒倒计时没有结束
        if (runningDownTimer) {
            return
        }

        downTimer.start()  // 倒计时开始

    }

    /**
     * 倒计时
     */
    private val downTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(l: Long) {
            runningDownTimer = true
            pcodeTv.text = activity?.getString(R.string.pvCodeDate)+(l / 1000).toString() + "s)"
            pcodeTv.setOnClickListener { null }
        }

        override fun onFinish() {
            runningDownTimer = false
            pcodeTv.textResource = R.string.pvRsend
            pcodeTv.withTrigger().click { sendVerification() }
        }

    }

    @SuppressLint("CheckResult")
    private fun sendVerification() {
        myDialog.show()
        val deviceModel = Build.MODEL
        val manufacturer = Build.BRAND
        val deviceType = "ANDROID"
        val codeType = "LOGIN"

        val params = mapOf(
            "phone" to phone,
            "country" to country,
            "deviceType" to deviceType,
            "codeType" to codeType,
            "manufacturer" to manufacturer,
            "deviceModel" to deviceModel
        )

        val userJson = JSON.toJSONString(params)

        val body = RequestBody.create(json, userJson)
        var retrofitUils = RetrofitUtils(mContext!!,this.getString(R.string.authUrl))

        retrofitUils.create(RegisterApi::class.java)
            .getVerification(body)
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                if(it.code() == 204){
                    myDialog.dismiss()
                    onPcode()
                }else {
                    myDialog.dismiss()
                    println("获取验证码失效")
                }

            },{
                myDialog.dismiss()
            })
    }

    fun closeKeyfocus(){
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        verificationCode.clearFocus()
    }

}