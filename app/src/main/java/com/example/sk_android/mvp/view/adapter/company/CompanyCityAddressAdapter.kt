package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.sk_android.R
import org.jetbrains.anko.*

class CompanyCityAddressAdapter(
    private val list: MutableList<ArrayList<String>>

) : RecyclerView.Adapter<CompanyCityAddressAdapter.ViewHolder>() {


    lateinit var image: ImageView
    var index = 0

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            relativeLayout() {
                linearLayout {
                    orientation=LinearLayout.HORIZONTAL
                    image=imageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.face_address)
                    }.lparams {
                        width=dip(11)
                        height=dip(15)
                        gravity = Gravity.CENTER_VERTICAL
                    }

                    verticalLayout {
                        textView {
                            text=list[index][0]
                            textSize=14f
                            letterSpacing=0.05f
                            textColorResource=R.color.black33
                            gravity=Gravity.CENTER
                        }.lparams {
                            height = dip(20)
                        }

                        textView {
                            if(list[index][1].length<40)
                                text=list[index][1]
                            else
                                text = "${list[index][1].substring(0,37)}..."
                            textSize=11f
                            letterSpacing=0.05f
                            textColorResource=R.color.gray89
                            gravity=Gravity.CENTER
                        }.lparams {
                            height = dip(15)
                        }
                    }.lparams {
                        leftMargin=dip(11)
                        width=0
                        weight=1f
                    }


                    verticalLayout {
                        image=imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_go_position)
                        }.lparams {
                            width=dip(6)
                            height=dip(11)
                        }
                    }.lparams {
                        width= wrapContent
                        height= wrapContent
                        gravity = Gravity.CENTER_VERTICAL
                    }




                }.lparams() {
                    topMargin=dip(5)
                    width = matchParent
                    height= wrapContent
                    bottomMargin = dip(5)
                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        index++
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem( item: String,view:View?,listener: (item: String) -> Unit) {

                listener(item)
            }
    }




}