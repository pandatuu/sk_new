package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class TrpActionBarFragment:Fragment() {

    var TrpToolbar: Toolbar?=null
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): TrpActionBarFragment {
            return TrpActionBarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }

    private fun createView():View{
        return UI {
            verticalLayout {
                backgroundResource = R.drawable.action_bar_border
                relativeLayout(){
                    textView {
                        backgroundColorResource = R.color.splitLineColor
                    }

                    TrpToolbar = toolbar {
                        backgroundResource = R.color.transparent
                        isEnabled = true
                        title = ""
                        navigationIconResource= R.mipmap.nav_ico_return
                    }.lparams(){
                        width = matchParent
                        height =dip(65)
                        alignParentBottom()
                    }

                    textView {
                        textResource = R.string.trpTitle
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColorResource = R.color.black33
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                    }.lparams() {
                        width = matchParent
                        height = wrapContent
                        height =dip(65-getStatusBarHeight(this@TrpActionBarFragment.context!!))
                        alignParentBottom()
                    }
                }.lparams(){
                    width = matchParent
                    height = dip(65)
                }
            }
        }.view
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }

}