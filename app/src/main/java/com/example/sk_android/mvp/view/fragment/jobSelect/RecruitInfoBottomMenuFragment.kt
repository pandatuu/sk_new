package com.example.sk_android.mvp.view.fragment.jobSelect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout


class RecruitInfoBottomMenuFragment : Fragment() {


    private var mContext: Context? = null
    private lateinit var recruitInfoBottomMenu:RecruitInfoBottomMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): RecruitInfoBottomMenuFragment {
            val fragment = RecruitInfoBottomMenuFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        recruitInfoBottomMenu =  activity as RecruitInfoBottomMenu
        return fragmentView
    }

    fun createView(): View {

        return UI {
            linearLayout {
                linearLayout {
                    backgroundResource=R.drawable.border_top_grayf2
                    orientation = LinearLayout.HORIZONTAL
                    relativeLayout {
                        verticalLayout{
                            gravity=Gravity.CENTER

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_position_h_home_clicked)
                            }.lparams() {

                            }

                            textView {
                                text="職種"
                                textSize=10f
                                gravity=Gravity.CENTER
                                textColor=R.color.gray66
                            }.lparams {
                                height= wrapContent
                                topMargin=dip(3)
                            }

                        }.lparams {
                            height= wrapContent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        verticalLayout{
                            gravity=Gravity.CENTER

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_company_unclicked)
                            }.lparams() {

                            }

                            textView {
                                text="会社"
                                textSize=10f
                                gravity=Gravity.CENTER_VERTICAL
                                textColor=R.color.gray66
                            }.lparams {
                                height= wrapContent
                                topMargin=dip(3)
                            }


                        }.lparams {
                            height=matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        verticalLayout{
                            gravity=Gravity.CENTER
                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_message_home_unclicked)
                            }.lparams() {

                            }

                            textView {
                                text="メッセージ"
                                textSize=10f
                                gravity=Gravity.CENTER_VERTICAL
                                textColor=R.color.gray66
                            }.lparams {
                                height= wrapContent
                                topMargin=dip(3)
                            }


                        }.lparams {
                            height=matchParent
                            centerInParent()
                        }
                    }.lparams(width = 0, height = matchParent) {
                        weight = 1f
                    }

                    relativeLayout {
                        verticalLayout{
                            gravity=Gravity.CENTER
                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                setImageResource(R.mipmap.icon_me_home_unclicked)
                            }.lparams() {

                            }

                            textView {
                                text="マイ"
                                textSize=10f
                                gravity=Gravity.CENTER_VERTICAL
                                textColor=R.color.gray66
                            }.lparams {
                                height= wrapContent
                                topMargin=dip(3)
                            }


                        }.lparams {
                            height=matchParent
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

    public interface RecruitInfoBottomMenu {

        fun getSelectedMenu( )
    }


}

