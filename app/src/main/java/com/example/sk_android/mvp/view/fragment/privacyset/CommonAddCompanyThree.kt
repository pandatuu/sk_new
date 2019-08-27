package com.example.sk_android.mvp.view.fragment.privacyset

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.BlackCompanyAdd
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger

class CommonAddCompanyThree : Fragment() {

    var key: String = ""
    lateinit var list: BlackCompanyAdd
    private lateinit var checkbox : CheckBox
    private var bool: Boolean? = null
    private lateinit var checkBoxStatus : CheckBoxStatus

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

        return createView()
    }

    @SuppressLint("SetTextI18n")
    private fun createView(): View? {
        return UI {
            relativeLayout {
                verticalLayout {
                    relativeLayout {
                        textView {
                            text = "「$key」と関わる会社は1社見つかった"
                            textColor = Color.parseColor("#FF5C5C5C")
                            textSize = 12f
                            ellipsize = TextUtils.TruncateAt.END
                            maxLines = 1
                        }.lparams {
                            width = matchParent
                            alignParentLeft()
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        setMargins(dip(10), dip(15), 0, dip(15))
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.text_view_bottom_border
                        gravity = Gravity.CENTER_VERTICAL
                        verticalLayout {
                            textView {
                                text = list.model.name
                                textSize = 15f
                                textColor = Color.parseColor("#202020")
                                ellipsize = TextUtils.TruncateAt.END
                                maxLines = 1
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                rightMargin = dip(10)
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
                            width = 0
                            weight = 1f
                            height = wrapContent
                            gravity = Gravity.RIGHT
                            leftMargin = dip(15)
                        }
                        checkbox = checkBox {
                            isChecked = if(list.isTrueChecked!=null){
                                list.isTrueChecked!!
                            }else{
                                false
                            }
                            if(bool == false){
                                isChecked = false
                            }
                            buttonDrawableResource = if (isChecked) {
                                R.mipmap.hook
                            } else {
                                R.mipmap.oval
                            }
                            setOnCheckedChangeListener { _, isChecked ->
                                if (isChecked) {
                                    buttonDrawableResource = R.mipmap.hook
                                    checkBoxStatus.updateCheckStatus(list,true)
                                } else {
                                    buttonDrawableResource = R.mipmap.oval
                                    checkBoxStatus.updateCheckStatus(list,null)
                                }
                            }
                        }.lparams {
                            width = wrapContent
                            height = dip(30)
                            gravity = Gravity.RIGHT
                            rightMargin = dip(15)
                        }
                        var a =0
                        this.withTrigger().click {
                            checkbox.isChecked = a%2==0
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