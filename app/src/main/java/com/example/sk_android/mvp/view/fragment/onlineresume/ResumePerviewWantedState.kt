package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class ResumePerviewWantedState : Fragment() {

    private lateinit var jobState: TextView

    companion object {
        fun newInstance(): ResumePerviewWantedState {
            return ResumePerviewWantedState()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return creatV()
    }

    fun setJobState(state: String) {
        when (state) {
            "OFF" -> jobState.text = "離職中"
            "ON_NEXT_MONTH" -> jobState.text = "１か月以内には退職予定"
            "ON_CONSIDERING" -> jobState.text = "良い条件が見つかり次第"
            "OTHER" -> jobState.text = "その他"
        }
    }

    private fun creatV(): View {
        return UI {
            verticalLayout {
                //就職状況
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    textView {
                        text = "求職ステータス"
                        textSize = 16f
                        textColor = Color.parseColor("#FF202020")
                        typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerVertically()
                        alignParentLeft()
                    }
                    jobState = textView {
                        textSize = 13f
                        textColor = Color.parseColor("#FF5C5C5C")
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerVertically()
                        alignParentRight()
                        rightMargin = dip(25)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(80)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        }.view
    }
}