package com.example.sk_android.mvp.view.fragment.message

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.widget.TextView
import android.widget.Toolbar

class SideToSideFragment : Fragment() {

    var toolbar1: Toolbar?=null
    private var mContext: Context? = null


    lateinit var textView1:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }
    companion object {
        fun newInstance(): SideToSideFragment {
            return SideToSideFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity

        return fragmentView
    }
    private fun createView(): View {
        return UI {
            linearLayout {
                linearLayout() {



                        textView1=textView {
                            backgroundResource=R.drawable.bottom_border_yellow_3dp
                        }.lparams {
                            height=matchParent
                            width=dip(20)
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



}




