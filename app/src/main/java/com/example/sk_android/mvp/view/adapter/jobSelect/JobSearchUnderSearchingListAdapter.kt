package com.example.sk_android.mvp.view.adapter.jobSelect

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobSelect.JobSearchUnderSearching
import org.jetbrains.anko.*



class JobSearchUnderSearchingListAdapter(
    private val context: RecyclerView,
    private val list: Array<JobSearchUnderSearching>,
    private val listener: (JobSearchUnderSearching) -> Unit
) : RecyclerView.Adapter<JobSearchUnderSearchingListAdapter.ViewHolder>() {

    lateinit var key:TextView
    lateinit var name:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            relativeLayout {
                linearLayout {
                    backgroundResource=R.drawable.recycle_view_bottom_border
                    gravity=Gravity.CENTER_VERTICAL
                    key=textView {

                        gravity=Gravity.CENTER_VERTICAL
                        textColorResource=R.color.themeColor
                        textSize=14f
                    }.lparams {
                        height= dip(20)
                    }
                    name=textView {

                        gravity=Gravity.CENTER_VERTICAL
                        textColorResource=R.color.normalTextColor
                        textSize=14f
                    }.lparams {
                        height= dip(20)
                    }

                }.lparams {
                    width = matchParent
                    height = dip(52)

                }
            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        key.text=list[position].key
        name.text=list[position].name

        holder.bindItem(list[position],position,listener,context)
    }


    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(item: JobSearchUnderSearching, position:Int, listener: (JobSearchUnderSearching) -> Unit, context: RecyclerView) {
            itemView.setOnClickListener {
                listener(item)
            }
        }
    }



}