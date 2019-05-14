package com.example.sk_android.mvp.view.fragment.jobselect

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
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.model.jobselect.SelectedItemContainer
import com.example.sk_android.mvp.view.adapter.jobselect.RecruitInfoSelectBarMenuSelectItemAdapter

class RecruitInfoSelectBarMenuRequireFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuRequireSelect:RecruitInfoSelectBarMenuRequireSelect
    var resultMap:MutableMap<String, String> =  mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): RecruitInfoSelectBarMenuRequireFragment {
            val fragment = RecruitInfoSelectBarMenuRequireFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        recruitInfoSelectBarMenuRequireSelect =  activity as RecruitInfoSelectBarMenuRequireSelect
        return fragmentView
    }

    fun createView(): View {
        var list: MutableList<SelectedItemContainer> = mutableListOf()
        var count=0
        var p0=SelectedItemContainer("学歴",
            arrayOf("すべて","中学以下","専门学校/技术校","高校","専門大学","学部","修士","博士は、")
                .map{
                    count++
                    if(count!=2){
                        SelectedItem(it,false)
                    }else{
                        SelectedItem(it,true)
                    }
                }
                .toTypedArray()
        )

        count=0
        var p1=SelectedItemContainer("経験",
            arrayOf("すべて","現役生","1年以内に","1~3年","3~5年","5~10年","10年以上")
                .map{
                count++
                if(count!=2){
                    SelectedItem(it,false)
                }else{
                    SelectedItem(it,true)
                }
            }
                .toTypedArray()
        )

        count=0
        var p2=SelectedItemContainer("賃金（単選）",
            arrayOf("すべて","5000以下","5000~8000","5000~8000")
                .map{
                    count++
                    if(count!=2){
                        SelectedItem(it,false)
                    }else{
                        SelectedItem(it,true)
                    }
                }
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
                            setAdapter(RecruitInfoSelectBarMenuSelectItemAdapter(this,  list) { title, item ->
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

                                                recruitInfoSelectBarMenuRequireSelect.getRequireSelectedItems(null)
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
                                                recruitInfoSelectBarMenuRequireSelect.getRequireSelectedItems(resultMap)
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

    interface RecruitInfoSelectBarMenuRequireSelect{
        fun getRequireSelectedItems(map:MutableMap<String, String>?)
    }


}

