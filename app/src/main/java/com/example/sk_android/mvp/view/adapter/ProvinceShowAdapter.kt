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
import com.example.sk_android.mvp.model.jobSelect.City
import org.jetbrains.anko.*

class ProvinceShowAdapter(
    private val context: RecyclerView,
    private val Citys: MutableList<City>,
    private val fatherHeight: Int,
    private val listener: (City) -> Unit
) : RecyclerView.Adapter<ProvinceShowAdapter.ViewHolder>() {

    lateinit var itemShow: TextView
    lateinit var blankSpace: LinearLayout


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            relativeLayout {
                itemShow = textView {
                        backgroundResource=R.drawable.text_view_bottom_border
                        topPadding = dip(8)
                        bottomPadding = dip(8)
                        rightPadding = dip(11)
                        leftPadding = dip(11)
                        textColorResource = R.color.normalTextColor
                        textSize = 14f
                        gravity = Gravity.CENTER

                    }.lparams {
                        width = matchParent
                        height=dip(56)
                        rightMargin =dip(15)
                        leftMargin = dip(15)
                    }
            }

        }
        return ViewHolder(view)
    }
    var selectedView: View?=null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position==0){
            (itemShow.parent as RelativeLayout).backgroundResource=R.color.originColor
            itemShow.backgroundResource=R.color.originColor
            itemShow.textColorResource=R.color.themeColor
        }
        itemShow.text = Citys[position].province
        if(position==getItemCount()-1){
            itemShow.backgroundColor=Color.WHITE

        }
        holder.bindItem(Citys[position],position,listener,context)
    }

    override fun getItemCount(): Int = Citys.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(city: City, position:Int,listener: (City) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {
                //设置选中的item的样式
                for(i in 0 until  context.childCount) {
                    (context.getChildAt(i) as  RelativeLayout).backgroundColor=Color.WHITE
                    if(i!= context.childCount-1){
                        ((context.getChildAt(i) as  RelativeLayout).getChildAt(0) as TextView).backgroundResource=R.drawable.text_view_bottom_border
                    }else{
                        ((context.getChildAt(i) as  RelativeLayout).getChildAt(0) as TextView).backgroundColor=Color.WHITE
                    }
                    ((context.getChildAt(i) as  RelativeLayout).getChildAt(0) as TextView). textColorResource = R.color.normalTextColor
                }
                (it as RelativeLayout).backgroundResource=R.color.originColor
                ((it as RelativeLayout).getChildAt(0) as TextView).backgroundResource=R.color.originColor
                ((it as RelativeLayout).getChildAt(0) as TextView).textColorResource=R.color.themeColor


                listener(city)
            }
        }
    }




}