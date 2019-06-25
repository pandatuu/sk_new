package com.example.sk_android.mvp.view.adapter.onlineresume

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import com.example.sk_android.mvp.view.activity.onlineresume.EditEduExperience
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ResumeEditEduAdapter(mList: MutableList<EduExperienceModel>) : RecyclerView.Adapter<ResumeEditEduAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun RecyclerViewHolder(itemView: View) {
            super.itemView

        }
    }

//    private lateinit var education: EduExperienceModel
    private var list = mList
    private var schoolName: String = ""
    private var educationalBackground: String = ""
    private var date: String = ""

    private var index = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ResumeEditEduAdapter.ViewHolder {
        println("=======================================")
        println("mlist---------------------------------"+list)

        schoolName = list[p1].schoolName
        educationalBackground = "${list[index].educationalBackground}  ${list[index].major}"

        println("schoolName---------------------------------"+schoolName)
        println("educationalBackground---------------------------------"+educationalBackground)

        println("=======================================")
        val view = with(p0.context){
            linearLayout {
                orientation = LinearLayout.VERTICAL

            }
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ResumeEditEduAdapter.ViewHolder, p1: Int) {
        println("bond-----------------------------------------"+p1)
        println("=======================================")
    }

}