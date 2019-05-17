package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class ResumeBackgroundFragment : Fragment() {

    var TrpToolbar: Toolbar? = null
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
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {
        return UI {
            verticalLayout {
                backgroundColorResource = R.color.black66000000
                isClickable = true
            }
        }.view
    }


}