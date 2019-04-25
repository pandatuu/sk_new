package com.example.sk_android.mvp.view.fragment.jobSelect

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*


import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface
import com.example.sk_android.custom.layout.recyclerView


class JobListFragment : Fragment() {

    lateinit var toolbar1: android.widget.Toolbar

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): ActionBarFragment {
            val fragment = ActionBarFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }


    fun createView(): View {

        return UI {
            linearLayout {
                recyclerView {

                }
            }
        }.view

    }

}



