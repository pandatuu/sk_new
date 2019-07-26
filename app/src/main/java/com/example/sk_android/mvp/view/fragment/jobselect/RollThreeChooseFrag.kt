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

class RollThreeChooseFrag : Fragment() {

    interface DemoClick {
        fun rollThreeCancel()
        fun rollThreeConfirm(text1: String, text2: String, text3: String)
    }

    private var mTitle: String = ""
    private var mListOne = mutableListOf<Item>()
    private var mListTwo = mutableListOf<Item>()
    private var mListThree = mutableListOf<Item>()


    var hourlySalary = mutableListOf(
        300,
        600,
        750,
        900,
        1000,
        1200
    ).map {
        Item(it.toString())
    }.toMutableList()

    var dailySalary = mutableListOf<Int>(
        2400,
        4800,
        6500,
        7000,
        8000,
        9000
    ).map {
        Item(it.toString())
    }.toMutableList()

    var monthlySalary = mutableListOf<Int>(
        90000,
        120000,
        150000,
        180000,
        210000,
        240000
    ).map {
        Item(it.toString())
    }.toMutableList()

    var yearlySalary = mutableListOf<Int>(
        900000,
        1200000,
        1500000,
        1800000,
        2100000,
        2400000
    ).map {
        Item(it.toString())
    }.toMutableList()


    lateinit var rollchoose: DemoClick

    companion object {
        fun newInstance(
            title: String,
            list1: MutableList<String>,
            list2: MutableList<String>,
            list3: MutableList<String>
        ): RollThreeChooseFrag {
            val frag = RollThreeChooseFrag()
            frag.mTitle = title
            for (i in list1) {
                frag.mListOne.add(Item(i))
            }
            for (i in list2) {
                frag.mListTwo.add(Item(i))
            }
            for (i in list3) {
                frag.mListThree.add(Item(i))
            }
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rollchoose = activity as DemoClick

        val fragmentView = inflater.inflate(R.layout.roll_choose_three, container, false)
        var tirle = fragmentView.findViewById(R.id.title) as TextView
        val pickerView1 = fragmentView.findViewById(R.id.picker_view1) as PickerView
        val pickerView2 = fragmentView.findViewById(R.id.picker_view2) as PickerView
        val pickerView3 = fragmentView.findViewById(R.id.picker_view3) as PickerView
        val cancelBtn = fragmentView.findViewById(R.id.tool1) as Toolbar
        val confirmBtn = fragmentView.findViewById(R.id.tool2) as Toolbar
        var itemTextOne = mListOne[0].text
        var itemTextTwo = mListTwo[0].text
        var itemTextThree = mListThree[0].text

        tirle.text = mTitle
        pickerView1.setTextSize(dip(16))
        pickerView1.setItems(mListOne) { item ->
            itemTextOne = item!!.text + "  "

            if (item != null && item!!.text .equals(this.getString(R.string.hourly))) {
                pickerView2.setItems(hourlySalary) { item ->
                    itemTextTwo = item!!.text + "  "
                    var index = hourlySalary.indexOf(item)
                    pickerView3.setItems(hourlySalary.subList(index, hourlySalary.size)) { item ->
                        itemTextThree = item!!.text + "  "
                    }
                    pickerView3.notifyDataSetChanged()
                }
                pickerView3.setItems(hourlySalary.subList(1, hourlySalary.size)) { item ->
                    itemTextThree = item!!.text + "  "
                }

            } else if (item != null && item!!.text .equals(this.getString(R.string.daySalary))) {
                pickerView2.setItems(dailySalary) { item ->
                    itemTextTwo = item!!.text + "  "
                    var index = dailySalary.indexOf(item)
                    pickerView3.setItems(dailySalary.subList(index, dailySalary.size)) { item ->
                        itemTextThree = item!!.text + "  "
                    }
                    pickerView3.notifyDataSetChanged()
                }
                pickerView3.setItems(dailySalary.subList(1, dailySalary.size)) { item ->
                    itemTextThree = item!!.text + "  "
                }

            } else if (item != null && item!!.text .equals(this.getString(R.string.monthSalary))) {
                pickerView2.setItems(monthlySalary) { item ->
                    itemTextTwo = item!!.text + "  "
                    var index = monthlySalary.indexOf(item)
                    pickerView3.setItems(monthlySalary.subList(index, monthlySalary.size)) { item ->
                        itemTextThree = item!!.text + "  "
                    }
                    pickerView3.notifyDataSetChanged()

                }
                pickerView3.setItems(monthlySalary.subList(1, monthlySalary.size)) { item ->
                    itemTextThree = item!!.text + "  "
                }

            } else if (item != null && item!!.text .equals(this.getString(R.string.yearSalary))) {
                pickerView2.setItems(yearlySalary) { item ->
                    itemTextTwo = item!!.text + "  "
                    var index = yearlySalary.indexOf(item)
                    pickerView3.setItems(yearlySalary.subList(index, yearlySalary.size)) { item ->
                        itemTextThree = item!!.text + "  "
                    }
                    pickerView3.notifyDataSetChanged()

                }
                pickerView3.setItems(yearlySalary.subList(1, yearlySalary.size)) { item ->
                    itemTextThree = item!!.text + "  "
                }

            }

            pickerView2.selectedItemPosition=0
            pickerView3.selectedItemPosition=0

            pickerView2.notifyDataSetChanged()
            pickerView3.notifyDataSetChanged()
        }


        pickerView2.setTextSize(dip(16))
        pickerView2.setItems(hourlySalary) { item ->
            itemTextTwo = item!!.text + "  "

            var index = hourlySalary.indexOf(item)

            pickerView3.setItems(hourlySalary.subList(index, hourlySalary.size)) { item ->
                itemTextThree = item!!.text + "  "
            }
            pickerView3.notifyDataSetChanged()

        }







        pickerView3.setTextSize(dip(16))
        pickerView3.setItems(hourlySalary.subList(1, hourlySalary.size)) { item -> itemTextThree = item!!.text + "  " }

        cancelBtn.onClick {
            rollchoose.rollThreeCancel()
        }
        confirmBtn.onClick {
            rollchoose.rollThreeConfirm(itemTextOne, itemTextTwo, itemTextThree)
        }
        return fragmentView
    }

    class Item(s: String) : PickerView.PickerItem {
        private var text: String = s

        override fun getText(): String {
            return text
        }
    }
}