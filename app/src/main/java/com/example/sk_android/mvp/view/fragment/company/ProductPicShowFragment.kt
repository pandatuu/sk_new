package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v4.view.ViewPager
import android.widget.ImageView
import android.widget.TextView
import com.example.sk_android.mvp.view.adapter.jobselect.PicAdapter
import com.ocnyang.pagetransformerhelp.transformer.FlipVerticalTransformer
import com.ocnyang.pagetransformerhelp.transformer.ForegroundToBackgroundTransformer
import com.ocnyang.pagetransformerhelp.transformer.ParallaxTransformer
import com.ocnyang.pagetransformerhelp.transformer.RotateDownTransformer
import org.jetbrains.anko.support.v4.viewPager

class ProductPicShowFragment : Fragment() {

    private var mContext: Context? = null
    var viewPager: ViewPager?=null

    lateinit var textShow:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): ProductPicShowFragment {
            return ProductPicShowFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }
    private fun createView(): View {

        var view=UI {
            linearLayout {
                verticalLayout{

                    linearLayout {

                        textView {
                            text="("
                            textSize=12f
                            textColorResource=R.color.gray5c
                            letterSpacing=0.05f
                            gravity=Gravity.CENTER
                        }.lparams {
                            height= matchParent
                        }

                        textShow=textView {
                            text="1"
                            textSize=12f
                            textColorResource=R.color.themeColor
                            letterSpacing=0.05f
                            gravity=Gravity.CENTER
                        }.lparams {
                            height= matchParent
                        }

                        textView {
                            text="/10)"
                            textSize=12f
                            textColorResource=R.color.gray5c
                            letterSpacing=0.05f
                            gravity=Gravity.CENTER
                        }.lparams {
                            height= matchParent
                        }

                    }.lparams {
                        width= matchParent
                        height=dip(17)
                    }


                    viewPager=viewPager {
                        addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                            override fun onPageScrollStateChanged(p0: Int) {
                            }

                            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                            }

                            override fun onPageSelected(p0: Int) {
                                textShow.text=((p0+1).toString())
                            }

                        })
                    }



                }.lparams {
                    height=dip(220)
                    width= matchParent
                }
            }
        }.view
        var list= mutableListOf(addPic(),addPic(),addPic(),addPic(),addPic(),addPic())
        viewPager!!.adapter= PicAdapter(list)
        viewPager!!.setPageTransformer(true, RotateDownTransformer())
        return view

    }



    fun addPic():View{

        return with(viewPager!!.context) {
            verticalLayout {
                verticalLayout{
                    gravity=Gravity.CENTER
                    imageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.company_bg)
                    }.lparams {
                        width= matchParent
//                        height=dip(180)
//                        width=dip(290)
                    }
                }.lparams {
                    width= matchParent
                }

            }
        }

    }



}




