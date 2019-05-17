package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.tool.BaseTool
import com.example.sk_android.mvp.view.activity.register.LoginActivity
import com.example.sk_android.mvp.view.activity.register.SetPasswordActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI


class MtMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var account:EditText
    lateinit var accountErrorMessage: TextView
    lateinit var tool:BaseTool

    companion object {
        fun newInstance(): MtMainBodyFragment {
            val fragment = MtMainBodyFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        return fragmentView
    }

    fun createView(): View {
        tool= BaseTool()
        var view = View.inflate(mContext, R.layout.radion, null)
        return UI {
            linearLayout {
                backgroundColorResource = R.color.whiteFF
                orientation = LinearLayout.VERTICAL

                textView {
                    gravity = Gravity.CENTER_HORIZONTAL
                    textResource = R.string.mtIntroduction
                    textColorResource = R.color.black20
                    textSize = 15f
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(26)
                }

                scrollView {
                    textView {
                        textResource = R.string.mtText
                        textSize = 14f
                        letterSpacing = 1f
                        textColorResource = R.color.black33
                    }.lparams(width = matchParent,height = wrapContent){

                    }
                }.lparams(width = matchParent){
                    weight = 1f
                    topMargin = dip(6)
                }

            }
        }.view
    }

}