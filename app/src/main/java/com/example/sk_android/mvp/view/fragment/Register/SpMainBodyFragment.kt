package com.example.sk_android.mvp.view.fragment.Register

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
import com.example.sk_android.mvp.tool.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.widget.TextView
import android.os.CountDownTimer
import android.text.InputType
import com.example.sk_android.mvp.view.activity.Register.ImproveInformationActivity
import com.example.sk_android.mvp.view.activity.Register.LoginActivity
import com.example.sk_android.mvp.view.activity.Register.MemberTreatyActivity

class SpMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var tool:BaseTool

    companion object {
        fun newInstance(): SpMainBodyFragment {
            val fragment = SpMainBodyFragment()
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
                    textResource = R.string.spIntroduction
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.spIntroductionColor
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(35)
                }

                email = editText {
                    backgroundColorResource = R.color.loginBackground
                    hintResource = R.string.spEmail
                    hintTextColor = Color.parseColor("#B3B3B3")
                    textSize = 15f //sp
                    inputType = InputType.TYPE_CLASS_PHONE
                    singleLine = true
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(45)
                }

                view {
                    backgroundColorResource = R.color.spSplit
                }.lparams(width = matchParent, height = dip(2)) {
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    password = editText {
                        backgroundResource = R.drawable.shape_corner
                        hintResource = R.string.spPassword
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
                    topMargin = dip(30)
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

                    setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var email = tool.getEditText(email)
                            var password = tool.getEditText(password)
                            if (email == "")
                                alert ("邮件不可为空"){
                                    yesButton { toast("Yes!!!") }
                                    noButton { }
                                }.show()

                            if(password == "")
                                alert ("密码不可为空"){
                                    yesButton { toast("Yes!!!") }
                                    noButton { }
                                }.show()
                            else
                                startActivity<ImproveInformationActivity>()
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