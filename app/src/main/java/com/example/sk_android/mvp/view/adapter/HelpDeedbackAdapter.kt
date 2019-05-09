package com.example.sk_android.mvp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class HelpDeedbackAdapter(var mData : LinkedList<Array<String>>) : RecyclerView.Adapter<HelpDeedbackAdapter.ViewHolder>() {
    var index : Int = 0
    var texta :String = ""
    lateinit var toolbar1 : Toolbar
    var tool = LinkedList<Toolbar>()
    var number : Int = 0
    var vertiveList = LinkedList<LinearLayout>()
    lateinit var vertive : LinearLayout
    lateinit var relative : RelativeLayout

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
//        Log.d("aaa","create----"+index)

        val view = with(parent.context){
            verticalLayout{
                relative = relativeLayout{
                    backgroundResource= R.drawable.actionbar_bottom_border
                    textView{
                        texta = mData.get(index)[0]
                        text= texta
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
                    toolbar1=toolbar{
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
                    tool.add(toolbar1)
                }.lparams{
                    width = matchParent
                    height = dip(55)
                    leftPadding = dip(15)
                    rightPadding = dip(15)
                }

                vertive = verticalLayout {
                    visibility = 8
                    val arraylength = mData.get(index).size
                    for (a in 2..arraylength) {
                        textView {
                            text = mData.get(index)[a-1]
                            textSize = 13f
                            textColor = Color.parseColor("#5C5C5C")
                            includeFontPadding = false
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams {
                            width = matchParent
                            height = dip(35)
                            leftMargin = dip(13)
                        }
                    }
                }.lparams{
                    width = matchParent
                    height = dip(105)
                    leftPadding = dip(15)
                    rightPadding = dip(15)
                }
                vertiveList.add(vertive)
            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.d("bbb","bind----"+index)

        p0.itemView.onClick {
            if(number%2==0){
                vertiveList.get(p1).visibility = View.VISIBLE
                tool.get(p1).navigationIconResource = R.mipmap.icon_down
            }else{
                vertiveList.get(p1).visibility = View.GONE
                tool.get(p1).navigationIconResource = R.mipmap.icon_go_position
            }

            number++
        }

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