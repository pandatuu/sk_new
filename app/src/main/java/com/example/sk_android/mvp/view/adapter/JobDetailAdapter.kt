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
import org.jetbrains.anko.*



class JobDetailAdapter(
    private val context: RecyclerView,
    private val jobNameList: Array<String>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<JobDetailAdapter.ViewHolder>() {

    lateinit var textView:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                backgroundResource=R.drawable.recycle_view_gray_bottom_border
                relativeLayout() {
                    textView=textView {
                        gravity=Gravity.CENTER_VERTICAL
                        textColorResource=R.color.normalTextColor
                        textSize=14f
                    }.lparams {
                        height= matchParent
                        width = matchParent
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(53)

                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        textView.text=jobNameList[position]

        holder.bindItem(jobNameList[position],position,listener,context)
    }


    override fun getItemCount(): Int = jobNameList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(jobName: String, position:Int, listener: (String) -> Unit, context: RecyclerView) {
            itemView.setOnClickListener {
                for(i in 0 until  context.childCount) {
                    (((context.getChildAt(i) as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView). textColorResource = R.color.normalTextColor
                }
                (((it as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView).textColorResource=R.color.themeColor
                listener(jobName)
            }
        }
    }



}