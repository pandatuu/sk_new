package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.JobSearchUnderSearching
import com.example.sk_android.mvp.view.adapter.jobselect.JobSearchUnderSearchingListAdapter

class JobSearchUnderSearchingDisplayFragment : Fragment() {


    private var mContext: Context? = null
    var jobSearchResult: Array<JobSearchUnderSearching>?=null
    private lateinit var underSearching:UnderSearching

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(list:Array<JobSearchUnderSearching>): JobSearchUnderSearchingDisplayFragment {
            val fragment = JobSearchUnderSearchingDisplayFragment()
            fragment.jobSearchResult=list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        underSearching =  activity as UnderSearching
        return fragmentView
    }

    fun createView(): View {
        return UI {
            linearLayout {
                recyclerView{
                    overScrollMode = View.OVER_SCROLL_NEVER
                    setLayoutManager(LinearLayoutManager(this.getContext()))
                    var searchList:Array<JobSearchUnderSearching> =  arrayOf<JobSearchUnderSearching>()
                    if(jobSearchResult!=null){
                        searchList= jobSearchResult as Array<JobSearchUnderSearching>
                    }
                    setAdapter(JobSearchUnderSearchingListAdapter(this,  searchList) { item ->
                        underSearching.getUnderSearchingItem(item)
                    })
                }.lparams {
                    leftMargin=dip(15)
                    rightMargin=dip(15)
                }
            }
        }.view
    }

    interface UnderSearching {

        fun getUnderSearchingItem(item:JobSearchUnderSearching )
    }

}

