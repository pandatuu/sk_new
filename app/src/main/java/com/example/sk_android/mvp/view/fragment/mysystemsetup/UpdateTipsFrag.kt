package com.example.sk_android.mvp.view.fragment.mysystemsetup

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import click
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger

class UpdateTipsFrag : Fragment() {

    lateinit var mContext: Context
    lateinit var buttomCLick : ButtomCLick

    companion object {
        fun newInstance(context: Context):UpdateTipsFrag{
            val fragment = UpdateTipsFrag()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        buttomCLick = activity as ButtomCLick
        var view = createV()

        return view
    }

    private fun createV(): View? {
        return UI{
            linearLayout {
                gravity = Gravity.CENTER
                linearLayout {
                    isClickable = true
                    orientation = LinearLayout.VERTICAL
                    backgroundResource = R.drawable.fourdp_white_dialog
                    verticalLayout {
                        gravity = Gravity.TOP
                        imageView {
                            backgroundResource = R.mipmap.update_background
                        }.lparams(matchParent,dip(154))
                        textView {
                            text = "1.ビデオ面接機能を追加しまし"
                            textSize = 13f
                        }.lparams(wrapContent, wrapContent){
                            leftMargin = dip(15)
                            rightMargin = dip(10)
                        }
                        textView {
                            text = "2.フィードバック情報がbugに遅れていたこ とを復旧しまし"
                            textSize = 13f
                        }.lparams(wrapContent, wrapContent){
                            leftMargin = dip(15)
                            rightMargin = dip(10)
                        }
                    }.lparams(matchParent,dip(237))
                    relativeLayout {
                        button {
                            text = "キャンセル"
                            textSize = 16f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.button_shape_grey
                            this.withTrigger().click {
                                buttomCLick.cancelUpdateClick()
                            }
                        }.lparams(dip(120),dip(40)){
                            topMargin = dip(30)
                            alignParentLeft()
                        }
                        button {
                            text = "確定"
                            textSize = 16f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.yellow_background
                            this.withTrigger().click {
                                buttomCLick.defineClick()
                            }
                        }.lparams(dip(120),dip(40)){
                            topMargin = dip(30)
                            alignParentRight()
                        }
                    }.lparams(matchParent,dip(103)){
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams(matchParent,dip(340)){
                    leftMargin = dip(38)
                    rightMargin = dip(38)
                }
            }
        }.view
    }

    interface ButtomCLick{
        fun cancelUpdateClick()
        fun defineClick()
    }
}