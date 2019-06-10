package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.adapter.rxjava2.HttpException

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
        mContext = activity
    }

    override fun onStart() {
        super.onStart()
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
                    setOnClickListener { onPcode() }
                }.lparams(width = matchParent,height = wrapContent) {
                    topMargin = dip(10)
                }

                button {
                    backgroundColorResource = R.color.themeColor
                    textResource = R.string.btnCont
                    textColorResource = R.color.white
                    textSize = 18f //sp

                    setOnClickListener {
                        submit()
                    }
                }.lparams(width = matchParent, height = dip(47)) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    leftMargin = dip(38)
                    topMargin = dip(35)
                    bottomMargin = dip(36)
                    rightMargin = dip(38)
                }
            }
        }.view
    }

    //验证验证码
    private fun submit() {
        var code = tool.getEditText(verificationCode)
        if(code == ""){
            codeErrorMessage.textResource = R.string.pvCodeEmpty
            codeErrorMessage.visibility = View.VISIBLE
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

        var retrofitUils = RetrofitUtils(mContext!!,"https://auth.sk.cgland.top/")

        retrofitUils.create(RegisterApi::class.java)
            .findPassword(body)
            .map { it ?: "" }
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                startActivity<LoginActivity>()
            },{
                if(it is HttpException){
                    if(it.code() == 406){
                        codeErrorMessage.textResource = R.string.codeErrorMessage
                        codeErrorMessage.visibility = View.VISIBLE
                    }
                    else
                        codeErrorMessage.textResource = R.string.pcCodeError
                        codeErrorMessage.visibility = View.VISIBLE
                }
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
        override fun onTick(l: Long) {
            runningDownTimer = true
            pcodeTv.text = "もう一回送ります。("+(l / 1000).toString() + "s)"
        }

        override fun onFinish() {
            runningDownTimer = false
            pcodeTv.text = "重新发送"
            pcodeTv.setOnClickListener { sendVerification() }
        }

    }

    private fun sendVerification() {
        toast("再次发送")
    }

}