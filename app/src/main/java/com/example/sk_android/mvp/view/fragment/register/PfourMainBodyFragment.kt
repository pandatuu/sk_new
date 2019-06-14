package com.example.sk_android.mvp.view.fragment.register

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import android.widget.Toast
import com.example.sk_android.mvp.view.activity.jobselect.IndustrySelectActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobSelectActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedEditActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedManageActivity
import com.wlwl.os.listbottomsheetdialog.BottomSheetDialogUtil
import org.jetbrains.anko.support.v4.startActivity


class PfourMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var jobText: TextView
    lateinit var addressText:TextView
    lateinit var password:EditText
    lateinit var tool: BaseTool
    lateinit var dialog:DatePickerDialog
    lateinit var mid:Mid


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
        mid = activity as Mid
        return fragmentView
    }

    fun createView():View{
        tool= BaseTool()
        return UI {

            scrollView {
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
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = wrapContent, height = matchParent){
                        }
                        jobText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.expectedPositionHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { mid.confirmJob() }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                            rightMargin = dip(28)
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
                        textResource = R.string.remuneration
                        textColorResource = R.color.black33
                        textSize = 15f
                        gravity = Gravity.LEFT
                    }.lparams(width = wrapContent,height = wrapContent){
                        topMargin = dip(17)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_money
                        linearLayout {
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                            backgroundResource = R.drawable.input_money_one
                            orientation = LinearLayout.HORIZONTAL
                            onClick { aa() }

                            textView {
                                textResource = R.string.hourly
                                gravity = Gravity.CENTER_VERTICAL
                                textSize = 15f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent,height = dip(15))
                            }.lparams(width = dip(8),height = matchParent)
                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }

                        linearLayout {
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                            backgroundResource = R.drawable.input_money_one
                            orientation = LinearLayout.HORIZONTAL

                            textView {
                                textResource = R.string.startHourly
                                gravity = Gravity.CENTER_VERTICAL
                                textSize = 15f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent,height = dip(15))
                            }.lparams(width = dip(8),height = matchParent)

                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                            leftMargin = dip(10)
                        }

                        linearLayout {
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                            backgroundResource = R.drawable.input_money_one
                            orientation = LinearLayout.HORIZONTAL

                            textView {
                                textResource = R.string.endHourly
                                gravity = Gravity.CENTER_VERTICAL
                                textSize = 15f
                                textColorResource = R.color.black33
                            }.lparams(width = wrapContent,height = matchParent){
                                weight = 1f
                                rightMargin = dip(15)
                            }

                            linearLayout {
                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.register_select_nor
                                }.lparams(width = matchParent,height = dip(15))
                            }.lparams(width = dip(8),height = matchParent)

                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                            leftMargin = dip(10)
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(7)
                    }

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.desiredIndustry
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = wrapContent, height = matchParent){
                        }
                        textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.desiredIndustryHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                            rightMargin = dip(28)
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
                        }.lparams(width = wrapContent, height = matchParent){
                        }
                        addressText = textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.workAddressHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                            onClick { mid.confirmAddress() }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                            rightMargin = dip(28)
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
                        }.lparams(width = wrapContent, height = matchParent){
                        }
                        textView {
                            backgroundColorResource = R.color.whiteFF
                            hintResource = R.string.employmentFormHint
                            hintTextColor = Color.parseColor("#333333")
                            textSize = 15f
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                            rightMargin = dip(28)
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
                        textSize = 13f
                        hintTextColor = Color.parseColor("#B3B3B3")
                        backgroundResource = R.drawable.input_border
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
                        bottomMargin = dip(30)
                    }

                }.lparams(width = matchParent,height = wrapContent){}
            }

        }.view
    }

    private fun aa(){
        BottomSheetDialogUtil.init(
            context, arrayOf("拍照", "从相册选取")
        ) { v, position -> Toast.makeText(context, "点击了第" + position + "个", Toast.LENGTH_SHORT).show() }.show()
    }

    private fun bb(){
        var mIntent = Intent(mContext,JobSelectActivity::class.java)
        startActivityForResult(mIntent,1)
    }

    public interface Mid{
        fun confirmJob()
        fun confirmAddress()
    }

    fun setJob(job:String){
        jobText.setText(job)
    }

    fun setAddress(address:String){
        addressText.setText(address)
    }

}

