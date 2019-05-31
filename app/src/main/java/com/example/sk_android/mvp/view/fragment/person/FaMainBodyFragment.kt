package com.example.sk_android.mvp.view.fragment.person

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class FaMainBodyFragment:Fragment() {
    private lateinit var myDialog : MyDialog
    private var mContext: Context? = null
    lateinit var tool: BaseTool


    companion object {
        fun newInstance(): FaMainBodyFragment {
            val fragment = FaMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView():View{
        tool= BaseTool()
        return UI {
            verticalLayout {
                leftPadding = dip(15)
                rightPadding = dip(15)
                linearLayout {
                    linearLayout {
                        gravity = Gravity.CENTER
                        imageView {
                            imageResource = R.mipmap.company_logo
                        }.lparams(width = dip(60),height = dip(60))
                    }.lparams(width = dip(60),height = matchParent)

                    linearLayout{
                        gravity = Gravity.CENTER_VERTICAL
                        orientation = LinearLayout.VERTICAL
                        textView {
                            textResource = R.string.faceCompany
                            textSize = 17f
                            textColorResource = R.color.black20
                        }.lparams(height = wrapContent)

                        textView {
                            textResource = R.string.reserved
                            textSize = 13f
                            textColorResource = R.color.gray99
                        }.lparams(height = wrapContent){

                        }
                    }.lparams(height = matchParent){
                        weight = 1f
                        leftMargin = dip(10)
                    }
                }.lparams(width = matchParent,height = dip(90))

                linearLayout {
                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            imageResource = R.mipmap.face_work
                        }.lparams(width = dip(26),height = dip(26))
                        textView {
                            textResource = R.string.facePosition
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent,height = wrapContent){
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent,height = matchParent){
                        weight = 1f
                    }

                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            imageResource = R.mipmap.face_navigation
                        }.lparams(width = dip(26),height = dip(26))
                        textView {
                            textResource = R.string.faceNavi
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent,height = wrapContent){
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent,height = matchParent){
                        weight = 1f
                    }

                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        imageView {
                            imageResource = R.mipmap.face_chat
                        }.lparams(width = dip(26),height = dip(26))
                        textView {
                            textResource = R.string.faceChat
                            textColorResource = R.color.gray5c
                            textSize = 13f
                        }.lparams(width = wrapContent,height = wrapContent){
                            topMargin = dip(11)
                        }
                    }.lparams(width = wrapContent,height = matchParent){
                        weight = 1f
                    }
                }.lparams(width = matchParent,height = dip(105))

                view {
                    backgroundColorResource = R.color.grayEBEAEB
                }.lparams(width = matchParent, height = dip(1)) {}

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_time
                    }.lparams(width = dip(16),height = dip(16))
                    textView {
                        textResource = R.string.faceTime
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent,height = wrapContent){
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent,height = dip(20)){
                    topMargin = dip(23)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_job
                    }.lparams(width = dip(16),height = dip(16))
                    textView {
                        textResource = R.string.faceTask
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent,height = wrapContent){
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent,height = dip(20)){
                    topMargin = dip(15)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    imageView {
                        imageResource = R.mipmap.face_address
                    }.lparams(width = dip(16),height = dip(16))
                    textView {
                        textResource = R.string.faceAddress
                        textColorResource = R.color.black20
                        textSize = 14f
                    }.lparams(width = wrapContent,height = wrapContent){
                        weight = 1f
                        leftMargin = dip(11)
                    }
                }.lparams(width = matchParent,height = dip(20)){
                    topMargin = dip(15)
                }

                linearLayout{
                }.lparams(width = matchParent,height = wrapContent){
                    weight = 1f
                }

                textView {
                    gravity = Gravity.CENTER_HORIZONTAL
                    textResource = R.string.cancelInterView
                    textSize = 12f
                    textColorResource = R.color.gray89
                    setOnClickListener(object :View.OnClickListener{
                        override fun onClick(v: View?) {
                            afterShowLoading()
                        }

                    })

                }.lparams(width = matchParent,height = wrapContent){
                    bottomMargin = dip(52)
                }
            }
        }.view
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.cancel_interview, null)
        val mmLoading2 = MyDialog(this.mContext!!, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2
        myDialog.setCancelable(false)
        myDialog.show()
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
        determineBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
    }


}

