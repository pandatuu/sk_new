package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.Job
import org.jetbrains.anko.*



class JobTypeDetailAdapter(
    private val context: RecyclerView,
    private val jobList: MutableList<Job>,
    private val listener: (Job) -> Unit
) : RecyclerView.Adapter<JobTypeDetailAdapter.ViewHolder>() {

    lateinit var textView:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                backgroundResource=R.drawable.recycle_view_bottom_border
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
        textView.text=jobList[position].jobType

        holder.bindItem(jobList[position],position,listener,context)
    }


    override fun getItemCount(): Int = jobList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(job: Job, position:Int, listener: (Job) -> Unit, context: RecyclerView) {
            itemView.setOnClickListener {
                for(i in 0 until  context.childCount) {
                    (((context.getChildAt(i) as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView). textColorResource = R.color.normalTextColor
                }
                (((it as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView).textColorResource=R.color.themeColor
                listener(job)
            }
        }
    }



}