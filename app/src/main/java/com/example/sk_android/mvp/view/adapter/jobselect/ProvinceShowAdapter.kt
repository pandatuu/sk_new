package com.example.sk_android.mvp.view.adapter.jobselect

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
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.view.fragment.jobselect.IndustryListFragment
import org.jetbrains.anko.*

class ProvinceShowAdapter(
    private val context: RecyclerView,
    private val areaList: MutableList<Area>,
    private val fatherHeight: Int,
    private val listener: (Area,Int) -> Unit
) : RecyclerView.Adapter<ProvinceShowAdapter.ViewHolder>() {

    companion object {
        var isFirst=true
        var SELECTED=2
        var NORMAL=1

    }




    fun resetData(list: MutableList<Area>) {
        areaList.clear()
        areaList.addAll(list)
        notifyDataSetChanged()
    }


    fun appendData(list: MutableList<Area>) {
        areaList.addAll(list)
        notifyDataSetChanged()
    }


    fun selectData(index:Int) {

        for(i in 0..areaList.size-1){
            areaList.get(i).type=1
        }
        areaList.get(index).type=2

        notifyDataSetChanged()
    }




    override fun getItemViewType(position:Int):Int
    {

        var area= areaList.get(position)

        return area.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        lateinit var itemShow: TextView
        var view:View
        if(viewType== NORMAL){
            view = with(parent.context) {
                relativeLayout {
                    itemShow = textView {
                        backgroundResource = R.drawable.text_view_bottom_border
                        topPadding = dip(8)
                        bottomPadding = dip(8)
                        rightPadding = dip(11)
                        leftPadding = dip(11)
                        textColorResource = R.color.normalTextColor
                        textSize = 14f
                        gravity = Gravity.CENTER

                    }.lparams {
                        width = matchParent
                        height = dip(56)
                        rightMargin = dip(15)
                        leftMargin = dip(15)
                    }
                }

            }
        }else{
             view = with(parent.context) {
                relativeLayout {
                    backgroundResource = R.color.originColor
                    itemShow = textView {
                        backgroundResource = R.color.originColor
                        topPadding = dip(8)
                        bottomPadding = dip(8)
                        rightPadding = dip(11)
                        leftPadding = dip(11)
                        textColorResource =  R.color.themeColor
                        textSize = 14f
                        gravity = Gravity.CENTER

                    }.lparams {
                        width = matchParent
                        height = dip(56)
                        rightMargin = dip(15)
                        leftMargin = dip(15)
                    }
                }

            }
        }




        return ViewHolder(view, itemShow)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         if(position == getItemCount() - 1) {
            holder.itemShow.backgroundColor = Color.WHITE

        }else{
            holder.itemShow.text = areaList[position].province

        }

        holder.bindItem(areaList[position], position, listener)
    }

    override fun getItemCount(): Int = areaList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var itemShow: TextView
        constructor(view: View, itemShow: TextView) : this(view) {
            this.itemShow=itemShow
        }

        @SuppressLint("ResourceType")
        fun bindItem(area: Area, position: Int, listener: (Area,Int) -> Unit) {
            itemShow.setOnClickListener {
                listener(area,position)
            }
        }
    }


}