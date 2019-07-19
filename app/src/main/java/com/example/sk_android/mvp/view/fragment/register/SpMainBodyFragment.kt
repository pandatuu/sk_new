package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.mvp.view.activity.register.RegisterLoginActivity
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import withTrigger
import java.util.regex.Matcher
import java.util.regex.Pattern

class SpMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var password:EditText
    lateinit var repeatPassword:EditText
    lateinit var tool:BaseTool
    private val img = intArrayOf(R.mipmap.ico_eyes, R.mipmap.ico_eyes_no)
    private var flag = false//定义一个标识符，用来判断是apple,还是grape
    lateinit var newPasswordImage: ImageView
    lateinit var passwordImage:ImageView
    var phone:String = ""
    var code:String = ""
    var country = ""
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    private lateinit var myDialog: MyDialog

    companion object {
        fun newInstance(phone:String,code:String,country:String): SpMainBodyFragment {
            val fragment = SpMainBodyFragment()
            fragment.phone = phone
            fragment.code = code
            fragment.country = country
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        password.transformationMethod = PasswordTransformationMethod.getInstance()
        repeatPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView()
    }

    fun createView():View{
        tool= BaseTool()
        return UI {
            verticalLayout {
                backgroundColorResource = R.color.loginBackground
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(13)
                rightPadding = dip(13)

                onClick {
                    closeKeyfocus()
                }

                textView {
                    textResource = R.string.spIntroduction
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.spIntroductionColor
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(35)
                }

                textView {
                    textResource = R.string.spRemind
                    textSize = 12f
                    textColorResource = R.color.spRemindColor
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(16)
                }


                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER
                    password = editText {
                        backgroundResource = R.drawable.shape_corner
                        hintResource = R.string.spEmail
                        singleLine = true
                        hintTextColor = Color.parseColor("#B3B3B3")
                        filters = arrayOf(InputFilter.LengthFilter(16))
                        textSize = 15f //sp
                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 1F
                    }

                    passwordImage = imageView {
                        imageResource = R.mipmap.ico_eyes_no

                        setOnClickListener { changeImage(password,passwordImage) }
                    }.lparams(width = dip(51), height = wrapContent) {
                    }



                }.lparams(width = matchParent){
                    topMargin = dip(27)
                }

                view {
                    backgroundColorResource = R.color.spSplit
                }.lparams(width = matchParent, height = dip(2)) {
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER
                    repeatPassword = editText {
                        backgroundResource = R.drawable.shape_corner
                        hintResource = R.string.spPassword
                        singleLine = true
                        hintTextColor = Color.parseColor("#B3B3B3")
                        filters = arrayOf(InputFilter.LengthFilter(16))
                        textSize = 15f //sp
                    }.lparams(width = wrapContent, height = wrapContent) {
                        weight = 1F
                    }

                    newPasswordImage = imageView {
                        imageResource = R.mipmap.ico_eyes_no

                        setOnClickListener { changeImage(repeatPassword,newPasswordImage) }
                    }.lparams(width = dip(51), height = wrapContent) {
                    }



                }.lparams(width = matchParent){
                    topMargin = dip(36)
                }

                view {
                    backgroundColorResource = R.color.spSplit
                }.lparams(width = matchParent, height = dip(2)) {
                }

                button {
                    backgroundColorResource = R.color.themeColor
                    textResource = R.string.spButton
                    textColorResource = R.color.spButtonText
                    textSize = 18f //sp

                    this.withTrigger().click{submit()}
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

    @SuppressLint("CheckResult")
    private fun submit() {
        myDialog.show()
            var password = tool.getEditText(password)
            var repeatPassword = tool.getEditText(repeatPassword)

            var pattern: Pattern = Pattern.compile("^[a-zA-Z](?![a-zA-Z]+\\\$)(?!\\d\\\$)(?=.*\\d)[a-zA-Z\\d\\\$]{7,15}")
            var matcher: Matcher = pattern.matcher(password)
            if(!matcher.matches()){
                var toast = Toast.makeText(mContext, this.getString(R.string.spPasswordError), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                myDialog.dismiss()
                return
            }


            if(repeatPassword != password ){
                var toast = Toast.makeText(mContext, this.getString(R.string.spPasswordInconsistent), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                myDialog.dismiss()
                return
            }

        val params = HashMap<String, String>()
        params["country"] = country
        params["username"] = phone
        params["code"] = code
        params["password"] = password
        params["system"] = "SK"
        params["deviceType"] = "ANDROID"


        val userJson = JSON.toJSONString(params)

        val body = RequestBody.create(json,userJson)

        var retrofitUils = RetrofitUtils(mContext!!,this.getString(R.string.authUrl))

        retrofitUils.create(RegisterApi::class.java)
            .userRegister(body)
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                myDialog.dismiss()
                startActivity<LoginActivity>()
            },{
                myDialog.dismiss()
                System.out.println(it)
            })



    }


    private fun changeImage(result:EditText,imageView: ImageView) {
        if (flag === true) {
            result.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageView.setImageResource(img[0])
            flag = false
        } else {
            result.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView.setImageResource(img[1])
            flag = true
        }
    }

    private fun closeKeyfocus(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        password.clearFocus()
        repeatPassword.clearFocus()
    }



}