package com.example.sk_android.mvp.view.fragment.onlineresume

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
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class ResumeEditBarFrag : Fragment() {

    interface EditBar{
        fun jumpNextPage()
    }

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null
    lateinit var editBar: EditBar
    var titleShow:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(title:String): ResumeEditBarFrag {
            var f=ResumeEditBarFrag()
            f.titleShow=title
            return f
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        editBar = activity as EditBar
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout {
                relativeLayout() {
                    textView() {
                        backgroundResource = R.drawable.actionbar_bottom_border
                    }.lparams() {
                        width = matchParent
                        height = dip(65)

                    }
                    relativeLayout() {


                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back
                        }.lparams() {
                            width = matchParent
                            height = dip(65)
                            alignParentBottom()

                        }

                        textView {
                            text = titleShow
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColorResource = R.color.toolBarTextColor
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                        }.lparams() {
                            width = matchParent
                            height = wrapContent
                            height = dip(65 - getStatusBarHeight(this@ResumeEditBarFrag.context!!))
                            alignParentBottom()
                        }

                        textView {
                            gravity = Gravity.CENTER_VERTICAL
                            text = "プレビュー"
                            textColor = Color.parseColor("#FFFFB706")
                            textSize = 14f
                            onClick {
                                editBar.jumpNextPage()
                            }
                        }.lparams(){
                            width = wrapContent
                            height = dip(65 - getStatusBarHeight(this@ResumeEditBarFrag.context!!))
                            alignParentRight()
                            alignParentBottom()
                            rightMargin = dip(15)
                        }

                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                }.lparams() {
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




