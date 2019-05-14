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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {


        fun newInstance(index:Int): CompanyInfoSelectBarMenuFragment {
            val fragment = CompanyInfoSelectBarMenuFragment()
            fragment.index=index
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        selectBarMenuSelect =  activity as SelectBarMenuSelect
        return fragmentView
    }

    fun createView(): View {
        var list: MutableList<SelectedItem> = mutableListOf()
        var item11:SelectedItem= SelectedItem("すべて",false)
        var item12:SelectedItem=SelectedItem("未融資",false)
        var item13:SelectedItem=SelectedItem("天使輪",false)
        var item14:SelectedItem=SelectedItem("a次",false)
        var item15:SelectedItem=SelectedItem("b次",true)
        var item16:SelectedItem=SelectedItem("c次",false)
        var item17:SelectedItem=SelectedItem("dホイール以上",false)
        var item18:SelectedItem=SelectedItem("すでに上場された",false)
        var item19:SelectedItem=SelectedItem("融資はいらない",false)


        list= mutableListOf(item11,item12,item13,item14,item15,item16,item17,item18,item19)


        var list2=
            listOf("すべて","0~20","20~99","100~499","500~999","10000人以上")
                .map { SelectedItem(it, false) }
                .toTypedArray()

        var list3=
            arrayOf("すべて","電子商取引","ゲーム","メディア","広告マーケティング","O2O")
                .map { SelectedItem(it, false) }
                .toTypedArray()


        return UI {
            linearLayout {
                relativeLayout{
                    verticalLayout   {
                        backgroundColor=Color.WHITE
                        recyclerView{
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))
                            setAdapter(CompanyInfoSelectBarMenuSelectItemAdapter(this,  list) {  item ->
//                                recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)
                                resultMap.add(item)
                                toast(item)
                            })
                        }.lparams {
                            height=0
                            weight=1f
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
                                        backgroundResource= R.drawable.radius_button_gray_e0
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
                                        backgroundResource= R.drawable.radius_button_blue
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

                    }.lparams(width =matchParent, height =dip(462)){

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

