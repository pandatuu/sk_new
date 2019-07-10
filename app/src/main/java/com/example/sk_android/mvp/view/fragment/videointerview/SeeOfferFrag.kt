package com.example.sk_android.mvp.view.fragment.videointerview

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import click
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger

class SeeOfferFrag : Fragment(){

interface SeeOfferButton{
    fun cancel()
    suspend fun demire()
}
    private lateinit var seeoffer: SeeOfferButton

    companion object {
        fun newInstance(): SeeOfferFrag{

            return SeeOfferFrag()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        seeoffer = activity as SeeOfferButton
        return createV()
    }

    private fun createV(): View {
        return UI {
            relativeLayout {
                linearLayout() {
                    gravity= Gravity.CENTER_VERTICAL
                    button {
                        backgroundResource = R.drawable.button_shape_grey
                        text = "このofferを拒否する"
                        textSize = 13f
                        textColor = Color.WHITE
                        this.withTrigger().click {
                            seeoffer.cancel()
                        }
                    }.lparams{
                        weight=1f
                        leftMargin=dip(10)
                        rightMargin=dip(10)
                        height = dip(50)
                        width = dip(0)
                    }



                    button {
                        backgroundResource = R.drawable.button_shape_orange
                        text = "このofferを承認する"
                        textSize = 13f
                        textColor = Color.WHITE
                        onClick {
                            toast("このofferを承認する")
                            seeoffer.demire()
                        }
                    }.lparams{
                        weight=1f
                        leftMargin=dip(10)
                        rightMargin=dip(10)
                        height = dip(50)
                        width = dip(0)
                    }
                }.lparams{
                    width = matchParent
                    height = wrapContent
                    setMargins(dip(25),dip(15),dip(25),30)
                }
            }
        }.view
    }
}