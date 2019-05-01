package com.example.sk_android.mvp.view.adapter

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
import com.example.sk_android.mvp.model.JobContainer
import com.example.sk_android.mvp.model.SelectedItem
import org.jetbrains.anko.*



class RecruitInfoSelectBarMenuPlaceAdapter(
    private val context: RecyclerView,
    private val list: Array<SelectedItem>,
    private val listener: (SelectedItem) -> Unit
) : RecyclerView.Adapter<RecruitInfoSelectBarMenuPlaceAdapter.ViewHolder>() {

    lateinit var textView:TextView
    lateinit var imageView: ImageView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        textView.text=list[position].name
        holder.bindItem(textView,imageView,list[position],position,listener,context)
        if(list[position].selected){
            textView.textColorResource=R.color.themeColor
            imageView.visibility=View.VISIBLE
        }
    }


    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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