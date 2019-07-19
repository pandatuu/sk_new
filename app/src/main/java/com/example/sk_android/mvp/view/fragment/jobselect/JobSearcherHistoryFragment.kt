package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.view.adapter.jobselect.JobSearchHistoryAdapter
import org.json.JSONArray

class JobSearcherHistoryFragment : Fragment() {

    private lateinit var histroyList: MutableList<String>

    private var mContext: Context? = null
    private lateinit var sendMessage: HistoryText

    var theAdapter: JobSearchHistoryAdapter? = null
    var recycler: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(list: MutableList<String>): JobSearcherHistoryFragment {
            val fragment = JobSearcherHistoryFragment()
            fragment.histroyList = list
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        sendMessage = activity as HistoryText
        return fragmentView
    }

    fun createView(): View {
//        var list: MutableList<String> = mutableListOf("電子商取引","ソフトウエア","メディア","販売促進","データ分析","移动インターネット","ソフトウエア","インターネット")

        var view= UI {
            linearLayout {
                verticalLayout {

                    relativeLayout {
                        textView {
                            text = "検索履歴"
                            textSize = 15f
                            textColorResource = R.color.normalTextColor
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(height = matchParent) {
                            alignParentLeft()
                        }

                        imageView {
                            imageResource = R.mipmap.ico_delete_light
                            setOnClickListener(object : View.OnClickListener {
                                override fun onClick(v: View?) {
                                    sendMessage.clearHistroy()
                                    clearStorageData()
                                }
                            })
                        }.lparams {
                            alignParentRight()
                            alignParentBottom()
                            width = dip(15)
                            height = dip(18)
                        }
                    }.lparams(width = matchParent, height = dip(21)) {
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                        topMargin = dip(10)
                    }

                    recycler = recyclerView {
                        overScrollMode = View.OVER_SCROLL_NEVER
                        setLayoutManager(LinearLayoutManager(this.getContext()))


                    }.lparams {
                        rightMargin = dip(15)
                    }

                }.lparams(width = matchParent, height = wrapContent) {

                }
            }
        }.view

        resetListData(histroyList)


        return view
    }


    fun clearStorageData() {
        var ms = PreferenceManager.getDefaultSharedPreferences(activity)
        var mEditor: SharedPreferences.Editor = ms.edit()
        mEditor.putString("historySearch", "[]")
        mEditor.commit()
    }


    fun resetListData(list: MutableList<String>) {
        //倒叙展示
        var showList: MutableList<String> = mutableListOf()


        if (list != null && list.size > 0)
            for (i in 0..list.size - 1) {
                showList.add(list.get(list.size - 1 - i))
            }


        if (theAdapter != null) {
            theAdapter!!.resetData(showList)
        } else {
            theAdapter = JobSearchHistoryAdapter(recycler!!, showList) { item ->
                sendMessage.sendHistoryText(item)
            }
            recycler!!.setAdapter(theAdapter)
        }
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

        fun sendHistoryText(msg: String)


        fun clearHistroy()
    }

}

