package com.example.sk_android.mvp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout
import com.example.sk_android.mvp.model.City
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class CityShowAdapter(
    private val context: RecyclerView,
    private val w: Int,
    private val city: MutableList<City>,
    private val listener: (City) -> Unit
) : RecyclerView.Adapter<CityShowAdapter.ViewHolder>() {

    lateinit var itemShow: FlowLayout
    lateinit var nowLocation: LinearLayout
    var cityTextwidth:Int=92
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                backgroundColorResource=R.color.originColor
                verticalLayout() {
                    relativeLayout {
                        var nowLocationLabeltextViewId=1
                        var nowLocationLabeltextView=textView {
                            text="現在な勤務地"
                            id=nowLocationLabeltextViewId
                            textColorResource=R.color.tinyLabelColor
                            textSize=13f
                        }.lparams {
                            alignParentTop()
                            topMargin=dip(37)
                        }

                        relativeLayout {
                            backgroundResource = com.example.sk_android.R.drawable.radius_border_location
                            var localtionIconId = 1

                            var localtionIcon=imageView {
                                id=localtionIconId
                                imageResource=R.mipmap.icon_position
                            }.lparams {
                                alignParentLeft()
                                centerVertically()

                            }


                            textView {
                                text = "东京"
                                textColorResource = com.example.sk_android.R.color.selectButtomTextColor
                                textSize = 13f
                                gravity = android.view.Gravity.CENTER_VERTICAL
                            }.lparams {
                                centerVertically()
                                rightOf(localtionIcon)
                                leftMargin=dip(5)
                            }


                        }.lparams {
                            margin=0
                            height=dip(34)
                            topMargin = dip(15)
                            width = org.jetbrains.anko.wrapContent

                            bottomOf(nowLocationLabeltextView)
                        }

                        textView {
                            text="人気な勤務地"
                            textColorResource=R.color.tinyLabelColor
                            textSize=13f
                        }.lparams {
                            alignParentBottom()
                            bottomMargin=dip(7)
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(155)
                        leftMargin=(w-cityTextwidth*2)/5
                        rightMargin=(w-cityTextwidth*2)/5
                    }
                    itemShow = flowLayout {

                    }.lparams {
                        width = matchParent

                    }

                }.lparams() {
                    width = matchParent
                    height = wrapContent


                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cityArray= city[position].city
        for (i in cityArray.indices) {
            itemShow.addView(getItemView(cityArray[i],i))
        }

    }

    fun getItemView(tx: String,i:Int): View? {
        return with(context.context) {
            verticalLayout {
                relativeLayout {
                    textView {
                        text = tx
                        backgroundResource = com.example.sk_android.R.drawable.radius_border_unselect
                        topPadding = dip(8)
                        bottomPadding = dip(8)
                        rightPadding = dip(11)
                        leftPadding = dip(11)
                        textColorResource = com.example.sk_android.R.color.normalTextColor
                        textSize = 14f
                        gravity=Gravity.CENTER

                    }.lparams {
                        margin = dip(7)
                        width=dip(cityTextwidth)
                        height=dip(34)
                        centerVertically()
                        if(i%2==0){
                            alignParentRight()
                            rightMargin=(w-width*2)/5/2
                        }else{
                            alignParentLeft()
                            leftMargin=(w-width*2)/5/2
                        }
                    }
                }.lparams {
                    width = w/2
                    height = org.jetbrains.anko.wrapContent
                }
            }
        }
    }
    override fun getItemCount(): Int = city.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem( position:Int,listener: (City) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {

            }
        }
    }



}