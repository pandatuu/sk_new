package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.adapter.jobselect.jobSelect.RecruitInfoSelectBarMenuSelectListAdapter


class RecruitInfoSelectBarMenuOtherFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuOtherSelect:RecruitInfoSelectBarMenuOtherSelect


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): RecruitInfoSelectBarMenuOtherFragment {
            val fragment = RecruitInfoSelectBarMenuOtherFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        recruitInfoSelectBarMenuOtherSelect =  activity as RecruitInfoSelectBarMenuOtherSelect
        return fragmentView
    }

    fun createView(): View {
        var s1= SelectedItem("すべて")
        var s2=SelectedItem("パートタイム")
        var s3=SelectedItem("現役生")
        var s4=SelectedItem("社会的求人",true)
        var s5=SelectedItem("海外採用")
        var s6=SelectedItem("他の")

        var list:Array<SelectedItem> = arrayOf<SelectedItem>(s1,s2,s3,s4,s5,s6)


        return UI {
            linearLayout {
                verticalLayout {
                    relativeLayout  {
                        backgroundColor=Color.WHITE
                        recyclerView{
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))
                            setAdapter(RecruitInfoSelectBarMenuSelectListAdapter(this,  list) { item ->
                                recruitInfoSelectBarMenuOtherSelect.getOtherSelected(item)
                            })
                        }.lparams {
                            leftMargin=dip(15)
                            rightMargin=dip(15)
                        }
                    }.lparams(width =matchParent, height =dip(288)){

                    }
                }.lparams {
                    width =matchParent
                    height =matchParent
                }
            }
        }.view
    }

    interface RecruitInfoSelectBarMenuOtherSelect{
        fun getOtherSelected(item:SelectedItem)
    }


}

