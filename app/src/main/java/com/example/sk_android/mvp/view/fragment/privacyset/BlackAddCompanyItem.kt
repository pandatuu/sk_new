package com.example.sk_android.mvp.view.fragment.privacyset

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.privacySet.BlackCompanyAdd
import com.example.sk_android.mvp.model.privacySet.ListItemModel
import com.example.sk_android.mvp.view.adapter.privacyset.CommonAddItemAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*

class BlackAddCompanyItem : Fragment() {


    lateinit var mList: MutableList<BlackCompanyAdd>
    lateinit var bubianList: MutableList<BlackCompanyAdd>
    var text = ""
    lateinit var onCycleClickListener : BlackOnRecycleClickListener

    companion object {
        fun newInstance(mtext : String, linkedlist: MutableList<BlackCompanyAdd>): BlackAddCompanyItem {
            val fragment = BlackAddCompanyItem()
            fragment.text = mtext
            fragment.mList = linkedlist
            fragment.bubianList = linkedlist
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onCycleClickListener = activity as BlackOnRecycleClickListener
        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            relativeLayout{
                relativeLayout {
                    verticalLayout {
                        recyclerView {
                            setLayoutManager(LinearLayoutManager(this.getContext()))
                            var itemadapter = CommonAddItemAdapter(text,mList)
                            adapter = itemadapter
                            itemadapter.setOnItemClickListener(object: CommonAddItemAdapter.OnItemClickListener{
                                override fun OnItemClick(view: View?, data: BlackCompanyAdd) {
                                    onCycleClickListener.blackOnCycleClick(data)
                                }
                            })
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        bottomMargin = dip(125)
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                    topMargin = dip(64)
                }
            }
        }.view
    }

    interface BlackOnRecycleClickListener {

        fun blackOnCycleClick(data: BlackCompanyAdd)
    }
}