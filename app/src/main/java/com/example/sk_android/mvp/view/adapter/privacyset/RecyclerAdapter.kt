package com.example.sk_android.mvp.view.adapter.privacyset

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.RecyclerDemoActivity
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.verticalLayout
import java.util.ArrayList

class RecyclerAdapter(
    context : Context,
    createList: ArrayList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDataSet : ArrayList<String> = createList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context
    private val binderHelper = ViewBinderHelper()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = mInflater.inflate(R.layout.row_list, parent, false)

            return ViewHolder(view)
    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {
        val holder = h as ViewHolder

        if (mDataSet != null && 0 <= position && position < mDataSet.size) {
            val data = mDataSet[position]

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            binderHelper.bind(holder.swipeLayout, data)

            // Bind your data here
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return if (mDataSet == null) 0 else mDataSet.size
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onSaveInstanceState]
     */
    fun saveStates(outState: Bundle) {
        binderHelper.saveStates(outState)
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onRestoreInstanceState]
     */
    fun restoreStates(inState: Bundle) {
        binderHelper.restoreStates(inState)
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipeLayout: SwipeRevealLayout
        private val frontLayout: View
        private val deleteLayout: View
        private val textView: TextView

        init {
            swipeLayout = itemView.findViewById(R.id.swipe_layout) as SwipeRevealLayout
            frontLayout = itemView.findViewById(R.id.front_layout)
            deleteLayout = itemView.findViewById(R.id.delete_layout)
            textView = itemView.findViewById(R.id.text) as TextView
        }

        fun bind(data: String) {
            deleteLayout.setOnClickListener {
                println("------------------------------")
                mDataSet.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }

            textView.text = data

            frontLayout.setOnClickListener {
                val displayText = "$data clicked"
                Toast.makeText(mContext, displayText, Toast.LENGTH_SHORT).show()
                Log.d("RecyclerAdapter", displayText)
            }
        }
    }
}