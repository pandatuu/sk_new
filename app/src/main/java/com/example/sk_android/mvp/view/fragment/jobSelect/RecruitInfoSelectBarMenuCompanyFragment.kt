package com.example.sk_android.mvp.view.fragment.jobSelect

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
import com.example.sk_android.mvp.model.jobSelect.SelectedItemContainer
import com.example.sk_android.mvp.view.adapter.*

class RecruitInfoSelectBarMenuCompanyFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuCompanySelect:RecruitInfoSelectBarMenuCompanySelect
    var resultMap:MutableMap<String, String> =  mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): RecruitInfoSelectBarMenuCompanyFragment {
            val fragment = RecruitInfoSelectBarMenuCompanyFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        recruitInfoSelectBarMenuCompanySelect =  activity as RecruitInfoSelectBarMenuCompanySelect
        return fragmentView
    }

    fun createView(): View {
        var list: MutableList<SelectedItemContainer> = mutableListOf()
        var item11:SelectedItem= SelectedItem("すべて",false)
        var item12:SelectedItem=SelectedItem("未融資",false)
        var item13:SelectedItem=SelectedItem("天使輪",false)
        var item14:SelectedItem=SelectedItem("a次",false)
        var item15:SelectedItem=SelectedItem("b次",true)
        var item16:SelectedItem=SelectedItem("c次",false)
        var item17:SelectedItem=SelectedItem("dホイール以上",false)
        var item18:SelectedItem=SelectedItem("すでに上場された",false)
        var item19:SelectedItem=SelectedItem("融資はいらない",false)


        var p0=SelectedItemContainer("融資段階",

            arrayOf(item11,item12,item13,item14,item15,item16,item17,item18,item19))
        var p1=SelectedItemContainer("人员规模",
            listOf("すべて","0~20","20~99","100~499","500~999","10000人以上")
                .map { SelectedItem(it, false) }
                .toTypedArray()
        )

        var p2=SelectedItemContainer("業界",
            arrayOf("すべて","電子商取引","ゲーム","メディア","広告マーケティング","O2O")
                .map { SelectedItem(it, false) }
                .toTypedArray()
        )

        list.add(p0)
        list.add(p1)
        list.add(p2)

        return UI {
            linearLayout {
                relativeLayout{
                    verticalLayout   {
                        backgroundColor=Color.WHITE
                        recyclerView{
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))
                            setAdapter(RecruitInfoSelectBarMenuSelectItemAdapter(this,  list) { title,item ->
//                                recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)
                                resultMap.put(title,item)
                                toast(title+"--"+item)
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

                                                recruitInfoSelectBarMenuCompanySelect.getCompanySelectedItems(null)
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
                                                recruitInfoSelectBarMenuCompanySelect.getCompanySelectedItems(resultMap)
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

    interface RecruitInfoSelectBarMenuCompanySelect{
        fun getCompanySelectedItems(map:MutableMap<String, String>?)
    }


}

