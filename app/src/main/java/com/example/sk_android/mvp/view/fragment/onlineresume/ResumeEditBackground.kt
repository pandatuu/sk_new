package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class ResumeEditBackground : Fragment() {


    companion object {
        fun newInstance(): ResumeEditBackground {
            val fragment = ResumeEditBackground()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        val view = UI {
            relativeLayout{
                relativeLayout {
                    backgroundResource = R.mipmap.job_photo_upload
                    button {
                        backgroundResource = R.drawable.fifteen_radius_button
                        text = "仕事の写真/ビデオをアップロド"
                        textSize = 10f
                        textColor = Color.WHITE
                        onClick {
                            toast("yeah!!!!!!!!!!!!")
                        }
                    }.lparams{
                        width = dip(170)
                        height = dip(35)
                        centerInParent()
                    }
                }.lparams {
                    width = dip(370)
                    height = dip(370)
                }
            }
        }.view
        return view
    }
}