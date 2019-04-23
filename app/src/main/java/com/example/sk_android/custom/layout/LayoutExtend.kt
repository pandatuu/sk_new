package com.example.sk_android.custom.layout


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import com.biao.pulltorefresh.PtrLayout

import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.internals.AnkoInternals

inline fun ViewManager.flowLayout(
    ctx: Context = AnkoInternals.getContext(this),
    theme: Int = 0,
    init: FlowLayout.() -> Unit
): FlowLayout {
    return ankoView({ FlowLayout(ctx) }, theme, init)
}


inline fun ViewManager.recyclerView(
    ctx: Context = AnkoInternals.getContext(this),
    theme: Int = 0,
    init: RecyclerView.() -> Unit): RecyclerView {
    return ankoView({RecyclerView(ctx)},theme,init)
}






