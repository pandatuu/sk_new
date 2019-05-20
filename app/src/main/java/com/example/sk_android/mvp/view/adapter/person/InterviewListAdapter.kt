package com.example.sk_android.mvp.view.adapter.person

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.airsaid.pickerviewlibrary.TimePickerView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.JobContainer
import org.jetbrains.anko.*

class InterviewListAdapter(
    private val context: RecyclerView,
    private val jobContainer: MutableList<JobContainer>,
    private val listener: (JobContainer) -> Unit
) : RecyclerView.Adapter<InterviewListAdapter.ViewHolder>() {

    lateinit var text:TextView
    lateinit var topShow:LinearLayout



    val NORMAL=1
    val GRAY=2


    lateinit var labelShow:LinearLayout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            relativeLayout {
                verticalLayout {
                    backgroundColor=Color.WHITE

                    verticalLayout {

                    linearLayout {

                        textView {
                            gravity=Gravity.CENTER_VERTICAL
                            text="明日"
                            textSize=12f
                            textColorResource=R.color.black20
                        }.lparams {
                            height=matchParent
                            width= wrapContent
                        }

                        textView {
                            gravity=Gravity.CENTER_VERTICAL
                            text="3月29日"
                            textSize=12f
                            textColorResource=R.color.gray99
                        }.lparams {
                            height=matchParent
                            width= 0
                            weight=1f
                            leftMargin=dip(10)
                        }


                        text=textView {
                            gravity=Gravity.CENTER_VERTICAL
                            text="7時間後、取り消す"
                            textSize=15f
                            textColorResource=R.color.black20
                        }.lparams {
                            height=matchParent
                            width= wrapContent
                        }



                    }.lparams {
                        height=dip(37)
                        width=matchParent
                    }


                        view {
                            backgroundResource=R.color.grayEBEAEB
                        }.lparams {
                            height=dip(1)
                            width=matchParent
                        }



                        linearLayout{

                            gravity=Gravity.CENTER_VERTICAL


                            imageView {

                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.company_logo)

                            }.lparams() {
                                width = dip(60)
                                height =dip(60)

                            }


                            verticalLayout {

                                textView {
                                    gravity=Gravity.CENTER_VERTICAL
                                    text="任天堂株式会社東京本社"
                                    textSize=16f
                                    textColorResource=R.color.black20
                                }.lparams {
                                    height=matchParent
                                    width= wrapContent
                                }

                                linearLayout{

                                    textView {
                                        gravity=Gravity.CENTER_VERTICAL
                                        text="面接"
                                        textSize=13f
                                        textColorResource=R.color.gray99
                                    }.lparams {
                                        height=dip(19)
                                        width= wrapContent
                                    }

                                    textView {
                                        gravity=Gravity.CENTER_VERTICAL
                                        text="phpエンジニア"
                                        textSize=13f
                                        textColorResource=R.color.gray99
                                    }.lparams {
                                        height=dip(19)
                                        width= wrapContent
                                        leftMargin=dip(5)
                                    }

                                    textView {
                                        gravity=Gravity.CENTER_VERTICAL
                                        text="30万-50万"
                                        textSize=13f
                                        textColorResource=R.color.gray99
                                    }.lparams {
                                        height=dip(19)
                                        width= wrapContent
                                        leftMargin=dip(5)

                                    }


                                }.lparams() {
                                    width = wrapContent
                                    height =wrapContent
                                    topMargin=dip(3)

                                }


                            }.lparams() {
                                width = 0
                                weight=1f
                                height =wrapContent
                                leftMargin=dip(10)

                            }


                            linearLayout {
                                textView {
                                    gravity=Gravity.CENTER_VERTICAL
                                    text="予約済み"
                                    textSize=12f
                                    textColorResource=R.color.gray5c
                                }.lparams {
                                    height=dip(17)
                                    width= dip(48)
                                    topMargin=dip(26)
                                }
                            }.lparams {
                                height = matchParent
                            }


                        }.lparams {
                            height=dip(0)
                            weight=1f
                            width=matchParent
                        }







                    }.lparams() {
                        width = matchParent
                        height = matchParent
                        leftMargin=dip(15)
                        rightMargin=dip(15)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(127)
                    topMargin=dip(8)
                }
            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(position%2==0){
            text.textSize=12f
            text.textColorResource=R.color.gray5c
            text.text="取り消し"
        }

//        textView.text=jobContainer[position].containerName
//
//        if(position%2==0){
//            labelShow.addView(getLabelView(NORMAL))
//            topShow.addView(getTopView(NORMAL))
//        }
//        else{
//            labelShow.addView(getLabelView(GRAY))
//            topShow.addView(getTopView(GRAY))
//        }
//
//        holder.bindItem(jobContainer[position],position,listener,context)
    }


    override fun getItemCount(): Int = jobContainer.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(jobContainer:JobContainer,position:Int,listener: (JobContainer) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {
                listener(jobContainer)
            }
        }
    }


    fun getTopView(type:Int): View? {
        return with(topShow.context) {
            verticalLayout {
                linearLayout{
                    orientation = LinearLayout.HORIZONTAL
                    linearLayout{
                        orientation = LinearLayout.HORIZONTAL
                        if(type==GRAY){
                            backgroundResource=R.drawable.box_shadow_bottom_bg_gray
                        }else if(type==NORMAL){
                            backgroundResource=R.drawable.box_shadow_bottom_bg_blue
                        }

                        gravity=Gravity.CENTER_VERTICAL
                        textView {
                            backgroundResource=R.drawable.circle_border_white
                            textSize=10f
                            textColor=Color.WHITE
                            text="年"
                            gravity=Gravity.CENTER
                        }.lparams {
                            leftMargin=dip(8)
                            height=dip(19)
                            width=dip(19)
                        }

                        textView {
                            textSize=12f
                            textColor=Color.WHITE
                            text="600台~800台"
                            gravity=Gravity.CENTER
                        }.lparams {
                            leftMargin=dip(8)
                            height=dip(19)
                        }
                    }.lparams {
                        leftMargin=dip(10)
                        width=dip(130)
                        height= matchParent
                    }


                    relativeLayout {
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity=Gravity.BOTTOM

                            imageView{
                                imageResource=R.mipmap.icon_canbu_home
                            }.lparams {
                            }

                            imageView{
                                imageResource=R.mipmap.icon_coffee_home
                            }.lparams {
                                leftMargin=dip(17)
                            }


                            imageView{
                                imageResource=R.mipmap.icon_fl_home
                            }.lparams {
                                leftMargin=dip(17)
                            }


                            imageView{
                                imageResource=R.mipmap.icon_cb_home
                            }.lparams {
                                leftMargin=dip(17)
                            }

                        }.lparams {
                            height= matchParent
                            alignParentLeft()
                            alignParentBottom()
                            bottomMargin=dip(8)
                        }

                        imageView{
                            imageResource=R.mipmap.icon_new_home
                        }.lparams {
                            alignParentRight()
                            alignParentBottom()
                            bottomMargin=dip(8)
                        }

                    }.lparams {
                        height= matchParent
                        width=0
                        weight=1f
                        rightMargin=dip(17)
                        leftMargin=dip(15)
                    }



                }.lparams {
                    height=dip(42)
                    width= matchParent
                }

            }
        }
    }

    fun getLabelView(type:Int): View? {
        return with(topShow.context) {
            verticalLayout {
                labelShow=linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        if(type==GRAY){
                            backgroundResource=R.drawable.label_gray_border
                            textColorResource=R.color.grayCD
                        }else if(type==NORMAL){
                            backgroundResource=R.drawable.label_theme_bule_border
                            textColorResource=R.color.blue0097D6
                        }

                        textSize=11f
                        text="東京"
                        gravity=Gravity.CENTER_VERTICAL
                        leftPadding=dip(7)
                        rightPadding=dip(7)
                    }.lparams {
                        height= matchParent
                    }

                    textView {
                        if(type==GRAY){
                            backgroundResource=R.drawable.label_gray_border
                            textColorResource=R.color.grayCD
                        }else if(type==NORMAL){
                            backgroundResource=R.drawable.label_theme_bule_border
                            textColorResource=R.color.blue0097D6
                        }
                        textSize=11f
                        text="1～3"
                        gravity=Gravity.CENTER_VERTICAL
                        leftPadding=dip(7)
                        rightPadding=dip(7)
                    }.lparams {
                        height= matchParent
                        leftMargin=dip(5)
                    }

                    textView {
                        if(type==GRAY){
                            backgroundResource=R.drawable.label_gray_border
                            textColorResource=R.color.grayCD
                        }else if(type==NORMAL){
                            backgroundResource=R.drawable.label_theme_bule_border
                            textColorResource=R.color.blue0097D6
                        }
                        textSize=11f
                        text="大卒"
                        gravity=Gravity.CENTER_VERTICAL
                        leftPadding=dip(7)
                        rightPadding=dip(7)
                    }.lparams {
                        height= matchParent
                        leftMargin=dip(5)
                    }

                }.lparams {
                    height=dip(18)

                }

            }
        }
    }

}