package com.example.sk_android.mvp.view.fragment.jobselect

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

    var index:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(ind:Int): RecruitInfoBottomMenuFragment {
            val fragment = RecruitInfoBottomMenuFragment()
            fragment.index=ind
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
                    backgroundResource=R.drawable.border_top_f2_ba
                    orientation = LinearLayout.HORIZONTAL
                    relativeLayout {
                        verticalLayout{
                            gravity=Gravity.CENTER

                            imageView {
                                backgroundColor = Color.TRANSPARENT
                                scaleType = ImageView.ScaleType.CENTER
                                if(index==0){
                                    setImageResource(R.mipmap.icon_position_h_home_clicked)
                                }else{
                                    setImageResource(R.mipmap.icon_position_h_home_unclicked)
                                }
                            }.lparams() {

                            }

                            textView {
                                text="職種"
                                textSize=10f
                                gravity=Gravity.CENTER
                                textColorResource=R.color.gray66
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
                                if(index==1){
                                    setImageResource(R.mipmap.icon_company_unclicked)
                                }else{
                                    setImageResource(R.mipmap.icon_company_unclicked)
                                }
                            }.lparams() {

                            }

                            textView {
                                text="会社"
                                textSize=10f
                                gravity=Gravity.CENTER_VERTICAL
                                textColorResource=R.color.gray66
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
                                if(index==2){
                                    setImageResource(R.mipmap.icon_message_home_clicked)
                                }else{
                                    setImageResource(R.mipmap.icon_message_home_unclicked)
                                }
                            }.lparams() {

                            }

                            textView {
                                text="メッセージ"
                                textSize=10f
                                gravity=Gravity.CENTER_VERTICAL
                                textColorResource=R.color.gray66
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
                                if(index==3){
                                    setImageResource(R.mipmap.icon_me_home_unclicked)
                                }else{
                                    setImageResource(R.mipmap.icon_me_home_unclicked)
                                }
                            }.lparams() {

                            }

                            textView {
                                text="マイ"
                                textSize=10f
                                gravity=Gravity.CENTER_VERTICAL
                                textColorResource=R.color.gray66
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
