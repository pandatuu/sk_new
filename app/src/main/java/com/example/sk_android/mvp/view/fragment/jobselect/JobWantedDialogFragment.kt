package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface

class JobWantedDialogFragment : Fragment() {



    private var mContext: Context? = null
    private lateinit var confirmSelection:ConfirmSelection
    var type:String=""
    var condition:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {

        public val CANCLE="cancle"
        public val DELETE="delete"

        fun newInstance(type:String,id:String): JobWantedDialogFragment {
            val fragment = JobWantedDialogFragment()
            fragment.type=type
            fragment.condition = id
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        confirmSelection =  activity as ConfirmSelection
        return fragmentView
    }

    fun createView(): View {

        return UI {
            linearLayout {
                relativeLayout()  {
                    verticalLayout {
                        gravity=Gravity.CENTER_HORIZONTAL
                        backgroundResource=R.drawable.radius_dialog_white
                        relativeLayout {
                            imageView {
                                imageResource=R.mipmap.icon_close_tc
                                setOnClickListener(object :View.OnClickListener{
                                    override fun onClick(v: View?) {
                                        confirmSelection.confirmResult(false,condition)
                                    }
                                })
                            }.lparams{
                               alignParentRight()
                            }
                        }.lparams {
                            width= matchParent
                            rightMargin=dip(17)
                            leftMargin=dip(17)
                            topMargin=dip(16)
                        }

                        relativeLayout {
                            imageView {
                                imageResource=R.mipmap.icon_tishi_tc
                            }.lparams{
                                centerHorizontally()
                            }
                        }.lparams {
                            width= matchParent
                        }

                        textView {
                            textColorResource=R.color.themeColor
                            textSize=16f
                            text="ご注意"
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            gravity=Gravity.CENTER_HORIZONTAL
                        }.lparams {
                            width= matchParent
                            topMargin=dip(18)
                        }

                        textView {
                            textColorResource=R.color.normalTextColor
                            textSize=14f
                            if(type.equals("cancle")){
                                text="まだ保存されていませんが、 編集をキャンセルしますか？"
                            }else if(type.equals("delete")){
                                text="この就職希望を削除して宜しいでしょうか？"
                            }
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            gravity=Gravity.CENTER_HORIZONTAL
                        }.lparams {
                            width=dip(187)
                            height=dip(44)
                            topMargin=dip(14)
                        }

                        textView {
                            backgroundResource=R.drawable.radius_button_orange
                            if(type.equals("cancle")){
                                text="あきらめて"
                            }else if(type.equals("delete")){
                                text="削除"
                            }
                            textSize=15f
                            gravity=Gravity.CENTER
                            textColor=Color.WHITE
                            if(type.equals("cancle")){
                                setOnClickListener {confirmSelection.changeResult(true)}
                            }else if(type.equals("delete")){
                                setOnClickListener { confirmSelection.confirmResult(true,condition) }
                            }

                        }.lparams {
                            width=dip(240)
                            height=dip(44)
                            topMargin=dip(12)
                        }

                        textView {
                            backgroundResource=R.drawable.radius_button_theme
                            text="手が滑べました"
                            textSize=15f
                            gravity=Gravity.CENTER
                            textColor=Color.WHITE
                            if(type.equals("cancle")){
                                setOnClickListener {confirmSelection.changeResult(false)}
                            }else if(type.equals("delete")){
                                setOnClickListener { confirmSelection.confirmResult(false,condition) }
                            }

                        }.lparams {
                            width=dip(240)
                            height=dip(44)
                            topMargin=dip(14)
                        }

                    }.lparams(width = dip(302), height =dip(307)){
                        centerInParent()
                    }


                    backgroundColorResource=R.color.shadowColor
                }.lparams(width = matchParent, height =matchParent){
                }
            }
        }.view
    }

    public interface ConfirmSelection {
        fun confirmResult(b:Boolean,id:String)
        fun changeResult(condition:Boolean)
    }


}

