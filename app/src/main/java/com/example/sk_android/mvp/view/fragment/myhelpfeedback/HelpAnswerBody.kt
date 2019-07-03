package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import anet.channel.util.Utils.context
import com.example.sk_android.R
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.google.gson.JsonObject
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class HelpAnswerBody : Fragment() {

    lateinit var mContext: Context
    var mList = mutableListOf<HelpModel>()
    var title = ""
    var content = ""

    companion object {
        fun newInstance(list: MutableList<HelpModel>, context: Context): HelpAnswerBody {
            val fragment = HelpAnswerBody()
            fragment.mContext = context
            fragment.mList = list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = createV()
        return view
    }

    private fun createV(): View? {
        if (mList.size > 0) {
            println("mList----------------------------${mList}")
            title = mList.get(0).title
            content = mList.get(0).content
        }

        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL

                verticalLayout {
                    textView {
                        text = title
                        textSize = 18f
                        textColor = Color.parseColor("#FF333333")
                        typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(20)
                        bottomMargin = dip(18)
                        leftMargin = dip(15)
                    }

                    textView {
                        text = content
                        textSize = 13f
                        textColor = Color.parseColor("#FF333333")
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        leftMargin = dip(15)

                    }
                }.lparams {
                    width = matchParent
                    height = dip(82)
                }
            }
        }.view
    }
}