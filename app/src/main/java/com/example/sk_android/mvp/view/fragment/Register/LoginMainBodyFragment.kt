package com.example.sk_android.mvp.view.fragment.Register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.R.drawable.*
import com.example.sk_android.mvp.view.activity.Register.MemberRegistActivity
import com.example.sk_android.mvp.view.activity.Register.TelephoneResetPasswordActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.dip


class LoginMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var account:EditText
    lateinit var password: EditText
    lateinit var passwordErrorMessage: TextView

    companion object {
        fun newInstance(): LoginMainBodyFragment {
            val fragment = LoginMainBodyFragment()
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

    fun createView(): View {
        return UI {
            linearLayout {
                backgroundColorResource = R.color.loginBackground
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(10)
                rightPadding = dip(10)
                textView {
                    text = "アカウント登録"
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.toolBarTextColor
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(33)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        text = "+81"
                        textSize = 15f
                        textColor = R.color.gray5c
                        gravity = Gravity.CENTER
                    }.lparams(width = wrapContent,height = matchParent)
                    imageView {
                        backgroundResource = R.mipmap.btn_continue_nor
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                        gravity = Gravity.CENTER_VERTICAL
                    }
                    account = editText {
                        backgroundColorResource = R.color.loginBackground
                        hint = "携帯番号を入力してください"
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f //sp
                        inputType = InputType.TYPE_CLASS_PHONE
                        singleLine = true
                    }
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(45)
                }

                view {
                    backgroundColor = Color.parseColor("#F6F6F6")
                }.lparams(width = matchParent, height = dip(2)) {
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    password = editText {
                        backgroundResource = shape_corner
                        hint = "パスワードを入力してください"
                        singleLine = true
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f //sp
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 4f
                    }
                    switch {
                        setThumbResource(shape_switch_thumb)
                        setTrackResource(shape_switch_track_selector)
                    }.lparams(width = dip(50), height = dip(25)) {
                        weight = 1f
                        gravity = Gravity.RIGHT
                    }
                }.lparams(width = matchParent){
                    topMargin = dip(25)
                }

                view {
                    backgroundColor = Color.parseColor("#F6F6F6")
                }.lparams(width = matchParent, height = dip(2)) {}

                passwordErrorMessage = textView {
                    text = "パスワードが間違いました,改めて入力してください!"
                    visibility = View.GONE
                    textColor = Color.parseColor("#FF6406")
                    //android:enabled = false //not support attribute
                    textSize = 12f //sp
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(10)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        gravity = Gravity.LEFT
                        text = "登録"
                        textColor = Color.parseColor("#5C5C5C")
                        textSize = 12f //sp
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                                startActivity<MemberRegistActivity>()
                            }

                        })
                    }.lparams(height = wrapContent) {
                        weight = 1f
                    }
                    textView {
                        gravity = Gravity.RIGHT
                        text = "パスワードを忘れました?"
                        textColor = Color.parseColor("#B3B3B3")
                        textSize = 12f //sp
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                                startActivity<TelephoneResetPasswordActivity>()
                            }

                        })
                    }.lparams(height = wrapContent) {
                        weight = 1f
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(10)
                }


                button {
                    backgroundColor = Color.parseColor("#FFB706")
                    text = "ログイン"
                    textColor = Color.parseColor("#999999")
                    textSize = 18f //sp

                    setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {

                            if ("15110317021" == (getUsername()) && "1234567" == (getPassword()))
                                toast("${getUsername()}+${getPassword()}")
                            else
                                passwordErrorMessage.visibility = View.VISIBLE

                        }
                    })
                }.lparams(width = matchParent, height = dip(47)) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    leftMargin = dip(48)
                    topMargin = dip(35)
                    bottomMargin=dip(36)
                    rightMargin = dip(48)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER
                    imageView {
                        imageResource = R.mipmap.checkbox_nor
                    }.lparams(width = dip(12), height = dip(12)) {
                        gravity = Gravity.CENTER_VERTICAL
                    }
                    textView {
                        text = "上記会員規約と個人情報の取り扱いに同意し会員登録"
                        textSize = 10f //sp
                        textColor = Color.parseColor("#999999")
                    }.lparams(height = matchParent)
                }.lparams(width = matchParent, height = dip(14)) {
                    topMargin = dip(18)
                }

            }
        }.view
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

}