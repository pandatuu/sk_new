package com.example.sk_android.mvp.view.fragment.mysystemsetup

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class LoginOutFrag : Fragment() {

    lateinit var mContext: Context
    lateinit var buttomCLick : TextViewCLick

    companion object {
        fun newInstance(context: Context):LoginOutFrag{
            val fragment = LoginOutFrag()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        buttomCLick = activity as TextViewCLick
        var view = createV()

        return view
    }

    private fun createV(): View? {
        return UI{
            relativeLayout {
                gravity = Gravity.CENTER
                linearLayout {
                    isClickable = true
                    backgroundResource = R.drawable.system_logout
                    orientation = LinearLayout.VERTICAL
                    relativeLayout {
                        textView {
                            text = "注意"
                            textSize = 16f
                            textColor = Color.parseColor("#202020")
                        }.lparams(wrapContent, wrapContent){
                            topMargin = dip(20)
                            centerHorizontally()
                        }
                        textView {
                            text = "アプリを終了する？"
                            textSize = 12f
                            textColor = Color.parseColor("#5C5C5C")
                        }.lparams(wrapContent, wrapContent){
                            topMargin = dip(60)
                            leftMargin = dip(15)
                        }
                    }.lparams(matchParent,dip(96))
                    view{
                        backgroundColor = Color.parseColor("#979797")
                    }.lparams(matchParent,dip(1))
                    relativeLayout {
                        textView {
                            gravity = Gravity.CENTER
                            text = "キャンセル"
                            textSize = 14f
                            textColor = Color.parseColor("#007AFF")
                            onClick {
                                buttomCLick.cancelClick()
                            }
                        }.lparams(dip(135), matchParent){
                            alignParentLeft()
                        }
                        view {
                            backgroundColor = Color.parseColor("#979797")
                        }.lparams(dip(1), matchParent){
                            centerHorizontally()
                        }
                        textView {
                            gravity = Gravity.CENTER
                            text = "確定"
                            textSize = 14f
                            textColor = Color.parseColor("#007AFF")
                            onClick {
                                buttomCLick.chooseClick()
                            }
                        }.lparams(dip(135), matchParent){
                            alignParentRight()
                        }
                    }.lparams(matchParent, wrapContent)
                }.lparams(dip(271),dip(141))
            }
        }.view
    }

    interface TextViewCLick{
        fun cancelClick()
        suspend fun chooseClick()
    }
}