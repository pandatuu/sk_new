package com.example.sk_android.mvp.view.adapter.privacyset

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.BlackListItemModel
import org.jetbrains.anko.*
import java.util.*

class BlackAddItemAdapter(mText : String, mList : LinkedList<BlackListItemModel>) : RecyclerView.Adapter<BlackAddItemAdapter.ViewHolder>() {
    var list : LinkedList<BlackListItemModel> = mList
    var text = mText
    var index = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        println("1111111111111111111111111"+list.size)
        return ViewHolder(with(p0.context){
            relativeLayout {
                if(list.get(index).companyName.indexOf(text)!==-1)
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    textView {
                        text = list.get(index).companyName
                        textSize = 15f
                    }.lparams{
                        centerVertically()
                        alignParentLeft()
                    }
                }.lparams{
                    width = matchParent
                    height = dip(50)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        })
    }

    override fun getItemCount(): Int {
       if(list!=null)
           return list.size
        return 0

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
       index++
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun RecyclerViewHolder(itemView: View) {
            super.itemView

        }
    }
}