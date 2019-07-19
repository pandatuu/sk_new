package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.jobWanted.JobState
import com.example.sk_android.mvp.model.onlineresume.jobWanted.JobWantedModel
import com.example.sk_android.mvp.model.onlineresume.jobWanted.SalaryType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.util.*

class ResumeEditWantedState : Fragment() {

    interface WantedFrag{
        fun jobState()
    }

    private lateinit var want: WantedFrag
    private lateinit var jobState: TextView

    companion object {
        fun newInstance(): ResumeEditWantedState {
            return ResumeEditWantedState()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        want = activity as WantedFrag
        return creatV()
    }

    fun setJobState(state: String){
        when(state){
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
                        text = "就職状況"
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
                    imageView {
                        imageResource = R.mipmap.icon_go_position
                        this.withTrigger().click {
                            want.jobState()
                        }
                    }.lparams {
                        width = dip(6)
                        height = dip(11)
                        centerVertically()
                        alignParentRight()
                    }
                    this.withTrigger().click {
                        want.jobState()
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