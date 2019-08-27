package com.example.sk_android.mvp.view.fragment.privacyset

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.BlackCompanyAdd
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class BlackAddCompanyItem : Fragment() {


    lateinit var mList: MutableList<BlackCompanyAdd>
    lateinit var bubianList: MutableList<BlackCompanyAdd>
    var text = ""
    lateinit var onCycleClickListener : BlackOnRecycleClickListener

    companion object {
        fun newInstance(mtext : String, linkedlist: MutableList<BlackCompanyAdd>): BlackAddCompanyItem {
            val fragment = BlackAddCompanyItem()
            fragment.text = mtext
            fragment.mList = linkedlist
            fragment.bubianList = linkedlist
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onCycleClickListener = activity as BlackOnRecycleClickListener
        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        return UI {
            relativeLayout{
                scrollView {
                    verticalLayout {
                        for (item in mList){
                            val name = item.model.name
                            relativeLayout {
                                relativeLayout {
                                    backgroundResource = R.drawable.text_view_bottom_border
                                    if (text != "") {
                                        val textview = textView {
                                            text = name
                                            textSize = 15f
                                            ellipsize = TextUtils.TruncateAt.END
                                            maxLines = 1
                                        }.lparams {
                                            width = matchParent
                                            centerVertically()
                                            alignParentLeft()
                                        }
                                        val ss = matcherSearchTitle("#FFFFB706", textview.text.toString(), text)
                                        textview.text = SpannableStringBuilder(ss)
                                    } else {
                                        textView {
                                            text = name
                                            textSize = 15f
                                            ellipsize = TextUtils.TruncateAt.END
                                            maxLines = 1
                                        }.lparams {
                                            width = matchParent
                                            centerVertically()
                                            alignParentLeft()
                                        }
                                    }
                                    this.withTrigger().click {
                                        onCycleClickListener.blackOnCycleClick(item)
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = dip(50)
                                    leftMargin = dip(15)
                                    rightMargin = dip(15)
                                }
                            }
                        }
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                    topMargin = dip(75)
                    bottomMargin = dip(125)
                }
            }
        }.view
    }

    interface BlackOnRecycleClickListener {

        fun blackOnCycleClick(data: BlackCompanyAdd)
    }

    //查找关键字并改颜色
    fun matcherSearchTitle(color: String, text: String, keyword: String): SpannableStringBuilder {
        val string: String = text.toLowerCase()
        val key: String = keyword.toLowerCase()
        val pattern: Pattern = Pattern.compile("$key")
        val matcher: Matcher = pattern.matcher(string)
        var ss = SpannableStringBuilder(text)
        var endList = LinkedList<Int>()
        val bkaccolor = ForegroundColorSpan(Color.parseColor(color))
        var num = 0
        while (matcher.find()) {
            var start: Int = matcher.start()
            var end: Int = matcher.end()
            if (num == 0)
                endList.add(start)
            endList.add(end)
            num++
        }
        ss.setSpan(
            bkaccolor, endList.first, endList.last,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return ss
    }
}