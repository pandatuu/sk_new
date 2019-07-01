package com.example.sk_android.mvp.view.fragment.mysystemsetup

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.example.sk_android.R
import com.example.sk_android.mvp.model.mysystemsetup.Greeting
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import java.util.*

class GreetingListFrag : Fragment() {

    interface GreetingRadio {
        fun clickRadio(id: UUID)
    }

    lateinit var mContext: Context
    lateinit var greeting: GreetingRadio
    lateinit var group: RadioGroup
    var listFrag: LinkedHashMap<Int, Greeting>? = LinkedHashMap<Int, Greeting>()
    var greetingId: UUID? = null

    companion object {
        fun newInstance(
            context: Context,
            greetingList: LinkedHashMap<Int, Greeting>?,
            gId: UUID?
        ): GreetingListFrag {
            val fragment = GreetingListFrag()
            fragment.listFrag = greetingList
            fragment.mContext = context
            fragment.greetingId = gId
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        greeting = activity as GreetingRadio
        return createV()
    }

    fun setCheck(id: Int) {
        group.check(id)
    }

    private fun createV(): View? {
        return UI {
            relativeLayout {
                if (listFrag != null) {
                    group = radioGroup {
                        for (model in listFrag!!) {
                            radioButton {
                                id = model.key
                                backgroundResource = R.drawable.text_view_bottom_border
                                buttonDrawableResource = R.mipmap.oval
                                onClick {
                                    if (isChecked) {
                                        buttonDrawableResource = R.mipmap.hook
                                        greeting.clickRadio(model.value.id)
                                    } else {
                                        buttonDrawableResource = R.mipmap.oval
                                    }
                                }
                                onCheckedChange { _, isChecked ->
                                    if (isChecked) {
                                        buttonDrawableResource = R.mipmap.hook
                                    } else {
                                        buttonDrawableResource = R.mipmap.oval
                                    }
                                }
                                leftPadding = dip(10)
                                text = model.value.content
                                textSize = 16f
                                textColor = Color.parseColor("#202020")

                            }.lparams {
                                width = matchParent
                                height = dip(62)
                            }
                        }
                    }.lparams(matchParent, wrapContent)
                }
            }
        }.view
    }
}