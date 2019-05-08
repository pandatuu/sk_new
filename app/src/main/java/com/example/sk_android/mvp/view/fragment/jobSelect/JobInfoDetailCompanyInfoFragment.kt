package com.example.sk_android.mvp.view.fragment.jobSelect


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.widget.ImageView


class JobInfoDetailCompanyInfoFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): JobInfoDetailCompanyInfoFragment {
            return JobInfoDetailCompanyInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {
        return UI {
            linearLayout {
                verticalLayout {
                    gravity=Gravity.CENTER_VERTICAL
                    relativeLayout {
                        //backgroundResource=R.drawable.box_shadow_weak
                        var iamgeId=31
                        var iamge=imageView {
                            id=iamgeId
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_tx_home)

                        }.lparams() {
                            width = dip(48)
                            height =dip(48)
                            leftMargin=dip(18)
                            topMargin=dip(30)
                        }


                        verticalLayout {



                            textView {
                                text="ジャさん·社長"
                                textColorResource=R.color.normalTextColor
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                textSize=15f
                            }.lparams {
                                topMargin=dip(3)
                                width= wrapContent
                            }

                            textView {
                                text="上場会社·500-999人·IT"
                                textColorResource=R.color.gray99
                                textSize=13f

                            }.lparams {
                                topMargin=dip(3)
                            }

                        }.lparams {
                            rightOf(iamge)
                            leftMargin=dip(17)
                            centerVertically()
                            width= wrapContent
                        }


                        imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_go_zwxq)

                        }.lparams() {

                            rightMargin=dip(18)
                            centerVertically()
                            alignParentRight()
                        }

                    }.lparams {
                        leftMargin=dip(15)
                        rightMargin=dip(15)
                        height=dip(105)
                        width= matchParent
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(177)
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




