package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout

import org.jetbrains.anko.*

/**
 *
 * Created by Wanhar Aderta Daeng Maro on 9/7/2018.
 * Email : wanhardaengmaro@gmail.com
 *
 */
class JobSearchHistoryAdapter(
    private val context: RecyclerView,
    private val list: Array<String>,
    private val listener: (item: String) -> Unit

) : RecyclerView.Adapter<JobSearchHistoryAdapter.ViewHolder>() {

    lateinit var itemShow: FlowLayout

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                verticalLayout() {
                    itemShow = flowLayout {
                    }
                }.lparams() {
                    width = matchParent
                    height = wrapContent
                    topMargin = dip(7)
                }
            }
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position==1)
            for (item in list) {
                var view=getItemView(item)
                itemShow.addView(view)
                holder.bindItem(item,view!!,listener)
            }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem(item: String,view:View,listener: ( item: String) -> Unit) {
            var selectedItem=(((view!! as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView)
            selectedItem.setOnClickListener {
                listener(item)
            }
        }
    }

    fun getItemView(tx: String): View? {
        return with(itemShow.context) {
            verticalLayout {
                relativeLayout {
                    textView {
                        text = tx
                        backgroundColorResource = R.color.originColor
                        topPadding = dip(8)
                        bottomPadding = dip(8)
                        rightPadding = dip(11)
                        leftPadding = dip(11)
                        textColorResource = R.color.selectButtomTextColor
                        textSize = 13f

                    }.lparams {
                        topMargin = dip(15)
                        leftMargin=dip(20)
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                }
            }
        }
    }





}