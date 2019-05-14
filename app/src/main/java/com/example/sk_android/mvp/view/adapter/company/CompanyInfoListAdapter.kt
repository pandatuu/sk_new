package com.example.sk_android.mvp.view.adapter.company

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
import com.example.sk_android.mvp.model.jobSelect.JobContainer
import org.jetbrains.anko.*

class CompanyInfoListAdapter(
    private val context: RecyclerView,
    private val jobContainer: MutableList<JobContainer>,
    private val listener: (JobContainer) -> Unit
) : RecyclerView.Adapter<CompanyInfoListAdapter.ViewHolder>() {

    lateinit var textView:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            relativeLayout {
                verticalLayout {
                    backgroundColor=Color.WHITE
                    linearLayout{
                        orientation = LinearLayout.HORIZONTAL
                        imageView{
                            imageResource=R.mipmap.logo_company
                        }.lparams {
                            width=dip(50)
                            height=dip(50)
                        }

                        verticalLayout {
                            gravity=Gravity.BOTTOM
                            textView= textView {
                                gravity=Gravity.CENTER_VERTICAL
                                textColorResource=R.color.normalTextColor
                                textSize=16f
                                text="任天堂株式会社東京本社"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                bottomMargin=dip(3)
                            }

                            linearLayout {
                                orientation=LinearLayout.HORIZONTAL
                                textView {
                                    textColorResource=R.color.gray99
                                    textSize=13f
                                    text="東京都"
                                }.lparams {
                                }

                                textView {
                                    textColorResource=R.color.gray99
                                    textSize=13f
                                    text="中央区"
                                }.lparams {
                                    leftMargin=dip(2)
                                }

                                textView {
                                    textColorResource=R.color.gray99
                                    textSize=13f
                                    text="ｘｘｘｘ"
                                }.lparams {
                                    leftMargin=dip(2)
                                }

                            }



                        }.lparams {
                            height= matchParent
                            width=wrapContent
                            leftMargin=dip(11)
                        }



                    }.lparams {
                        height= wrapContent
                        width= matchParent
                        topMargin=dip(25)
                        leftMargin=dip(15)
                    }


                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL

                        textView {
                            backgroundResource=R.drawable.radius_border_blue_02b8f7
                            textColorResource=R.color.blue0097D6
                            textSize=11f
                            text="上場してる"
                            gravity=Gravity.CENTER_VERTICAL
                            leftPadding=dip(7)
                            rightPadding=dip(7)
                        }.lparams {
                            height= matchParent
                        }

                        textView {
                            backgroundResource=R.drawable.radius_border_blue_02b8f7
                            textColorResource=R.color.blue0097D6
                            textSize=11f
                            text="500-999人"
                            gravity=Gravity.CENTER_VERTICAL
                            leftPadding=dip(7)
                            rightPadding=dip(7)
                        }.lparams {
                            height= matchParent
                            leftMargin=dip(10)
                        }

                        textView {
                            backgroundResource=R.drawable.radius_border_blue_02b8f7
                            textColorResource=R.color.blue0097D6
                            textSize=11f
                            text="インターネット"
                            gravity=Gravity.CENTER_VERTICAL
                            leftPadding=dip(7)
                            rightPadding=dip(7)
                        }.lparams {
                            height= matchParent
                            leftMargin=dip(10)
                        }

                    }.lparams {
                        height=dip(18)
                        topMargin=dip(10)
                        leftMargin=dip(15)
                    }


                    relativeLayout {
                        backgroundResource=R.drawable.border_top_ebeaeb
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity=Gravity.CENTER_VERTICAL

                            textView {
                                textColorResource=R.color.gray89
                                textSize=12f
                                text="大ヒット:"
                                gravity=Gravity.CENTER_VERTICAL
                            }.lparams {
                            }

                            textView {
                                textColorResource=R.color.gray5c
                                textSize=12f
                                text="phpエンジニアなど"
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                gravity=Gravity.CENTER_VERTICAL
                            }.lparams {
                                leftMargin=dip(2)
                            }


                            textView {
                                textColorResource=R.color.gray89
                                textSize=12f
                                text="職位20"
                                gravity=Gravity.CENTER_VERTICAL
                            }.lparams {
                            }



                        }.lparams {
                            height= matchParent
                            alignParentLeft()

                        }

                        imageView{
                            var flag=true
                            imageResource=R.mipmap.icon_go_position
                        }.lparams {
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams {
                        height=dip(55)
                        width= matchParent
                        rightMargin=dip(15)
                        leftMargin=dip(15)
                        topMargin=dip(15)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(170)
                    topMargin=dip(8)
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