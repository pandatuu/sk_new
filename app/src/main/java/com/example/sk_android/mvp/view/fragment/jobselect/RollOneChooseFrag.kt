package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import com.example.sk_android.R
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.dip
import top.defaults.view.PickerView

class RollOneChooseFrag : Fragment() {

    interface DemoClick{
        fun rollOneCancel()
        fun rollOneConfirm(title: String,text : String)
    }

    private var mTitle: String = ""
    private var mList = mutableListOf<String>()

    lateinit var rollchoose : DemoClick

    companion object {
        fun newInstance(title: String,list: MutableList<String>): RollOneChooseFrag {
            val frag = RollOneChooseFrag()
            frag.mTitle = title
            frag.mList = list
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rollchoose = activity as DemoClick

        val fragmentView = inflater.inflate(R.layout.roll_choose_one, container, false)
        var tirle = fragmentView.findViewById(R.id.title) as TextView
        val pickerView = fragmentView.findViewById(R.id.picker_view) as PickerView
        val cancelBtn = fragmentView.findViewById(R.id.tool1)  as ImageView
        val confirmBtn = fragmentView.findViewById(R.id.tool2) as ImageView
        var itemText = mList[0]

        tirle.text = mTitle
        val items = mutableListOf<Item>()
        for (i in mList) {
            items.add(Item(i))
        }

        pickerView.setItemHeight(dip(40))
        pickerView.setTextSize(dip(16))
        pickerView.setItems(items) { item -> itemText = item!!.text }


        cancelBtn.onClick {
            rollchoose.rollOneCancel()
        }
        confirmBtn.onClick {
            rollchoose.rollOneConfirm(mTitle, itemText)
        }
        return fragmentView
    }

    class Item(s: String): PickerView.PickerItem{
        private var text: String = s

        override fun getText(): String {
            return text
        }
    }
}