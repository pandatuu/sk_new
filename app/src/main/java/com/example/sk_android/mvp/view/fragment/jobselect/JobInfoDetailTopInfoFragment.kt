package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.widget.ImageView

class JobInfoDetailTopInfoFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): JobInfoDetailTopInfoFragment {
            return JobInfoDetailTopInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {




        var intent=activity!!.intent
        var positionName=intent.getStringExtra("positionName")
        var salaryType= intent.getStringExtra("salaryType")
        var showSalaryMinToMax= intent.getStringExtra("showSalaryMinToMax")
        var address= intent.getStringExtra("address")
        var workingExperience= intent.getStringExtra("workingExperience")
        var educationalBackground= intent.getStringExtra("educationalBackground")

        return UI {
            linearLayout {
                verticalLayout {
                    textView() {
                        backgroundColorResource = R.color.translucentBlue
                        textColorResource=R.color.themeBule
                        text = positionName
//                            if(positionName.length>14) positionName.substring(0,14)+"..." else positionName
                        textSize=21f
                        gravity=Gravity.CENTER_VERTICAL
                        padding=dip(14)
                    }.lparams() {
                        width = matchParent
                        height = wrapContent
                    }

                    linearLayout {
                        gravity=Gravity.CENTER_VERTICAL
                        textView {
                            textSize=14f
                            text=salaryType
                            textColorResource=R.color.themeColor
                            backgroundResource=R.drawable.circle_border_theme_color
                            gravity=Gravity.CENTER
                        }.lparams {
                            width=dip(27)
                            height=dip(27)
                        }

                        textView {
                            textSize=17f
                            text=showSalaryMinToMax
                            textColorResource=R.color.themeColor
                        }.lparams {
                            leftMargin=dip(9)
                        }

                    }.lparams() {
                        width = matchParent
                        height = dip(63)
                        leftMargin=dip(14)
                        rightMargin=dip(14)
                    }

                    linearLayout {
                        linearLayout {
                            gravity=Gravity.CENTER_VERTICAL

                            imageView {

                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.icon_position_zwxq)

                            }.lparams() {
                                width = wrapContent
                                height =wrapContent
                            }

                            textView {
                                textSize=12f
                                text=address
                                textColorResource=R.color.gray89
                            }.lparams {
                                leftMargin=dip(7)
                            }

                        }.lparams {
                            height= matchParent
                        }

                        linearLayout {
                            gravity=Gravity.CENTER_VERTICAL
                            imageView {

                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.icon_time_zwxq)

                            }.lparams() {
                                width = wrapContent
                                height =wrapContent
                            }

                            textView {
                                textSize=12f
                                text=workingExperience
                                textColorResource=R.color.gray89
                            }.lparams {
                                leftMargin=dip(7)
                            }

                        }.lparams {
                            height= matchParent
                            leftMargin=dip(25)
                        }

                        linearLayout {
                            gravity=Gravity.CENTER_VERTICAL
                            imageView {

                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.xueli)

                            }.lparams() {
                                width = wrapContent
                                height =wrapContent
                            }

                            textView {
                                textSize=12f
                                text=educationalBackground
                                textColorResource=R.color.gray89
                            }.lparams {
                                leftMargin=dip(7)
                            }

                        }.lparams {
                            height= matchParent
                            leftMargin=dip(25)
                        }


                    }.lparams() {
                        width = matchParent
                        height = dip(25)
                        leftMargin=dip(14)
                        rightMargin=dip(14)
                    }

                }.lparams() {
                    width = matchParent
                    height = wrapContent
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




