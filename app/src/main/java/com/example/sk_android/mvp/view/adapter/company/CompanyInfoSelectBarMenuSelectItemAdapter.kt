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
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import org.jetbrains.anko.*

class CompanyInfoSelectBarMenuSelectItemAdapter(
    private val context: RecyclerView,
    private val list: MutableList<SelectedItem>,
    private val listener: ( item: String) -> Unit

) : RecyclerView.Adapter<CompanyInfoSelectBarMenuSelectItemAdapter.ViewHolder>() {


    var selectedNumber = 0
    lateinit var itemShow: FlowLayout

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                verticalLayout() {


                    itemShow = flowLayout {

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
        if(position==0) {
            for (item in list) {
                var view = getItemView(item.name, item.selected)
                itemShow.addView(view)
                holder.bindItem(item.name, view, listener)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem( item: String,view:View?,listener: (item: String) -> Unit) {
            var selectedItem=(((view!! as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView)
            selectedItem.setOnClickListener {

//                var container=(view.parent as FlowLayout)
//                for(i in 0 until  container.childCount) {
//                    (((container.getChildAt(i) as LinearLayout).getChildAt(0) as RelativeLayout).getChildAt(0) as TextView). backgroundResource = R.drawable.radius_border_unselect
//                }
                selectedItem.backgroundResource = R.drawable.radius_border_select_theme_bg

                listener(item)
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