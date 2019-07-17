package com.example.sk_android.mvp.view.adapter.jobselect.jobSelect

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import org.jetbrains.anko.*

class RecruitInfoSelectBarMenuSelectListAdapter(
    private val context: RecyclerView,
    private val list: MutableList<SelectedItem>,
    private val listener: (SelectedItem) -> Unit
) : RecyclerView.Adapter<RecruitInfoSelectBarMenuSelectListAdapter.ViewHolder>() {





    fun appendData(data: MutableList<SelectedItem>){
        list.addAll(data)
        notifyDataSetChanged()
    }















    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        lateinit var textView:TextView
        lateinit var imageView: ImageView

        var view = with(parent.context) {
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


                    imageView=imageView {
                        setImageResource(R.mipmap.icon_select_home)
                        visibility=View.INVISIBLE
                    }.lparams {
                        alignParentRight()
                        centerVertically()
                        rightMargin=dip(7)
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(52)


                }


            }

        }
        return ViewHolder(view,textView,imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text=list[position].name
        holder.bindItem( holder.textView, holder.imageView,list[position],position,listener,context)
        if(list[position].selected){
            holder.textView.textColorResource=R.color.themeColor
            holder.imageView.visibility=View.VISIBLE
        }

        holder.setIsRecyclable(false);

    }


    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View,val textView:TextView,val imageView:ImageView) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(textView:TextView,imageView:ImageView,str:SelectedItem,position:Int,listener: (SelectedItem) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {
                for(i in 0 until  context.childCount) {
                    (((context.getChildAt(i) as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView). textColorResource = R.color.normalTextColor
                    (((context.getChildAt(i) as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(1) as ImageView).visibility=View.INVISIBLE
                }
                textView.textColorResource=R.color.themeColor
                imageView.visibility=View.VISIBLE
                listener(str)
            }
        }
    }



}