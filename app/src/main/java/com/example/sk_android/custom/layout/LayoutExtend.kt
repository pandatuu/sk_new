package com.example.sk_android.custom.layout

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import cn.jiguang.imui.view.RoundImageView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import github.ll.view.FloatOnKeyboardLayout
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

inline fun ViewManager.roundImageView(
    ctx: Context = AnkoInternals.getContext(this),
    theme: Int = 0,
    init: RoundImageView.() -> Unit): RoundImageView {
    return ankoView({RoundImageView(ctx)},theme,init)
}
inline fun ViewManager.floatOnKeyboardLayout(
    ctx: Context = AnkoInternals.getContext(this),
    theme: Int = 0,
    init: FloatOnKeyboardLayout.() -> Unit): FloatOnKeyboardLayout {
    return ankoView({ FloatOnKeyboardLayout(ctx,null) },theme,init)
}

inline fun ViewManager.smartRefreshLayout(ctx: Context = AnkoInternals.getContext(this), theme: Int = 0, init: SmartRefreshLayout.() -> Unit): SmartRefreshLayout {
    return ankoView({ SmartRefreshLayout(ctx) }, theme, init)
}