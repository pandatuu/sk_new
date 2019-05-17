package com.example.sk_android.mvp.view.fragment.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI


class TipDialogFragment : Fragment() {


    private var mContext: Context? = null
    var list: MutableList<String> = mutableListOf()

    lateinit var layout: LinearLayout
    lateinit var tipDialogSelect:TipDialogSelect
    var type:Int=0
    lateinit var des:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {

        var ALERT=1
        var COMFIRM=2
        var COMFIRM_NO_TITLE=3

        fun newInstance(type:Int,content:String): TipDialogFragment {
            var f = TipDialogFragment()
            f.type = type
            f.des=content
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        tipDialogSelect=activity as TipDialogSelect
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    private fun createView(): View {

        var view = UI {
            relativeLayout {
                layout= verticalLayout {
                    gravity=Gravity.CENTER

                }.lparams(width = matchParent, height = matchParent) {

                }
            }
        }.view

        if(type==ALERT){
            layout.addView(getAlertDialog(des))
        }
        return view
    }


    fun getAlertDialog(tx: String): View? {
        return with(layout.context) {
            relativeLayout {
                gravity=Gravity.CENTER_HORIZONTAL
                verticalLayout{
                    backgroundResource=R.drawable.radius_dialog_white_lager_radius
                    textView {
                        gravity=Gravity.CENTER
                        text=tx
                        textSize=14f
                        textColorResource=R.color.black33
                        letterSpacing=0.05f
                    }.lparams {
                        width= matchParent
                        leftMargin=dip(10)
                        rightMargin=dip(10)
                        topMargin=dip(25)
                        bottomMargin=dip(20)
                    }

                    view {
                        backgroundColorResource = R.color.grayd5
                    }.lparams(width = matchParent, height = dip(1)) {
                    }

                    linearLayout{

                        textView {
                            text = "キャンセル"
                            gravity=Gravity.CENTER
                            textSize=15f
                            letterSpacing=0.05f
                            textColorResource=R.color.blue007AFF
                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    tipDialogSelect.getTipDialogSelect(false)

                                }
                            })
                        }.lparams {
                            width = 0
                            height = matchParent
                            weight=1f
                        }

                        view {
                            backgroundColorResource = R.color.grayd5
                        }.lparams(width = dip(1), height = matchParent) {

                        }


                        textView {
                            text = "確定"
                            gravity=Gravity.CENTER
                            textSize=15f
                            letterSpacing=0.05f
                            textColorResource=R.color.blue007AFF
                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    tipDialogSelect.getTipDialogSelect(true)
                                }
                            })
                        }.lparams {
                            width = 0
                            height = matchParent
                            weight=1f
                        }

                    }.lparams {
                        height=dip(40)
                        width= matchParent
                    }
                }.lparams {
                    width=dip(245)
                }
            }
        }
    }

    interface  TipDialogSelect{
        fun getTipDialogSelect(b:Boolean)
    }
}