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

class PfourActionBarFragment:Fragment() {

    var TrpToolbar: Toolbar?=null
    private var mContext: Context? = null
    lateinit var back:mm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): PfourActionBarFragment {
            return PfourActionBarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        back = activity as mm
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
//                        navigationIconResource= R.mipmap.nav_ico_return
                        setNavigationOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
//                                startActivity<TelephoneResetPasswordActivity>()
                                // toast("1234")
                            }

                        })
                    }.lparams(){
                        width = matchParent
                        height =dip(65)
                        alignParentBottom()
                    }

                    textView {
                        textResource = R.string.PfourTitle
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColorResource = R.color.titleColor
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                    }.lparams() {
                        width = matchParent
                        height = wrapContent
                        height =dip(65-getStatusBarHeight(this@PfourActionBarFragment.context!!))
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

    public interface mm {

        fun goback()
    }
}