package com.example.sk_android.mvp.view.fragment.privacyset

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyPicker
import com.example.sk_android.custom.layout.Pickers
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.ArrayList


class RollChooseFrag : Fragment() {

    private var pickerScrollView: MyPicker? = null
    private var list: ArrayList<String>? = null
    private var mText : String = ""
    private var name: Array<String>? = null
    lateinit var cancelBtn : Toolbar
    lateinit var confirmBtn : Toolbar
    lateinit var rollchoose : RollToolClick

    companion object {
        fun newInstance(text : String): RollChooseFrag {
            val fragment = RollChooseFrag()
            fragment.mText = text
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rollchoose = activity as RollToolClick
        val fragmentView = inflater.inflate(R.layout.demoactivity, container, false)
        pickerScrollView = fragmentView.findViewById(R.id.picker_view) as MyPicker
        cancelBtn = fragmentView.findViewById(R.id.tool1) as Toolbar
        confirmBtn = fragmentView.findViewById(R.id.tool2) as Toolbar
        initData()
        cancelBtn.onClick {
            rollchoose.cancelClick()
        }

        var text = ""
        pickerScrollView!!.setOnSelectListener(object : MyPicker.onSelectListener{
            override fun onSelect(pickers: String) {
                if(pickers!=null)
                    text = pickers
            }
        })
        confirmBtn.onClick {
            rollchoose.confirmClick(text)
        }
        return fragmentView
    }
    private fun initData() {
        list = ArrayList<String>()
        name = arrayOf("ホワイトリスト有効", "ブラックリストは有効", "完全に非公開", "完全に公開")
        for (i in name!!) {
            list!!.add(i)
        }
        // 设置数据，默认选择第一条
        pickerScrollView!!.setData(list!!)
        pickerScrollView!!.setSelected(mText)
    }
    interface RollToolClick{
        fun cancelClick()
        fun confirmClick(text : String)
    }
}