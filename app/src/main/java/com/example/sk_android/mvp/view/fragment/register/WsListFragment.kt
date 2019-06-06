package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toolbar
import com.example.sk_android.R
import com.example.sk_android.mvp.view.adapter.register.PersonAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import java.util.*
import kotlin.collections.ArrayList


class WsListFragment:Fragment() {
    var mmId = 100
    var TrpToolbar: Toolbar?=null
    private var mContext: Context? = null
    lateinit var cancelTool:CancelTool
    lateinit var myList: ListView
    lateinit var mData:ArrayList<String>
    lateinit var personAdapter:PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): WsListFragment {
            return WsListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        mContext = activity
        cancelTool = activity as CancelTool

        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initView()
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    private fun createView():View{
        return UI {
            linearLayout{
                verticalLayout {
                    gravity = Gravity.BOTTOM

                    verticalLayout {
                        backgroundResource = R.drawable.list_border

                        textView {
                            textResource = R.string.jobSearchStatus
                            gravity = Gravity.CENTER
                            textSize = 14f
                            textColorResource = R.color.gray9B
                        }.lparams(width = matchParent,height = wrapContent){
                            topMargin = dip(12)
                        }

                        view {
                            backgroundColorResource = R.color.grayE6
                        }.lparams(width = matchParent, height = dip(1)) {
                            topMargin = dip(13)
                            leftMargin = dip(10)
                            rightMargin = dip(10)
                        }
                        myList = listView {

                            id = mmId

                        }.lparams(width = matchParent,height = dip(232)){
                            leftMargin = dip(10)
                            rightMargin = dip(10)
                        }
                    }.lparams(width = matchParent,height = dip(277)){
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                    }



                    button {
                        backgroundResource = R.drawable.list_border
                        textResource = R.string.jobStatuVerify
                        gravity = Gravity.CENTER
                        textSize = 19f
                        textColorResource = R.color.blue007AFF

                        setOnClickListener(object : View.OnClickListener{
                            override fun onClick(v: View?) {
                                cancelTool.cancelList()
                            }
                        })
                    }.lparams(width = matchParent,height = dip(58)){
                        topMargin = dip(8)
                        bottomMargin = dip(10)
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                    }
                }.lparams(width = matchParent,height = matchParent){

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

    public interface CancelTool {

        fun cancelList()
    }

    private fun initView() {
        mContext = activity
        myList = this.find(mmId)
        mData = ArrayList<String>(Arrays.asList(this.getString(R.string.IiStatusOne),this.getString(R.string.IiStatusTwo), this.getString(R.string.IiStatusThree), this.getString(R.string.IiStatusFour)))

        personAdapter = PersonAdapter(mData, mContext)
        myList.setAdapter(personAdapter)

        myList.setOnItemClickListener(object: AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                toast(mData.get(position))
            }
        });
    }

}