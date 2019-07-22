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

class RecruitInfoSelectBarMenuEmploymentTypeFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var recruitInfoSelectBarMenuEmploymentTypeSelect:RecruitInfoSelectBarMenuEmploymentTypeSelect

    private lateinit var selectedJson:JSONObject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(j: JSONObject): RecruitInfoSelectBarMenuEmploymentTypeFragment {
            val fragment = RecruitInfoSelectBarMenuEmploymentTypeFragment()
            val json=j
           fragment.selectedJson=JSONObject()

            var item1=JSONObject()
            item1.put("name","")
            item1.put("value","")

            item1.put("index",-1)
            fragment.selectedJson.put("仕事のタイプ",item1)

//            var item2=JSONObject()
//            item2.put("name","")
//            item2.put("value","")
//            item2.put("index",-1)
//            fragment.selectedJson.put("海外採用",item2)



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
        recruitInfoSelectBarMenuEmploymentTypeSelect =  activity as RecruitInfoSelectBarMenuEmploymentTypeSelect
        return fragmentView
    }

    fun createView(): View {
        var list: MutableList<SelectedItemContainer> = mutableListOf()

        var count=-1
        var valueList1 = mutableListOf<String>("ALL","FULL_TIME","PART_TIME")
        var p0=SelectedItemContainer("仕事のタイプ",
            listOf("全て","フルタイム","パートタイム")
                .map{
                    count++
                    if(selectedJson.has("仕事のタイプ")  && selectedJson.getJSONObject("仕事のタイプ").getInt("index")==count ){
                        SelectedItem(it,true,valueList1.get(count))
                    }else{
                        SelectedItem(it,false,valueList1.get(count))
                    }

                }

       .        toMutableList()

        )
        list.add(p0)


//        count=-1
//        var p1=SelectedItemContainer("海外採用",
//            listOf("全て","は","いいえ")
//                .map {
//                    count++
//                    if(selectedJson.has("海外採用")  && selectedJson.getJSONObject("海外採用").getInt("index")==count ){
//                        SelectedItem(it,true)
//                    }else{
//                        SelectedItem(it,false)
//                    }
//
//                }
//                .toTypedArray()
//        )
//
//
//        list.add(p1)


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
                                selectItem.put("name",item.name)
                                selectItem.put("index",index)
                                selectItem.put("value",item.value)

                                selectedJson.put(title,selectItem)
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
                                                var j=JSONObject()
                                                recruitInfoSelectBarMenuEmploymentTypeSelect.getEmploymentTypeSelectedItems(j)
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
                                                recruitInfoSelectBarMenuEmploymentTypeSelect.getEmploymentTypeSelectedItems(selectedJson)
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

    interface RecruitInfoSelectBarMenuEmploymentTypeSelect{
        fun getEmploymentTypeSelectedItems(json:JSONObject?)
    }


}

