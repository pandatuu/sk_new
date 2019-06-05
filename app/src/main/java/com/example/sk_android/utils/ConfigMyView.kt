package com.example.sk_android.utils

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.roundImageView(theme: Int = 0, init: CircleImageView.() -> Unit): CircleImageView {
    return ankoView({ CircleImageView(it) }, theme = theme, init = init)
}