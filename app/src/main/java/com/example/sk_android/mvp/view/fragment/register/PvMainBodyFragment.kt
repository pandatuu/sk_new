package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
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
import android.widget.Toast
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.view.activity.register.PasswordVerifyActivity
import com.example.sk_android.mvp.view.activity.register.SetPasswordActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.adapter.rxjava2.HttpException
import withTrigger

class PvMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var verificationCode:EditText
    lateinit var codeErrorMessage:TextView
    lateinit var tool: BaseTool
    lateinit var pcodeTv: TextView
    private var runningDownTimer: Boolean = false
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(context, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }
    var phone:String = ""
    var myPhone:String = ""
    var country = ""
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    companion object {
        fun newInstance(phone:String,country:String): PvMainBodyFragment {
            val fragment = PvMainBodyFragment()
            fragment.phone = phone
            fragment.country = country
            fragment.myPhone = phone.substring(phone.length-4)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = activity
        onPcode()
    }

    override fun onDestroy() {
        super.onDestroy()
        downTimer.onFinish()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView():View{
        tool= BaseTool()
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
                    text = "携帯番号${myPhone}に認証コードを送りました"
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
                }.lparams(matchParent, wrapContent) {
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
                    textResource = R.string.pvButton
                    textColorResource = R.color.white
                    textSize = 18f //sp

                    onClick {
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
    private suspend fun submit() {
        thisDialog=DialogUtils.showLoading(context!!)
        mHandler.postDelayed(r, 12000)
        var myCode = tool.getEditText(verificationCode)
        if(myCode == ""){
            codeErrorMessage.textResource = R.string.pvCodeEmpty
            codeErrorMessage.visibility = View.VISIBLE
            DialogUtils.hideLoading(thisDialog)
            return
        }


        val params = HashMap<String, String>()
        params["country"] = country
        params["phone"] = phone
        params["code"] = myCode

        val userJson = JSON.toJSONString(params)
        System.out.println(userJson.toString())

        val body = RequestBody.create(json,userJson)
        System.out.println(body)
        var retrofitUils = RetrofitUtils(mContext!!,this.getString(R.string.authUrl))

        try {
            var it = retrofitUils.create(RegisterApi::class.java)
                .checkVerification(body)
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
                var code = it.code()
            if(code in 200..299){
                DialogUtils.hideLoading(thisDialog)
                startActivity<SetPasswordActivity>("phone" to phone,"code" to myCode,"country" to country)
            }else{
                DialogUtils.hideLoading(thisDialog)
                codeErrorMessage.visibility = View.VISIBLE
                if(code == 406){
                    codeErrorMessage.textResource = R.string.codeErrorMessage
                }
                else {
                    codeErrorMessage.textResource = R.string.pcCodeError
                }
            }

        }catch (it:Throwable){
            DialogUtils.hideLoading(thisDialog)
            println("失败")
            println(it)
        }

    }


    //发送验证码按钮
    fun onPcode() {

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

        thisDialog= DialogUtils.showLoading(context!!)
        mHandler.postDelayed(r, 12000)

        val deviceModel: String = Build.MODEL
        val manufacturer: String = Build.BRAND

        val params = HashMap<String, String>()
        params["phone"] = phone
        params["country"] = country
        params["deviceType"] = "ANDROID"
        params["codeType"] = "REG"
        params["deviceModel"] = deviceModel
        params["manufacturer"] = manufacturer

        val userJson = JSON.toJSONString(params)

        val body = RequestBody.create(json, userJson)

        var retrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.authUrl))

        retrofitUils.create(RegisterApi::class.java)
            .getVerification(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                var code =it.code()
                if(code in 200..299){
                    DialogUtils.hideLoading(thisDialog)
                    onPcode()
                }else {
                    DialogUtils.hideLoading(thisDialog)
                }
            },{
                DialogUtils.hideLoading(thisDialog)
            })
    }

    fun closeKeyfocus(){
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        verificationCode.clearFocus()
    }

}