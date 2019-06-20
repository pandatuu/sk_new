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
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.view.adapter.company.LabelShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyCityAddressAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyPicShowAdapter

class ProductDetailInfoTopPartFragment : Fragment() {

    private var mContext: Context? = null
    var contentText: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(contentText: String): ProductDetailInfoTopPartFragment {
            var f = ProductDetailInfoTopPartFragment()
            f.contentText = contentText
            return f
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
                verticalLayout {
                    relativeLayout {
                        imageView {
                            backgroundColor = Color.TRANSPARENT
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.company_logo)

                        }.lparams() {
                            width = dip(70)
                            height = dip(70)
                            alignParentLeft()
                        }

                        verticalLayout {
                            gravity = Gravity.RIGHT
                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.dianzan)

                            }.lparams() {
                                topMargin = dip(10)
                                rightMargin = dip(10)
                                bottomMargin = dip(10)
                            }

                            textView {
                                gravity = Gravity.RIGHT
                                text = "1.1まん"
                                textSize = 13f
                                textColorResource = R.color.themeColor
                            }.lparams {
                                width = wrapContent
                            }

                        }.lparams() {
                            alignParentRight()
                        }


                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                        topMargin = dip(25)
                    }


                    textView {
                        text = "任天堂株式会社"
                        textSize = 24f
                        textColorResource = R.color.black33
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        setOnClickListener(object : View.OnClickListener {

                            override fun onClick(v: View?) {


                            }

                        })
                    }.lparams {
                        topMargin = dip(15)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }


                    linearLayout {


                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            textSize = 13f
                            textColorResource = R.color.gray5c
                            text = "上場してる"
                            gravity = Gravity.CENTER
                        }

                        textView {
                            backgroundResource = R.color.grayb8
                        }.lparams {
                            height = matchParent
                            width = dip(1)
                            leftMargin = dip(10)
                        }

                        textView {
                            textSize = 13f
                            textColorResource = R.color.gray5c
                            text = "500-999人"
                        }.lparams {
                            leftMargin = dip(10)
                        }

                        textView {
                            backgroundResource = R.color.grayb8
                        }.lparams {
                            height = matchParent
                            width = dip(1)
                            leftMargin = dip(10)
                        }

                        textView {
                            textSize = 13f
                            textColorResource = R.color.gray5c
                            text = "インターネット"
                        }.lparams {
                            leftMargin = dip(10)
                        }

                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(10)
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }


                    var list =
                        mutableListOf(
                            R.mipmap.company_bg,
                            R.mipmap.company_bg,
                            R.mipmap.company_bg,
                            R.mipmap.company_bg
                        )

                    recyclerView {
                        overScrollMode = View.OVER_SCROLL_ALWAYS

                        var layoutManager = LinearLayoutManager(this.getContext())
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
                        setLayoutManager(layoutManager)
                        setAdapter(CompanyPicShowAdapter(this, list) { item ->

                        })
                        onFlingListener=object : RecyclerView.OnFlingListener() {
                            override fun onFling(p0: Int, p1: Int): Boolean {
                                toast("sadasdasdasdasd")
                                return false
                            }

                        }
                    }.lparams {
                        height = dip(144)
                        width = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }


                    textView {
                        backgroundColorResource = R.color.originColor
                    }.lparams {
                        width = matchParent
                        height = dip(8)
                    }

                }.lparams {
                    width= matchParent
                }
            }
        }.view

    }







}




