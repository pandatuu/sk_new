package com.example.sk_android.mvp.view.fragment.person

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toolbar
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class PsActionBarFragment:Fragment() {
    var toolbar: Toolbar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance():PsActionBarFragment{
            return PsActionBarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        return fragmentView

    }

    private fun createView(): View? {
        return UI {

            verticalLayout {

                relativeLayout() {
                    textView() {
                        backgroundColorResource = R.color.yellowFED95A
                    }.lparams() {
                        width = matchParent
                        height = dip(175)

                    }

                    relativeLayout() {
                        toolbar = toolbar {
                            backgroundResource = R.color.yellowFED95A
                            isEnabled = true
                            title = ""
                        }.lparams() {
                            width = matchParent
                            height =matchParent
                        }
                    }.lparams(width = matchParent,height = dip(10)){

                    }



                    linearLayout{
                        imageView {
                            imageResource = R.mipmap.portrait
                        }.lparams(width = dip(70),height = dip(70)){
                            leftMargin = dip(15)
                        }


                        linearLayout {
                            textView {
                                textResource = R.string.personName
                                textColorResource = R.color.black33
                                textSize = 24f
                                backgroundColorResource = R.color.yellowFED95A
                                gravity = Gravity.CENTER
                            }.lparams(width = wrapContent,height = matchParent){
                            }
                        }.lparams(width = wrapContent,height = matchParent) {
                            leftMargin = dip(20)
                        }


                    }.lparams {
                        width= matchParent
                        height=dip(70)
                        alignParentBottom()
                        bottomMargin=dip(36)
                    }


                }.lparams {
                    width = matchParent
                    height = dip(175)
                }
            }

        }.view
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