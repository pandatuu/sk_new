package com.example.sk_android.mvp.view.fragment.privacyset

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.BlackListItemModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*

class BlackAddCompanyThree : Fragment() {

    lateinit var key : String
    lateinit var list : BlackListItemModel

    companion object {
        fun newInstance(mtext : String, linkedlist: BlackListItemModel): BlackAddCompanyThree {
            val fragment = BlackAddCompanyThree()
            fragment.key = mtext
            fragment.list = linkedlist
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            relativeLayout{
                verticalLayout {
                    relativeLayout {
                        textView {
                            text = "「$key」と関わる会社は1社見つかった"
                            textColor = Color.parseColor("#FF333333")
                            textSize = 12f
                        }.lparams{
                            alignParentLeft()
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        setMargins(dip(10),dip(15),0,dip(15))
                    }
                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        verticalLayout {
                            textView {
                                text = list.companyName
                                textSize = 15f
                                textColor = Color.parseColor("#202020")
                            }.lparams{
                                width = wrapContent
                                height = wrapContent
                            }
                            relativeLayout {
                                textView {
                                    text = "略称"
                                    textSize = 13f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams{
                                    width = wrapContent
                                    height = wrapContent
                                }
                                textView {
                                    text = "「$key」"
                                    textSize = 13f
                                    textColor = Color.parseColor("#FFFFB706")
                                }.lparams{
                                    width = wrapContent
                                    height = wrapContent
                                    leftMargin = dip(25)
                                }
                            }.lparams{
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(5)
                            }
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            leftMargin = dip(15)
                            centerVertically()
                        }
                        radioButton {
                            buttonDrawableResource = R.mipmap.hook
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        buttonDrawableResource=R.mipmap.hook
                                    } else {
                                        buttonDrawableResource=R.mipmap.oval
                                    }
                                }
                            })
                        }.lparams{
                            width = wrapContent
                            height = dip(20)
                            alignParentRight()
                            rightMargin = dip(15)
                            centerVertically()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(70)
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                    topMargin = dip(64)
                }
            }
        }.view
    }
}