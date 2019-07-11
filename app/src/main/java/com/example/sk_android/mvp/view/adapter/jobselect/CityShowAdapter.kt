package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout
import com.example.sk_android.mvp.model.jobselect.Area
import com.example.sk_android.mvp.model.jobselect.City
import com.example.sk_android.mvp.view.fragment.jobselect.CitySelectFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class CityShowAdapter(
    private val context: RecyclerView,
    private val w: Int,
    private val area: MutableList<Area>,
    private val mostChooseNum:Int,//最多可选多少个  现在是3个
    private val listener: (City,Int,Boolean?) -> Unit
) : RecyclerView.Adapter<CityShowAdapter.ViewHolder>() {

    lateinit var itemShow: FlowLayout
    lateinit var nowLocation: LinearLayout
    var cityTextwidth:Int=92




    var addressText: TextView?=null


    fun setNowAddress(add:String){
        if(addressText!=null){
            addressText!!.text=add
        }
        nowAddress=add
        notifyDataSetChanged()
    }



    companion object {
        var nowAddress="东京"
        var selectedItemNumber=0
    }


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


                            addressText = textView {
                                text = nowAddress
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
        var cityArray= area[position].city
        for (i in cityArray.indices) {
            itemShow.addView(getItemView(cityArray[i],i,position))
        }

    }

    fun getItemView(tx: City,i:Int,position:Int): View? {
        return with(context.context) {
            verticalLayout {

                relativeLayout {
                    textView {
                        setOnClickListener(object :View.OnClickListener{

                            override fun onClick(v: View?) {
                                if(isSelected){
                                    isSelected=false
                                    backgroundResource = R.drawable.radius_border_unselect
                                    selectedItemNumber=selectedItemNumber-1

                                }else{
                                    if(selectedItemNumber<mostChooseNum){
                                        isSelected=true
                                        backgroundResource = R.drawable.radius_border_select_theme_bg
                                        selectedItemNumber=selectedItemNumber+1
                                    }else{
                                        listener(tx,i,null)
                                        return
                                    }
                                }
                                listener(tx,i,isSelected)
                            }

                        })
                        text = tx.name
                        if(tx.selected){
                            backgroundResource = R.drawable.radius_border_select_theme_bg
                            isSelected=true
                        }else{
                            backgroundResource = R.drawable.radius_border_unselect
                            isSelected=false
                        }


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
    override fun getItemCount(): Int = area.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem( position:Int,listener: (City) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {
            }
        }
    }


}