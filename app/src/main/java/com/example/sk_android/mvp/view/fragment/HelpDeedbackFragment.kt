package com.example.sk_android.mvp.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import java.util.*

class HelpDeedbackFragment : Fragment() {

    companion object {
        fun newInstance(): HelpDeedbackFragment {
            return HelpDeedbackFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        var fragmentView=createView()

        return fragmentView
    }

    private fun createView() : View {
        var list = LinkedList<String>()
        list.add("チュートリアル")
        list.add("攻略")
        list.add("アクティビティ")
        return UI {
            relativeLayout{
                verticalLayout {
//                    visibility = View.GONE
                    for (linklist in list) {
                            textView {
                                text = linklist
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                                includeFontPadding = false
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams {
                                width = matchParent
                                height = dip(35)
                                leftMargin = dip(13)
                            }
                    }
                    onClick {
                        toast("11111111")
                    }
                }.lparams{
                    width = matchParent
                    height = dip(105)
                    leftPadding = dip(15)
                    rightPadding = dip(15)
                }
            }
        }.view
    }
}