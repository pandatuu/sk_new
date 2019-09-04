package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class ShowExampleFrag : Fragment() {

    var toolbar1: Toolbar? = null
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): ShowExampleFrag {
            return ShowExampleFrag()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {
        return UI {
            linearLayout {
                relativeLayout {
                    textView {
                        backgroundResource = R.drawable.actionbar_bottom_border
                    }.lparams {
                        width = matchParent
                        height = dip(65)

                    }
                    relativeLayout {

                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back
                        }.lparams {
                            width = matchParent
                            height = dip(65)
                            alignParentBottom()

                        }
                    }.lparams {
                        width = matchParent
                        height = dip(65)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(65)
                }
            }
        }.view

    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
            val scale = context.resources.displayMetrics.density
            result = ((result / scale + 0.5f).toInt())
        }
        return result
    }

}




