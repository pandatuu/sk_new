package com.example.sk_android.mvp.view.fragment.jobSelect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobSelect.JobSearchResult
import com.example.sk_android.mvp.view.adapter.JobSearchShowAdapter

class JobSearchResultFragment : Fragment() {


    private var mContext: Context? = null
    var jobSearchResult: Array<JobSearchResult>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(list:Array<JobSearchResult>): JobSearchResultFragment {
            val fragment = JobSearchResultFragment()
            fragment.jobSearchResult=list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView(): View {
        return UI {
            linearLayout {
                recyclerView{
                    overScrollMode = View.OVER_SCROLL_NEVER
                    setLayoutManager(LinearLayoutManager(this.getContext()))
                    var searchList:Array<JobSearchResult> =  arrayOf<JobSearchResult>()
                    if(jobSearchResult!=null){
                        searchList= jobSearchResult as Array<JobSearchResult>
                    }
                    setAdapter(JobSearchShowAdapter(this,  searchList) { item ->
                        toast(item.name)
                    })
                }.lparams {
                    leftMargin=dip(15)
                    rightMargin=dip(15)
                }
            }
        }.view
    }

}

