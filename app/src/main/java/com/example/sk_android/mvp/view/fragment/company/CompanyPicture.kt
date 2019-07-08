package com.example.sk_android.mvp.view.fragment.company

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.sk_android.R
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.wrapContent

class CompanyPicture: Fragment() {

    interface ClickPicAglan{
        fun picClick()
    }

    private lateinit var image: ImageView
    private var mUrl = ""
    private lateinit var two: ClickPicAglan

    companion object {
        fun newIntance(url: String): CompanyPicture{
            val frag = CompanyPicture()
            frag.mUrl = url
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        two = activity as ClickPicAglan
        return onCreatV()
    }

    private fun onCreatV(): View? {
        return UI {
            linearLayout {
                image = imageView {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }.lparams(matchParent, wrapContent){
                    gravity = Gravity.CENTER
                }
                Glide.with(this@CompanyPicture)
                    .asBitmap()
                    .load(mUrl)
                    .placeholder(R.mipmap.no_network)
                    .into(image)
                onClick {
                    two.picClick()
                }
            }
        }.view
    }
}