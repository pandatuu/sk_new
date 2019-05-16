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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.adapter.company.LabelShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyCityAddressAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyPicShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.IndustryListAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.IndustrySelectAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.px2dip

class CompanyDetailInfoFragment : Fragment() {

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null
    var contentText:String=""
    lateinit var desContent:TextView

    lateinit var swipeLayout:LinearLayout


    lateinit var addShow:RecyclerView
    lateinit var fuliContaner:FlowLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(contentText:String): CompanyDetailInfoFragment {
            var f=CompanyDetailInfoFragment()
            f.contentText=contentText
            return f
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        return fragmentView
    }
    private fun createView(): View {

        var  dm =getResources().getDisplayMetrics();
        var w_screen = dm.widthPixels;
        var h_screen = dm.heightPixels;

        return UI {
            linearLayout {
                linearLayout() {
                    backgroundColor=Color.TRANSPARENT
                    swipeLayout=verticalLayout {
                        backgroundResource=R.drawable.radius_top_white
                        verticalLayout {

                            relativeLayout {
                                imageView {
                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.company_logo)

                                }.lparams() {
                                    width=dip(70)
                                    height=dip(70)
                                    alignParentLeft()
                                }

                                verticalLayout {
                                    gravity=Gravity.RIGHT
                                    imageView {
                                        backgroundColor = Color.TRANSPARENT
                                        scaleType = ImageView.ScaleType.CENTER_CROP
                                        setImageResource(R.mipmap.dianzan)

                                    }.lparams() {
                                        topMargin=dip(10)
                                        rightMargin=dip(10)
                                        bottomMargin=dip(10)
                                    }

                                    textView {
                                        gravity=Gravity.RIGHT
                                        text="1.1まん"
                                        textSize=13f
                                        textColorResource=R.color.themeColor
                                    }.lparams {
                                        width= wrapContent
                                    }

                                }.lparams() {
                                    alignParentRight()
                                }


                            }.lparams {
                                width= matchParent
                                height= wrapContent
                                leftMargin=dip(15)
                                rightMargin=dip(15)
                                topMargin=dip(25)
                            }


                            textView {
                                text="任天堂株式会社"
                                textSize=24f
                                textColorResource=R.color.black33
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                            }.lparams {
                                topMargin=dip(15)
                                leftMargin=dip(15)
                                rightMargin=dip(15)
                            }


                            linearLayout {
                                gravity=Gravity.CENTER_VERTICAL

                                textView {
                                        textSize=13f
                                        textColorResource=R.color.gray5c
                                        text="上場してる"
                                        gravity=Gravity.CENTER
                                    }

                                    textView {
                                      backgroundResource=R.color.grayb8
                                    }.lparams {
                                        height= matchParent
                                        width=dip(1)
                                        leftMargin=dip(10)
                                    }

                                    textView {
                                        textSize=13f
                                        textColorResource=R.color.gray5c
                                        text="500-999人"
                                    }.lparams {
                                        leftMargin=dip(10)
                                    }

                                    textView {
                                        backgroundResource=R.color.grayb8
                                    }.lparams {
                                        height= matchParent
                                        width=dip(1)
                                        leftMargin=dip(10)
                                    }

                                    textView {
                                        textSize=13f
                                        textColorResource=R.color.gray5c
                                        text="インターネット"
                                    }.lparams {
                                        leftMargin=dip(10)
                                    }

                            }.lparams {
                                width= matchParent
                                height= wrapContent
                                topMargin=dip(10)
                                leftMargin=dip(15)
                                rightMargin=dip(15)
                            }


                            var list=
                                mutableListOf(R.mipmap.company_bg ,R.mipmap.company_bg,R.mipmap.company_bg,R.mipmap.company_bg)

                            recyclerView{
                                overScrollMode = View.OVER_SCROLL_NEVER
                                var layoutManager=LinearLayoutManager(this.getContext())
                                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
                                setLayoutManager(layoutManager)
                                setAdapter(CompanyPicShowAdapter(this, list ) { item ->

                                })
                            }.lparams {
                                height=dip(144)
                                width= matchParent
                                leftMargin=dip(15)
                                rightMargin=dip(15)
                            }


                            textView {
                                backgroundColorResource=R.color.originColor
                            }.lparams {
                                width= matchParent
                                height=dip(8)
                            }

                            scrollView {


//                                setOnScrollChangeListener(object :View.OnScrollChangeListener{
//                                    override fun onScrollChange(
//                                        v: View?,
//                                        scrollX: Int,
//                                        scrollY: Int,
//                                        oldScrollX: Int,
//                                        oldScrollY: Int
//                                    ) {
//
//                                        toast("")
//
//
//                                        if(scrollY>oldScrollY  ){
//                                            toast("上滑动")
//
//                                        }
//
//                                        if( scrollY==0){
//                                            toast("下滑")
//
//                                        }
//
//
//
//                                    }
//
//                                })

//                                setOnTouchListener(object :View.OnTouchListener{
//                                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                                        toast(event!!.getY().toString())
//                                        return false
//                                    }
//
//                                })

                                verticalLayout {
                                    verticalLayout {
                                        textView {
                                            text = "人気職位(200)"
                                            textSize = 16f
                                            textColorResource = R.color.black20
                                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                            gravity = Gravity.CENTER_VERTICAL
                                        }.lparams {
                                            height = dip(61)
                                            width = matchParent

                                        }

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

                                        desContent = textView {
                                            text = contentText
                                            textSize = 18f
                                            textColorResource = R.color.black20
                                            text = "会社住所"
                                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                        }.lparams {
                                            topMargin = dip(21)
                                            bottomMargin = dip(5)
                                        }


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
                                        leftMargin=dip(15)
                                        rightMargin=dip(15)
                                    }

                                    var list=mutableListOf("無料スナックの内容","年度の旅行","年次有給休暇","年次有給休暇")

                                    recyclerView {
                                        overScrollMode = View.OVER_SCROLL_NEVER
                                        var layoutManager = LinearLayoutManager(this.getContext())
                                        setLayoutManager(layoutManager)
                                        setAdapter(LabelShowAdapter(list) { str ->
                                            toast("11")
                                        })
                                    }.lparams {
                                        width = matchParent
                                        bottomMargin=dip(10)
                                    }




                                    verticalLayout {
                                        textView {
                                            backgroundColorResource=R.color.originColor
                                        }.lparams {
                                            width= matchParent
                                            height=dip(1)
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
                                            orientation=LinearLayout.HORIZONTAL
                                            gravity=Gravity.CENTER_VERTICAL
                                            imageView {
                                                backgroundColor = Color.TRANSPARENT
                                                scaleType = ImageView.ScaleType.CENTER_CROP
                                                setImageResource(R.mipmap.ico_time)

                                            }.lparams() {
                                                height=dip(16)
                                                width=dip(16)
                                            }

                                            textView {
                                                text="上班时间：9：00"
                                                textColorResource=R.color.black33
                                                letterSpacing=0.05f
                                                textSize=14f

                                            }.lparams {
                                                height= matchParent
                                                leftMargin=dip(8)
                                            }

                                        }.lparams {
                                            width= matchParent
                                            height=dip(20)
                                            topMargin=dip(17)
                                        }

                                        linearLayout {
                                            orientation=LinearLayout.HORIZONTAL
                                            gravity=Gravity.CENTER_VERTICAL
                                            imageView {
                                                backgroundColor = Color.TRANSPARENT
                                                scaleType = ImageView.ScaleType.CENTER_CROP
                                                setImageResource(R.mipmap.ico_time)

                                            }.lparams() {
                                                height=dip(16)
                                                width=dip(16)
                                            }

                                            textView {
                                                text="上班时间：9：00"
                                                textColorResource=R.color.black33
                                                letterSpacing=0.05f
                                                textSize=14f

                                            }.lparams {
                                                height= matchParent
                                                leftMargin=dip(8)
                                            }

                                        }.lparams {
                                            width= matchParent
                                            height=dip(20)
                                            topMargin=dip(10)
                                            bottomMargin=dip(21)

                                        }


                                        textView {
                                            backgroundColorResource=R.color.originColor
                                        }.lparams {
                                            width= matchParent
                                            height=dip(1)
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
                                            gravity=Gravity.CENTER_VERTICAL
                                            imageView {
                                                backgroundColor = Color.TRANSPARENT
                                                scaleType = ImageView.ScaleType.CENTER_CROP
                                                setImageResource(R.mipmap.ico_web)

                                            }.lparams() {
                                                height=dip(15)
                                                width=dip(15)
                                            }


                                            textView {
                                                gravity=Gravity.CENTER_VERTICAL
                                                text="https://www.cgland.top"
                                                textSize=14f
                                                letterSpacing=0.05f
                                                textColorResource=R.color.black33
                                            }.lparams {
                                                width=0
                                                weight=1f
                                                height=dip(20)
                                                leftMargin=dip(10)
                                            }

                                            imageView {
                                                backgroundColor = Color.TRANSPARENT
                                                scaleType = ImageView.ScaleType.CENTER_CROP
                                                setImageResource(R.mipmap.icon_go_position)

                                            }.lparams() {
                                                height=dip(11)
                                                width=dip(6)
                                            }


                                        }.lparams {
                                            width= matchParent
                                            height=dip(20)
                                            topMargin = dip(15)
                                            bottomMargin=dip(50)
                                        }

                                    }.lparams {
                                        leftMargin=dip(15)
                                        rightMargin=dip(15)
                                        width= matchParent
                                    }







                                }
                            }.lparams {
                                height=matchParent
                                width=matchParent
                            }

                        }.lparams {
                            width= matchParent
                            height= matchParent
                        }
                    }.lparams() {
                        width = matchParent
                        topMargin=dip(65)
                        topMargin=dip(343)
                        height =dip(px2dip(context,h_screen*1.0f)-65)
                    }


                }.lparams() {
                    width = matchParent
                    height =matchParent
                }


            }
        }.view



    }

    fun px2dip(context:Context , pxValue:Float ):Int{
        var scale = context.getResources().getDisplayMetrics().density;
        return ((pxValue / scale + 0.5f).toInt())
    }




}




