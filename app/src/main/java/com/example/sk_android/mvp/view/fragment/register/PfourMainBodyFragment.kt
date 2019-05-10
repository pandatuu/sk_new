package com.example.sk_android.mvp.view.fragment.register

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.tool.BaseTool
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI


class PfourMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var dateInput:EditText
    lateinit var password:EditText
    lateinit var tool:BaseTool
    lateinit var dialog:DatePickerDialog


    companion object {
        fun newInstance(): PfourMainBodyFragment {
            val fragment = PfourMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView():View{
        tool=BaseTool()
        var view = View.inflate(mContext, R.layout.radion_gender, null)
        return UI {

                verticalLayout {
                    backgroundColorResource = R.color.whiteFF
                    orientation = LinearLayout.VERTICAL
                    leftPadding = dip(15)
                    rightPadding = dip(15)

                    textView {
                        textResource = R.string.PfourIntroduction
                        textSize = 18f
                        gravity = Gravity.LEFT
                        textColorResource = R.color.black33
                    }.lparams(width = matchParent, height = dip(25)) {
                        topMargin = dip(20)
                    }


                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.expectedPosition
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isEnabled = false
                            hintResource = R.string.expectedPositionHint
                            hintTextColor = R.color.grayB3
                            rightPadding = dip(10)
                            textSize = 15f
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.remuneration
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isEnabled = false
                            hintResource = R.string.remunerationHint
                            hintTextColor = R.color.grayB3
                            rightPadding = dip(10)
                            textSize = 15f
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.register_select_nor
                            }.lparams(width = matchParent,height = dip(15))
                        }.lparams(width = dip(8),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.desiredIndustry
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isEnabled = false
                            hintResource = R.string.desiredIndustryHint
                            hintTextColor = R.color.grayB3
                            rightPadding = dip(10)
                            textSize = 15f
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.employmentForm
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isEnabled = false
                            hintResource = R.string.employmentFormHint
                            hintTextColor = R.color.grayB3
                            rightPadding = dip(10)
                            textSize = 15f
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }


                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.workAddress
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isEnabled = false
                            hintResource = R.string.workAddressHint
                            hintTextColor = R.color.grayB3
                            rightPadding = dip(10)
                            textSize = 15f
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }


                    textView {
                        textResource = R.string.advantage
                        textSize = 15f
                        textColorResource = R.color.black33
                        gravity = Gravity.LEFT
                    }.lparams(width = matchParent,height = dip(21)){
                        topMargin = dip(17)
                    }

                    editText {
                        hintResource = R.string.advantageHint
                        textSize = 15f
                        hintTextColor = R.color.grayB3
                        backgroundResource = R.drawable.input_border
                        inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        maxHeight = dip(100)
                        gravity = Gravity.TOP
                    }.lparams(width = matchParent,height = dip(100)){
                        topMargin = dip(7)
                    }




                    button {
                        backgroundResource = R.drawable.button_border
                        textResource = R.string.PtwoButton
                        textColorResource = R.color.whiteFF
                        textSize = 16f
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                                startActivity<LoginActivity>()
                            }

                        })
                    }.lparams(width = matchParent,height = dip(47)){
                        topMargin = dip(20)
                    }

                }

        }.view
    }
}

