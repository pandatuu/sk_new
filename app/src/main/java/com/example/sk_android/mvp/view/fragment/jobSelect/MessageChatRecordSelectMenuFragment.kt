package com.example.sk_android.mvp.view.fragment.jobSelect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.widget.Toolbar

class MessageChatRecordSelectMenuFragment : Fragment() {

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null

    private lateinit var Selecter: MenuSelect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): MessageChatRecordSelectMenuFragment {
            return MessageChatRecordSelectMenuFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        Selecter =  activity as MenuSelect

        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout {
                linearLayout() {

                    relativeLayout {

                        textView {
                            backgroundResource=R.drawable.bottom_border_yellow_3dp
                        }.lparams {
                            height=matchParent
                            width=dip(20)
                            centerInParent()
                        }


                        textView {
                            backgroundColor=Color.TRANSPARENT
                            text="チャット履歴"
                            textSize=16f
                            gravity=Gravity.CENTER
                            textColorResource=R.color.normalTextColor
                        }.lparams {
                            height=matchParent
                            width=matchParent
                        }



                    }.lparams {
                        width=0
                        weight=1f
                        height= matchParent
                    }


                    relativeLayout {

                        textView {
                            backgroundResource=R.drawable.bottom_border_yellow_3dp
                        }.lparams {
                            height=matchParent
                            width=dip(20)
                            centerInParent()
                        }




                        textView {
                            backgroundColor=Color.TRANSPARENT
                            text="連絡先"
                            textSize=16f
                            gravity=Gravity.CENTER
                            textColorResource=R.color.gray7f
                        }.lparams {
                            height=matchParent
                            width=matchParent
                        }

                    }.lparams {
                        width=0
                        weight=1f
                        height= matchParent
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(62)
                }
            }
        }.view

    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }


    interface  MenuSelect{

        fun  getMenuSelect(index:Int)
    }

}




