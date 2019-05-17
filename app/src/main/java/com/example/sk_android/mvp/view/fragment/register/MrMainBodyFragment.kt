package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.tool.BaseTool
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.mvp.view.activity.register.SetPasswordActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI


class MrMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var account:EditText
    lateinit var accountErrorMessage: TextView
    lateinit var tool:BaseTool

    companion object {
        fun newInstance(): MrMainBodyFragment {
            val fragment = MrMainBodyFragment()
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
        tool= BaseTool()
        var view = View.inflate(mContext, R.layout.radion, null)
        return UI {
            linearLayout {
                backgroundColorResource = R.color.mrBackground
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(15)
                rightPadding = dip(15)
                textView {
                    textResource = R.string.mrIntroduction
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.mrIntroductionColor
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(41)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        textResource = R.string.phonePrefix
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
                        hintResource = R.string.mrHint
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f //sp
                        inputType = InputType.TYPE_CLASS_PHONE
                        singleLine = true
                    }
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(38)
                }

                view {
                    backgroundColorResource = R.color.splitColor
                }.lparams(width = matchParent, height = dip(2)) {
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        textResource = R.string.ask
                        textColorResource = R.color.askColor
                        textSize = 12f //sp
                    }.lparams(height = wrapContent) {}
                    textView {
                        textResource = R.string.login
                        textColorResource = R.color.loginColor
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
                    textResource = R.string.accountMessage
                    visibility = View.GONE
                    textColorResource = R.color.mrMessage
                    textSize = 12f //sp
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(10)
                }


                button {
                    backgroundColorResource = R.color.mrButton
                    textResource = R.string.mrButton
                    textColorResource = R.color.mrButtonText
                    textSize = 18f //sp

                    setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var phone = tool.getEditText(account)
                            if ("15110317021" != phone)
                                startActivity<SetPasswordActivity>()
                            else
                                accountErrorMessage.visibility = View.VISIBLE

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
    }

}