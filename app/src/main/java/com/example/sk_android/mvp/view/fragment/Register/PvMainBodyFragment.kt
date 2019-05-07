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
import com.example.sk_android.mvp.view.activity.Register.MemberTreatyActivity

class PvMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var verificationCode:EditText
    lateinit var codeErrorMessage:TextView
    lateinit var tool:BaseTool
    lateinit var pcodeTv: TextView
    private var runningDownTimer: Boolean = false
    var phone:String = ""
    var myPhone:String = ""

    companion object {
        fun newInstance(phone:String): PvMainBodyFragment {
            val fragment = PvMainBodyFragment()
            fragment.phone = phone
            fragment.myPhone = phone.substring(phone.length-4)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onStart() {
        super.onStart()
        onPcode()
    }

    override fun onDestroy() {
        super.onDestroy()
        downTimer.onFinish()

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
                    textResource = R.string.pvIntroduction
                    textSize = 21f
                    gravity = Gravity.CENTER
                    textColorResource = R.color.toolBarTextColor
                }.lparams(width = matchParent, height = dip(30)) {
                    topMargin = dip(35)
                }

                textView {
                    text = "番号${myPhone}に検証コードを送信しました"
                    textSize = 12f
                    textColorResource = R.color.SmsNotification
                    gravity = Gravity.CENTER
                }.lparams(width = matchParent,height = wrapContent) {
                    topMargin = dip(35)
                }

                verificationCode = editText {
                    backgroundColorResource = R.color.loginBackground
                    hintResource = R.string.verificationCode
                    hintTextColor = Color.parseColor("#B3B3B3")
                    textSize = 15f //sp
                    inputType = InputType.TYPE_CLASS_PHONE
                    singleLine = true
                }.lparams(){
                     topMargin = dip(35)

                }

                view {
                    backgroundColorResource = R.color.splitLineColor
                }.lparams(width = matchParent, height = dip(2)) {
//                    topMargin = dip(71)
                }

                codeErrorMessage = textView {
                    textResource = R.string.codeErrorMessage
                    visibility = View.GONE
                    textColorResource = R.color.codeMessageColor
                    textSize = 12f //sp
                }.lparams(width = matchParent, height = wrapContent){
                    topMargin = dip(10)
                }

                pcodeTv = textView {
                    text = "もう一回送ります。(10s)"
                    textColor = Color.parseColor("#007AFF")
                    textSize = 12f
                    gravity = Gravity.CENTER
                    setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            onPcode()
                        }
                    })
                }.lparams(width = matchParent,height = wrapContent) {
                    topMargin = dip(10)
                }

                button {
                    backgroundColorResource = R.color.themeColor
                    textResource = R.string.btnCont
                    textColorResource = R.color.white
                    textSize = 18f //sp

                    setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v: View?) {
                            var code = tool.getEditText(verificationCode)
                            if (code != "1234"){
                                codeErrorMessage.visibility =View.VISIBLE
                                verificationCode.setText("")
                            }
                            else
                                startActivity<MemberTreatyActivity>()
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


    //发送验证码按钮
    fun onPcode() {

        //如果60秒倒计时没有结束
        if (runningDownTimer) {
            return
        }

        downTimer.start()  // 倒计时开始

    }

    /**
     * 倒计时
     */
    private val downTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {
        override fun onTick(l: Long) {
            runningDownTimer = true
            pcodeTv.text = "もう一回送ります。("+(l / 1000).toString() + "s)"
        }

        override fun onFinish() {
            runningDownTimer = false
            pcodeTv.text = "重新发送"
        }

    }

}