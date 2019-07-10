package com.example.sk_android.mvp.view.adapter.myhelpfeedback

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.activity.myhelpfeedback.HelpDetailInformation
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import withTrigger

class SecondHelpInformationAdapter(var mData: List<HelpModel>, val mContext: Context) :
    RecyclerView.Adapter<SecondHelpInformationAdapter.ViewHolder>() {
    var index: Int = 0
    var title: String = ""
    lateinit var toolbar1: Toolbar

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        if (index == mData.size)
            index = 0
        val item = mData.get(index)
        val id = item.id
        title = item.title
        val mClass: Class<out Any> = HelpDetailInformation::class.java
        val view = with(parent.context) {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.actionbar_bottom_border
                    textView {
                        text = title
                        textSize = 13f
                        textColor = Color.parseColor("#FF333333")
                        includeFontPadding = false
                    }.lparams {
                        width = matchParent
                        height = dip(19)
                        alignParentLeft()
                        topMargin = dip(19)
                        bottomMargin = dip(19)
                    }
                    toolbar {
                        backgroundResource = R.color.transparent
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_go_position
                        this.withTrigger().click {
                            val intent = Intent(mContext, mClass)
                            intent.putExtra("id", id.toString())
                            startActivity(intent)

                        }
                    }.lparams {
                        width = dip(30)
                        height = wrapContent
                        alignParentRight()
                        centerVertically()
                    }
                    this.withTrigger().click {
                        val intent = Intent(mContext, mClass)
                        intent.putExtra("id", id.toString())
                        startActivity(intent)

                    }
                }.lparams {
                    width = matchParent
                    height = dip(55)
                    leftPadding = dip(15)
                    rightPadding = dip(15)
                }
            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.d("", mData.get(index).toString())
        index++
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun RecyclerViewHolder(itemView: View) {
            super.itemView

        }
    }

}