package com.example.sk_android.mvp.view.adapter.privacyset

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.ListItemModel
import org.jetbrains.anko.*
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class CommonAddItemAdapter(mText: String, mList: LinkedList<ListItemModel>) :
    RecyclerView.Adapter<CommonAddItemAdapter.ViewHolder>() {
    var list: LinkedList<ListItemModel> = mList
    var text = mText
    var index = 0
    lateinit var relative: RelativeLayout

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(with(p0.context) {
            relativeLayout {
                val name = list.get(index).companyName
                val textlist = list.get(index)
                relative = relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    if (text != "") {
                        var textview = textView {
                            text = name
                            textSize = 15f
                        }.lparams {
                            centerVertically()
                            alignParentLeft()
                        }
                        val ss = matcherSearchTitle("#FFFFB706", textview.text.toString(), text)
                        textview.text = ss
                    } else {
                        textView {
                            text = list.get(index).companyName
                            textSize = 15f
                        }.lparams {
                            centerVertically()
                            alignParentLeft()
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = dip(50)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        })

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        relative.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener.OnItemClick(v, list.get(position));
                }
            }
        })
    }

    override fun getItemCount(): Int {
        if (list != null)
            return list.size
        return 0

    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        index++
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun RecyclerViewHolder(itemView: View) {
            super.itemView
        }
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
    /**
     * 设置item的监听事件的接口
     */
    interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        fun OnItemClick(view: View?, data: ListItemModel)
    }
    private lateinit var onItemClickListener : OnItemClickListener
    fun setOnItemClickListener(onItemClickListener1: OnItemClickListener) {
        onItemClickListener = onItemClickListener1
    }
}