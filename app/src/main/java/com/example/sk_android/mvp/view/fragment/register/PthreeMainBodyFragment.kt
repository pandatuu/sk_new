package com.example.sk_android.mvp.view.fragment.register

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.text.InputType
import com.example.sk_android.mvp.view.activity.register.PersonInformationFourActivity

class PthreeMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var dateInput:EditText
    lateinit var password:EditText
    lateinit var tool: BaseTool
    lateinit var dialog:DatePickerDialog


    companion object {
        fun newInstance(): PthreeMainBodyFragment {
            val fragment = PthreeMainBodyFragment()
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
        tool= BaseTool()
        var view = View.inflate(mContext, R.layout.radion_gender, null)
        return UI {

                verticalLayout {
                    backgroundColorResource = R.color.whiteFF
                    orientation = LinearLayout.VERTICAL
                    leftPadding = dip(15)
                    rightPadding = dip(15)

                    textView {
                        textResource = R.string.PthreeIntroduction
                        textSize = 18f
                        gravity = Gravity.LEFT
                        textColorResource = R.color.black33
                    }.lparams(width = matchParent, height = dip(25)) {
                        topMargin = dip(20)
                    }


                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.company
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.companyHint
                            hintTextColor = R.color.grayB3
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        textView {
                            textResource = R.string.companySelector
                            textSize = 12f
                            textColorResource = R.color.gray5c
                            gravity = Gravity.LEFT
                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }

                        switch {
                            setThumbResource(R.drawable.shape_switch_thumb)
                            setTrackResource(R.drawable.shape_switch_track_selector)
                        }.lparams(width = dip(26), height = dip(16)) {
                            weight = 1f
                            gravity = Gravity.RIGHT
                        }
                    }.lparams(width = matchParent,height = dip(17)){
                        topMargin = dip(8)
                    }


                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.position
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.positionHint
                            hintTextColor = R.color.grayB3
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(17)
                    }


                    textView {
                        textResource = R.string.workTime
                        textColorResource = R.color.black33
                        textSize = 15f
                        gravity = Gravity.LEFT
                    }.lparams(width = matchParent, height =  wrapContent){
                        topMargin = dip(16)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            backgroundResource = R.drawable.input_border
                            editText {
                                backgroundColorResource = R.color.whiteFF
                                singleLine = true
                                isEnabled = false
                                hintResource = R.string.startTime
                                hintTextColor = R.color.black33
                                rightPadding = dip(10)
                                textSize = 15f
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
                            weight = 1f
                            rightMargin = dip(8)

                        }


                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            backgroundResource = R.drawable.input_border
                            editText {
                                backgroundColorResource = R.color.whiteFF
                                singleLine = true
                                isEnabled = false
                                hintResource = R.string.endTime
                                hintTextColor = R.color.black33
                                rightPadding = dip(10)
                                textSize = 15f
                            }.lparams(width = matchParent, height = wrapContent){
                                weight = 1f
                            }
                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent,height = dip(15))
                            }.lparams(width = dip(8),height = matchParent)
                        }.lparams(width = matchParent,height = matchParent){
                            weight = 1f
                            leftMargin = dip(8)
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(16)
                    }

                    textView {
                        textResource = R.string.work
                        textSize = 15f
                        textColorResource = R.color.black33
                        gravity = Gravity.LEFT
                    }.lparams(width = matchParent,height = dip(21)){
                        topMargin = dip(17)
                    }

                    editText {
                        hintResource = R.string.workHint
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
                                startActivity<PersonInformationFourActivity>()
                            }

                        })
                    }.lparams(width = matchParent,height = dip(47)){
                        topMargin = dip(20)
                    }

                }

        }.view
    }
}

