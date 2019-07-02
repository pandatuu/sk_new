package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.sk_android.R
import com.example.sk_android.mvp.model.resume.Resume
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class RlOpeartListFragment:Fragment() {

    var TrpToolbar: Toolbar?=null
    private var mContext: Context? = null
    lateinit var cancelTool:CancelTool
    var resume = Resume(R.mipmap.word,"","","","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(resume:Resume): RlOpeartListFragment {
            var fragment = RlOpeartListFragment()
            fragment.resume = resume
            return fragment
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

                        textView {
                            textResource = R.string.rlRename
                            gravity = Gravity.CENTER
                            textSize = 19f
                            textColorResource = R.color.blue007AFF
                            setOnClickListener(object : View.OnClickListener{
                                override fun onClick(v: View?) {
                                    cancelTool.reName(resume)
                                }
                            })
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(17)
                        }

                        view {
                            backgroundColorResource = R.color.grayE6
                        }.lparams(width = matchParent, height = dip(1)) {
                            topMargin = dip(16)
                        }

                        textView {
                            textResource = R.string.rlSendEmail
                            gravity = Gravity.CENTER
                            textSize = 19f
                            textColorResource = R.color.blue007AFF
                            setOnClickListener(object : View.OnClickListener{
                                override fun onClick(v: View?) {
                                    cancelTool.sendEmail(resume)
                                }
                            })
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(17)
                        }

                        view {
                            backgroundColorResource = R.color.grayE6
                        }.lparams(width = matchParent, height = dip(1)) {
                            topMargin = dip(16)
                        }

                        textView {
                            textResource = R.string.rlDelete
                            gravity = Gravity.CENTER
                            textSize = 19f
                            textColorResource = R.color.blue007AFF
                            setOnClickListener(object : View.OnClickListener{
                                override fun onClick(v: View?) {
                                    cancelTool.delete(resume)
                                }
                            })
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(17)
                        }
                    }.lparams(width = matchParent,height = dip(173)){
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                    }

                    button {
                        backgroundResource = R.drawable.list_border
                        textResource = R.string.rlOperatCancel
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

        fun reName(resume:Resume)

        fun sendEmail(resume:Resume)

        fun delete(resume: Resume)
    }

}