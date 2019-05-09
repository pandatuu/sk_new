package com.example.sk_android.mvp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sk_android.R

import com.example.sk_android.mvp.model.jobSelect.JobSearchResult
import org.jetbrains.anko.*



class JobSearchShowAdapter(
    private val context: RecyclerView,
    private val list: Array<JobSearchResult>,
    private val listener: (JobSearchResult) -> Unit
) : RecyclerView.Adapter<JobSearchShowAdapter.ViewHolder>() {

    lateinit var title:TextView
    lateinit var des:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            relativeLayout {
                verticalLayout  {
                    backgroundResource=R.drawable.recycle_view_bottom_border
                    gravity=Gravity.CENTER_VERTICAL
                    title=textView {

                        gravity=Gravity.CENTER_VERTICAL
                        textColorResource=R.color.normalTextColor
                        textSize=14f
                    }.lparams {
                        height= dip(20)
                        width = matchParent
                    }

                    des=textView {
                        gravity=Gravity.CENTER_VERTICAL
                        textColorResource=R.color.describeTextColor
                        textSize=12f
                    }.lparams {
                        topMargin=dip(5)
                        height= dip(17)
                        width = matchParent
                    }

                }.lparams {
                    width = matchParent
                    height = dip(77)

                }
            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        title.text=list[position].name
        des.text=list[position].des

        holder.bindItem(list[position],position,listener,context)
    }


    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(item: JobSearchResult, position:Int, listener: (JobSearchResult) -> Unit, context: RecyclerView) {
            itemView.setOnClickListener {
                listener(item)
            }
        }
    }



}