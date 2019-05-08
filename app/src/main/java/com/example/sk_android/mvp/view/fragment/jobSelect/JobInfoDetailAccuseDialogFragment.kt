package com.example.sk_android.mvp.view.fragment.jobSelect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*

import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface


class JobInfoDetailAccuseDialogFragment : Fragment() {



    private var mContext: Context? = null
    private lateinit var confirmSelection:DialogConfirmSelection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {


        fun newInstance(): JobInfoDetailAccuseDialogFragment {
            val fragment = JobInfoDetailAccuseDialogFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        confirmSelection =  activity as DialogConfirmSelection
        return fragmentView
    }

    fun createView(): View {

        return UI {
            linearLayout {
                relativeLayout()  {
                    verticalLayout {
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {

                            }
                        })
                        backgroundResource=R.drawable.radius_dialog_white
                        relativeLayout {
                            backgroundResource=R.drawable.bottom_border_graycd
                            textView{
                                textSize=15f
                                textColorResource=R.color.themeColor
                                text="告発する"
                            }.lparams {
                                centerVertically()
                                alignParentLeft()
                                width= matchParent
                                height=dip(21)
                            }


                            imageView {
                                imageResource=R.mipmap.icon_close_tc
                                setOnClickListener(object :View.OnClickListener{
                                    override fun onClick(v: View?) {
                                        confirmSelection.dialogConfirmResult(false)
                                    }
                                })
                            }.lparams{
                               alignParentRight()
                                rightMargin=dip(4)
                                topMargin=dip(16)
                            }
                        }.lparams {
                            width= matchParent
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            height=dip(48)
                        }


                        textView {
                            text="告発の種類"
                            textSize=13f
                            textColor=R.color.gray5c
                        }.lparams {
                            topMargin=dip(15)
                            leftMargin=dip(15)
                        }

                        editText {
                            backgroundResource=R.drawable.border_graycd
                            textSize=14f
                        }.lparams {
                            width= matchParent
                            height=dip(37)
                            topMargin=dip(7)
                            leftMargin=dip(15)
                            rightMargin=dip(15)

                        }

                        textView {
                            text="通報内容"
                            textSize=13f
                            textColor=R.color.gray5c
                        }.lparams {
                            topMargin=dip(15)
                            leftMargin=dip(15)
                            topMargin=dip(13)
                        }

                        editText {
                            backgroundResource=R.drawable.border_graycd
                            singleLine=false
                            gravity=Gravity.TOP
                            textSize=14f
                        }.lparams {
                            width= matchParent
                            height=dip(100)
                            topMargin=dip(7)
                            leftMargin=dip(15)
                            rightMargin=dip(15)

                        }

                        textView {
                            text="告発の添付ファイル"
                            textSize=13f
                            textColor=R.color.gray5c
                        }.lparams {
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            topMargin=dip(13)
                        }

                        linearLayout {
                            backgroundColorResource=R.color.originColor
                            gravity=Gravity.CENTER_VERTICAL
                            textView {
                                textSize=12f
                                text="添付ファイルを追加"
                                textColorResource=R.color.gray63
                            }.lparams {
                                leftMargin=dip(5)
                            }
                        }.lparams {
                            topMargin=dip(7)
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            height=dip(35)
                            width=dip(150)
                        }



                        textView {
                            backgroundResource=R.drawable.radius_button_theme
                            text="送信"
                            textSize=15f
                            gravity=Gravity.CENTER
                            textColor=Color.WHITE
                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    confirmSelection.dialogConfirmResult(false)
                                }
                            })
                        }.lparams {
                            width= matchParent
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            height=dip(44)
                            topMargin=dip(14)
                        }


                        textView {
                            backgroundResource=R.drawable.radius_button_gray_cc
                            text="キャンセル"
                            textSize=15f
                            gravity=Gravity.CENTER
                            textColor=Color.WHITE
                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    confirmSelection.dialogConfirmResult(false)
                                }
                            })
                        }.lparams {
                            width= matchParent
                            rightMargin=dip(15)
                            leftMargin=dip(15)
                            height=dip(44)
                            topMargin=dip(14)
                        }

                    }.lparams(width = dip(310), height =dip(471)){
                        centerInParent()
                    }


                    backgroundColorResource=R.color.shadowColor
                }.lparams(width = matchParent, height =matchParent){
                }
            }
        }.view
    }

    public interface DialogConfirmSelection {
        fun dialogConfirmResult(b:Boolean)
    }


}

