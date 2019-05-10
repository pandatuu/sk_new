package com.example.sk_android.mvp.view.fragment.privacyset

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.BlackListItemModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*

class BlackAddCompanyFrag() : Fragment() {

    companion object {
        fun newInstance(): BlackAddCompanyFrag {
            val fragment = BlackAddCompanyFrag()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            relativeLayout{
                relativeLayout {
                    relativeLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape
                            gravity = Gravity.CENTER
                            textView {
                                text = "全てを非選択"
                                textSize = 16f
                                textColor = Color.parseColor("#FF02B8F7")
                                gravity = Gravity.CENTER
                            }.lparams{
                                width = wrapContent
                                height = matchParent
                                leftMargin = dip(25)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(50)
                            alignParentTop()
                            setMargins(dip(15),0,dip(15),0)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape_blue
                            gravity = Gravity.CENTER
                            textView {
                                text = "選択された会社をブラックリストに追加"
                                textSize = 16f
                                textColor = Color.parseColor("#FFFFFFFF")
                                gravity = Gravity.CENTER
                            }.lparams{
                                width = wrapContent
                                height = matchParent
                                leftMargin = dip(25)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(50)
                            alignParentBottom()
                            setMargins(dip(15),0,dip(15),dip(10))
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(125)
                        alignParentBottom()
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }
}