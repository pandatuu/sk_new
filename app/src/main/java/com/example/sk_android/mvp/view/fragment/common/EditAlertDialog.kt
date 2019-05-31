package com.example.sk_android.mvp.view.fragment.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class EditAlertDialog : Fragment() {
    lateinit var editDialogSelect: EditDialogSelect
    lateinit var des: String
    var mtext: String? = null
    lateinit var cancel: String
    lateinit var determine: String
    var tSize: Float = 0.0f

    companion object {
        fun newInstance(title: String, editText: String?, cancelBtn: String, determineBtn: String, btnSize: Float): EditAlertDialog {
            var frag = EditAlertDialog()
            frag.des = title
            frag.mtext = editText
            frag.cancel = cancelBtn
            frag.determine = determineBtn
            frag.tSize = btnSize
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        editDialogSelect = activity as EditDialogSelect
        return fragmentView
    }

    @SuppressLint("ResourceType")
    private fun createView(): View {

        var view = UI {
            relativeLayout {
                isClickable = true
                verticalLayout {
                    backgroundResource = R.drawable.fourdp_white_dialog
                    relativeLayout {
                        textView {
                            text = des
                            textSize = 15f
                            textColor = Color.parseColor("#FF202020")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerInParent()
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(60)
                    }
                    relativeLayout {
                        editText {
                            backgroundResource = R.drawable.area_text
                            gravity = top
                            if (text != null) {
                                hint = mtext
                                textSize = 12f
                                hintTextColor = Color.parseColor("#FF5C5C5C")
                            }
                        }.lparams {
                            width = matchParent
                            height = matchParent
                            centerHorizontally()
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(100)
                        setMargins(dip(10),0,dip(10),0)
                    }
                    relativeLayout {
                        button {
                            backgroundResource = R.drawable.button_shape_grey
                            text = cancel
                            textSize = tSize
                            textColor = Color.WHITE
                            onClick {
                                editDialogSelect.getEditDialogSelect()
                                toast(cancel)
                            }
                        }.lparams {
                            width = dip(130)
                            height = matchParent
                            alignParentLeft()
                            centerVertically()
                        }
                        button {
                            backgroundResource = R.drawable.button_shape_orange
                            text = determine
                            textSize = tSize
                            textColor = Color.WHITE
                            onClick {
                                editDialogSelect.getEditDialogSelect()
                                toast(determine)
                            }
                        }.lparams {
                            width = dip(130)
                            height = matchParent
                            alignParentRight()
                            centerVertically()
                        }
                    }.lparams {
                        width = matchParent
                        height = dip(40)
                        setMargins(dip(10),dip(20),dip(10),dip(20))
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    setMargins(dip(35),0,dip(35),0)
                    centerInParent()
                }
            }
        }.view
        return view
    }

    interface EditDialogSelect {
        fun getEditDialogSelect()
    }
}