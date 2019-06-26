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
import com.example.sk_android.mvp.model.jobselect.JobContainer
import org.jetbrains.anko.*



class IndustryListAdapter(
    private val context: RecyclerView,
    private val dataList: MutableList<JobContainer>,
    private val listener: (JobContainer,Int) -> Unit
) : RecyclerView.Adapter<IndustryListAdapter.ViewHolder>() {

    companion object {
        var NORMAL=1
        var SELECTED=2
        var BEFORE_SELECTED=3
    }


    fun selectData(index:Int) {

        //重置
        for(i in 0..dataList.size-1){
            dataList.get(i).selectedType= NORMAL
        }

        //前一项也没有下划线
        if(index-1>=0){
            dataList.get(index-1).selectedType= BEFORE_SELECTED
        }

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
                        backgroundResource=R.drawable.recycle_view_bottom_border
                        textView=textView {
                            gravity=Gravity.CENTER_VERTICAL
                            textColorResource=R.color.normalTextColor
                            textSize=14f
                        }.lparams {
                            alignParentLeft()
                            height= matchParent

                        }


                        imageView {
                            setImageResource(R.mipmap.icon_go_position)
                        }.lparams {
                            alignParentRight()
                            centerVertically()
                            rightMargin=dip(7)
                        }

                    }.lparams() {
                        width = matchParent
                        height = dip(52)
                        leftMargin=dip(15)
                        rightMargin=dip(15)


                    }


                }

            }

        }else if(viewType== BEFORE_SELECTED){
            //选中前一项
            view = with(parent.context) {
                verticalLayout {
                    relativeLayout() {
                        textView=textView {
                            gravity=Gravity.CENTER_VERTICAL
                            textColorResource=R.color.normalTextColor
                            textSize=14f
                        }.lparams {
                            alignParentLeft()
                            height= matchParent

                        }


                        imageView {
                            setImageResource(R.mipmap.icon_go_position)
                        }.lparams {
                            alignParentRight()
                            centerVertically()
                            rightMargin=dip(7)
                        }

                    }.lparams() {
                        width = matchParent
                        height = dip(52)
                        leftMargin=dip(15)
                        rightMargin=dip(15)

                    }


                }

            }




        } else{
            //选中
            view = with(parent.context) {
                verticalLayout {
                    backgroundColorResource=R.color.grayF6
                    relativeLayout() {
                        textView=textView {
                            gravity=Gravity.CENTER_VERTICAL
                            textColorResource=R.color.themeColor
                            textSize=14f
                        }.lparams {
                            alignParentLeft()
                            height= matchParent

                        }


                        imageView {
                            setImageResource(R.mipmap.icon_go_position)
                        }.lparams {
                            alignParentRight()
                            centerVertically()
                            rightMargin=dip(7)
                        }

                    }.lparams() {
                        width = matchParent
                        height = dip(52)
                        leftMargin=dip(15)
                        rightMargin=dip(15)

                    }


                }

            }
        }




        return ViewHolder(view,textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text=dataList[position].containerName

        holder.bindItem(dataList[position],position,listener,context)
    }


    override fun getItemCount(): Int = dataList.size

    class ViewHolder(view: View,val textView:TextView) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(jobContainer:JobContainer,position:Int,listener: (JobContainer,Int) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {
                listener(jobContainer,position)
            }
        }
    }



}