package com.example.sk_android.mvp.view.activity

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import com.example.sk_android.R
import kotlinx.android.synthetic.*

import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar1: Toolbar
    lateinit var imageView: ImageView

    var list = LinkedList<String>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {

            relativeLayout() {
                backgroundColor = Color.BLUE

                relativeLayout() {
                    toolbar1 = toolbar {
                        isEnabled = true
                        title = "XXXXXXXXXXX"
                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                        alignParentBottom()

                    }
                    textView {
                        text = "求職意思を管理する"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.WHITE
                        textSize = 16f
                        id= R.id.notification_main_column_container
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                    }.lparams() {
                        width = matchParent
                        height = wrapContent
                        height = dip(65 - getStatusBarHeight(this@MainActivity))
                        alignParentBottom()
                    }

                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }

        }


    }


    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }


}
