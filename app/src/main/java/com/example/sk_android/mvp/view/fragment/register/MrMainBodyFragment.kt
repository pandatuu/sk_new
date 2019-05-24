package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.example.sk_android.mvp.tool.BaseTool
import com.example.sk_android.mvp.tool.HttpUtil
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.yatoooon.screenadaptation.ScreenAdapterTools
import okhttp3.FormBody
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI




class MrMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var account:EditText
    private var stringHashMap: HashMap<String, String>? = null
    lateinit var accountErrorMessage: TextView
    lateinit var tool:BaseTool

    private val originAddress = "https://auth.sk.cgland.top/api/users/verify-code"


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView(): View {
        tool= BaseTool()
        var view1:View
        var view = View.inflate(mContext, com.example.sk_android.R.layout.radion, null)
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
                    textView {
                        textResource = com.example.sk_android.R.string.phonePrefix
                        textSize = 15f
                        textColor = com.example.sk_android.R.color.gray5c
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
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                                startActivity<LoginActivity>()
                            }

                        })
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

                    setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            login()
                        }
                    })
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

    fun login() {
        //检查用户电话的合法性
//        var formBody:FormBody = FormBody.Builder()
//            .add("phone", account.text.toString().trim())
//            .add("country", "86")
//            .add("deviceTyep","ANDROID")
//            .add("codeType","REG")
//            .build()
//        System.out.println(formBody)



        //构造HashMap
        val params = HashMap<String, String>()
        params["phone"]= account.text.toString().trim()
        params["country"] = "86"
        params["deviceType"] = "ANDROID"
        params["codeType"] = "REG"

        val userJson = JSON.toJSONString(params)

        HttpUtil.okPost(originAddress,userJson)

    }

}