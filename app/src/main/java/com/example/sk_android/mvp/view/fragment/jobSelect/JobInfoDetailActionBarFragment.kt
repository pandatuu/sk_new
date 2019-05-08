package com.example.sk_android.mvp.view.fragment.jobSelect


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


class JobInfoDetailActionBarFragment : Fragment() {

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): JobInfoDetailActionBarFragment {
            return JobInfoDetailActionBarFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout { relativeLayout() {
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

                    linearLayout() {

                        gravity=Gravity.CENTER_VERTICAL  or  Gravity.RIGHT

                        imageView {

                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_collect_zwxq)

                        }.lparams() {
                            width = wrapContent
                            height =wrapContent
                            rightMargin=dip(14)

                        }

                        imageView {

                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_report_zwxq)

                        }.lparams() {
                            width = wrapContent
                            height =wrapContent
                            rightMargin=dip(14)

                        }

                        imageView {

                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_share_zwxq)

                        }.lparams() {
                            width = wrapContent
                            height =wrapContent
                            rightMargin=dip(14)
                        }


                    }.lparams() {
                        width = wrapContent
                        height = dip(65 - getStatusBarHeight(this@JobInfoDetailActionBarFragment.context!!))
                        alignParentRight()
                        alignParentBottom()
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




