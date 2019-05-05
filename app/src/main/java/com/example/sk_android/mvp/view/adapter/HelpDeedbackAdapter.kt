package com.example.sk_android.mvp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.Club
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class HelpDeedbackAdapter(var mData : LinkedList<String>) : RecyclerView.Adapter<HelpDeedbackAdapter.ViewHolder>() {
    var index : Int = 0
    var num :Int = 0
    lateinit var secoundrecycle : RecyclerView
    var toolbar1= LinkedList<Toolbar>()
    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        Log.d("aaa","create----"+index)
        var list = LinkedList<String>()
        list.add("チュートリアル")
        list.add("攻略")
        list.add("アクティビティ")
        val view = with(parent.context){
            verticalLayout{
                relativeLayout{
                    backgroundResource= R.drawable.actionbar_bottom_border
                    textView{
                        text=mData.get(index)
                        textSize=13f
                        textColor = Color.parseColor("#5C5C5C")
                        includeFontPadding=false
                    }.lparams{
                        width = matchParent
                        height = dip(19)
                        alignParentLeft()
                        topMargin = dip(19)
                        bottomMargin = dip(19)
                    }

                    var a=toolbar{
                        backgroundResource = R.color.transparent
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_go_position
                    }.lparams{
                        width = dip(11)
                        height = dip(11)
                        alignParentRight()
                        topMargin = dip(19)
                        bottomMargin = dip(19)
                    }
                    toolbar1.add(a)
                }.lparams{
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
        Log.d("bbb","bind----"+index)

        index++
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun RecyclerViewHolder(itemView:View) {
            super.itemView

        }
    }
    
}