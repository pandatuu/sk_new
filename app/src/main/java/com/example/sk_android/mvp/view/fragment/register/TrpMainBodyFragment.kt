package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.mvp.view.activity.register.SetPasswordVerifyActivity
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.adapter.rxjava2.HttpException
import java.util.regex.Matcher
import java.util.regex.Pattern

class TrpMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var telephone:EditText
    lateinit var newPassword:EditText
    lateinit var tool:BaseTool
    lateinit var countryTextView: TextView
    private val img = intArrayOf(R.mipmap.ico_eyes, R.mipmap.ico_eyes_no)
    private lateinit var myDialog: MyDialog
    private var flag = true//定义一个标识符，用来判断是apple,还是grape
    private lateinit var image: ImageView
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    companion object {
        fun newInstance(): TrpMainBodyFragment {
            return TrpMainBodyFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = MyDialog.Builder(activity!!)
            .setMessage(this.getString(R.string.loadingHint))
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()
        mContext = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView()
    }

    fun createView():View{
        tool= BaseTool()
        return UI {
            verticalLayout {
                backgroundColorResource = R.color.whiteFF
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(10)
                rightPadding = dip(10)
                textView {
                    textResource = R.string.trpIntroduction
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.black33
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(41)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    countryTextView = textView {
                        textResource = R.string.trpPonePrefix
                        textSize = 15f
                        textColorResource = R.color.black20
                        gravity = Gravity.CENTER
                    }.lparams(width = wrapContent,height = matchParent)
                    imageView {
                        backgroundResource = R.mipmap.btn_continue_nor
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                        gravity = Gravity.CENTER_VERTICAL
                    }
                    telephone = editText {
                        backgroundColorResource = R.color.loginBackground
                        hintResource =  R.string.trpPhoneHint
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f //sp
                        inputType = InputType.TYPE_CLASS_PHONE
                        filters = arrayOf(InputFilter.LengthFilter(11))
                        singleLine = true
                    }
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(50)
                }

                view {
                    backgroundColorResource = R.color.splitLineColor
                }.lparams(width = matchParent, height = dip(2)) {
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER
                    newPassword = editText {
                        backgroundResource = R.drawable.shape_corner
                        hintResource = R.string.trpPasswordHint
                        singleLine = true
                        hintTextColor = Color.parseColor("#B3B3B3")
                        filters = arrayOf(InputFilter.LengthFilter(16))
                        textSize = 15f //sp
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 4f
                    }
                    image = imageView {
                        imageResource = R.mipmap.ico_eyes_no

                        setOnClickListener { changeImage() }
                    }.lparams(width = dip(51), height = wrapContent) {
                        leftPadding = dip(15)
                        rightPadding = dip(5)
                    }
                }.lparams(width = matchParent){
                    topMargin = dip(36)
                }

                view {
                    backgroundColorResource = R.color.splitLineColor
                }.lparams(width = matchParent, height = dip(2)) {}

                button {
                    backgroundColorResource = R.color.themeColor
                    textResource = R.string.trpButton
                    textColorResource = R.color.whiteFF
                    textSize = 18f //sp

                    onClick {
                        confirmPassword()
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

    private fun changeImage(){
        if (flag === true){
            newPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            image.setImageResource(img[0])
            flag = false
        }
        else {
            newPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            image.setImageResource(img[1])
            flag = true
        }

    }

    @SuppressLint("CheckResult")
    private fun confirmPassword(){
        myDialog.show()

        val telephone = tool.getEditText(telephone)
        val newPassword = tool.getEditText(newPassword)
        val countryText = countryTextView.text.toString().trim()
        val country = countryText.substring(1, 3)
        var deviceModel = Build.MODEL
        var manufacturer = Build.BRAND
        var deviceType = "ANDROID"
        var codeType = "LOGIN"
        var pattern: Pattern = Pattern.compile("^[a-zA-Z](?![a-zA-Z]+\\\$)(?!\\d\\\$)(?=.*\\d)[a-zA-Z\\d\\\$]{7,15}")
        var phonePattern: Pattern = Pattern.compile("/^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}\$/")
        var matcher: Matcher = pattern.matcher(newPassword)
        var matcherOne:Matcher = phonePattern.matcher(telephone)

//        if (!matcherOne.matches()){
//            alert (R.string.trpPhoneError){
//                yesButton { toast("Yes!!!") }
//                noButton { }
//            }.show()
//            return
//        }


        if(!matcher.matches()) {
            alert (R.string.trpPasswordError){
                yesButton { toast("Yes!!!") }
                noButton { }
            }.show()
            myDialog.dismiss()
            return
        }


        val params = mapOf(
            "phone" to telephone,
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
                    startActivity<SetPasswordVerifyActivity>("phone" to telephone,"country" to country,"password" to newPassword)
                }else {
                    myDialog.dismiss()
                    println("获取验证码失效")
                }

            },{
                myDialog.dismiss()
            })



    }

}