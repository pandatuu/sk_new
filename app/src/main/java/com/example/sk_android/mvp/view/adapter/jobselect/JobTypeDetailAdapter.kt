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
    private val dataList: MutableList<Job>,
    private val listener: (Job,Int) -> Unit
) : RecyclerView.Adapter<JobTypeDetailAdapter.ViewHolder>() {




    companion object {
        var NORMAL=1
        var SELECTED=2
    }


    fun selectData(index:Int) {

        //重置
        for(i in 0..dataList.size-1){
            dataList.get(i).selectedType= NORMAL
        }

//        //前一项也没有下划线
//        if(index-1>=0){
//            dataList.get(index-1).selectedType= BEFORE_SELECTED
//        }

        //选中项
        dataList.get(index).selectedType=SELECTED

        notifyDataSetChanged()
    }



    override fun getItemViewType(position:Int):Int
    {

        var item= dataList.get(position)
        return item.selectedType

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lateinit var textView:TextView
        lateinit var view:View
        if(viewType== NORMAL){
            view = with(parent.context) {
                verticalLayout {
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
        }else {
            view = with(parent.context) {
                verticalLayout {
                    relativeLayout() {
                        textView=textView {
                            gravity=Gravity.CENTER_VERTICAL
                            textColorResource=R.color.themeColor
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
        }

        return ViewHolder(view,textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text=dataList[position].name

        holder.bindItem(dataList[position],position,listener,context)
    }


    override fun getItemCount(): Int = dataList.size

    class ViewHolder(view: View,val textView:TextView) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(job: Job, position:Int, listener: (Job,Int) -> Unit, context: RecyclerView) {
            itemView.setOnClickListener {
                listener(job,position)
            }
        }
    }



}