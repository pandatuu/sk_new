package com.example.sk_android.mvp.view.fragment.privacyset

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.privacySet.BlackListItemModel
import com.example.sk_android.mvp.view.adapter.privacyset.BlackAddItemAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*

class BlackAddCompanyItem : Fragment() {


    lateinit var mList: LinkedList<BlackListItemModel>
    var text = ""

    companion object {
        fun newInstance(mtext : String, linkedlist: LinkedList<BlackListItemModel>): BlackAddCompanyItem {
            val fragment = BlackAddCompanyItem()
            fragment.text = mtext
            fragment.mList = linkedlist
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                            adapter = BlackAddItemAdapter(text,mList)
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        bottomMargin = dip(125)
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }
}