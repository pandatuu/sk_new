package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.Toolbar
import click
import withTrigger

class ThemeActionBarFragment : Fragment() {

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null
    var condition:Int = 1
    private lateinit var myHeadTest:headTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myHeadTest = activity as headTest
        mContext = activity

    }
    companion object {
        fun newInstance(condition:Int): ThemeActionBarFragment {
            val fragment = ThemeActionBarFragment()
            fragment.condition = condition
            return ThemeActionBarFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout {
                relativeLayout() {

                    imageView {

                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.pic_top)

                    }.lparams() {
                        width = matchParent
                        height =dip(65)

                    }


                    relativeLayout() {


                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource=R.mipmap.icon_back_white


                        }.lparams() {
                            width = matchParent
                            height =dip(65)
                            alignParentBottom()

                        }

                        textView {
                            text = "就職希望を編集"
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                        }.lparams() {
                            width = matchParent
                            height = wrapContent
                            height =dip(65-getStatusBarHeight(this@ThemeActionBarFragment.context!!))
                            alignParentBottom()
                        }

                        textView {
                            text = "保存"
                            textColor = Color.WHITE
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER_VERTICAL
                            textSize = 13f
                            this.withTrigger().click {
                                myHeadTest.submit()
                            }
                        }.lparams() {
                            width = dip(52)
                            height =dip(65-getStatusBarHeight(this@ThemeActionBarFragment.context!!))
                            alignParentRight()
                            alignParentBottom()
                        }
                    }.lparams() {
                        width = matchParent
                        height =dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height =dip(65)
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


    public interface headTest {
        fun submit()
    }

}




