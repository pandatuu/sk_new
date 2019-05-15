package com.example.sk_android.mvp.view.activity.privacyset

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.privacySet.ListItemModel
import com.example.sk_android.mvp.view.adapter.privacyset.RecyclerAdapter
import com.example.sk_android.mvp.view.fragment.privacyset.WhiteListBottomButton
import org.jetbrains.anko.*
import java.util.*

class WhiteListActivity :AppCompatActivity() {

    lateinit var whiteListBottomButton: WhiteListBottomButton
    lateinit var recyclerView: RecyclerView
    var whiteListItemList = LinkedList<ListItemModel>()
    var listsize = 0
    lateinit var readapter: RecyclerAdapter
    lateinit var textV : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        whiteListItemList.add(ListItemModel(R.mipmap.sk,"ソニー株式会社","東京都品川區南大井3-27-14"))
        whiteListItemList.add(ListItemModel(R.mipmap.sk,"ソニー诛仙会社","東京都品川區南大井3-27-14"))
        whiteListItemList.add(ListItemModel(R.mipmap.sk,"しん友教育","東京都品川區南小井1-27-14"))
        listsize=whiteListItemList.size
        var outside = 1
        frameLayout {
            id = outside
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "ホワイトリスト"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerInParent()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }

                verticalLayout {
                    textView {
                        text = "注目されている会社"
                        textSize = 16f
                        textColor = Color.parseColor("#FF202020")
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(15)
                    }
                    textV = textView {
                        text = "(合計${listsize}社)"
                        textSize = 13f
                        textColor = Color.parseColor("#FF5C5C5C")
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(5)
                        bottomMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                view { backgroundColor = Color.parseColor("#FFF6F6F6") }.lparams {
                    width = matchParent
                    height = dip(10)
                }
                val a = 2
                frameLayout {
                    //白名单公司,可左滑删除
                    relativeLayout {
                        recyclerView{
                            layoutManager = LinearLayoutManager(this@WhiteListActivity)
                            readapter = RecyclerAdapter(this@WhiteListActivity, whiteListItemList)
                            whiteListItemList = readapter.getData()
                            adapter = readapter

                            addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener{
                                override fun onChildViewDetachedFromWindow(p0: View) {
                                    println("add----------做了一些操作-------------")
                                    listsize = readapter.itemCount
                                    textV.text =  "(合計${listsize}社)"
                                }

                                override fun onChildViewAttachedToWindow(p0: View) {
                                    println("add----------第一次添加--------------")
                                }

                            })
                        }.lparams{
                            width = matchParent
                            height = matchParent
                        }
                    }.lparams{
                        width = matchParent
                        height = matchParent
                        bottomMargin = dip(60)
                    }
                    //最下面的按钮
                    frameLayout {
                        id = a
                        whiteListBottomButton = WhiteListBottomButton.newInstance();
                        supportFragmentManager.beginTransaction().add(id, whiteListBottomButton).commit()
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (readapter != null) {
            readapter!!.saveStates(outState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Only if you need to restore open/close state when
        // the orientation is changed
        if (readapter != null) {
            readapter!!.restoreStates(savedInstanceState)
        }
    }

}