package com.example.sk_android.mvp.view.fragment.mysystemsetup

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.model.mysystemsetup.Version
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger

class UpdateTipsFrag : Fragment() {

    lateinit var mContext: Context
    lateinit var buttomCLick : ButtomCLick
    lateinit var imageV : ImageView
    var model:Version? = null

    companion object {
        fun newInstance(
            context: Context,
            versionModel: Version
        ):UpdateTipsFrag{
            val fragment = UpdateTipsFrag()
            fragment.mContext = context
            fragment.model = versionModel
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        buttomCLick = activity as ButtomCLick
        val imageUrl = model?.imageUrls!![0]
        var view = createV()
        println("-----------------------------$imageUrl--------------------------------")
        Glide.with(this@UpdateTipsFrag)
            .asBitmap()
            .load(imageUrl)
            .placeholder(R.mipmap.update_background)
            .into(imageV)

        return view
    }

    private fun createV(): View? {
        return UI{
            linearLayout {
                gravity = Gravity.CENTER
                linearLayout {
                    isClickable = true
                    orientation = LinearLayout.VERTICAL
                    backgroundResource = R.drawable.fourdp_white_dialog
                    verticalLayout {
                        gravity = Gravity.TOP
                        imageV = imageView {
                        }.lparams(matchParent, wrapContent)
                        textView {
                            text = model?.description
                            textSize = 13f
                        }.lparams(wrapContent, wrapContent){
                            leftMargin = dip(15)
                            rightMargin = dip(10)
                        }
                    }.lparams(matchParent,dip(237))
                    relativeLayout {
                        button {
                            text = "キャンセル"
                            textSize = 16f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.button_shape_grey
                            this.withTrigger().click {
                                buttomCLick.cancelUpdateClick()
                            }
                        }.lparams(dip(120),dip(40)){
                            topMargin = dip(30)
                            alignParentLeft()
                        }
                        button {
                            text = "確定"
                            textSize = 16f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.yellow_background
                            this.withTrigger().click {
                                buttomCLick.defineClick(model?.downloadUrl ?: "")
                            }
                        }.lparams(dip(120),dip(40)){
                            topMargin = dip(30)
                            alignParentRight()
                        }
                    }.lparams(matchParent,dip(103)){
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams(matchParent,dip(340)){
                    leftMargin = dip(38)
                    rightMargin = dip(38)
                }
            }
        }.view
    }

    interface ButtomCLick{
        fun cancelUpdateClick()
        fun defineClick(downloadUrl: String)
    }
}