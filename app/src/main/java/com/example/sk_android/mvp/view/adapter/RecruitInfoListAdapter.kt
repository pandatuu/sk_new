package com.example.sk_android.mvp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.JobContainer
import org.jetbrains.anko.*



class RecruitInfoListAdapter(
    private val context: RecyclerView,
    private val jobContainer: MutableList<JobContainer>,
    private val listener: (JobContainer) -> Unit
) : RecyclerView.Adapter<RecruitInfoListAdapter.ViewHolder>() {

    lateinit var textView:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            relativeLayout {
                verticalLayout {
                    backgroundResource=R.drawable.box_shadow
                    linearLayout{
                        orientation = LinearLayout.HORIZONTAL
                        linearLayout{
                            orientation = LinearLayout.HORIZONTAL
                            backgroundResource=R.drawable.box_shadow_bottom
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


                    textView=textView {
                        gravity=Gravity.CENTER_VERTICAL
                        textColorResource=R.color.companyNameGray
                        textSize=13f
                        text="株式会社日本電気"
                    }.lparams {
                        leftMargin=dip(20)
                        topMargin=dip(20)
                    }

                    textView {
                        gravity=Gravity.CENTER_VERTICAL
                        textColorResource=R.color.normalTextColor
                        textSize=16f
                        text="株式会社日本電気"
                    }.lparams {
                        leftMargin=dip(20)
                        topMargin=dip(4)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL

                        textView {
                            backgroundResource=R.drawable.theme_bule_border
                            textColorResource=R.color.recruitInfoActionBarFragmentTinyLabelColor
                            textSize=11f
                            text="東京"
                            gravity=Gravity.CENTER_VERTICAL
                            leftPadding=dip(7)
                            rightPadding=dip(7)
                        }.lparams {
                            height= matchParent
                        }

                        textView {
                            backgroundResource=R.drawable.theme_bule_border
                            textColorResource=R.color.recruitInfoActionBarFragmentTinyLabelColor
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
                            backgroundResource=R.drawable.theme_bule_border
                            textColorResource=R.color.recruitInfoActionBarFragmentTinyLabelColor
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
                        topMargin=dip(8)
                        leftMargin=dip(20)
                    }


                    relativeLayout {
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity=Gravity.CENTER_VERTICAL
                            imageView{
                                imageResource=R.mipmap.icon_tx_home
                            }.lparams {

                            }
                            textView {
                                textColorResource=R.color.gray5c
                                textSize=11f
                                text="ジャさん·社長"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                gravity=Gravity.CENTER_VERTICAL
                            }.lparams {
                                leftMargin=dip(10)
                            }

                            imageView{
                                imageResource=R.mipmap.icon_gold_home
                            }.lparams {
                                leftMargin=dip(10)
                            }

                            imageView{
                                imageResource=R.mipmap.icon_message_home
                            }.lparams {
                                leftMargin=dip(10)

                            }
                        }.lparams {
                            height= matchParent
                            alignParentLeft()

                        }

                        imageView{
                            var flag=true
                            imageResource=R.mipmap.icon_zan_h_home
                            setOnClickListener ( object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    if(flag){
                                        flag=false
                                        imageResource=R.mipmap.icon_zan_n_home

                                    }else{
                                        flag=true
                                        imageResource=R.mipmap.icon_zan_h_home
                                    }
                                }
                            })
                        }.lparams {
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams {
                        height=dip(30)
                        width= matchParent
                        rightMargin=dip(17)
                        leftMargin=dip(20)
                        topMargin=dip(15)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(200)
                    topMargin=dip(5)
                }
            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        textView.text=jobContainer[position].containerName

        holder.bindItem(jobContainer[position],position,listener,context)
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



}