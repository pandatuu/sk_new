package com.example.sk_android.mvp.view.adapter.jobselect


import android.support.v4.view.PagerAdapter

import android.view.View
import android.view.ViewGroup




class PicAdapter(

    private val list: MutableList<View>


) : PagerAdapter() {
    override fun getCount(): Int {
        return list.size

    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1;
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(list.get(position))
        return list.get(position)
    }
    override fun destroyItem(
        container: ViewGroup, position: Int,
        `object`: Any
    ) {
        container.removeView(list.get(position))
    }

}