package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
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

class CompanyPicShowAdapter(
    private val context: RecyclerView,
    private val list: MutableList<Int>,
    private val listener: ( item: String) -> Unit

) : RecyclerView.Adapter<CompanyPicShowAdapter.ViewHolder>() {


    lateinit var image: ImageView

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                verticalLayout() {


                    image=imageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.company_logo)
                    }.lparams {
                        width=dip(135)
                        height=dip(90)
                    }


                }.lparams() {
                    topMargin=dip(28)
                    bottomMargin=dip(28)
                    width = matchParent
                    height = wrapContent

                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position!=0) {
            var lp =  LinearLayout.LayoutParams(image.getLayoutParams());
            lp.setMargins(15, 0, 0, 0);
            image.setLayoutParams(lp);
        }
        image.setImageResource(list.get(position))
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("ResourceType")
        fun bindItem( item: String,view:View?,listener: (item: String) -> Unit) {

                listener(item)
            }
    }




}