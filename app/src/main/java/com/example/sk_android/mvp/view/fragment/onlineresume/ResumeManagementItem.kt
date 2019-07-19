package com.example.sk_android.mvp.view.fragment.onlineresume

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.view.adapter.onlineresume.ResumeManagementAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*

class ResumeManagementItem : Fragment() {


    lateinit var mList: LinkedList<String>
    lateinit var bubianList: LinkedList<String>

    companion object {
        fun newInstance(linkedlist: LinkedList<String>): ResumeManagementItem {
            val fragment = ResumeManagementItem()
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
                    relativeLayout {
                        recyclerView {
                            setLayoutManager(LinearLayoutManager(this.getContext()))
                            var itemadapter = ResumeManagementAdapter(context,mList)
                            adapter = itemadapter
                        }.lparams{
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }
//    /**
//     * 设置item的监听事件的接口
//     */
//    interface BlackOnRecycleClickListener {
//        /**
//         * 接口中的点击每一项的实现方法，参数自己定义
//         *
//         * @param view 点击的item的视图
//         * @param data 点击的item的数据
//         */
//        fun BlackOnCycleClick(data: ListItemModel)
//    }
//    private lateinit var onCycleClickListener : BlackOnRecycleClickListener
//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        onCycleClickListener = context as BlackOnRecycleClickListener
//
//    }
}