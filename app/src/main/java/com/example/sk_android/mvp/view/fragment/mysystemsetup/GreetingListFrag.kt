package com.example.sk_android.mvp.view.fragment.mysystemsetup

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.sk_android.R
import com.example.sk_android.mvp.model.mysystemsetup.Greeting
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import java.util.*

class GreetingListFrag : Fragment() {

    lateinit var mContext: Context
    lateinit var greeting: GreetingRadio
    var listFrag: MutableList<Greeting>? = mutableListOf()
    var greetingId: UUID? = null
    var index = 0
    var modelId = 0

    companion object {
        fun newInstance(
            context: Context,
            greetingList: MutableList<Greeting>?,
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
        var view = createV()
        return view
    }

    private fun createV(): View? {
        return UI {
            relativeLayout {
                val group = radioGroup {
                    for (model in listFrag!!) {
                        if (greetingId == model.id) {
                            modelId = index
                        }
                        println("${greetingId}-----------------------" + model.id)
                        radioButton {
                            id = index
                            backgroundResource = R.drawable.text_view_bottom_border
//                            isChecked = (greetingId == model.id)
                            if(!isChecked){
                                buttonDrawableResource = R.mipmap.oval
                            }
                            onClick {
                                if (isChecked) {
                                    greeting.clickRadio(model.id)
                                }
                            }
                            onCheckedChange { buttonView, isChecked ->
                                if (isChecked) {
                                    buttonDrawableResource = R.mipmap.hook
                                } else {
                                    buttonDrawableResource = R.mipmap.oval
                                }
                            }
                            leftPadding = dip(10)
                            text = model.content
                            textSize = 16f
                            textColor = Color.parseColor("#202020")

                        }.lparams {
                            width = matchParent
                            height = dip(62)
                        }
                        index++
                    }
                }.lparams(matchParent, matchParent)
                println("final-----------------------" + modelId)
                group.check(modelId)
            }
        }.view
    }

    interface GreetingRadio {
        fun clickRadio(id: UUID)
    }
}