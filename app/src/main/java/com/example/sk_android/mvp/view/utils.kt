package com.example.sk_android.mvp.view

import android.content.Context
import com.chauthai.swipereveallayout.SwipeRevealLayout
import org.jetbrains.anko.AnkoViewDslMarker

inline fun Context.swipeRevealLayout(): SwipeRevealLayout = swipeRevealLayout() {}
inline fun Context.swipeRevealLayout(init: (@AnkoViewDslMarker SwipeRevealLayout).() -> Unit): SwipeRevealLayout {
    return SwipeRevealLayout(this).apply(init)
}
