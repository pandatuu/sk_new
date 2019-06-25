package com.example.sk_android.mvp.view.adapter.jobselect

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
import com.example.sk_android.mvp.model.jobselect.SelectedItemContainer
import org.jetbrains.anko.*

class RecruitInfoSelectBarMenuSelectItemAdapter(
    private val context: RecyclerView,
    private val list: MutableList<SelectedItemContainer>,
    private val listener: (title: String, item: String,index:Int) -> Unit

) : RecyclerView.Adapter<RecruitInfoSelectBarMenuSelectItemAdapter.ViewHolder>() {


    var selectedNumber = 0
    lateinit var titleShow: TextView
    lateinit var itemShow: FlowLayout
    lateinit var blankSpace: LinearLayout

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                verticalLayout() {

                    titleShow = textView() {
                        textColorResource = R.color.gray99
                        textSize = 12f
                    }.lparams() {
                        width = matchParent
                        leftMargin=dip(20)
                        rightMargin=dip(20)
                        height = dip(17)
                    }

                    itemShow = flowLayout {

                    }.lparams() {
                        width = matchParent
                        rightMargin=dip(20)
                    }

                    blankSpace = verticalLayout() {

                    }

                }.lparams() {
                    width = matchParent
                    height = wrapContent
                    topMargin = dip(19)

                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        titleShow.text = list[position].containerName

        for (i in 0..list[position].item.size-1) {
            var item=list[position].item.get(i)
            var view=getItemView(item.name,item.selected)
            itemShow.addView(view)
            holder.bindItem(list[position].containerName,item.name,view,i,listener)
        }
        if (position == getItemCount() - 1) {
            blankSpace.layoutParams.height = 100

        }

    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem(title: String, item: String,view:View?,index:Int,listener: (title: String, item: String,index:Int) -> Unit) {
            var selectedItem=(((view!! as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView)
            selectedItem.setOnClickListener {

                var container=(view.parent as FlowLayout)
                for(i in 0 until  container.childCount) {
                    (((container.getChildAt(i) as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView). backgroundResource = R.drawable.radius_border_unselect
                }
                selectedItem.backgroundResource = R.drawable.radius_border_select_theme_bg

                listener(title,item,index)
            }
        }
    }

    fun getItemView(tx: String,selected:Boolean): View? {
        return with(itemShow.context) {
            verticalLayout {
                relativeLayout {
                    textView {
                        text = tx
                        if(selected){
                            backgroundResource = R.drawable.radius_border_select_theme_bg
                        }else{
                            backgroundResource = R.drawable.radius_border_unselect
                        }
                        topPadding = dip(8)
                        bottomPadding = dip(8)
                        rightPadding = dip(11)
                        leftPadding = dip(11)
                        textColorResource = R.color.selectButtomTextColor
                        textSize = 13f

                    }.lparams {
                        topMargin = dip(15)
                        leftMargin=dip(20)
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                }
            }
        }
    }





}