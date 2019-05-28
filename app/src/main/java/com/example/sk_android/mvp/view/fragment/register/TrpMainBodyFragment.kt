package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
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
    private val img = intArrayOf(R.mipmap.ico_eyes, R.mipmap.ico_eyes_no)
    private var flag = false//定义一个标识符，用来判断是apple,还是grape
    lateinit var image: ImageView

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
                    textView {
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
                        textSize = 15f //sp
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 4f
                    }
                    image = imageView {
                        imageResource = R.mipmap.ico_eyes

                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?){
                                changeImage()
                            }
                        })
                    }.lparams(width = dip(21),height = dip(12)){
                        leftMargin = dip(15)
                        rightMargin = dip(15)
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

    fun changeImage(){
        if (flag === true){
            newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            image.setImageResource(img[0])
            flag = false
        }
        else {
            newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            image.setImageResource(img[1])
            flag = true
        }

    }

}