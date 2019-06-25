package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class FeedbackWhiteBackground : Fragment() {

    lateinit var whiteback: WhitebBack

    companion object {
        fun newInstance(): FeedbackWhiteBackground {
            val fragment = FeedbackWhiteBackground()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        whiteback = activity as WhitebBack
        val view = CreaV()
        return view
    }

    private fun CreaV(): View {
        return UI {
            linearLayout {
                backgroundColor = Color.TRANSPARENT
                isClickable = true
            }
        }.view
    }

    interface WhitebBack {
        fun clickwhite()
    }
}