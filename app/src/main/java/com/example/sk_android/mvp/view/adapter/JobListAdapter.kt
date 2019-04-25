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
import com.example.sk_android.mvp.model.JobContainer
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class JobListAdapter(
    private val context: RecyclerView,
    private val w: Int,
    private val jobContainer: MutableList<JobContainer>,
    private val listener: (JobContainer) -> Unit
) : RecyclerView.Adapter<JobListAdapter.ViewHolder>() {

    lateinit var textView:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = with(parent.context) {
            verticalLayout {
                backgroundColorResource=R.color.originColor
                relativeLayout() {
                    textView=textView {

                    }.lparams {
                        alignParentLeft()
                        height= matchParent
                    }


                    imageView {

                    }.lparams {
                        alignParentRight()
                        centerVertically()
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(60)


                }


            }

        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        textView.text=jobContainer[position].containerName
    }


    override fun getItemCount(): Int = jobContainer.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem( position:Int,listener: (City) -> Unit,context: RecyclerView) {
            itemView.setOnClickListener {

            }
        }
    }



}