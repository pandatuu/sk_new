package com.example.sk_android.mvp.view.fragment.privacyset

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.BlackCompanyAdd
import com.example.sk_android.mvp.model.privacySet.BlackCompanyModel
import com.example.sk_android.mvp.model.privacySet.ListItemModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger

class CommonAddCompanyThree : Fragment() {

    lateinit var key: String
    lateinit var list: BlackCompanyAdd
    lateinit var checkbox : CheckBox
    private var bool: Boolean? = null
    lateinit var checkBoxStatus : CheckBoxStatus

    companion object {
        fun newInstance(mtext: String, linkedlist: BlackCompanyAdd, boolean : Boolean?): CommonAddCompanyThree {
            val fragment = CommonAddCompanyThree()
            fragment.key = mtext
            fragment.list = linkedlist
            fragment.bool = boolean
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        checkBoxStatus = activity as CheckBoxStatus
        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            relativeLayout {
                verticalLayout {
                    relativeLayout {
                        textView {
                            text = "「$key」と関わる会社は1社見つかった"
                            textColor = Color.parseColor("#FF5C5C5C")
                            textSize = 12f
                        }.lparams {
                            alignParentLeft()
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        setMargins(dip(10), dip(15), 0, dip(15))
                    }

                    relativeLayout {
                        backgroundResource = R.drawable.text_view_bottom_border
                        verticalLayout {
                            textView {
                                text = list.model.name
                                textSize = 15f
                                textColor = Color.parseColor("#202020")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                            }
                            relativeLayout {
                                textView {
                                    text = "略称"
                                    textSize = 13f
                                    textColor = Color.parseColor("#FF999999")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                }
                                textView {
                                    text = "「${list.model.acronym}」"
                                    textSize = 13f
                                    textColor = Color.parseColor("#FFFFB706")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    leftMargin = dip(25)
                                }
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(5)
                            }
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentLeft()
                            leftMargin = dip(15)
                            centerVertically()
                        }
                        checkbox = checkBox {
                            if(list.isTrueChecked!=null){
                                isChecked = list.isTrueChecked!!
                            }else{
                                isChecked = false
                            }
                            if(bool == false){
                                isChecked = false
                            }
                            if (isChecked) {
                                buttonDrawableResource = R.mipmap.hook
                            } else {
                                buttonDrawableResource = R.mipmap.oval
                            }
                            setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                                    if (isChecked) {
                                        buttonDrawableResource = R.mipmap.hook
                                        checkBoxStatus.updateCheckStatus(list,true)
                                    } else {
                                        buttonDrawableResource = R.mipmap.oval
                                        checkBoxStatus.updateCheckStatus(list,null)
                                    }
                                }
                            })
                        }.lparams {
                            width = wrapContent
                            height = dip(30)
                            alignParentRight()
                            rightMargin = dip(15)
                            centerVertically()
                        }
                        var a =0
                        this.withTrigger().click {
                            if(a%2==0){
                                checkbox.isChecked = true
                            } else {
                                checkbox.isChecked = false
                            }
                            a++
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(70)
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                    topMargin = dip(64)
                }
            }
        }.view
    }
    interface CheckBoxStatus{
        fun updateCheckStatus(item: BlackCompanyAdd, boolean: Boolean?)
    }
}