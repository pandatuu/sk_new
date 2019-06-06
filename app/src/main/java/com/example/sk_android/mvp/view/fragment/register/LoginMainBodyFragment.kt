package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.R.drawable.shape_corner
import com.example.sk_android.mvp.view.activity.register.MemberRegistActivity
import com.example.sk_android.mvp.view.activity.register.TelephoneResetPasswordActivity
import com.yatoooon.screenadaptation.ScreenAdapterTools
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.os.Build
import android.preference.PreferenceManager
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.view.activity.register.ImproveInformationActivity
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.adapter.rxjava2.HttpException


class LoginMainBodyFragment : Fragment() {
    private var mContext: Context? = null
    lateinit var account: EditText
    lateinit var password: EditText
    lateinit var passwordErrorMessage: TextView
    private val img = intArrayOf(R.mipmap.ico_eyes, R.mipmap.ico_eyes_no)
    lateinit var checkBox: CheckBox
    private var flag = false//定义一个标识符，用来判断是apple,还是grape
    lateinit var image: ImageView
    lateinit var countryTextView: TextView
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    lateinit var ms: SharedPreferences


    companion object {
        fun newInstance(): LoginMainBodyFragment {
            return LoginMainBodyFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        ms =  PreferenceManager.getDefaultSharedPreferences(mContext)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        password.transformationMethod = PasswordTransformationMethod.getInstance()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView()
    }

    fun createView(): View {
        var view1: View
        var view = View.inflate(mContext, R.layout.radion, null)
        checkBox = view.findViewById(R.id.cornerstone)
        view1 = UI {
            linearLayout {
                backgroundColorResource = R.color.loginBackground
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(15)
                rightPadding = dip(15)
                textView {
                    textResource = R.string.liIntroduction
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.black33
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(41)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    countryTextView = textView {
                        textResource = R.string.liPhonePrefix
                        textSize = 15f
                        textColorResource = R.color.black20
                        gravity = Gravity.CENTER
                    }.lparams(width = wrapContent, height = matchParent)
                    imageView {
                        backgroundResource = R.mipmap.btn_continue_nor
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                        gravity = Gravity.CENTER_VERTICAL
                    }
                    account = editText {
                        backgroundColorResource = R.color.loginBackground
                        hintResource = R.string.liPhoneHint
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f //sp
                        inputType = InputType.TYPE_CLASS_PHONE
                        singleLine = true
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(40)
                }

                view {
                    backgroundColorResource = R.color.grayF6
                }.lparams(width = matchParent, height = dip(2)) {
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER
                    password = editText {
                        backgroundResource = shape_corner
                        hintResource = R.string.liPassWordHint
                        singleLine = true
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f //sp
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 1f
                    }
                    image = imageView {
                        imageResource = R.mipmap.ico_eyes_no

                        setOnClickListener { changeImage() }
                    }.lparams(width = dip(21), height = dip(12)) {
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams(width = matchParent) {
                    topMargin = dip(38)
                }

                view {
                    backgroundColorResource = R.color.grayF6
                }.lparams(width = matchParent, height = dip(2)) {}

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        gravity = Gravity.LEFT
                        textResource = R.string.liRegist
                        textColorResource = R.color.black33
                        textSize = 12f //sp
                        setOnClickListener { startActivity<MemberRegistActivity>() }
                    }.lparams(height = wrapContent) {
                        weight = 1f
                    }
                    textView {
                        gravity = Gravity.RIGHT
                        textResource = R.string.liForgotPassword
                        textColorResource = R.color.black33
                        textSize = 12f //sp
                        setOnClickListener { startActivity<TelephoneResetPasswordActivity>() }
                    }.lparams(height = wrapContent) {
                        weight = 1f
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(18)
                }

                passwordErrorMessage = textView {
                    textResource = R.string.liPasswordError
                    visibility = View.GONE
                    textColorResource = R.color.brownFF6406
                    //android:enabled = false //not support attribute
                    textSize = 12f //sp
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(26)
                }


                button {
                    backgroundColorResource = R.color.yellowFFB706
                    textResource = R.string.liButton
                    textColorResource = R.color.whiteFF
                    textSize = 18f //sp

                    setOnClickListener {
                        login()
                    }
                }.lparams(width = matchParent, height = dip(47)) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    leftMargin = dip(48)
                    topMargin = dip(35)
                    bottomMargin = dip(36)
                    rightMargin = dip(48)
                }

                addView(view)

            }
        }.view
        ScreenAdapterTools.getInstance().loadView(view1)

        return view1
    }

    //自定义函数
    private fun getUsername(): String {
        //定义变量，? 表示可空
        val username = account.text.toString().trim()
        return if (TextUtils.isEmpty(username))
            ""
        else
            username
    }

    private fun getPassword(): String {
        val password = password.text.toString().trim()
        return if (TextUtils.isEmpty(password))
            ""
        else
            password
    }

    private fun changeImage() {
        if (flag === true) {
            password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            image.setImageResource(img[0])
            flag = false
        } else {
            password.transformationMethod = PasswordTransformationMethod.getInstance()
            image.setImageResource(img[1])
            flag = true
        }

    }

    private fun login() {
        println(ms)
        if (checkBox.isChecked) {
            val userName = getUsername()
            val password = getPassword()
            val countryText = countryTextView.text.toString().trim()
            val country = countryText.substring(1, 3)
            val deviceToken = Build.FINGERPRINT
            val system = "SK"
            val deviceType = "ANDROID"
            val loginType = "PASSWORD"
            val manufacturer = Build.MANUFACTURER
            val deviceModel = Build.MODEL
            val scope = ""

            if (userName.isNullOrBlank()) {
                passwordErrorMessage.textResource = R.string.liAccountEmpty
                passwordErrorMessage.visibility = View.VISIBLE
                return
            }

            if (password.isNullOrBlank()) {
                passwordErrorMessage.textResource = R.string.liPasswordEmpty
                passwordErrorMessage.visibility = View.VISIBLE
                return
            }

            //构造HashMap
            val params = mapOf(
                "username" to userName,
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

            val userJson = JSON.toJSONString(params)

            val body = RequestBody.create(json, userJson)

            var retrofitUils = RetrofitUtils(mContext!!,"https://auth.sk.cgland.top/")

            retrofitUils.create(RegisterApi::class.java)
                .userLogin(body)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    var mEditor: SharedPreferences.Editor = ms.edit()
                    mEditor.putString("token", it.get("token").toString())
                    mEditor.commit()
                    retrofitUils.create(RegisterApi::class.java)
                        .verifyUser()
                        .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            println("进入首页")
                        },{
                            if(it is HttpException){
                                if(it.code() == 401){
                                    startActivity<ImproveInformationActivity>()
                                }
                            }
                        })

//                    startActivity<ImproveInformationActivity>()
                }, {
                    System.out.println(it)
                    if (it is HttpException) {
                        passwordErrorMessage.apply {
                            visibility = View.VISIBLE
                            textResource = if (it.code() == 406) {
                                R.string.liPasswordError
                            } else {
                                R.string.liNetworkError
                            }
                        }
                    }
                })


        } else {
            passwordErrorMessage.textResource = R.string.liCornerstoneError
            passwordErrorMessage.visibility = View.VISIBLE
        }
    }

}
