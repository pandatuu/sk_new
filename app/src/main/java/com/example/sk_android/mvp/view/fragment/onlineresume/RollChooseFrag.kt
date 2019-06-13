package com.example.sk_android.mvp.view.fragment.onlineresume

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyPicker
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import top.defaults.view.DateTimePickerView
import java.util.*


class RollChooseFrag : Fragment() {

    lateinit var rollchoose : RollToolClick
    private var methodName= ""

    companion object {
        fun newInstance(text: String): RollChooseFrag {
            val fragment = RollChooseFrag()
            fragment.methodName = text
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rollchoose = activity as RollToolClick

        val fragmentView = inflater.inflate(R.layout.roll_choose_date, container, false)
        val dateTimePickerView = fragmentView.findViewById(R.id.datePickerView) as DateTimePickerView
        val cancelBtn = fragmentView.findViewById(R.id.tool1)  as Toolbar
        val confirmBtn = fragmentView.findViewById(R.id.tool2) as Toolbar
        var dateString = ""

        dateTimePickerView.selectedDate = Calendar.getInstance()
        dateTimePickerView.setOnSelectedDateChangedListener(object : DateTimePickerView.OnSelectedDateChangedListener {
            override fun onSelectedDateChanged(date: Calendar) {
                val year = date.get(Calendar.YEAR)
                val month = date.get(Calendar.MONTH)
                val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
                dateString="${year}-${month+1}-${dayOfMonth}"
                toast(dateString)
            }
        })

        cancelBtn.onClick {
            rollchoose.cancelClick()
        }
        confirmBtn.onClick {
            rollchoose.confirmClick(methodName,dateString)
        }

        return fragmentView
    }
    interface RollToolClick{
        fun cancelClick()
        fun confirmClick(methodName : String,text : String)
    }
}