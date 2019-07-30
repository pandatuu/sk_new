package com.example.sk_android.mvp.view.fragment.onlineresume

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import click
import com.example.sk_android.R
import org.jetbrains.anko.sdk25.coroutines.onClick
import top.defaults.view.DateTimePickerView
import withTrigger
import java.text.SimpleDateFormat
import java.util.*


class RollChooseFrag : Fragment() {

    interface RollToolClick{
        fun cancelClick()
        fun confirmClick(methodName : String,text : String)
    }

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
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(System.currentTimeMillis())
        val now = simpleDateFormat.format(date)
        var dateString: String = now
        val start = Calendar.getInstance()
        start.set(1970,0,1)
        dateTimePickerView.setStartDate(start)
        dateTimePickerView.setEndDate(Calendar.getInstance())
        dateTimePickerView.selectedDate = Calendar.getInstance()
        dateTimePickerView.setOnSelectedDateChangedListener { date ->
            val year = date.get(Calendar.YEAR)
            val month = date.get(Calendar.MONTH)
            val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
            val mStr = if(month<9) "0${month+1}" else (month+1).toString()
            val dStr = if(dayOfMonth<10) "0$dayOfMonth" else dayOfMonth.toString()
            dateString = "$year-$mStr-$dStr"
        }
        cancelBtn.withTrigger().click {
            rollchoose.cancelClick()
        }
        confirmBtn.withTrigger().click {
            rollchoose.confirmClick(methodName = methodName, text = dateString)
        }

        return fragmentView
    }
}