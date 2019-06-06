package com.example.sk_android.mvp.view.fragment.common

import android.annotation.SuppressLint
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

class BottomSelectDialogFragment : Fragment() {

    var title1: String? = null
    private var mContext: Context? = null
    var list: MutableList<String> = mutableListOf()

    lateinit var layout: LinearLayout

    lateinit var bottomSelectDialogSelect: BottomSelectDialogSelect

    var mListCallback: BottomSelectDialogSelect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(title: String?, strArray: MutableList<String>): BottomSelectDialogFragment {
            var f = BottomSelectDialogFragment()
            f.list = strArray
            f.title1 = title
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        bottomSelectDialogSelect = activity as BottomSelectDialogSelect
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    private fun createView(): View {

        val view = UI {
            linearLayout {
                verticalLayout {
                    gravity = Gravity.BOTTOM
                    layout = verticalLayout {
                        backgroundResource = R.drawable.list_border
                        if (title1 != null) {
                            textView {
                                text = title1
                                gravity = Gravity.CENTER
                                textSize = 14f
                                textColorResource = R.color.gray9B
                                gravity = Gravity.CENTER
                            }.lparams(width = matchParent, height = dip(44)) {
                                topMargin = dip(12)
                                leftMargin = dip(10)
                                rightMargin = dip(10)
                            }
                        }

                    }.lparams(width = matchParent, height = wrapContent) {
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                    }
                    button {
                        backgroundResource = R.drawable.list_border
                        textResource = R.string.jobStatuVerify
                        gravity = Gravity.CENTER
                        textSize = 19f
                        textColorResource = R.color.blue007AFF

                        setOnClickListener(object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                bottomSelectDialogSelect.getBottomSelectDialogSelect()
                            }
                        })
                    }.lparams(width = matchParent, height = dip(58)) {
                        topMargin = dip(8)
                        bottomMargin = dip(10)
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                    }
                }.lparams(width = matchParent, height = matchParent) {

                }
            }
        }.view

        for (i in list.indices) {
            layout.addView(getItemView(list[i], i))
        }
        return view
    }

    fun getItemView(tx: String, index: Int): View? {
        return with(layout.context) {
            verticalLayout {
                if(title1 != null || index != 0){
                    view {
                        backgroundResource = R.drawable.text_view_bottom_border
                    }.lparams {
                        width = matchParent
                        height = dip(1)
                    }
                }
                textView {
                    text = tx
                    gravity = Gravity.CENTER
                    textSize = 19f
                    letterSpacing = 0.05f
                    textColorResource = R.color.blue007AFF

                    onClick { mListCallback?.getback(index, list) }
                }.lparams {
                    width = matchParent
                    height = dip(50)
                }
            }
        }
    }

    interface BottomSelectDialogSelect {
        // 调用关闭按钮
        fun getBottomSelectDialogSelect()
        // 按下选项
        fun getback(index: Int,list : MutableList<String>)
    }


}