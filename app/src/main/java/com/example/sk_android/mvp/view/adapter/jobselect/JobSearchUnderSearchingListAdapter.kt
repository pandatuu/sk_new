package com.example.sk_android.mvp.view.adapter.jobselect

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.JobSearchUnderSearching
import org.jetbrains.anko.*


class JobSearchUnderSearchingListAdapter(
    private val context: RecyclerView,
    private val list: MutableList<JobSearchUnderSearching>,
    private val listener: (JobSearchUnderSearching) -> Unit
) : RecyclerView.Adapter<JobSearchUnderSearchingListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        lateinit var container: LinearLayout

        var view = with(parent.context) {
            relativeLayout {
                container = linearLayout {
                    backgroundResource = R.drawable.recycle_view_bottom_border
                    gravity = Gravity.CENTER_VERTICAL


                }.lparams {
                    width = matchParent
                    height = dip(52)

                }
            }

        }
        return ViewHolder(view, container)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        var key = list[position].key
        var name = list[position].name

        var i = 0
        var b = 0
        while (true) {
            b = name.indexOf(key, i)
            if (b >= 0) {
                holder.container.addView(getNormalWords(name.substring(i, b)))
                holder.container.addView(getKey(key))
                i = b + key.length
            } else {
                holder.container.addView(getNormalWords(name.substring(i, name.length)))
                break
            }
        }





        holder.bindItem(list[position], position, listener, context)
    }


    override fun getItemCount(): Int = list.size

    class ViewHolder(view: View, val container: LinearLayout) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bindItem(
            item: JobSearchUnderSearching,
            position: Int,
            listener: (JobSearchUnderSearching) -> Unit,
            context: RecyclerView
        ) {
            itemView.setOnClickListener {
                listener(item)
            }
        }
    }


    fun getKey(s: String): View {
        var view = with(context.context) {
            linearLayout {
                textView {
                    letterSpacing = 0.1f
                    gravity = Gravity.CENTER_VERTICAL
                    textColorResource = R.color.themeColor
                    textSize = 14f
                    text = s
                }.lparams {
                    height = dip(20)
                }
            }

        }
        return view

    }


    fun getNormalWords(s: String): View {
        return with(context.context) {
            linearLayout {
                textView {
                    letterSpacing = 0.1f
                    gravity = Gravity.CENTER_VERTICAL
                    textColorResource = R.color.normalTextColor
                    textSize = 14f
                    text = s
                }.lparams {
                    height = dip(20)
                }
            }

        }

    }

}