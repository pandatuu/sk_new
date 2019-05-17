package com.example.sk_android.mvp.view.adapter.company

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
import com.example.sk_android.mvp.model.jobselect.Club
import com.example.sk_android.mvp.model.jobselect.Industry
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class LabelShowAdapter(
    private val professions: MutableList<String>,
    private val listener: (String) -> Unit

) : RecyclerView.Adapter<LabelShowAdapter.ViewHolder>() {


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
                    topMargin = dip(5)
                    leftMargin = dip(5)
                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position==0)
            for (item in professions) {
                itemShow.addView(getItemView(item))
            }
    }

    override fun getItemCount(): Int = professions.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem(str: String, listener: (String) -> Unit) {
            itemView.setOnClickListener {
                listener(str)
            }
        }
    }

    fun getItemView(tx: String): View? {
        return with(itemShow.context) {
            verticalLayout {
                relativeLayout {
                    textView {
                        text = tx
                        backgroundResource = R.drawable.radius_border_unselect
                        topPadding = dip(8)
                        bottomPadding = dip(8)
                        rightPadding = dip(10)
                        leftPadding = dip(10)
                        textColorResource = R.color.black33
                        textSize = 11f
                    }.lparams {
                        leftMargin = dip(10)
                        topMargin = dip(10)
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                }
            }
        }
    }




}