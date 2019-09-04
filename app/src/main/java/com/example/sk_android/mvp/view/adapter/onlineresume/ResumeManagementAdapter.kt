package com.example.sk_android.mvp.view.adapter.onlineresume

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toolbar
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.sk_android.R
import org.jetbrains.anko.buttonDrawableResource
import java.util.*

class ResumeManagementAdapter(context: Context, mList: LinkedList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDataSet: LinkedList<String> = mList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private val binderHelper = ViewBinderHelper()
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = mInflater.inflate(R.layout.resume_management, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, position: Int) {
        val holder = p0 as ResumeManagementAdapter.ViewHolder
        if (0 <= position && position < mDataSet.size) {
            val data = mDataSet[position]

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            binderHelper.bind(holder.swipeLayout, data)

            // Bind your data here
            holder.bind(data, position)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    fun getData(): LinkedList<String> {
        return mDataSet
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var swipeLayout: SwipeRevealLayout
        private val frontLayout: View
        private val deleteLayout: TextView
        private val editLayout: TextView
        private val texttop: TextView
        private val textbottom: RadioButton
        private val righttoolbar: Toolbar
        var radioList = LinkedList<RadioButton>()

        init {
            swipeLayout = itemView.findViewById(R.id.swipe_layout) as SwipeRevealLayout
            frontLayout = itemView.findViewById(R.id.front_layout) as View
            deleteLayout = itemView.findViewById(R.id.delete_layout) as TextView
            editLayout = itemView.findViewById(R.id.edit_layout) as TextView
            texttop = itemView.findViewById(R.id.texttop) as TextView
            textbottom = itemView.findViewById(R.id.textbottom) as RadioButton
            righttoolbar = itemView.findViewById(R.id.righttoolbar) as Toolbar

        }

        fun bind(data: String, p1: Int) {
            righttoolbar.setOnClickListener {
            }
            editLayout.setOnClickListener {
            }
            deleteLayout.setOnClickListener {
                mDataSet.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                radioList.remove(textbottom)
                if (mDataSet.size > 0) {
                    radioList[0].text = "黙认した"
                    radioList[0].isChecked = true
                    radioList[0].buttonDrawableResource = R.mipmap.hook
                }
            }
            texttop.text = data

            if (p1 == 0) {
                textbottom.text = "黙认した"
                textbottom.isChecked = true
                textbottom.buttonDrawableResource = R.mipmap.hook
            } else {
                textbottom.isChecked = false
                textbottom.buttonDrawableResource = R.mipmap.oval
            }
            radioList.add(textbottom)
            textbottom.setOnCheckedChangeListener { _, _ ->
                if (textbottom.isChecked) {
                    textbottom.text = "黙认した"
                    textbottom.buttonDrawableResource = R.mipmap.hook
                } else {
                    textbottom.text = "黙認とする"
                    textbottom.buttonDrawableResource = R.mipmap.oval
                }
            }
            textbottom.setOnClickListener {
                for (list in radioList) {
                    if (list != textbottom) {
                        list.text = "黙認とする"
                        list.isChecked = false
                    } else {
                        textbottom.text = "黙认した"
                        textbottom.isChecked = true
                    }
                }
            }
            frontLayout.setOnClickListener {
                for (list in radioList) {
                    if (list != textbottom) {
                        list.text = "黙認とする"
                        list.isChecked = false
                    } else {
                        textbottom.text = "黙认した"
                        textbottom.isChecked = true
                    }
                }
            }
        }
    }


}
