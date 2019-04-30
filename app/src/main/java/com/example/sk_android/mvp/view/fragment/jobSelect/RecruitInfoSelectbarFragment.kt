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
import android.widget.LinearLayout


class RecruitInfoSelectbarFragment : Fragment() {


    private var mContext: Context? = null
    private lateinit var selectBar:SelectBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): RecruitInfoSelectbarFragment {
            val fragment = RecruitInfoSelectbarFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        selectBar =  activity as SelectBar
        return fragmentView
    }

    fun createView(): View {

        return UI {
            linearLayout {
                linearLayout {
                    backgroundResource=R.drawable.selectbar_bottom_border
                    orientation = LinearLayout.HORIZONTAL
                    relativeLayout {
                        linearLayout{

                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    selectBar.getSelectBarItem(0)
                                }

                            })

                            orientation = LinearLayout.HORIZONTAL
                            gravity=Gravity.CENTER_VERTICAL
                            textView {
                                text="別の"
                                textSize=12f
                                gravity=Gravity.CENTER_VERTICAL
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                height= matchParent
                            }

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_down_home)
                            }.lparams() {
                                width = dip(10)
                                height =dip(5)
                                leftMargin=dip(7)
                            }
                        }.lparams {
                            height=dip(17)
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        linearLayout{

                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    selectBar.getSelectBarItem(1)
                                }

                            })

                            orientation = LinearLayout.HORIZONTAL
                            gravity=Gravity.CENTER_VERTICAL
                            textView {
                                text="地点"
                                textSize=12f
                                gravity=Gravity.CENTER_VERTICAL
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                height= matchParent
                            }

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_down_home)
                            }.lparams() {
                                width = dip(10)
                                height =dip(5)
                                leftMargin=dip(7)
                            }
                        }.lparams {
                            height=dip(17)
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        linearLayout{

                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    selectBar.getSelectBarItem(2)
                                }

                            })

                            orientation = LinearLayout.HORIZONTAL
                            gravity=Gravity.CENTER_VERTICAL
                            textView {
                                text="会社の"
                                textSize=12f
                                gravity=Gravity.CENTER_VERTICAL
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                height= matchParent
                            }

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_down_home)
                            }.lparams() {
                                width = dip(10)
                                height =dip(5)
                                leftMargin=dip(7)
                            }
                        }.lparams {
                            height=dip(17)
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        linearLayout{

                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    selectBar.getSelectBarItem(3)
                                }

                            })

                            orientation = LinearLayout.HORIZONTAL
                            gravity=Gravity.CENTER_VERTICAL
                            textView {
                                text="要求"
                                textSize=12f
                                gravity=Gravity.CENTER_VERTICAL
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                height= matchParent
                            }

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_down_home)
                            }.lparams() {
                                width = dip(10)
                                height =dip(5)
                                leftMargin=dip(7)
                            }
                        }.lparams {
                            height=dip(17)
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }
                }.lparams {
                    width= matchParent
                    height=dip(51)
                }
            }
        }.view
    }

    public interface SelectBar {

        fun getSelectBarItem( index:Int)
    }


}

