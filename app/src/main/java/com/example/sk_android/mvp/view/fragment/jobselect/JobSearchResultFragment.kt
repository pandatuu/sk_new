package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.JobSearchResult
import com.example.sk_android.mvp.view.adapter.jobselect.JobSearchShowAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.ProvinceShowAdapter
import org.jetbrains.anko.sdk25.coroutines.onTouch

class JobSearchResultFragment : Fragment() {


    private var mContext: Context? = null
    var jobSearchResult: MutableList<JobSearchResult>?=null
    lateinit var listener:JobSearchResultModel

    lateinit var theAdapter: JobSearchShowAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        listener= activity as JobSearchResultModel

    }

    companion object {
        fun newInstance(list:MutableList<JobSearchResult>): JobSearchResultFragment {
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
                    var searchList:MutableList<JobSearchResult> =  mutableListOf()
                    if(jobSearchResult!=null){
                        searchList= jobSearchResult as MutableList<JobSearchResult>
                    }

                    theAdapter=JobSearchShowAdapter(this,  searchList) { item ,position->
                        listener.getSearchResultSelectedItem(item)
                        theAdapter.selectData(position)
                    }

                    setAdapter(theAdapter)

                    onTouch { v, event ->
                        listener.hideSoftKeyboard()
                    }

                }.lparams {
                    leftMargin=dip(15)
                    rightMargin=dip(15)
                }
            }
        }.view
    }


    interface JobSearchResultModel {

        fun hideSoftKeyboard()

        fun getSearchResultSelectedItem(i:JobSearchResult)
    }


}

