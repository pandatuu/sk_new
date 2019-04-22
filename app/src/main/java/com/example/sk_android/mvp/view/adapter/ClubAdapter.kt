package com.example.sk_android.mvp.view.adapter

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
import com.example.sk_android.mvp.model.Club
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * Created by Wanhar Aderta Daeng Maro on 9/7/2018.
 * Email : wanhardaengmaro@gmail.com
 *
 */
class ClubAdapter(
    private val context: RecyclerView,
    private val selectedItemShowArea: FlowLayout,
    private val numberShow: TextView,
    private val clubs: MutableList<Club>,
    private val listener: (Club) -> Unit
) : RecyclerView.Adapter<ClubAdapter.ViewHolder>() {


    var selectedNumber = 0
    lateinit var tvDesc: TextView
    lateinit var ImClub: ImageView


    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                verticalLayout() {

                    backgroundResource = R.drawable.text_view_bottom_border

                    textView() {
                        text = "インターネット/IT/电子/通信"
                        textColorResource = R.color.lebelTextColor
                        textSize = 12f
                    }.lparams() {
                        width = matchParent
                        height = dip(17)
                    }

                    flowLayout {
                        for (i in 1..10) {
                            relativeLayout {
                                textView {
                                    text = "電子商取引" + i
                                    backgroundResource = R.drawable.radius_border_unselect
                                    topPadding = dip(8)
                                    bottomPadding = dip(8)
                                    rightPadding = dip(11)
                                    leftPadding = dip(11)
                                    textColorResource = R.color.selectButtomTextColor
                                    textSize = 14f
                                    onClick {
                                        var realNum = numberShow.text.toString().toInt()
                                        if (realNum < 3) {
                                            val tx = text
                                            selectedItemShowArea.addView(getView(tx as String))
                                            selectedNumber = realNum + 1
                                            numberShow.text = selectedNumber.toString()
                                        }

                                    }
                                }.lparams {
                                    margin = 14
                                }
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                            }
                        }


                    }

                }.lparams() {
                    width = matchParent
                    height = wrapContent
                    topMargin = dip(10)
                    rightMargin = 50
                    leftMargin = 50
                }


            }

        }
        return ViewHolder(view)
    }


    // ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        tvDesc .text  = clubs[position].name
//        Glide.with(holder.itemView.context).load( clubs[position].image).into(ImClub)

//        holder.bindItem(clubs[position],listener)
    }


    //override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(clubs[position],listener)

    override fun getItemCount(): Int = clubs.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem(club: Club, listener: (Club) -> Unit) {

//            itemView.findViewById<TextView>(R.string.tvId).text   = club.name
//            var image=itemView.findViewById<ImageView>(R.string.ivId)
//            Glide.with(itemView.context).load(club.image).into(image)

            itemView.setOnClickListener {
                listener(club)
            }
        }
    }

    fun getView(tx: String): View? {
        return with(selectedItemShowArea.context) {
            verticalLayout {
                relativeLayout {
                    backgroundResource = com.example.sk_android.R.drawable.radius_border_select
//                   visibility= android.view.View.INVISIBLE
                    var textId = 1
                    var text = textView {
                        id = textId
                        text = tx
                        textColorResource = com.example.sk_android.R.color.selectButtomTextColor
                        textSize = 14f
                        padding = 0
                        gravity = android.view.Gravity.CENTER_VERTICAL
                    }.lparams {
                        width = org.jetbrains.anko.wrapContent
                        height = org.jetbrains.anko.wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    imageView {
                        setImageResource(com.example.sk_android.R.mipmap.delete)
                        onClick {
                            var realNum = numberShow.text.toString().toInt()

                            numberShow.text = (realNum-1).toString()

                            selectedItemShowArea.removeView((parent as RelativeLayout).parent as View)
                        }
                    }.lparams() {
                        width = org.jetbrains.anko.wrapContent
                        height = org.jetbrains.anko.wrapContent
                        leftMargin = dip(10)
                        rightOf(text)
                        centerVertically()
                    }

                }.lparams {
                    margin = 10
                    width = org.jetbrains.anko.wrapContent
                    height = org.jetbrains.anko.wrapContent
                }
            }
        }
    }

}