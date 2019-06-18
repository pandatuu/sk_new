package com.example.sk_android.mvp.view.adapter.jobselect

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.sk_android.R
import org.jetbrains.anko.*
import java.util.*


class ListAdapter(private val mData: LinkedList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): Any? {
        return mData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val appUser = getItem(position)

        if(position==getCount()-1){
            return  with(parent!!.context) {
                verticalLayout {
                    verticalLayout() {


                    }.lparams() {
                        width = matchParent
                        height = dip(80)
                        rightMargin = 50
                        leftMargin = 50
                    }
                }
            }
        }

        return with(parent!!.context){

            verticalLayout{
                backgroundResource = R.drawable.text_view_bottom_border
                linearLayout(){

                    textView {
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
                    textView {
                        text="10K-20K"
                        textSize=12f
                        textColorResource=R.color.jobNameLabel
                    }.lparams(){
                        width= wrapContent
                        height= wrapContent

                    }
                    textView {
                        text="移动互联网"
                        textSize=12f
                        textColorResource=R.color.jobNameLabel
                    }.lparams(){
                        width= wrapContent
                        height= wrapContent
                        leftMargin=10
                    }
                    textView {
                        text="|"
                        textSize=12f
                        textColorResource=R.color.jobNameLabel
                    }.lparams(){
                        width= wrapContent
                        height= wrapContent
                        leftMargin=10
                    }
                    textView {
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
                }

            }

        }
    }
}