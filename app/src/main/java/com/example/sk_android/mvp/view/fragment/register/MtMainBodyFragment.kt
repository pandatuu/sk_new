package com.example.sk_android.mvp.view.fragment.register

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI


class MtMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var tool: BaseTool

    companion object {
        fun newInstance(): MtMainBodyFragment {
            return MtMainBodyFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView()
    }

    fun createView(): View {
        tool= BaseTool()
        View.inflate(mContext, R.layout.radion, null)
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
                        letterSpacing = 0.5f
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