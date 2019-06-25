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
import org.json.JSONObject

class RecruitInfoSelectBarMenuRequireFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuRequireSelect:RecruitInfoSelectBarMenuRequireSelect


    private lateinit var selectedJson:JSONObject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(j:JSONObject): RecruitInfoSelectBarMenuRequireFragment {
            val fragment = RecruitInfoSelectBarMenuRequireFragment()
            val json=j
            fragment.selectedJson=JSONObject()

            var item1=JSONObject()
            item1.put("name","")
            item1.put("index",-1)
            fragment.selectedJson.put("学歴",item1)

            var item2=JSONObject()
            item2.put("name","")
            item2.put("index",-1)
            fragment.selectedJson.put("経験",item2)

            var item3=JSONObject()
            item3.put("name","")
            item3.put("index",-1)
            fragment.selectedJson.put("希望月収",item3)


            var iterator=json!!.keys().iterator()
            while(iterator.hasNext()){
                var key=iterator.next()
                fragment.selectedJson.put(key,json.getJSONObject(key))
            }

            println(fragment.selectedJson)
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

        var count=-1
        var p0=SelectedItemContainer("学歴",
            arrayOf("全て","中学卒業及び以下","高校卒業","専門学校卒業","大学卒業","修士","博士")
                .map{
                    count++

                    if(selectedJson.has("学歴")  && selectedJson.getJSONObject("学歴").getInt("index")==count ){
                        SelectedItem(it,true)
                    }else{
                        SelectedItem(it,false)
                    }
                }
                .toTypedArray()
        )

        count=-1
        var p1=SelectedItemContainer("経験",
            arrayOf("全て","卒業生","1年以内","1~3年","3~5年","5~10年","10年以上")
                .map{
                    count++
                    if(selectedJson.has("経験")  && selectedJson.getJSONObject("経験").getInt("index")==count ){
                        SelectedItem(it,true)
                    }else{
                        SelectedItem(it,false)
                    }
            }
                .toTypedArray()
        )

        count=-1
        var p2=SelectedItemContainer("希望月収",
            arrayOf("时薪","日薪","月薪","年薪","全て","30万以下","30万-40万","40万-50万","50万-60万","60万-70万")
                .map{
                    count++
                    if(selectedJson.has("希望月収")  && selectedJson.getJSONObject("希望月収").getInt("index")==count ){
                        SelectedItem(it,true)
                    }else{
                        SelectedItem(it,false)
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
                            setAdapter(RecruitInfoSelectBarMenuSelectItemAdapter(this,  list) { title, item,index ->
//                                recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)

                                var selectItem=JSONObject()
                                selectItem.put("name",item)
                                selectItem.put("index",index)

                                selectedJson.put(title,selectItem)

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
                                        backgroundResource= R.drawable.radius_button_gray_cc
                                        setOnClickListener(object :View.OnClickListener{
                                            override fun onClick(v: View?) {
                                                var j=JSONObject()
                                                recruitInfoSelectBarMenuRequireSelect.getRequireSelectedItems(j)
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

                                                recruitInfoSelectBarMenuRequireSelect.getRequireSelectedItems(selectedJson)
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
        fun getRequireSelectedItems(json:JSONObject?)
    }


}

