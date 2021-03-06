package com.example.sk_android.mvp.view.fragment.privacyset

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class PrivacyFragment : Fragment() {

    private lateinit var click: PrivacyClick
    private var isPublic: Switch? = null // 公开简历

    companion object {
        fun newInstance(): PrivacyFragment {
            return PrivacyFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        click = activity as PrivacyClick
        return createView()
    }

    private fun createView(): View? {
        return UI {
            verticalLayout {
                //公开简历
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    imageView {
                        imageResource = R.mipmap.vc
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }
                    textView {
                        text = "履歴書を公開"
                        textSize = 13f
                        textColor = Color.parseColor("#FF333333")
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        leftMargin = dip(25)
                        centerVertically()
                    }
                    isPublic = switch {
                        setThumbResource(R.drawable.thumb)
                        setTrackResource(R.drawable.track)
                        onClick {
                            click.isPublicClick(isChecked)
                        }
                    }.lparams(wrapContent, wrapContent) {
                        alignParentRight()
                        centerVertically()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(55)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                }
                //ブラックリスト
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    imageView {
                        imageResource = R.mipmap.black_list
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }
                    textView {
                        text = "ブラックリスト"
                        textSize = 13f
                        textColor = Color.parseColor("#FF5C5C5C")
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        leftMargin = dip(25)
                        centerVertically()
                    }
                    imageView {
                        imageResource = R.mipmap.icon_go_position
                        onClick {
                            click.blacklistClick()
                        }
                    }.lparams {
                        width = dip(6)
                        height = dip(11)
                        alignParentRight()
                        centerVertically()
                    }
                    onClick {
                        click.blacklistClick()

                    }
                }.lparams {
                    width = matchParent
                    height = dip(55)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                }
            }
        }.view
    }

    interface PrivacyClick {
        suspend fun isPublicClick(checked: Boolean)
        fun blacklistClick()
    }

    fun setSwitch(public: Boolean) {
        isPublic?.isChecked = public
    }
}