package com.example.sk_android.mvp.view.fragment.common

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class DialogLoading: Fragment() {

    companion object {
        fun newInstance(): DialogLoading{
            val frag = DialogLoading()
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createV()
    }

    private fun createV(): View {
        return UI {
            linearLayout {
                gravity = Gravity.CENTER
                frameLayout {
                    val image = imageView {}.lparams(dip(70), dip(80))
                    Glide.with(this@linearLayout)
                        .load(R.mipmap.turn_around)
                        .into(image)
                }
            }
        }.view
    }
}