package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class ResumeListFragment:Fragment() {

    var TrpToolbar: Toolbar?=null
    private var mContext: Context? = null
    lateinit var cancelTool:CancelTool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): ResumeListFragment {
            return ResumeListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        cancelTool = activity as CancelTool
        return fragmentView
    }

    private fun createView():View{
        return UI {
            linearLayout{
                verticalLayout {
                    gravity = Gravity.BOTTOM
                    verticalLayout {
                        backgroundResource = R.drawable.list_border
                        relativeLayout {
                            textView {
                                text = "顔"
                                gravity = Gravity.CENTER
                                textSize = 14f
                                textColorResource = R.color.gray9B
                            }.lparams{
                                width = wrapContent
                                height = wrapContent
                                centerInParent()
                            }
                            view{
                                backgroundColorResource = R.color.grayE6
                            }.lparams{
                                width = matchParent
                                height = dip(1)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(45)
                        }
                        relativeLayout {
                            textView {
                                text = "撮影"
                                gravity = Gravity.CENTER
                                textSize = 19f
                                textColorResource = R.color.blue007AFF
                            }.lparams{
                                width = wrapContent
                                height = wrapContent
                                centerInParent()
                            }
                            view{
                                backgroundColorResource = R.color.grayE6
                            }.lparams{
                                width = matchParent
                                height = dip(1)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(45)
                        }

                        relativeLayout {
                            textView {
                                text = "アルバムから選ぶ"
                                gravity = Gravity.CENTER
                                textSize = 19f
                                textColorResource = R.color.blue007AFF
                            }.lparams{
                                width = wrapContent
                                height = wrapContent
                                centerInParent()
                            }
                            view{
                                backgroundColorResource = R.color.grayE6
                            }.lparams{
                                width = matchParent
                                height = dip(1)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(45)
                        }
                        relativeLayout {
                            textView {
                                text = "黙認"
                                gravity = Gravity.CENTER
                                textSize = 19f
                                textColorResource = R.color.blue007AFF
                            }.lparams{
                                width = wrapContent
                                height = wrapContent
                                centerInParent()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(45)
                        }
                    }.lparams(width = matchParent,height = wrapContent){
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                    }

                    button {
                        backgroundResource = R.drawable.list_border
                        textResource = R.string.jobStatuVerify
                        gravity = Gravity.CENTER
                        textSize = 19f
                        textColorResource = R.color.blue007AFF

                        setOnClickListener(object : View.OnClickListener{
                            override fun onClick(v: View?) {
                                cancelTool.cancelList()
                            }
                        })
                    }.lparams(width = matchParent,height = dip(58)){
                        topMargin = dip(8)
                        bottomMargin = dip(10)
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                    }
                }.lparams(width = matchParent,height = matchParent){

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

    public interface CancelTool {

        fun cancelList()
    }

}