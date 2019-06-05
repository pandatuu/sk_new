package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.text.InputType
import android.widget.ImageView
import com.codbking.widget.bean.DateType
import com.codbking.widget.DatePickDialog
import com.example.sk_android.mvp.view.activity.register.PersonInformationTwoActivity
import com.lcw.library.imagepicker.ImagePicker
import android.content.Intent
import android.app.Activity.RESULT_OK
import android.media.Image
import android.net.Uri
import android.text.InputFilter
import com.example.sk_android.custom.layout.PictruePicker
import com.example.sk_android.utils.roundImageView
import com.yancy.gallerypick.config.GalleryPick
import java.net.URI
import java.util.ArrayList


class IiMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var dateInput:EditText
    lateinit var password:EditText
    lateinit var tool:BaseTool
    lateinit var headImageView: ImageView

    lateinit var middleware:Middleware
    private var ImagePaths = HashMap<String,Uri>()



    companion object {
        fun newInstance(result:HashMap<String,Uri>): IiMainBodyFragment {
            val fragment = IiMainBodyFragment()
            fragment.ImagePaths = result
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView=createView()
        middleware =  activity as Middleware
        return fragmentView
    }

    @SuppressLint("RtlHardcoded")
    fun createView():View{
        tool=BaseTool()
        val view = View.inflate(mContext, R.layout.radion_gender, null)
        val dialog = DatePickDialog(mContext)
        dialog.setYearLimt(5)
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(DateType.TYPE_YMD)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd")
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
                        if(ImagePaths.get("uri") != null){
                            headImageView = roundImageView {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                imageURI = ImagePaths.get("uri")
                                setOnClickListener { middleware.addImage() }
                            }.lparams(width = dip(90),height = dip(90)){}
                        }else {
                            headImageView = roundImageView {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                imageResource = R.mipmap.ico_head
                                setOnClickListener { middleware.addImage() }
                            }.lparams(width = dip(90),height = dip(90)){}
                        }

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

                        linearLayout{
                            editText {
                                backgroundColorResource = R.color.whiteFF
                                hintResource = R.string.IiSurname
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                singleLine = true
                            }.lparams(width = wrapContent,height = matchParent){
                                weight = 1f
                            }

                            editText {
                                backgroundColorResource = R.color.whiteFF
                                hintResource = R.string.IiNameHint
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                singleLine = true
                            }.lparams(width = wrapContent,height = matchParent){
                                weight = 1f
                            }
                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }
//                        editText {
//                            backgroundColorResource = R.color.whiteFF
//                            singleLine = true
//                            hintResource = R.string.IiNameHint
//                            hintTextColor = Color.parseColor("#B3B3B3")
//                            textSize = 15f
//                        }.lparams(width = matchParent, height = wrapContent){
//                            weight = 1f
//                        }
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
                        }.lparams(width = wrapContent,height = matchParent){
                            weight = 1f
                        }
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
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        dateInput = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiBornHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            isFocusableInTouchMode = false
                            setOnClickListener { dialog.show() }
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
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        dateInput = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiInitialInaugurationHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            isFocusableInTouchMode = false
                            setOnClickListener { dialog.show() }
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
                            isFocusableInTouchMode = false
                            setOnClickListener { middleware.addListFragment() }
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
                        isHorizontalScrollBarEnabled = false
                        gravity = Gravity.TOP
//                        inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        filters = arrayOf(InputFilter.LengthFilter(50))
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
                        isVerticalScrollBarEnabled = true
                        isHorizontalScrollBarEnabled = false
                        gravity = Gravity.TOP
                        filters = arrayOf(InputFilter.LengthFilter(50))
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
                        setOnClickListener { startActivity<PersonInformationTwoActivity>() }

                    }.lparams(width = matchParent,height = dip(47)){
                        topMargin = dip(20)
                    }


                }

            }
        }.view
    }


    interface Middleware {

        fun addListFragment()

        fun addImage()
    }


}

