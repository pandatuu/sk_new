package com.example.sk_android.mvp.view.fragment.jobSelect

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.*
import com.example.sk_android.mvp.view.activity.JobSelectActivity
import com.example.sk_android.mvp.view.adapter.*
import org.jetbrains.anko.support.v4.toast


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
        var s1=SelectedItem("すべて")
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
                            setAdapter(RecruitInfoSelectBarMenuPlaceAdapter(this,  list) { item ->
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

