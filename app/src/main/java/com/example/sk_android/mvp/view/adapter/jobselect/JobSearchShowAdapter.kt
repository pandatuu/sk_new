package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sk_android.R

import com.example.sk_android.mvp.model.jobselect.JobSearchResult
import org.jetbrains.anko.*



class JobSearchShowAdapter(
    private val context: RecyclerView,
    private val list: MutableList<JobSearchResult>,
    private val listener: (JobSearchResult,Int) -> Unit
) : RecyclerView.Adapter<JobSearchShowAdapter.ViewHolder>() {

    var NORMAL=1
    var SELECTED=2


    fun selectData(index:Int) {

        //重置
        for(i in 0..list.size-1){
            list.get(i).type= NORMAL
        }


        //选中项
        list.get(index).type= SELECTED

        notifyDataSetChanged()
    }




    override fun getItemViewType(position:Int):Int
    {

        var item= list.get(position)

        return item.type
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        lateinit var title:TextView
        lateinit var des:TextView
        var view:View
        if(viewType==NORMAL){
            view = with(parent.context) {
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
        }else{
            view = with(parent.context) {
                relativeLayout {
                    verticalLayout  {
                        backgroundResource=R.drawable.recycle_view_bottom_border
                        gravity=Gravity.CENTER_VERTICAL
                        title=textView {
                            gravity=Gravity.CENTER_VERTICAL
                            textColorResource=R.color.themeColor
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
        }

        return ViewHolder(view,title,des)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=list[position].name
        holder.des.text=list[position].des

        holder.bindItem(list[position],position,listener,context)
    }


    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View,val title:TextView,val des:TextView) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(item: JobSearchResult, position:Int, listener: (JobSearchResult,Int) -> Unit, context: RecyclerView) {
            itemView.setOnClickListener {
                listener(item,position)
            }
        }


    }



}