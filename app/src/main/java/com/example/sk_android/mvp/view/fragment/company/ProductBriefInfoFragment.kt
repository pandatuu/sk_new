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

class ProductBriefInfoFragment : Fragment() {

    private var mContext: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): ProductBriefInfoFragment {
            return ProductBriefInfoFragment()
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
                linearLayout{
                    backgroundColor=Color.WHITE
                    orientation=LinearLayout.HORIZONTAL
                    gravity=Gravity.CENTER_VERTICAL
                    imageView {

                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.product_img)

                    }.lparams() {
                        width = dip(60)
                        height =dip(60)

                    }

                    verticalLayout {

                        textView {
                            text="製品名"
                            textSize=18f
                            gravity=Gravity.CENTER
                            textColorResource=R.color.black20
                            letterSpacing=0.05f
                        }.lparams {
                            height=dip(25)
                        }


                        textView {
                            text="製品の特徴介绍製品の特徴介绍製品の特徴介绍绍 两行文字"
                            textSize=12f
                            textColorResource=R.color.black33
                            letterSpacing=0.05f
                        }.lparams {

                        }

                    }.lparams {
                        leftMargin=dip(10)
                    }
                }.lparams {
                    height=dip(100)
                    width= matchParent
                }
            }
        }.view

    }







}




