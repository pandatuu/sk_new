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

class ProductDetailInfoTopPartFragment : Fragment() {

    private var mContext: Context? = null
    var contentText: String = ""

    lateinit var desContent: TextView
    lateinit var addShow: RecyclerView

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

                    verticalLayout {

                        textView {
                            backgroundColorResource = R.color.originColor
                        }.lparams {
                            width = matchParent
                            height = dip(1)
                        }

                        textView {
                            text = "会社について"
                            textSize = 18f
                            textColorResource = R.color.black20
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            topMargin = dip(25)
                            bottomMargin = dip(15)
                        }


                        desContent = textView {
                            text = contentText
                            textSize = 14f
                            textColorResource = R.color.gray5c
                        }.lparams {
                        }

                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.icon_down)

                            }.lparams() {

                            }
                        }.lparams {
                            topMargin = dip(15)
                            width = matchParent
                            height = wrapContent
                            bottomMargin = dip(25)
                        }

                        textView {
                            backgroundColorResource = R.color.originColor
                        }.lparams {
                            width = matchParent
                            height = dip(1)
                        }

                        textView {
                            textSize = 18f
                            textColorResource = R.color.black20
                            text = "会社住所"
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams {
                            topMargin = dip(21)
                            bottomMargin = dip(5)
                        }

                        var list =
                            mutableListOf(
                                R.mipmap.company_bg,
                                R.mipmap.company_bg,
                                R.mipmap.company_bg,
                                R.mipmap.company_bg
                            )
                        addShow = recyclerView {
                            overScrollMode = View.OVER_SCROLL_NEVER
                            var layoutManager = LinearLayoutManager(this.getContext())
                            setLayoutManager(layoutManager)
                            setAdapter(CompanyCityAddressAdapter(this, list) { item ->

                            })
                        }.lparams {
                            height = dip(145)
                            width = matchParent
                        }


                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.icon_down)
                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        var pl = LinearLayout.LayoutParams(addShow.layoutParams)
                                        pl.height = matchParent
                                        addShow.layoutParams = pl
                                    }

                                })

                            }.lparams() {

                            }
                        }.lparams {
                            topMargin = dip(15)
                            width = matchParent
                            height = wrapContent
                            bottomMargin = dip(25)
                        }



                        textView {
                            backgroundColorResource = R.color.originColor
                        }.lparams {
                            width = matchParent
                            height = dip(1)
                        }

                        textView {
                            text = contentText
                            textSize = 18f
                            textColorResource = R.color.black20
                            text = "会社の福利"
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams {
                            topMargin = dip(21)
                        }
                    }.lparams {
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }

                    var list = mutableListOf("無料スナックの内容", "年度の旅行", "年次有給休暇", "年次有給休暇")

                    recyclerView {
                        overScrollMode = View.OVER_SCROLL_NEVER
                        var layoutManager = LinearLayoutManager(this.getContext())
                        setLayoutManager(layoutManager)
                        setAdapter(LabelShowAdapter(list) { str ->
                            toast("11")
                        })
                    }.lparams {
                        width = matchParent
                        bottomMargin = dip(10)
                    }




                    verticalLayout {
                        textView {
                            backgroundColorResource = R.color.originColor
                        }.lparams {
                            width = matchParent
                            height = dip(1)
                        }

                        textView {
                            text = contentText
                            textSize = 18f
                            textColorResource = R.color.black20
                            text = "勤務時間"
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams {
                            topMargin = dip(21)
                        }


                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.ico_time)

                            }.lparams() {
                                height = dip(16)
                                width = dip(16)
                            }

                            textView {
                                text = "上班时间：9：00"
                                textColorResource = R.color.black33
                                letterSpacing = 0.05f
                                textSize = 14f

                            }.lparams {
                                height = matchParent
                                leftMargin = dip(8)
                            }

                        }.lparams {
                            width = matchParent
                            height = dip(20)
                            topMargin = dip(17)
                        }

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_VERTICAL
                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.ico_time)

                            }.lparams() {
                                height = dip(16)
                                width = dip(16)
                            }

                            textView {
                                text = "上班时间：9：00"
                                textColorResource = R.color.black33
                                letterSpacing = 0.05f
                                textSize = 14f

                            }.lparams {
                                height = matchParent
                                leftMargin = dip(8)
                            }

                        }.lparams {
                            width = matchParent
                            height = dip(20)
                            topMargin = dip(10)
                            bottomMargin = dip(21)

                        }


                        textView {
                            backgroundColorResource = R.color.originColor
                        }.lparams {
                            width = matchParent
                            height = dip(1)
                        }



                        textView {
                            text = contentText
                            textSize = 18f
                            textColorResource = R.color.black20
                            text = "ホームページ"
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams {
                            topMargin = dip(21)
                        }


                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL
                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.ico_web)

                            }.lparams() {
                                height = dip(15)
                                width = dip(15)
                            }


                            textView {
                                gravity = Gravity.CENTER_VERTICAL
                                text = "https://www.cgland.top"
                                textSize = 14f
                                letterSpacing = 0.05f
                                textColorResource = R.color.black33
                            }.lparams {
                                width = 0
                                weight = 1f
                                height = dip(20)
                                leftMargin = dip(10)
                            }

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.icon_go_position)

                            }.lparams() {
                                height = dip(11)
                                width = dip(6)
                            }


                        }.lparams {
                            width = matchParent
                            height = dip(20)
                            topMargin = dip(15)
                            bottomMargin = dip(50)
                        }

                    }.lparams {
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                        width = matchParent
                    }


                }
            }
        }.view

    }







}




