package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
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
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.mysystemsetup.SystemSetupApi
import com.example.sk_android.mvp.api.person.User
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.activity.register.ImproveInformationActivity
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.mvp.view.activity.register.RegisterLoginActivity
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import com.umeng.message.IUmengCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException
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

    val deviceToken = App.getInstance()?.getDeviceToken()
    val system = "SK"
    val deviceType = "ANDROID"
    val loginType = "PASSWORD"
    val manufacturer = Build.MANUFACTURER
    val deviceModel = Build.MODEL
    val scope = "offline_access"
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
//            val toast = Toast.makeText(context, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
//            toast.setGravity(Gravity.CENTER, 0, 0)
//            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }

    lateinit var ms: SharedPreferences

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

        mContext = activity

        ms = PreferenceManager.getDefaultSharedPreferences(mContext)
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
                        textSize = 13f //sp
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
                        textSize = 13f //sp
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

                onClick {
                    closeKeyfocus()
                }
            }
        }.view
    }

    @SuppressLint("CheckResult")
    private fun submit() {
        thisDialog=DialogUtils.showLoading(context!!)
        mHandler.postDelayed(r, 12000)
            var password = tool.getEditText(password)
            var repeatPassword = tool.getEditText(repeatPassword)

            var pattern: Pattern = Pattern.compile("^[a-zA-Z](?![a-zA-Z]+\\\$)(?!\\d\\\$)(?=.*\\d)[a-zA-Z\\d\\\$]{7,15}")
            var matcher: Matcher = pattern.matcher(password)
            if(!matcher.matches()){
                var toast = Toast.makeText(mContext, this.getString(R.string.spPasswordError), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                DialogUtils.hideLoading(thisDialog)
                return
            }


            if(repeatPassword != password ){
                var toast = Toast.makeText(mContext, this.getString(R.string.spPasswordInconsistent), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                DialogUtils.hideLoading(thisDialog)
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


        val loginParams = mapOf(
            "username" to phone,
            "password" to password,
            "country" to country,
            "deviceToken" to deviceToken,
            "system" to system,
            "deviceType" to deviceType,
            "loginType" to loginType,
            "manufacturer" to manufacturer,
            "deviceModel" to deviceModel,
            "scope" to scope
        )

        val loginJson = JSON.toJSONString(loginParams)

        val loginBody = RequestBody.create(json, loginJson)

        var retrofitUils = RetrofitUtils(mContext!!,this.getString(R.string.authUrl))

        retrofitUils.create(RegisterApi::class.java)
            .userRegister(body)
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({

                //   登录完成,取到token
                retrofitUils.create(RegisterApi::class.java)
                    .userLogin(loginBody)
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        Log.i("login", it.toString())

                        var mEditor: SharedPreferences.Editor = ms.edit()

                        mEditor.putString("token", it.get("token").toString())
                        mEditor.putString("phone",phone)
                        mEditor.putString("password",password)
                        mEditor.putString("country",country)
                        mEditor.commit()


                        // 通过token,判定是否完善个人信息

                        var requestUserInfo = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))
                        //获取用户是否通知推送通知
                        requestUserInfo.create(SystemSetupApi::class.java)
                            .getUserInformation()
                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                val bool = it.body()?.get("remind")?.asBoolean?:true
                                val push = App.getInstance()?.getPushAgent()
                                var mEditor: SharedPreferences.Editor = ms.edit()
                                mEditor.putBoolean("isNofication",bool)
                                mEditor.commit()
                                if(bool){
                                    push?.enable(object: IUmengCallback {
                                        override fun onSuccess() {
                                            println("推送打开")
                                        }

                                        override fun onFailure(p0: String?, p1: String?) {

                                        }

                                    })
                                }else{
                                    push?.disable(object: IUmengCallback {
                                        override fun onSuccess() {
                                            println("推送关闭")
                                        }

                                        override fun onFailure(p0: String?, p1: String?) {

                                        }

                                    })
                                }
                            }, {

                            })

                        //重置socket
                        var application = App.getInstance()
                        application!!.initMessage()

                        //跳转信息完善页面
                        val i = Intent(activity, ImproveInformationActivity::class.java)
                        startActivity(i)
                        activity!!.finish()
                        activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                    }, {
                        println(it)
                        DialogUtils.hideLoading(thisDialog)
                        System.out.println(it)
                        if (it is HttpException) {
                             var result = this.getString(R.string.liNetworkError)
                             when(it.code()){
                                404 -> result = this.getString(R.string.liNoAccount)
                                406 -> result = this.getString(R.string.liPasswordError)
                                else -> result = this.getString(R.string.liNetworkError)
                            }
                            toast(result)
                        }
                    })
            },{
                DialogUtils.hideLoading(thisDialog)
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

    fun closeKeyfocus(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        password.clearFocus()
        repeatPassword.clearFocus()
    }



}