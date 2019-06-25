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

class RecruitInfoSelectBarMenuPlaceFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuPlaceSelect:RecruitInfoSelectBarMenuPlaceSelect

    private lateinit var selectedString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(str:String): RecruitInfoSelectBarMenuPlaceFragment {
            val fragment = RecruitInfoSelectBarMenuPlaceFragment()
            fragment.selectedString=""
            if(str!=null  && !str.equals("")){
                fragment.selectedString=str
            }

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        recruitInfoSelectBarMenuPlaceSelect =  activity as RecruitInfoSelectBarMenuPlaceSelect
        return fragmentView
    }

    fun createView(): View {

        var list=
            listOf("東京","大阪","名古屋","神戸","横浜","京都")
                .map{
                    if(selectedString!=null && selectedString.equals(it) ){
                        SelectedItem(it,true)
                    }else{
                        SelectedItem(it,false)
                    }

                }
                . toTypedArray()


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

