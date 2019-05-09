package com.example.sk_android.mvp.view.fragment.jobSelect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobSelect.Job
import com.example.sk_android.mvp.model.jobSelect.JobContainer
import com.example.sk_android.mvp.view.adapter.JobDetailAdapter
import com.example.sk_android.mvp.view.adapter.JobTypeDetailAdapter
import org.jetbrains.anko.support.v4.toast

class JobTypeDetailFragment : Fragment() {

    lateinit var jobDetail:RecyclerView
    private var mContext: Context? = null
    var showItem: JobContainer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(item: JobContainer): JobTypeDetailFragment {
            val fragment = JobTypeDetailFragment()
            fragment.showItem=item
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
                relativeLayout  {
                    linearLayout  {
                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                            }
                        })
                        verticalLayout{
                            backgroundColorResource=R.color.white
                            recyclerView {
                                backgroundColorResource=R.color.white
                                overScrollMode = View.OVER_SCROLL_NEVER
                                setLayoutManager(LinearLayoutManager(this.getContext()))
                                var jobList:Array<Job> =  arrayOf<Job>()
                                if(showItem!=null){
                                    jobList=showItem!!.item
                                }
                                setAdapter(JobTypeDetailAdapter(this,  jobList) { item ->
                                    showJobDetail(item)
                                })
                            }.lparams(width =matchParent){
                                leftMargin=dip(15)
                                rightMargin=dip(15)
                            }

                        }.lparams (width=dip(155), height = matchParent){
                        }
                        verticalLayout{
                            backgroundColorResource=R.color.originColor

                            jobDetail=recyclerView {

                            }.lparams(width =matchParent){
                                leftMargin=dip(15)
                                rightMargin=dip(15)
                            }

                        }.lparams ( width=dip(155), height = matchParent){
                        }
                    }.lparams(width =dip(310), height = matchParent){
                        alignParentRight()
                    }
                }.lparams(width =matchParent, height =matchParent){

                }
            }
        }.view
    }


    private fun showJobDetail(item: Job) {
        jobDetail.overScrollMode = View.OVER_SCROLL_NEVER
        jobDetail.setLayoutManager(LinearLayoutManager(jobDetail.getContext()))
        jobDetail.setAdapter(JobDetailAdapter(jobDetail,  item.job) { item ->
                toast(item)
        })
    }

}

