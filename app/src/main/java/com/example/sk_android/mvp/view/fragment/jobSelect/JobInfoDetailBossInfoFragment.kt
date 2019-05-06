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


class JobInfoDetailBossInfoFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): JobInfoDetailBossInfoFragment {
            return JobInfoDetailBossInfoFragment()
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
                        backgroundResource=R.drawable.box_shadow_weak
                        var iamgeId=31
                        var iamge=imageView {
                            id=iamgeId
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_tx_home)

                        }.lparams() {
                            width = dip(50)
                            height =dip(50)
                            leftMargin=dip(18)
                            topMargin=dip(33)
                        }


                        verticalLayout {

                            textView {
                                text="成都アニメバレー"
                                textColorResource=R.color.themeBule
                                textSize=13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                            }.lparams {
                                width= wrapContent
                            }

                            textView {
                                text="ジャさん·社長"
                                textColorResource=R.color.themeBule
                                textSize=15f
                            }.lparams {
                                topMargin=dip(3)
                                width= wrapContent
                            }

                            textView {
                                backgroundResource=R.drawable.radius_border_label_gray89
                                text="活躍中"
                                textColorResource=R.color.gray89
                                textSize=11f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                topPadding=dip(2)
                                bottomPadding=dip(2)
                                rightPadding=dip(4)
                                leftPadding=dip(4)
                            }.lparams {
                                topMargin=dip(5)
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
                        height=dip(110)
                        width= matchParent
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(150)
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




