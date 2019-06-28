package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import org.jetbrains.anko.*

class CompanyCityAddressAdapter(
    private val list: MutableList<String>

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
                        topMargin=dip(3)
                    }


                    verticalLayout {
                        textView {
                            text=list[index]
                            textSize=14f
                            letterSpacing=0.05f
                            textColorResource=R.color.black33
                            gravity=Gravity.CENTER
                        }.lparams {
                            height = dip(20)
                        }

                        textView {
                            text="東大街牛王廟100号B座1706"
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
                        gravity=Gravity.CENTER_VERTICAL
                        image=imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            setImageResource(R.mipmap.icon_go_position)
                        }.lparams {
                            width=dip(6)
                            height=dip(11)
                        }
                    }.lparams {
                        width=dip(6)
                        height= matchParent
                    }




                }.lparams() {
                    topMargin=dip(10)
                    width = matchParent
                    height= wrapContent
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