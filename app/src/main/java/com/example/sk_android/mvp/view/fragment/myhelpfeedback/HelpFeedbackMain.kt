package com.example.sk_android.mvp.view.fragment.myhelpfeedback

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.myhelpfeedback.HelpModel
import com.example.sk_android.mvp.view.adapter.myhelpfeedback.HelpFeedbackAdapter
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.scrollView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.wrapContent

class HelpFeedbackMain :Fragment() {

    lateinit var mContext : Context
    var mList:MutableList<HelpModel>? = mutableListOf<HelpModel>()

    companion object {
        fun newInstance(
            context: Context,list: MutableList<HelpModel>?) : HelpFeedbackMain{
            var frag = HelpFeedbackMain()
            frag.mContext = context
            frag.mList = list
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = Create()
        return view
    }

    private fun Create(): View? {
        return UI {
            scrollView {
                relativeLayout {
                    recyclerView {
                        layoutManager =
                            LinearLayoutManager(mContext)
                        if(mList!=null){
                            adapter = HelpFeedbackAdapter(mList!!, mContext)
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
            }
        }.view
    }
}