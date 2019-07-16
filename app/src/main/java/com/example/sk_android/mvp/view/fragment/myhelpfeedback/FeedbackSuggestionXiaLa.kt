package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class FeedbackSuggestionXiaLa : Fragment() {

    var mList = mutableListOf<String>()
    lateinit var mContext: Context
    lateinit var xiala : XiaLaKuang

    companion object {
        fun newInstance(list: MutableList<String>, context: Context): FeedbackSuggestionXiaLa {
            val fragment = FeedbackSuggestionXiaLa()
            fragment.mContext = context
            fragment.mList = list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        xiala = activity as XiaLaKuang
        val view = CreaV()
        return view
    }

    private fun CreaV():View{
        val view = UI {
            linearLayout {
                isClickable = true
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    backgroundResource = R.drawable.button_shape
                    if(mList!=null){
                        for (item in mList){
                            textView {
                                text = item
                                textSize = 15f
                                onClick {
                                    xiala.onClickXiala(item)
                                }
                            }.lparams(matchParent,dip(20)){
                                margin = dip(10)
                            }
                        }
                    }
                }.lparams(wrapContent, wrapContent){
                    setMargins(dip(15),dip(105),0,0)
                }
            }
        }.view
        return view
    }

    interface XiaLaKuang{
        fun onClickXiala(text1 : String)
    }
}