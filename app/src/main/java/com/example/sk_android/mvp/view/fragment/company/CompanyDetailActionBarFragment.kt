package com.example.sk_android.mvp.view.fragment.jobselect

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class CompanyDetailActionBarFragment : Fragment() {

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null

    lateinit var mainLayout:RelativeLayout
    lateinit var select:CompanyDetailActionBarSelect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): CompanyDetailActionBarFragment {
            return CompanyDetailActionBarFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        select=activity as CompanyDetailActionBarSelect
        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout {
                mainLayout=relativeLayout() {

                    imageView {

                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.company_bg)

                    }.lparams() {
                        width = matchParent
                        height =dip(383)

                    }


                    relativeLayout() {
                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back_white

                        }.lparams() {
                            width = matchParent
                            height =dip(65)
                            alignParentBottom()

                        }

                        var textViewLeftId=1
                        var textViewLeft=textView {
                            id=textViewLeftId
                            text = ""
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams() {
                            width = wrapContent
                            height =dip(65-getStatusBarHeight(this@CompanyDetailActionBarFragment.context!!))
                            alignParentBottom()
                            centerInParent()
                            leftMargin=dip(15)
                        }

                        linearLayout {
                            orientation= LinearLayout.HORIZONTAL
                            gravity=Gravity.RIGHT or Gravity.CENTER_VERTICAL

                            var soucangId = 1
                            var soucang = toolbar {
                                id = soucangId
                                backgroundColor = Color.TRANSPARENT
                                navigationIconResource = R.mipmap.soucang_no
                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        navigationIconResource = R.mipmap.icon_zan_h_home
                                    }

                                })
                            }.lparams(dip(25), dip(25)) {
                                rightMargin = dip(10)

                            }

                            var jubaoId = 2
                            var juba = toolbar {
                                id = jubaoId
                                navigationIconResource = R.mipmap.jubao
                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        navigationIconResource = R.mipmap.jubao_light
                                        select.jubaoSelect()
                                    }

                                })
                            }.lparams(dip(25), dip(25)) {

                                rightMargin = dip(10)

                            }

                            var pingbiId = 3
                            var pingbi = toolbar {
                                id = pingbiId
                                backgroundColor = Color.TRANSPARENT
                                navigationIconResource = R.mipmap.pingbi

                            }.lparams(dip(25), dip(25)) {

                            }

                        }.lparams() {
                            width = wrapContent
                            height =dip(65-getStatusBarHeight(this@CompanyDetailActionBarFragment.context!!))
                            alignParentRight()
                            alignParentBottom()
                            rightMargin=dip(15)
                        }

                    }.lparams() {
                        width = matchParent
                        height =dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height =dip(383)
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



    interface  CompanyDetailActionBarSelect{
        fun jubaoSelect()
    }

}




