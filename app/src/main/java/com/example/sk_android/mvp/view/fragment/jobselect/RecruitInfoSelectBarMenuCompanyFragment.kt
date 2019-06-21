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

class RecruitInfoSelectBarMenuCompanyFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuCompanySelect:RecruitInfoSelectBarMenuCompanySelect

    private lateinit var selectedJson:JSONObject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(j: JSONObject): RecruitInfoSelectBarMenuCompanyFragment {
            val fragment = RecruitInfoSelectBarMenuCompanyFragment()
            val json=j
           fragment.selectedJson=JSONObject()

            var item1=JSONObject()
            item1.put("name","")
            item1.put("index",-1)
            fragment.selectedJson.put("融資段階",item1)

            var item2=JSONObject()
            item2.put("name","")
            item2.put("index",-1)
            fragment.selectedJson.put("人员规模",item2)

            var item3=JSONObject()
            item3.put("name","")
            item3.put("index",-1)
            fragment.selectedJson.put("業界",item3)


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
        recruitInfoSelectBarMenuCompanySelect =  activity as RecruitInfoSelectBarMenuCompanySelect
        return fragmentView
    }

    fun createView(): View {
        var list: MutableList<SelectedItemContainer> = mutableListOf()

        var count=-1
        var p0=SelectedItemContainer("融資段階",
            listOf("すべて","未融資","天使輪","a次","b次","c次","dホイール以上","すでに上場された","融資はいらない")
                .map{
                    count++
                    if(selectedJson.has("融資段階")  && selectedJson.getJSONObject("融資段階").getInt("index")==count ){
                        SelectedItem(it,true)
                    }else{
                        SelectedItem(it,false)
                    }

                }

       .        toTypedArray()

        )

        count=-1
        var p1=SelectedItemContainer("人员规模",
            listOf("すべて","0~20","20~99","100~499","500~999","10000人以上")
                .map {
                    count++
                    if(selectedJson.has("人员规模")  && selectedJson.getJSONObject("人员规模").getInt("index")==count ){
                        SelectedItem(it,true)
                    }else{
                        SelectedItem(it,false)
                    }

                }
                .toTypedArray()
        )
        count=-1
        var p2=SelectedItemContainer("業界",
            arrayOf("すべて","電子商取引","ゲーム","メディア","広告マーケティング","O2O")
                .map {

                    count++
                    if(selectedJson.has("業界")  && selectedJson.getJSONObject("業界").getInt("index")==count ){
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
                                        backgroundResource= R.drawable.radius_button_gray_e0
                                        setOnClickListener(object :View.OnClickListener{
                                            override fun onClick(v: View?) {
                                                var j=JSONObject()
                                                recruitInfoSelectBarMenuCompanySelect.getCompanySelectedItems(j)
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
                                                recruitInfoSelectBarMenuCompanySelect.getCompanySelectedItems(selectedJson)
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
        fun getCompanySelectedItems(json:JSONObject?)
    }


}

