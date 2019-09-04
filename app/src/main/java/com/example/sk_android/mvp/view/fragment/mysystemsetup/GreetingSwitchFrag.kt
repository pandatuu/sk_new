package com.example.sk_android.mvp.view.fragment.mysystemsetup

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class GreetingSwitchFrag : Fragment() {

    lateinit var mContext: Context
    private lateinit var switchXuanze : GreetingSwitch
    var isBool: Boolean = false

    companion object {
        fun newInstance(
            context: Context,
            greeting: Boolean
        ): GreetingSwitchFrag {
            val fragment = GreetingSwitchFrag()
            fragment.mContext = context
            fragment.isBool = greeting
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        switchXuanze = activity as GreetingSwitch
        return createV()
    }


    private fun createV(): View? {
        return UI {
            relativeLayout {
                textView {
                    text = "「ご挨拶を」"
                    textSize = 13f
                    textColor = Color.parseColor("#5C5C5C")
                    gravity = Gravity.CENTER_VERTICAL
                }.lparams {
                    alignParentLeft()
                    centerVertically()
                }
                switch {
                    setThumbResource(R.drawable.thumb)
                    setTrackResource(R.drawable.track)
                    isChecked = isBool
                    onClick {
                        if (isChecked) {
                            switchXuanze.clickSwitch(true)
                        } else {
                            switchXuanze.clickSwitch(false)
                        }
                    }
                }.lparams {
                    alignParentRight()
                    centerVertically()
                }
            }
        }.view
    }

    interface GreetingSwitch {
        suspend fun clickSwitch(bool : Boolean)
    }

}