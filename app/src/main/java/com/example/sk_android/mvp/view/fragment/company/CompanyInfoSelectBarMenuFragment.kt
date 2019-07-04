package com.example.sk_android.mvp.view.fragment.company

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
import com.example.sk_android.mvp.view.adapter.jobselect.CompanyInfoSelectBarMenuSelectItemAdapter

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


        fun newInstance(index:Int,selectedItems:MutableList< String>): CompanyInfoSelectBarMenuFragment {
            val fragment = CompanyInfoSelectBarMenuFragment()
            fragment.index=index
            var list:MutableList<SelectedItem> = mutableListOf()
            var count = -1
            if(index==0){

                var valueList1 = mutableListOf<String>("ALL","TSE_1_APP","NONE")
                list=
                    listOf("全て",  "上場企業", "未上場企業")
                        .map {
                            count++
                            var flag=false
                            for(item in selectedItems){
                                if(item.equals(it)){
                                    flag=true
                                    break
                                }
                            }
                            if(flag){
                                fragment.resultMap.add(it)
                                SelectedItem(it, true,valueList1.get(count))
                            }else{
                                SelectedItem(it, false,valueList1.get(count))
                            }
                        }.toMutableList()




            }else  if (index==1){

                var valueList2 = mutableListOf<String>("ALL","TINY","SMALL","MEDIUM","BIG","HUGE")
                list=
                    mutableListOf("全部","0~20人","20~99人","100~499人","500~999人","10000人以上")
                        .map {
                            count++
                            var flag=false
                            for(item in selectedItems){
                                if(item.equals(it)){
                                    flag=true
                                    break
                                }
                            }
                            if(flag){
                                fragment.resultMap.add(it)
                                SelectedItem(it, true,valueList2.get(count))
                            }else{
                                SelectedItem(it, false,valueList2.get(count))
                            }
                        }
                        .toMutableList()
            }else  if (index==2){
                list=
                    mutableListOf("全部","電子商取引","ゲーム","メディア","販売促進","データ分析","O2O","其它")
                        .map {
                            var flag=false
                            for(item in selectedItems){
                                if(item.equals(it)){
                                    flag=true
                                    break
                                }
                            }
                            if(flag){
                                fragment.resultMap.add(it)
                                SelectedItem(it, true,"")
                            }else{
                                SelectedItem(it, false,"")
                            }}
                        .toMutableList()

            }else  if (index==3){
                var valueList3 = mutableListOf<String>("ALL","REGULAR","CONTRACT","DISPATCH","SHORT_TERM","OTHER")

                list=
                    mutableListOf("全て", "正社員", "契約社員", "派遣社員","有期社員","その他")
                        .map {
                            var flag=false
                            for(item in selectedItems){
                                if(item.equals(it)){
                                    flag=true
                                    break
                                }
                            }
                            if(flag){
                                fragment.resultMap.add(it)
                                SelectedItem(it, true,valueList3.get(count))
                            }else{
                                SelectedItem(it, false,valueList3.get(count))
                            }
                        }
                        .toMutableList()
            }

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
                        backgroundResource=R.drawable.border_top_97

                        recyclerView{
                            overScrollMode = View.OVER_SCROLL_NEVER
                            setLayoutManager(LinearLayoutManager(this.getContext()))
                            setAdapter(CompanyInfoSelectBarMenuSelectItemAdapter(this,  list, {  item ->
                                //recruitInfoSelectBarMenuCompanySelect.getPlaceSelected(item)
                                if(!resultMap.contains(item)){
                                    resultMap.add(item)
                                }else{
                                    resultMap.remove(item)
                                }
                                toast(item)
                            }))
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
                                                var list:MutableList< String> = mutableListOf()
                                                selectBarMenuSelect.getSelectedItems(index,list)
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
        fun getSelectedItems(index:Int,list:MutableList< String>)
    }


}

