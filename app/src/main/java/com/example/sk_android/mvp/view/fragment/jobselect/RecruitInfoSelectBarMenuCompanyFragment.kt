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
            fragment.selectedJson.put("会社規模",item2)

            var item3=JSONObject()
            item3.put("name","")
            item3.put("index",-1)
            fragment.selectedJson.put("業種",item3)

            var item4=JSONObject()
            item4.put("name","")
            item4.put("index",-1)
            fragment.selectedJson.put("求人手段",item4)



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
            listOf("全て","未融資","エンジェルラウンド","ラウンドA","ラウンドB","ラウンドC","ラウンドD及び以上","上場企業","融资不要")
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
        var p1=SelectedItemContainer("会社規模",
            listOf("全て","0~20人","20~99人","100~499人","500~999人","1000~9999人")
                .map {
                    count++
                    if(selectedJson.has("会社規模")  && selectedJson.getJSONObject("会社規模").getInt("index")==count ){
                        SelectedItem(it,true)
                    }else{
                        SelectedItem(it,false)
                    }

                }
                .toTypedArray()
        )
        count=-1
        var p2=SelectedItemContainer("業種",
            arrayOf("全て","電子商取引","ゲーム","メディア","販売促進","データ分析")
                .map {

                    count++
                    if(selectedJson.has("業種")  && selectedJson.getJSONObject("業種").getInt("index")==count ){
                        SelectedItem(it,true)
                    }else{
                        SelectedItem(it,false)
                    }
                }
                .toTypedArray()
        )


        count=-1
        var p3=SelectedItemContainer("求人手段",
            arrayOf("全て","直接雇用","派遣会社経由","ヘッドハンティング会社経由")
                .map {
                    count++
                    if(selectedJson.has("求人手段")  && selectedJson.getJSONObject("求人手段").getInt("index")==count ){
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
        list.add(p3)

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
                                        backgroundResource= R.drawable.radius_button_theme
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

