package com.example.sk_android.mvp.view.fragment.mysystemsetup

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.support.v4.UI

class NotificationFrag : Fragment(){

    lateinit var mContext: Context
    var bool : Boolean = false

    companion object {
        fun newInstance(context: Context): NotificationFrag {
            val fragment = NotificationFrag()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = createV()

        return view
    }

    private fun createV(): View? {
        return UI{}.view
    }
}