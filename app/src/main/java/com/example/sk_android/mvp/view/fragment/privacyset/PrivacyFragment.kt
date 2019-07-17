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
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class PrivacyFragment : Fragment() {

    private lateinit var click: PrivacyClick
    private var isPublic: Switch? = null // 公开简历
    private var isResume: Switch? = null // ビデオ履歴書有効
    private var isCompanyName: Switch? = null // 就職経験に会社フルネームが表示される

    companion object {
        fun newInstance(): PrivacyFragment {
            val fragment = PrivacyFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        click = activity as PrivacyClick
        var fragmentView = createView()
        return fragmentView
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
                    onClick() {
                        click.blacklistClick()

                    }
                }.lparams {
                    width = matchParent
                    height = dip(55)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                }
                view {
                    backgroundColor = Color.parseColor("#FFF6F6F6")
                }.lparams(matchParent, dip(8))
                //ビデオ履歴書有効
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    imageView {
                        imageResource = R.mipmap.open_video_cv
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }
                    textView {
                        text = "ビデオ履歴書有効"
                        textSize = 13f
                        textColor = Color.parseColor("#FF5C5C5C")
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        leftMargin = dip(25)
                        centerVertically()
                    }
                    isResume = switch {
                        setThumbResource(R.drawable.thumb)
                        setTrackResource(R.drawable.track)
                        onClick {
                            click.isResumeClick(isChecked)
                        }
                    }.lparams {
                        alignParentRight()
                        centerVertically()
                        rightMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(55)
                    leftMargin = dip(15)
                }
                //就職経験に会社フルネームが表示される
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    imageView {
                        imageResource = R.mipmap.show_work_experience
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }
                    textView {
                        text = "職務経歴に会社フルネームが表示される"
                        textSize = 13f
                        textColor = Color.parseColor("#FF5C5C5C")
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        leftMargin = dip(25)
                        centerVertically()
                    }
                    isCompanyName = switch {
                        setThumbResource(R.drawable.thumb)
                        setTrackResource(R.drawable.track)
                        onClick {
                            click.companyNameClick(isChecked)
                        }
                    }.lparams {
                        alignParentRight()
                        centerVertically()
                        rightMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(55)
                    leftMargin = dip(15)
                }
            }
        }.view
    }

    interface PrivacyClick {
        suspend fun isPublicClick(checked: Boolean)
        fun blacklistClick()
        suspend fun companyNameClick(checked: Boolean)
        suspend fun isResumeClick(checked: Boolean)
    }

    fun setSwitch(public: Boolean, resume: Boolean, company: Boolean) {
        isPublic?.isChecked = public
        isCompanyName?.isChecked = company
        isResume?.isChecked = resume
    }
}