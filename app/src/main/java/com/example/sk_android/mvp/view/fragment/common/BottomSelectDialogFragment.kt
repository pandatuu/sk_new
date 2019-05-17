package com.example.sk_android.mvp.view.fragment.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toolbar
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.view.adapter.register.PersonAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.actionBarContainer
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import java.util.*
import kotlin.collections.ArrayList


class BottomSelectDialogFragment : Fragment() {


    private var mContext: Context? = null
    var list: MutableList<String> = mutableListOf()

    lateinit var layout: LinearLayout

    lateinit var bottomSelectDialogSelect:BottomSelectDialogSelect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(strArray: MutableList<String>): BottomSelectDialogFragment {
            var f = BottomSelectDialogFragment()
            f.list = strArray
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        bottomSelectDialogSelect=activity as BottomSelectDialogSelect
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    private fun createView(): View {

        var view = UI {
            linearLayout {
                verticalLayout {
                    gravity = Gravity.BOTTOM

                    layout = verticalLayout {
                        backgroundResource = R.drawable.list_border

                        textView {
                            textResource = R.string.jobSearchStatus
                            gravity = Gravity.CENTER
                            textSize = 14f
                            textColorResource = R.color.gray9B
                            gravity = Gravity.CENTER
                        }.lparams(width = matchParent, height = dip(44)) {
                            topMargin = dip(12)
                        }




                    }.lparams(width = matchParent, height = dip(277)) {
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
                                bottomSelectDialogSelect.getBottomSelectDialogSelect(-1)
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


        for(i in list.indices){
            layout.addView(getItemView(list[i],i))
        }
        return view
    }


    fun getItemView(tx: String,index:Int): View? {
        return with(layout.context) {
            verticalLayout {

                view {
                    backgroundColorResource = R.color.grayE6
                }.lparams(width = matchParent, height = dip(1)) {
                    topMargin = dip(13)
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                }

                textView {
                    text = tx
                    gravity=Gravity.CENTER
                    textSize=19f
                    letterSpacing=0.05f
                    textColorResource=R.color.blue007AFF
                    setOnClickListener(object :View.OnClickListener{
                        override fun onClick(v: View?) {
                            bottomSelectDialogSelect.getBottomSelectDialogSelect(index)
                        }
                    })
                }.lparams {
                    width = matchParent
                    height = dip(58)
                }
            }
        }
    }

    interface  BottomSelectDialogSelect{
        fun getBottomSelectDialogSelect(index:Int)
    }



}