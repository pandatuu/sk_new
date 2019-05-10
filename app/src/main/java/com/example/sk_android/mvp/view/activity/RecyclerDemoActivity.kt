package com.example.sk_android.mvp.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.adapter.privacyset.RecyclerAdapter
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.verticalLayout

import java.util.ArrayList

/**
 * Created by Chau Thai on 4/8/16.
 */
class RecyclerDemoActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerAdapter? = null

    lateinit var a : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            a = verticalLayout {
            }
        }
        val view = View.inflate(a.context,R.layout.activity_recycler,null)
        a.addView(view)
        setupList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter!!.saveStates(outState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter!!.restoreStates(savedInstanceState)
        }
    }

    private fun setupList() {
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        adapter = RecyclerAdapter(this, createList(20))
        recyclerView!!.adapter = adapter
    }

    private fun createList(n: Int): ArrayList<String> {
        val list = ArrayList<String>()

        for (i in 0 until n) {
            list.add("View $i")
        }

        return list
    }

}
