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
import com.example.sk_android.R
import com.example.sk_android.mvp.tool.BaseTool
import com.example.sk_android.mvp.view.activity.register.PasswordVerifyActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class TrpMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var telephone:EditText
    lateinit var newPassword:EditText
    lateinit var tool:BaseTool

    companion object {
        fun newInstance(): TrpMainBodyFragment {
            val fragment = TrpMainBodyFragment()
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
        tool=BaseTool()
        return UI {
            verticalLayout {
                backgroundColorResource = R.color.loginBackground
                orientation = LinearLayout.VERTICAL
                leftPadding = dip(10)
                rightPadding = dip(10)
                textView {
                    text = "パスワードを再設定します"
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.toolBarTextColor
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(35)
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
                    telephone = editText {
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
                    backgroundColorResource = R.color.splitLineColor
                }.lparams(width = matchParent, height = dip(2)) {
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    newPassword = editText {
                        backgroundResource = R.drawable.shape_corner
                        hint = "新しいパスワードを入力してください"
                        singleLine = true
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f //sp
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 4f
                    }
                    switch {
                        setThumbResource(R.drawable.shape_switch_thumb)
                        setTrackResource(R.drawable.shape_switch_track_selector)
                    }.lparams(width = dip(50), height = dip(25)) {
                        weight = 1f
                        gravity = Gravity.RIGHT
                    }
                }.lparams(width = matchParent){
                    topMargin = dip(25)
                }

                view {
                    backgroundColorResource = R.color.splitLineColor
                }.lparams(width = matchParent, height = dip(2)) {}

                button {
                    backgroundColorResource = R.color.themeColor
                    text = "次へ"
                    textColorResource = R.color.lebelTextColor
                    textSize = 18f //sp

                    setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var telephone = tool.getEditText(telephone)
                            var newPassword = tool.getEditText(newPassword)
                            if (telephone == "")
                                alert ("电话不可为空"){
                                    yesButton { toast("Yes!!!") }
                                    noButton { }
                                }.show()

                            if(newPassword == "")
                                alert ("密码不可为空"){
                                    yesButton { toast("Yes!!!") }
                                    noButton { }
                                }.show()

                            else
                                startActivity<PasswordVerifyActivity>("phone" to telephone)
                        }
                    })
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

}