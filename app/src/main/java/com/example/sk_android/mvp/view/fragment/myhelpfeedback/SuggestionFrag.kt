package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class SuggestionFrag : Fragment() {

    private lateinit var textClick : TextClick

    companion object {
        fun newInstance():SuggestionFrag{
            return SuggestionFrag()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        textClick = activity as TextClick
        return createV()
    }

    private fun createV(): View? {
        return UI{
            relativeLayout {
                relativeLayout {
                    backgroundResource = R.drawable.button_shape_orange
                    textView {
                        text = "提出"
                        textSize = 16f
                        gravity = Gravity.CENTER
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        centerInParent()
                    }
                    onClick {
                        textClick.clicktichu()
                    }
                }.lparams(matchParent, dip(45)){
                    setMargins(dip(15),0,dip(15),dip(20))
                    alignParentBottom()
                }
            }
        }.view
    }
    interface TextClick{
        suspend fun clicktichu()
    }
}