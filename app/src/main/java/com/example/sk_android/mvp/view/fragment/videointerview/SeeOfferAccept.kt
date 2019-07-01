package com.example.sk_android.mvp.view.fragment.videointerview

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class SeeOfferAccept : Fragment(){

interface OfferAccept{
    suspend fun email()
}
    private lateinit var seeoffer: OfferAccept

    companion object {
        fun newInstance(): SeeOfferAccept{

            return SeeOfferAccept()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        seeoffer = activity as OfferAccept
        return createV()
    }

    private fun createV(): View {
        return UI {
            relativeLayout {
                relativeLayout {
                    button {
                        backgroundResource = R.drawable.button_shape_grey
                        text = "このofferを承認する"
                        textSize = 13f
                        textColor = Color.WHITE
                        isEnabled = false
                    }.lparams{
                        width = dip(150)
                        height = dip(50)
                        alignParentLeft()
                    }
                    button {
                        backgroundResource = R.drawable.button_shape_orange
                        text = "转发到当前邮箱"
                        textSize = 13f
                        textColor = Color.WHITE
                        onClick {
                            toast("转发到邮箱")
                            seeoffer.email()
                        }
                    }.lparams{
                        width = dip(150)
                        height = dip(50)
                        alignParentRight()
                    }
                }.lparams{
                    width = matchParent
                    height = wrapContent
                    setMargins(dip(25),dip(40),dip(25),0)
                }
            }
        }.view
    }
}