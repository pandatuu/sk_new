package com.example.sk_android.mvp.view.fragment.Register

import android.app.DatePickerDialog
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
import com.example.sk_android.mvp.tool.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.text.InputType
import android.widget.ImageView
import com.codbking.widget.bean.DateType
import com.codbking.widget.DatePickDialog
import com.codbking.widget.OnSureLisener
import com.example.sk_android.mvp.view.activity.Register.PersonInformationTwoActivity


class IiMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var dateInput:EditText
    lateinit var password:EditText
    lateinit var tool:BaseTool
    lateinit var dialog:DatePickerDialog
    lateinit var middleware:Middleware


    companion object {
        fun newInstance(): IiMainBodyFragment {
            val fragment = IiMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

        val dialog = DatePickDialog(mContext)
        //设置上下年分限制
        dialog.setYearLimt(5)
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(DateType.TYPE_ALL)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm")
        //设置选择回调
//        dialog.setOnChangeLisener(null)
        //设置点击确定按钮回调

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        middleware =  activity as Middleware
        return fragmentView
    }

    fun createView():View{
        tool=BaseTool()
        var view = View.inflate(mContext, R.layout.radion_gender, null)
        return UI {
            scrollView {
                verticalLayout {
                    backgroundColorResource = R.color.whiteFF
                    orientation = LinearLayout.VERTICAL
                    leftPadding = dip(15)
                    rightPadding = dip(15)
                    bottomPadding = dip(38)

                    textView {
                        textResource = R.string.IiIntroduction
                        textSize = 18f
                        gravity = Gravity.LEFT
                        textColorResource = R.color.black33
                    }.lparams(width = matchParent, height = dip(25)) {
                        topMargin = dip(20)
                    }

                    linearLayout {
                        gravity = Gravity.CENTER
                        imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            imageResource = R.mipmap.ico_head
                        }.lparams(width = dip(90),height = dip(90)){}
                    }.lparams(width = matchParent,height = dip(145)){}


                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiName
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiNameHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){}

                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiGender
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL
                            addView(view)
                        }.lparams(width = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiPhone
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiPhoneHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiMail
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL

                        }.lparams(width = dip(110), height = matchParent){
                        }
                        editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiMailHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiBorn
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                            setOnClickListener(object : View.OnClickListener{
                                override fun onClick(v: View?) {
                                    val dialog = DatePickDialog(mContext)
                                    //设置上下年分限制
                                    dialog.setYearLimt(5)
                                    //设置标题
                                    dialog.setTitle("选择时间")
                                    //设置类型
                                    dialog.setType(DateType.TYPE_YMD)
                                    //设置消息体的显示格式，日期格式
                                    dialog.setMessageFormat("yyyy-MM")
                                    fun setOnSureLisener(onSureLisener: OnSureLisener) {
                                        dateInput.setText("5454")
                                    }
                                    dialog.show()
                                }
                            })
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        dateInput = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiBornHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiInitialInauguration
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                            setOnClickListener(object : View.OnClickListener{
                                override fun onClick(v: View?) {
                                    val dialog = DatePickDialog(mContext)
                                    //设置上下年分限制
                                    dialog.setYearLimt(5)
                                    //设置标题
                                    dialog.setTitle("选择时间")
                                    //设置类型
                                    dialog.setType(DateType.TYPE_YMD)
                                    //设置消息体的显示格式，日期格式
                                    dialog.setMessageFormat("yyyy-MM")
                                    fun setOnSureLisener(onSureLisener: OnSureLisener) {
                                        dateInput.setText("5454")
                                    }
                                    dialog.show()
                                }
                            })
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        dateInput = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiInitialInaugurationHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiEmploymentStatu
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        dateInput = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiEmploymentStatuHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f

                            setOnClickListener(object : View.OnClickListener{
                                override fun onClick(v: View?) {
                                    middleware.addListFragment()
                                }
                            })
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    textView {
                        textResource = R.string.IiWorkSkills
                        textSize = 15f
                        textColorResource = R.color.black33

                    }.lparams(width = matchParent, height = dip(21)){
                        topMargin = dip(16)
                    }

                    editText {
                        backgroundColorResource = R.color.whiteFF
                        isVerticalScrollBarEnabled = true
                        isHorizontalScrollBarEnabled = false
                        gravity = Gravity.TOP
                        inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        minLines = 3
                        maxLines = 5
                        hintResource = R.string.IiWorkSkillsHint
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f
                        backgroundResource = R.drawable.input_border
                    }.lparams(width = matchParent, height = dip(65)){
                        topMargin = dip(7)
                    }

                    textView {
                        textResource = R.string.IiPersonalSkills
                        textSize = 15f
                        textColorResource = R.color.black33

                    }.lparams(width = matchParent, height = dip(21)){
                        topMargin = dip(16)
                    }

                    editText {
                        backgroundColorResource = R.color.whiteFF
                        isVerticalScrollBarEnabled = true
                        isHorizontalScrollBarEnabled = false
                        gravity = Gravity.TOP
                        inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        minLines = 3
                        maxLines = 5
                        hintResource = R.string.IiPersonalSkillsHint
                        hintTextColor = Color.parseColor("#B3B3B3")
                        textSize = 15f
                        backgroundResource = R.drawable.input_border
                    }.lparams(width = matchParent, height = dip(65)){
                        topMargin = dip(7)
                    }

                    button {
                        textResource = R.string.IiButtonText
                        textSize = 16f
                        textColorResource = R.color.whiteFF
                        gravity = Gravity.CENTER
                        backgroundColorResource = R.color.yellowFFB706
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                                startActivity<PersonInformationTwoActivity>()
                            }

                        })

                    }.lparams(width = matchParent,height = dip(47)){
                        topMargin = dip(20)
                    }


                }

            }
        }.view
    }

    public interface Middleware {

        fun addListFragment()
    }

}

