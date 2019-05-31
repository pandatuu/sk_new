package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
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
import android.widget.ImageView
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.adapter.rxjava2.HttpException

class SpMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var password:EditText
    lateinit var repeatPassword:EditText
    lateinit var tool:BaseTool
    private val img = intArrayOf(R.mipmap.ico_eyes, R.mipmap.ico_eyes_no)
    private var flag = false//定义一个标识符，用来判断是apple,还是grape
    lateinit var image: ImageView
    var phone:String = ""
    var code:String = ""
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")

    companion object {
        fun newInstance(phone:String,code:String): SpMainBodyFragment {
            val fragment = SpMainBodyFragment()
            fragment.phone = phone
            fragment.code = code
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
                leftPadding = dip(13)
                rightPadding = dip(13)
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

                password = editText {
                    backgroundColorResource = R.color.loginBackground
                    hintResource = R.string.spEmail
                    hintTextColor = Color.parseColor("#B3B3B3")
                    textSize = 15f //sp
                    singleLine = true
                }.lparams(width = matchParent, height = wrapContent){
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
                        textSize = 15f //sp
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 4f
                    }

                    image = imageView {
                        imageResource = R.mipmap.ico_eyes

                        setOnClickListener { changeImage() }
                    }.lparams(width = dip(21),height = dip(12)){
                        leftMargin = dip(15)
                        rightMargin = dip(15)
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

                    setOnClickListener{submit()}
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

    private fun submit() {

            var password = tool.getEditText(password)
            var repeatPassword = tool.getEditText(repeatPassword)
            if (password == "") {
                alert ("密码不可为空"){
                    yesButton { toast("Yes!!!") }
                    noButton { }
                }.show()
                return
            }


            if(repeatPassword == ""){
                alert ("重复密码不可为空"){
                    yesButton { toast("Yes!!!") }
                    noButton { }
                }.show()
                return
            }

        val params = HashMap<String, String>()
        params["country"] = "86"
        params["username"] = phone
        params["code"] = code
        params["password"] = password
        params["system"] = "SK"
        params["deviceType"] = "ANDROID"


        val userJson = JSON.toJSONString(params)
        System.out.println(userJson.toString())

        val body = RequestBody.create(json,userJson)
        System.out.println(body)

        var retrofitUils = RetrofitUtils("https://auth.sk.cgland.top/")

        retrofitUils.create(RegisterApi::class.java)
            .userRegister(body)
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                startActivity<LoginActivity>()
            },{
                System.out.println(it)
                print("123456")
            })



    }


    private fun changeImage(){
        if (flag === true){
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            image.setImageResource(img[0])
            flag = false
        }
        else {
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            image.setImageResource(img[1])
            flag = true
        }

    }

}