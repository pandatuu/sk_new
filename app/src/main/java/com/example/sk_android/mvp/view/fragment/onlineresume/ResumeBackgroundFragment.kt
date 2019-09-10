package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.verticalLayout

class ResumeBackgroundFragment:Fragment() {

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): ResumeBackgroundFragment {
            return ResumeBackgroundFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView=createView()
        mContext = activity
        return fragmentView
    }

    private fun createView():View{
        return UI {
            verticalLayout {
                isClickable = true
                backgroundColorResource = R.color.black66000000
            }
        }.view
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
            val scale = context.resources.displayMetrics.density
            result = ((result / scale + 0.5f).toInt())
        }
        return result
    }

}