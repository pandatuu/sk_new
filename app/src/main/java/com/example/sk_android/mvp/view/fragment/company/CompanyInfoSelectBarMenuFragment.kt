package com.example.sk_android.mvp.view.fragment.company

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.jobSelect.SelectedItem
import com.example.sk_android.mvp.view.adapter.jobSelect.CompanyInfoSelectBarMenuSelectItemAdapter

class CompanyInfoSelectBarMenuFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var selectBarMenuSelect:SelectBarMenuSelect
    var resultMap:MutableList<String> =  mutableListOf()
    var index=-1
    var list:MutableList<SelectedItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {


        fun newInstance(index:Int,list:MutableList<SelectedItem>): CompanyInfoSelectBarMenuFragment {
            val fragment = CompanyInfoSelectBarMenuFragment()
            fragment.index=index
            fragment.list=list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        selectBarMenuSelect =  activity as SelectBarMenuSelect
        return fragmentView
    }

    fun createView(): View {


        return UI {
            linearLayout {
                relativeLayout{
                    verticalLayout   {
                        backgroundColor=Color.WHITE
                        recyclerView{
                            backgroundColor=Color.BLUE
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))
                            setAdapter(CompanyInfoSelectBarMenuSelectItemAdapter(this,  list) {  item ->
//                                recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)
                                resultMap.add(item)
                                toast(item)
                            })
                        }.lparams {
                            height= wrapContent
                            width= matchParent
                        }


                        verticalLayout {
                                setOnClickListener(object :View.OnClickListener{
                                    override fun onClick(v: View?) {

                                    }

                                })
                                gravity=Gravity.CENTER_HORIZONTAL
                                relativeLayout{
                                    textView {
                                        text="リセット"
                                        gravity=Gravity.CENTER
                                        backgroundResource= R.drawable.radius_button_gray_cc
                                        setOnClickListener(object :View.OnClickListener{
                                            override fun onClick(v: View?) {

                                                selectBarMenuSelect.getSelectedItems(index,null)
                                            }

                                        })
                                    }.lparams {
                                        height=dip(44)
                                        width=dip(163)
                                        alignParentLeft()
                                        centerVertically()
                                    }

                                    textView {
                                        text="確定"
                                        gravity=Gravity.CENTER
                                        backgroundResource= R.drawable.radius_button_theme
                                        setOnClickListener(object :View.OnClickListener{
                                            override fun onClick(v: View?) {
                                                selectBarMenuSelect.getSelectedItems(index,resultMap)
                                            }

                                        })
                                    }.lparams {
                                        height=dip(44)
                                        width=dip(163)
                                        alignParentRight()
                                        centerVertically()
                                    }
                                }.lparams {
                                    height= matchParent
                                    width=dip(338)
                                }
                        }.lparams {
                            height=dip(72)
                            width= matchParent
                        }

                    }.lparams(width =matchParent, height = wrapContent){

                    }
                }.lparams {
                    width =matchParent
                    height =matchParent
                }
            }
        }.view
    }

    interface SelectBarMenuSelect{
        fun getSelectedItems(index:Int,list:MutableList< String>?)
    }


}

