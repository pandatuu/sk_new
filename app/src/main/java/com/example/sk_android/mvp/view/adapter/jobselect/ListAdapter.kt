package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.jiguang.imui.utils.SpannableStringUtil
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.JobWantedModel
import com.example.sk_android.mvp.model.message.ChatRecordModel
import com.example.sk_android.mvp.view.adapter.message.MessageChatRecordListAdapter
import com.pingerx.imagego.core.strategy.loadImage
import org.jetbrains.anko.*
import java.util.*





class ListAdapter(
    private val context: RecyclerView,
    private val mData: MutableList<JobWantedModel>,
    private val listener: (JobWantedModel) -> Unit
) : RecyclerView.Adapter<ListAdapter.ViewHolder>()
{



    fun setChatRecords(list: MutableList<JobWantedModel>) {
        mData.clear()
        mData.addAll(list)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mData.size



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        var title: TextView? = null
        var address: TextView? = null
        var salary: TextView? = null
        var industry: TextView? = null
        var industrySon: TextView? = null


        var view = with(parent!!.context){

            relativeLayout(){
                verticalLayout {
                //backgroundResource = R.drawable.text_view_bottom_border
                linearLayout(){

                    title= textView {
                        text="PHP"
                        textSize=18f
                        textColorResource= R.color.black33
                        //backgroundColor= Color.RED
                        includeFontPadding=false
                        gravity=Gravity.CENTER
                    }.lparams(){
                        width= wrapContent
                        height= dip(28)
                        leftMargin=10
                    }
                }.lparams(){
                    topMargin=dip(12)
                }

                linearLayout(){
                    gravity=Gravity.CENTER
                    address=  textView {
                        text="| 东京"
                        textSize=12f
                        textColorResource=R.color.jobNameLabel
                    }.lparams(){
                        width= wrapContent
                        height= wrapContent

                    }
                    salary=textView {
                        text="| 5円~8円"
                        textSize=12f
                        textColorResource=R.color.jobNameLabel
                    }.lparams(){
                        width= wrapContent
                        height= wrapContent
                        leftMargin=10
                    }
                   industry= textView {
                        text="| 互联网"
                        textSize=12f
                        textColorResource=R.color.jobNameLabel
                    }.lparams(){
                        width= wrapContent
                        height= wrapContent
                        leftMargin=10
                    }
                    industrySon=textView {
                        text="计算机软件"
                        textSize=12f
                        textColorResource=R.color.jobNameLabel
                    }.lparams(){
                        width= wrapContent
                        height= wrapContent
                        leftMargin=10
                    }
                }.lparams(){
                    height= dip(40)
                    width= wrapContent
                }
                }.lparams(){
                    height=wrapContent
                    width= matchParent
                }
            }

        }
        return ViewHolder(view, title, address, salary, industry, industrySon)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title?.text=mData[position].title
        holder.address?.text="| "+mData[position].address
        holder.salary?.text="| "+mData[position].salary
        holder.industry?.text="| "+mData[position].industry
        holder.industrySon?.text="| "+mData[position].industrySon




        holder.bindItem(mData[position],position,listener,context)
    }





    class ViewHolder(
        view: View,
        val title: TextView?,
        val address: TextView?,
        val salary: TextView?,
        val industry: TextView?,
        val industrySon: TextView?
    ) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem(model:JobWantedModel,position:Int,listener: (JobWantedModel) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {
                listener(model)
            }
        }
    }

}