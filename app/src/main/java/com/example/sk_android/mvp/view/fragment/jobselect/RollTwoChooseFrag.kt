package com.example.sk_android.mvp.view.fragment.jobselect

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import com.example.sk_android.R
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.dip
import top.defaults.view.PickerView

class RollTwoChooseFrag : Fragment() {

    interface DemoClick{
        fun rollTwoCancel()
        fun rollTwoConfirm(text1 : String, text2 : String)
    }

    private var mTitle: String = ""
    private var mListOne = mutableListOf<Item>()
    private var mListTwo = mutableListOf<Item>()

    lateinit var rollchoose : DemoClick

    companion object {
        fun newInstance(title: String, list1: MutableList<String>, list2: MutableList<String>): RollTwoChooseFrag {
            val frag = RollTwoChooseFrag()
            frag.mTitle = title
            for (i in list1) {
                frag.mListOne.add(Item(i))
            }
            for (i in list2) {
                frag.mListTwo.add(Item(i))
            }
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rollchoose = activity as DemoClick

        val fragmentView = inflater.inflate(R.layout.roll_choose_two, container, false)
        var tirle = fragmentView.findViewById(R.id.title) as TextView
        val pickerView1 = fragmentView.findViewById(R.id.picker_view1) as PickerView
        val pickerView2 = fragmentView.findViewById(R.id.picker_view2) as PickerView
        val cancelBtn = fragmentView.findViewById(R.id.tool1)  as Toolbar
        val confirmBtn = fragmentView.findViewById(R.id.tool2) as Toolbar
        var itemTextOne = mListOne[0].text
        var itemTextTwo = mListTwo[0].text

        tirle.text = mTitle
        pickerView1.setTextSize(dip(16))
        pickerView1.setItems(mListOne) { item -> itemTextOne = item!!.text+"  " }


        pickerView2.setTextSize(dip(16))
        pickerView2.setItems(mListTwo) { item -> itemTextTwo = item!!.text+"  " }

        cancelBtn.onClick {
            rollchoose.rollTwoCancel()
        }
        confirmBtn.onClick {
            rollchoose.rollTwoConfirm(itemTextOne,itemTextTwo)
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