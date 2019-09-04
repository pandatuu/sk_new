package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import click
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger

class ResumeEditBarFrag : Fragment() {

    interface EditBar{
        fun jumpNextPage()
    }

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null
    lateinit var editBar: EditBar
    lateinit var titleView: TextView
    lateinit var defaultText: TextView
    var titleShow:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(title:String): ResumeEditBarFrag {
            val f=ResumeEditBarFrag()
            f.titleShow=title
            return f
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        editBar = activity as EditBar
        val fragmentView=createView()
        mContext = activity
        return fragmentView
    }

    fun setTiltle(text1: String){
        titleView.text = text1
        if(titleView.text.isEmpty()) {
            defaultText.text = "履歴書"
        }else{
            defaultText.text = "の履歴書"
        }
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
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back
                        }.lparams() {
                            width = dip(9)
                            height = dip(15)
                            leftMargin = dip(15)
                            bottomMargin = dip(15)
                            gravity = Gravity.BOTTOM
                        }

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            titleView = textView {
                                text = titleShow
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColorResource = R.color.toolBarTextColor
                                textSize = 16f
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                                ellipsize = TextUtils.TruncateAt.END
                                maxLines = 1
                                maxWidth = dip(140)
                            }.lparams() {
                                width = wrapContent
                                height = matchParent
                            }
                            defaultText = textView {
                                if(titleView.text.isEmpty()) {
                                    text = "履歴書"
                                }else{
                                    text = "の履歴書"
                                }
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColorResource = R.color.toolBarTextColor
                                textSize = 16f
                                maxLines = 1
                            }.lparams() {
                                width = wrapContent
                                height = matchParent
                            }
                        }.lparams(){
                            width = dip(0)
                            height = dip(65 - getStatusBarHeight(this@ResumeEditBarFrag.context!!))
                            weight = 1f
                            gravity = Gravity.BOTTOM
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }

                        textView {
                            gravity = Gravity.CENTER_VERTICAL
                            text = "プレビュー"
                            textColor = Color.parseColor("#FFFFB706")
                            textSize = 14f
                            this.withTrigger().click {
                                editBar.jumpNextPage()
                            }
                        }.lparams(){
                            width = wrapContent
                            height = dip(65 - getStatusBarHeight(this@ResumeEditBarFrag.context!!))
                            gravity = Gravity.BOTTOM
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




