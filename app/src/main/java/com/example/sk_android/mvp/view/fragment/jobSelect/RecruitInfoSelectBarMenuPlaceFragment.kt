package com.example.sk_android.mvp.view.fragment.jobSelect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobSelect.SelectedItem
import com.example.sk_android.mvp.view.adapter.*

class RecruitInfoSelectBarMenuPlaceFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuPlaceSelect:RecruitInfoSelectBarMenuPlaceSelect


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): RecruitInfoSelectBarMenuPlaceFragment {
            val fragment = RecruitInfoSelectBarMenuPlaceFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        recruitInfoSelectBarMenuPlaceSelect =  activity as RecruitInfoSelectBarMenuPlaceSelect
        return fragmentView
    }

    fun createView(): View {
        var s1=SelectedItem("東京")
        var s2= SelectedItem("大阪")
        var s3=SelectedItem("名古屋")
        var s4=SelectedItem("神戸",true)
        var s5=SelectedItem("横浜")
        var s6=SelectedItem("京都")

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
                                recruitInfoSelectBarMenuPlaceSelect.getPlaceSelected(item)
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

    interface RecruitInfoSelectBarMenuPlaceSelect{
        fun getPlaceSelected(item:SelectedItem)
    }


}

