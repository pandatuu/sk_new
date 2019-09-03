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
    var theAdapter:RecruitInfoSelectBarMenuSelectItemAdapter?=null

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
            item1.put("value","")

            fragment.selectedJson.put("学歴",item1)

            var item2=JSONObject()
            item2.put("name","")
            item2.put("index",-1)
            item2.put("value","")

            fragment.selectedJson.put("経験",item2)



            var item3=JSONObject()
            item3.put("name","")
            item3.put("index",-1)
            item3.put("value","")

            fragment.selectedJson.put("薪资类型",item3)


            var item4=JSONObject()
            item4.put("name","")
            item4.put("index",-1)
            item4.put("value","")

            fragment.selectedJson.put("希望月収",item4)




            var iterator=json.keys().iterator()
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
        var valueList1 = mutableListOf<String>("ALL","MIDDLE_SCHOOL","HIGH_SCHOOL","SHORT_TERM_COLLEGE","BACHELOR","MASTER","DOCTOR")
        var showList1=SelectedItemContainer("学歴",
            arrayOf("全て","中学卒業及び以下","高卒","専門卒・短大卒","大卒","修士","博士")
                .map{
                    count++

                    if(selectedJson.has("学歴")  && selectedJson.getJSONObject("学歴").getInt("index")==count ){
                        SelectedItem(it,true,valueList1.get(count))
                    }else{
                        SelectedItem(it,false,valueList1.get(count))
                    }
                }
                .toMutableList()
        )

        count=-1
        var valueList2 = mutableListOf<String>("ALL","0","1","3","5","10")
        var showList2=SelectedItemContainer("経験",
            arrayOf("全て","新卒","1年未満","3年未満","5年未満","10年未満")
                .map{
                    count++
                    if(selectedJson.has("経験")  && selectedJson.getJSONObject("経験").getInt("index")==count ){
                        SelectedItem(it,true,valueList2.get(count))
                    }else{
                        SelectedItem(it,false,valueList2.get(count))
                    }
            }
                .toMutableList()
        )


        //改动 薪资类型和希望月収的title统一被改成希望月収
        count=-1
        var seletedIndex=-1
        var valueList3 = mutableListOf<String>("HOURLY","DAILY","MONTHLY","YEARLY")
        var showList3=SelectedItemContainer("希望月収",
            arrayOf("時給","日給","月給","年収")
                .map{
                    count++
                    if(selectedJson.has("薪资类型")  && selectedJson.getJSONObject("薪资类型").getInt("index")==count ){
                        seletedIndex=count
                        SelectedItem(it,true,valueList3.get(count))
                    }else{
                        SelectedItem(it,false,valueList3.get(count))
                    }
                }
                .toMutableList()
        )

        //改动 希望月収的title被去掉
//        count=-1
//        var valueList4 = mutableListOf<String>("ALL","0-100000","100000-200000","200000-300000","300000-500000","500000-0")
//        var showList4=SelectedItemContainer("",
//            arrayOf("全て","10万円以下","10万円~20万円","20万円~30万円","30万円~50万円","50万円以上")
//                .map{
//                    count++
//                    if(selectedJson.has("希望月収")  && selectedJson.getJSONObject("希望月収").getInt("index")==count ){
//                        SelectedItem(it,true,valueList4.get(count))
//                    }else{
//                        SelectedItem(it,false,valueList4.get(count))
//                    }
//                }
//                .toMutableList()
//        )

        var showList4=changeSalaryShow(seletedIndex,false)



        list.add(showList1)
        list.add(showList2)
        list.add(showList3)
        list.add(showList4)



        return UI {
            linearLayout {
                relativeLayout{

                    verticalLayout   {
                        backgroundColor=Color.WHITE
                        recyclerView{
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))


                            theAdapter=RecruitInfoSelectBarMenuSelectItemAdapter(this,  list) { title, item,index ->
                                //                                recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)

                                //改动 薪资类型和希望月収的title统一被改成希望月収
                                //改动 希望月収的title被去掉
                                var theTitle=title
                                if(title.equals("")){
                                    theTitle="希望月収"
                                }else if(title.equals("希望月収")){
                                    theTitle="薪资类型"
                                }


                                var selectItem=JSONObject()
                                selectItem.put("name",item.name)
                                selectItem.put("index",index)
                                selectItem.put("value",item.value)
                                selectedJson.put(theTitle,selectItem)


                                if(theTitle.equals("薪资类型")){
                                    //重置薪资的选项
                                    var salaryItem=JSONObject()
                                    salaryItem.put("name","")
                                    salaryItem.put("index",-1)
                                    salaryItem.put("value","")
                                    selectedJson.put("希望月収",salaryItem)
                                    //刷新数据
                                    theAdapter?.updateData(refreshSelectedData())

                                }

                            }

                            setAdapter(theAdapter)

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



    fun refreshSelectedData(): MutableList<SelectedItemContainer>{

        var list: MutableList<SelectedItemContainer> = mutableListOf()


        var count=-1
        var valueList1 = mutableListOf<String>("ALL"," MIDDLE_SCHOOL","HIGH_SCHOOL","SHORT_TERM_COLLEGE","BACHELOR","MASTER","DOCTOR")
        var showList1=SelectedItemContainer("学歴",
            arrayOf("全て","中学卒業及び以下","高卒","専門卒・短大卒","大卒","修士","博士")
                .map{
                    count++

                    if(selectedJson.has("学歴")  && selectedJson.getJSONObject("学歴").getInt("index")==count ){
                        SelectedItem(it,true,valueList1.get(count))
                    }else{
                        SelectedItem(it,false,valueList1.get(count))
                    }
                }
                .toMutableList()
        )

        count=-1
        var valueList2 = mutableListOf<String>("ALL"," 0","1","3","5","10")
        var showList2=SelectedItemContainer("経験",
            arrayOf("全て","新卒","1年未満","3年未満","5年未満","10年未満")
                .map{
                    count++
                    if(selectedJson.has("経験")  && selectedJson.getJSONObject("経験").getInt("index")==count ){
                        SelectedItem(it,true,valueList2.get(count))
                    }else{
                        SelectedItem(it,false,valueList2.get(count))
                    }
                }
                .toMutableList()
        )


        //改动 薪资类型和希望月収的title统一被改成希望月収
        count=-1
        var seletedIndex=-1
        var valueList3 = mutableListOf<String>("HOURLY","DAILY","MONTHLY","YEARLY")
        var showList3=SelectedItemContainer("希望月収",
            arrayOf("時給","日給","月給","年収")
                .map{
                    count++
                    if(selectedJson.has("薪资类型")  && selectedJson.getJSONObject("薪资类型").getInt("index")==count ){
                        seletedIndex=count
                        SelectedItem(it,true,valueList3.get(count))
                    }else{
                        SelectedItem(it,false,valueList3.get(count))
                    }
                }
                .toMutableList()
        )


        var showList4=changeSalaryShow(seletedIndex,true)



        list.add(showList1)
        list.add(showList2)
        list.add(showList3)
        list.add(showList4)

        return  list

    }


    fun changeSalaryShow(type:Int,allReset:Boolean):SelectedItemContainer{


        var valueForHour= mutableListOf<String>("ALL","100-200","200-300","300-400","400-500","500-0")
        var stringForHour=arrayOf("全て","100円~200円","200円~300円","300円~400円","400円~500円","500円以上")


        var valueForDay= mutableListOf<String>("ALL","800-1600","1600-2400","2400-3200","3200-4000","4000-0")
        var stringForDay=arrayOf("全て","800円~1600円","1600円~2400円","2400円~3200円","3200円~4000円","4000円以上")


        var valueForMonth= mutableListOf<String>("ALL","0-100000","100000-200000","200000-300000","300000-500000","500000-0")
        var stringForMonth=arrayOf("全て","10万円以下","10万円~20万円","20万円~30万円","30万円~50万円","50万円以上")

        var valueForYear= mutableListOf<String>("ALL","0-1300000","1300000-2600000","2600000-3900000","3900000-4500000","4500000-0")
        var stringForYear=arrayOf("全て","130万円以下","130万円~260万円","260万円~390万円","390万円~450万円","450万円以上")




        var count=-1
        var valueList4 = mutableListOf<String>()
        var stringList4=arrayOf<String>()

        if(type==0){
            valueList4=valueForHour
            stringList4=stringForHour
        }else  if(type==1){
            valueList4=valueForDay
            stringList4=stringForDay
        }else  if(type==2 || type==-1){
            valueList4=valueForMonth
            stringList4=stringForMonth
        }else  if(type==3){
            valueList4=valueForYear
            stringList4=stringForYear
        }

        var showList4=SelectedItemContainer("",
            stringList4
                .map{
                    count++
                    //若是薪资类型的选项被改变，薪资的选择则会刷新
                    if(allReset){
                        SelectedItem(it,false,valueList4.get(count))
                    }else{
                        //初始回显
                        if(selectedJson.has("薪资类型")  && selectedJson.getJSONObject("薪资类型").getInt("index")==type  &&  selectedJson.has("希望月収")  && selectedJson.getJSONObject("希望月収").getInt("index")==count  ){
                            SelectedItem(it,true,valueList4.get(count))
                        } else if(selectedJson.has("希望月収")  && selectedJson.getJSONObject("希望月収").getInt("index")==count ){
                            SelectedItem(it,true,valueList4.get(count))
                        }else{
                            SelectedItem(it,false,valueList4.get(count))
                        }
                    }

                }
                .toMutableList()
        )

        return showList4

    }



}

