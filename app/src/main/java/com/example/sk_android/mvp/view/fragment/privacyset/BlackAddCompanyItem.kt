package com.example.sk_android.mvp.view.fragment.privacyset

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.privacySet.BlackListItemModel
import com.example.sk_android.mvp.view.adapter.privacyset.BlackAddItemAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import java.util.*

class BlackAddCompanyItem : Fragment() {


    lateinit var mList: LinkedList<BlackListItemModel>
    lateinit var bubianList: LinkedList<BlackListItemModel>
    var text = ""

    companion object {
        fun newInstance(mtext : String, linkedlist: LinkedList<BlackListItemModel>): BlackAddCompanyItem {
            val fragment = BlackAddCompanyItem()
            fragment.text = mtext
            fragment.mList = linkedlist
            fragment.bubianList = linkedlist
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
                            var itemadapter = BlackAddItemAdapter(text,mList)
                            adapter = itemadapter
                            itemadapter.setOnItemClickListener(object: BlackAddItemAdapter.OnItemClickListener{
                                override fun OnItemClick(view: View?, data: BlackListItemModel) {
//                                    Toast.makeText(getActivity(),data.companyName,Toast.LENGTH_SHORT).show()
                                    onCycleClickListener.OnCycleClick(data)
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
    /**
     * 设置item的监听事件的接口
     */
    interface OnRecycleClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        fun OnCycleClick(data: BlackListItemModel)
    }
    private lateinit var onCycleClickListener : OnRecycleClickListener
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onCycleClickListener = context as OnRecycleClickListener

    }
}