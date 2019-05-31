package com.example.sk_android.mvp.view.adapter.onlineresume

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class ResumeEditAdapter(var mData : LinkedList<View>) : RecyclerView.Adapter<ResumeEditAdapter.ViewHolder>() {
    var index : Int = 0
    lateinit var toolbar1 : Toolbar
    var tool = LinkedList<Toolbar>()

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        Log.d("aaa","create----"+index)

        val view = mData.get(index)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.d("bbb","bind----"+index)

        index++
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun RecyclerViewHolder(itemView:View) {
            super.itemView

        }
    }

}