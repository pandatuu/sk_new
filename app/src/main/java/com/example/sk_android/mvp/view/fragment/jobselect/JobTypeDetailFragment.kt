package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.Job
import com.example.sk_android.mvp.model.jobselect.JobContainer
import com.example.sk_android.mvp.view.adapter.jobselect.IndustryListAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.JobDetailAdapter
import com.example.sk_android.mvp.view.adapter.jobselect.JobTypeDetailAdapter
import org.jetbrains.anko.support.v4.toast

class JobTypeDetailFragment : Fragment() {

    private var mContext: Context? = null
    var showItem: JobContainer?=null

    private lateinit var recycler:RecyclerView
    private lateinit var  adapter: JobTypeDetailAdapter
    private lateinit var  jobItemSelected:JobItemSelected


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
        mContext = activity
        jobItemSelected= activity as  JobItemSelected
        return fragmentView

    }

    fun createView(): View {
        var view= UI {
            linearLayout {
                relativeLayout  {
                    linearLayout  {


                        setOnClickListener(object :View.OnClickListener{
                            override fun onClick(v: View?) {
                            }
                        })
                        verticalLayout{
                            backgroundColorResource=R.color.grayF6
                            recycler= recyclerView {
                                backgroundColorResource=R.color.grayF6
                                overScrollMode = View.OVER_SCROLL_NEVER
                                setLayoutManager(LinearLayoutManager(this.getContext()))

                            }.lparams(width =matchParent){
                                leftMargin=dip(15)
                                rightMargin=dip(15)
                            }

                        }.lparams (matchParent, height = matchParent){
                        }



//                        verticalLayout{
//                            backgroundColorResource=R.color.originColor
//
//                            jobDetail=recyclerView {
//
//                                overScrollMode = View.OVER_SCROLL_NEVER
//                                setLayoutManager(LinearLayoutManager(context))
//
//
//
//                            }.lparams(width =matchParent){
//                                leftMargin=dip(15)
//                                rightMargin=dip(15)
//                            }
//
//                        }.lparams ( width=dip(155), height = matchParent){
//                        }



                    }.lparams(width =dip(200), height = matchParent){
                        alignParentRight()
                    }
                }.lparams(width =matchParent, height =matchParent){

                }
            }
        }.view





        var jobList:MutableList<Job> =  mutableListOf()
        if(showItem!=null){
            jobList=showItem!!.item
        }


        adapter=JobTypeDetailAdapter(recycler,  jobList) { item ,index->

            adapter.selectData(index)
            jobItemSelected.getSelectedJobItem(item)




        }
        recycler.setAdapter(adapter)






//        jobDetailAdapter=JobDetailAdapter(jobDetail,  mutableListOf()) { item ->
//
//
//            var mIntent= Intent();//没有任何参数（意图），只是用来传递数据
//            mIntent.putExtra("job",item)
//            mIntent.putExtra("jobId","")
//            activity!!.setResult(AppCompatActivity.RESULT_OK,mIntent);
//            activity!!.finish()
//            activity!!.overridePendingTransition(R.anim.right_out,R.anim.right_out)
//
//        }
//
//        jobDetail.adapter=jobDetailAdapter

//        if(showItem!=null && showItem!!.item.size!=0){
//            showJobDetail(showItem!!.item.get(0))
//        }



        return view
    }


     interface JobItemSelected {

        fun getSelectedJobItem(item:Job )
    }




}

