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
import com.example.sk_android.mvp.view.adapter.myhelpfeedback.SecondHelpInformationAdapter
import org.jetbrains.anko.scrollView
import org.jetbrains.anko.support.v4.UI

class LevelSecondHelpFrag : Fragment() {
    lateinit var mContext: Context
    var mList: MutableList<HelpModel>? = mutableListOf()

    companion object {
        fun newInstance(
            context: Context, list: MutableList<HelpModel>?
        ): LevelSecondHelpFrag {
            val frag = LevelSecondHelpFrag()
            frag.mContext = context
            frag.mList = list
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return create()
    }

    private fun create(): View? {
        return UI {
            scrollView {
                recyclerView {
                    layoutManager =
                        LinearLayoutManager(mContext)
                    if (mList != null) {
                        adapter = SecondHelpInformationAdapter(mList!!, mContext)
                    }
                }
            }
        }.view
    }
}