package com.example.sk_android.mvp.view.fragment.jobselect

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
import com.example.sk_android.mvp.view.adapter.jobselect.JobInfoDetailSkillLabelAdapter
import org.json.JSONArray

class JobInfoDetailSkillLabelFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var jobInfoDetailSkillLabelSelect:JobInfoDetailSkillLabelSelect
    var list:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): JobInfoDetailSkillLabelFragment {
            val fragment = JobInfoDetailSkillLabelFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        jobInfoDetailSkillLabelSelect =  activity as JobInfoDetailSkillLabelSelect
        return fragmentView
    }

    fun createView(): View {
        var intent=activity!!.intent
        var skill=intent.getStringExtra("skill")


        if(skill!=null && !"".equals(skill)){
            var array:JSONArray=JSONArray(skill)
            for(i in 0..array.length()-1){
                list.add(array.get(i).toString())
            }

        }
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

                        var showList:MutableList<String> = mutableListOf()
                        if(list!=null){
                            showList=list!!
                        }
                        println(showList)
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

