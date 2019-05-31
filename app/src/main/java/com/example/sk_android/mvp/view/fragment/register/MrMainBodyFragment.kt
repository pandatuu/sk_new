package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.yatoooon.screenadaptation.ScreenAdapterTools
import okhttp3.MediaType
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.register.PasswordVerifyActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import com.example.sk_android.utils.RetrofitUtils
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.adapter.rxjava2.HttpException
import java.util.regex.Matcher
import java.util.regex.Pattern


class MrMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var account:EditText
    private var stringHashMap: HashMap<String, String>? = null
    lateinit var accountErrorMessage: TextView
    lateinit var tool: BaseTool
    lateinit var checkBox:CheckBox
    lateinit var countryTextView: TextView

    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")


    companion object {
        lateinit var TAG: String

        fun newInstance(): MrMainBodyFragment {
            val fragment = MrMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        stringHashMap = HashMap()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView()
    }

    fun createView(): View {
        tool= BaseTool()
        var view1:View
        var view = View.inflate(mContext, com.example.sk_android.R.layout.radion, null)
        checkBox = view.findViewById(R.id.cornerstone)
        view1 = UI {
            linearLayout {
                backgroundColorResource = com.example.sk_android.R.color.mrBackground
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(15)
                rightPadding = dip(15)
                textView {
                    textResource = com.example.sk_android.R.string.mrIntroduction
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = com.example.sk_android.R.color.mrIntroductionColor
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(41)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    countryTextView = textView {
                        textResource = com.example.sk_android.R.string.phonePrefix
                        textSize = 15f
                        textColor = com.example.sk_android.R.color.black20
                        gravity = Gravity.CENTER
                    }.lparams(width = wrapContent,height = matchParent)
                    imageView {
                        backgroundResource = com.example.sk_android.R.mipmap.btn_continue_nor
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                        gravity = Gravity.CENTER_VERTICAL
                    }
                    account = editText {
                        backgroundColorResource = com.example.sk_android.R.color.loginBackground
                        hintResource = com.example.sk_android.R.string.mrHint
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f //sp
                        inputType = InputType.TYPE_CLASS_PHONE
                        singleLine = true
                    }
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(38)
                }

                view {
                    backgroundColorResource = com.example.sk_android.R.color.splitColor
                }.lparams(width = matchParent, height = dip(2)) {
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        textResource = com.example.sk_android.R.string.ask
                        textColorResource = com.example.sk_android.R.color.askColor
                        textSize = 12f //sp
                    }.lparams(height = wrapContent) {}
                    textView {
                        textResource = com.example.sk_android.R.string.login
                        textColorResource = com.example.sk_android.R.color.loginColor
                        textSize = 12f //sp
                        setOnClickListener { startActivity<LoginActivity>() }
                    }.lparams(height = wrapContent) {

                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(17)
                }

                accountErrorMessage = textView {
                    textResource = com.example.sk_android.R.string.accountMessage
                    visibility = View.GONE
                    textColorResource = com.example.sk_android.R.color.mrMessage
                    textSize = 12f //sp
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(10)
                }


                button {
                    backgroundColorResource = com.example.sk_android.R.color.mrButton
                    textResource = com.example.sk_android.R.string.mrButton
                    textColorResource = com.example.sk_android.R.color.mrButtonText
                    textSize = 18f //sp

                    setOnClickListener { login() }
                }.lparams(width = matchParent, height = dip(47)) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    topMargin = dip(100)
                }

                linearLayout{
                    gravity = Gravity.CENTER
                    addView(view)
                }.lparams(width = wrapContent,height = wrapContent){
                    weight = 1f
                }

            }
        }.view

        ScreenAdapterTools.getInstance().loadView(view1)

        return view1
    }

    private fun login() {
        if(checkBox.isChecked){
            var myPhone:String = account.text.toString().trim()
            var deviceModel:String = Build.MODEL
            var manufacturer:String = Build.BRAND
            var countryText = countryTextView.text.toString().trim();
            var country:String = countryText.substring(1,3)
            var pattern: Pattern = Pattern.compile("/^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}\$/")
            var matcher: Matcher = pattern.matcher(myPhone)
            if(myPhone == ""){
                accountErrorMessage.textResource = R.string.mrTelephoneEmpty
                accountErrorMessage.visibility = View.VISIBLE
                return
            }

//          测试阶段先暂时屏蔽
//            if(!matcher.matches()){
//                accountErrorMessage.textResource = R.string.mrTelephoneFormat
//                accountErrorMessage.visibility = View.VISIBLE
//                return
//            }

            //构造HashMap
            val params = HashMap<String, String>()
            params["phone"]= account.text.toString().trim()
            params["country"] = country
            params["deviceType"] = "ANDROID"
            params["codeType"] = "REG"
            params["deviceModel"] = deviceModel
            params["manufacturer"] = manufacturer

            val userJson = JSON.toJSONString(params)


            val body = RequestBody.create(json,userJson)

            var retrofitUils = RetrofitUtils("https://auth.sk.cgland.top/")

            retrofitUils.create(RegisterApi::class.java)
                .getVerification(body)
                 .map { it ?: "" }
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    startActivity<PasswordVerifyActivity>("phone" to myPhone)
                },{
                    if(it is HttpException){
                        if(it.code() == 409){
                            accountErrorMessage.textResource = R.string.accountMessage
                            accountErrorMessage.visibility = View.VISIBLE
                        }
                        else {
                            accountErrorMessage.textResource = R.string.mrNetworkError
                            accountErrorMessage.visibility = View.VISIBLE
                        }
                    }
                })

        }
        else {
            accountErrorMessage.textResource = R.string.mrCornerstoneError
            accountErrorMessage.visibility = View.VISIBLE
        }
    }

}

