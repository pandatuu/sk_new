package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toolbar

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


                            var soucangId=1
                            var soucang=imageView {
                                id=soucangId
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.soucang_no)
                                setOnClickListener(object :View.OnClickListener{
                                    override fun onClick(v: View?) {
                                        setImageResource(R.mipmap.icon_zan_h_home)
                                    }

                                })
                            }.lparams() {
                                rightMargin=dip(10)

                            }

                            var jubaoId=2
                            var juba=imageView {
                                id=jubaoId
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.jubao)
                                setOnClickListener(object :View.OnClickListener{
                                    override fun onClick(v: View?) {
                                        setImageResource(R.mipmap.jubao_light)
                                        select.jubaoSelect()
                                    }

                                })
                            }.lparams() {

                                rightMargin=dip(10)

                            }



                            var pingbiId=3
                            var pingbi=imageView {
                                id=pingbiId
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.pingbi)

                            }.lparams() {

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



