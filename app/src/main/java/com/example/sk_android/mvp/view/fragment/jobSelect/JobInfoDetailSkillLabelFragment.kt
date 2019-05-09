package com.example.sk_android.mvp.view.fragment.jobSelect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.view.adapter.jobSelect.JobInfoDetailSkillLabelAdapter

class JobInfoDetailSkillLabelFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var jobInfoDetailSkillLabelSelect:JobInfoDetailSkillLabelSelect
    var list:Array<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(list: Array<String>): JobInfoDetailSkillLabelFragment {
            val fragment = JobInfoDetailSkillLabelFragment()
            fragment.list=list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        jobInfoDetailSkillLabelSelect =  activity as JobInfoDetailSkillLabelSelect
        return fragmentView
    }

    fun createView(): View {
        return UI {
            linearLayout {
                verticalLayout {
                    textView {
                        text="スキル"
                        setTypeface(Typeface.DEFAULT_BOLD)
                        textSize=16f
                        textColorResource=R.color.normalTextColor
                    }.lparams {
                        leftMargin=dip(15)
                    }

                    recyclerView {

                        overScrollMode = View.OVER_SCROLL_NEVER
                        setLayoutManager(LinearLayoutManager(this.getContext()))

                        var showList:Array<String> =  arrayOf<String>()
                        if(list!=null){
                            showList=list!!
                        }

                        setAdapter(JobInfoDetailSkillLabelAdapter(this,  showList) { item ->
                            jobInfoDetailSkillLabelSelect.getSelectedLabel(item)
                        })
                    }.lparams {
                        width= matchParent
                        height= wrapContent
                    }


                }.lparams() {
                    width = matchParent
                    height = wrapContent
                    topMargin=dip(15)

                    rightMargin=dip(15)
                }
            }
        }.view

    }

    interface JobInfoDetailSkillLabelSelect{
        fun getSelectedLabel(str:String)
    }


}

