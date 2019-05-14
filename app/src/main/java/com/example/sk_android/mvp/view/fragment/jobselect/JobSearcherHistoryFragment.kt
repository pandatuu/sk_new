package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.view.adapter.jobselect.JobSearchHistoryAdapter

class JobSearcherHistoryFragment : Fragment() {

    private lateinit var histroyList:Array<String>

    private var mContext: Context? = null
    private lateinit var sendMessage:HistoryText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(list:Array<String>): JobSearcherHistoryFragment {
            val fragment = JobSearcherHistoryFragment()
            fragment.histroyList=list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        sendMessage =  activity as HistoryText
        return fragmentView
    }

    fun createView(): View {
//        var list: MutableList<String> = mutableListOf("電子商取引","ソフトウエア","メディア","販売促進","データ分析","移动インターネット","ソフトウエア","インターネット")

        return UI {
            linearLayout {
                verticalLayout  {

                    relativeLayout {
                        textView {
                            text="历史搜索"
                            textSize=15f
                            textColorResource=R.color.normalTextColor
                            gravity=Gravity.CENTER_VERTICAL
                        }.lparams(height=matchParent){
                            alignParentLeft()
                        }

                        imageView {
                            imageResource=R.mipmap.icon_delete_search
                            setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    sendMessage.clearHistroy()
                                }
                            })
                        }.lparams {
                            alignParentRight()
                            alignParentBottom()
                            width=dip(15)
                            height=dip(16)
                        }
                    }.lparams(width= matchParent, height = dip(21)){
                        leftMargin=dip(15)
                        rightMargin=dip(15)
                        topMargin=dip(10)
                    }

                    recyclerView{
                        overScrollMode = View.OVER_SCROLL_NEVER
                        setLayoutManager(LinearLayoutManager(this.getContext()))
                        var showList:Array<String> =  arrayOf<String>()
                        if(histroyList!=null){
                            showList= histroyList as Array<String>
                        }
                        setAdapter(JobSearchHistoryAdapter(this,  showList) { item ->
                            sendMessage.sendHistoryText(item)
                        })
                    }.lparams {
                        rightMargin=dip(15)
                    }

                }.lparams(width = matchParent, height = wrapContent){

                }
            }
        }.view
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }

    interface HistoryText {

        fun sendHistoryText(msg:String )


        fun clearHistroy()
    }

}

