package com.example.sk_android.mvp.view.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout
import com.example.sk_android.mvp.model.Club
import com.example.sk_android.mvp.model.Industry
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * Created by Wanhar Aderta Daeng Maro on 9/7/2018.
 * Email : wanhardaengmaro@gmail.com
 *
 */
class IndustrySelectAdapter(
    private val context: RecyclerView,
    private val selectedItemShowArea: FlowLayout,
    private val numberShow: TextView,
    private val professions: MutableList<Industry>,
    private val listener: (Industry) -> Unit

) : RecyclerView.Adapter<IndustrySelectAdapter.ViewHolder>() {


    var selectedNumber = 0
    lateinit var titleShow: TextView
    lateinit var itemShow: FlowLayout
    lateinit var blankSpace: LinearLayout

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                verticalLayout() {

                    backgroundResource = R.drawable.text_view_bottom_border

                    titleShow = textView() {
                        textColorResource = R.color.lebelTextColor
                        textSize = 12f
                    }.lparams() {
                        width = matchParent
                        height = dip(17)
                    }

                    itemShow = flowLayout {

                    }

                    blankSpace = verticalLayout() {

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        titleShow.text = professions[position].title
        for (item in professions[position].item) {
            itemShow.addView(getItemView(item))
        }
        if (position == getItemCount() - 1) {
            blankSpace.layoutParams.height = 100

        }
    }

    override fun getItemCount(): Int = professions.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem(club: Club, listener: (Club) -> Unit) {
            itemView.setOnClickListener {
                listener(club)
            }
        }
    }

    fun getItemView(tx: String): View? {
        return with(selectedItemShowArea.context) {
            verticalLayout {
                relativeLayout {
                    textView {
                        text = tx
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
                                selectedItemShowArea.addView(getSelectedView(tx as String))
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
    }


    fun getSelectedView(tx: String): View? {
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
                        setImageResource(com.example.sk_android.R.mipmap.icon_delete_zwxz)
                        onClick {
                            var realNum = numberShow.text.toString().toInt()

                            numberShow.text = (realNum - 1).toString()

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