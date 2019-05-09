package com.example.sk_android.mvp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobSelect.JobContainer
import org.jetbrains.anko.*

class MessageChatRecordListAdapter(
    private val context: RecyclerView,
    private val jobContainer: MutableList<JobContainer>,
    private val listener: (JobContainer) -> Unit
) : RecyclerView.Adapter<MessageChatRecordListAdapter.ViewHolder>() {

    lateinit var textView:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            relativeLayout {
                linearLayout {
                    backgroundResource=R.drawable.text_view_bottom_border
                    imageView {

                        imageResource=R.mipmap.icon_tx_home

                    }.lparams {
                        width=dip(44)
                        height=dip(44)
                        topMargin=dip(15)
                    }
                    linearLayout{
                        gravity=Gravity.CENTER_VERTICAL
                        verticalLayout {
                            linearLayout{
                                textView {
                                    text="清水さん"
                                    textSize=16f
                                    textColorResource=R.color.normalTextColor
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                                }

                                textView {
                                    text="ジャさん·社長"
                                    textSize=12f
                                    textColorResource=R.color.grayb3
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                                }.lparams {
                                    leftMargin=dip(7)
                                }
                            }

                            linearLayout{
                                gravity=Gravity.CENTER_VERTICAL
                                textView {
                                    backgroundResource=R.drawable.ellipse_border_grayb3
                                    text="已读"
                                    textSize=11f
                                    textColorResource=R.color.gray99
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                    topPadding=dip(2)
                                    bottomPadding=dip(2)
                                    leftPadding=dip(5)
                                    rightPadding=dip(5)
                                }

                                textView {
                                    text="是非御社で働きたいと思います。"
                                    textSize=14f
                                    textColorResource=R.color.gray5c
                                    setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                                }.lparams {
                                    leftMargin=dip(7)
                                }
                            }.lparams {
                                topMargin=dip(12)
                            }
                        }

                    }.lparams {
                        height= matchParent
                        leftMargin=dip(14)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(95)
                }
            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // textView.text=jobContainer[position].containerName

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