package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.content.Intent
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
import com.example.sk_android.mvp.model.register.Education
import com.example.sk_android.mvp.model.register.Work
import com.example.sk_android.mvp.view.activity.register.PersonInformationFourActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import java.io.Serializable

class PthreeActionBarFragment:Fragment() {

    var TrpToolbar: Toolbar?=null
    private var mContext: Context? = null
    var resumeId:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(resumeId:String): PthreeActionBarFragment {
            val fragment= PthreeActionBarFragment()
            fragment.resumeId = resumeId
            return fragment
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
                        textResource = R.string.PthreeTitle
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColorResource = R.color.titleColor
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                    }.lparams() {
                        width = matchParent
                        height = wrapContent
                        height =dip(65-getStatusBarHeight(this@PthreeActionBarFragment.context!!))
                        alignParentBottom()
                    }


                    button {
                        textResource = R.string.jumpOver
                        textColorResource = R.color.whiteFF
                        backgroundResource = R.drawable.button_shape_right
                        gravity = Gravity.CENTER_VERTICAL
                        textSize = 12f
                        onClick {
                            jump()
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        rightMargin = dip(15)
                        bottomMargin = dip(10)
                        topMargin = dip(25)
                        leftMargin = dip(13)

                        alignParentRight()

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

    private fun jump(){
        startActivity<PersonInformationFourActivity>("resumeId" to resumeId)
    }

}