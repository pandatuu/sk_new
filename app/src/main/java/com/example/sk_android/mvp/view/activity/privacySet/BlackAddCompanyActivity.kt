package com.example.sk_android.mvp.view.activity.privacySet

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.BlackListItemModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import android.view.MotionEvent
import android.view.View
import com.example.sk_android.mvp.view.fragment.privacyset.BlackAddCompanyFrag
import com.example.sk_android.mvp.view.fragment.privacyset.BlackAddCompanyItem


class BlackAddCompanyActivity : AppCompatActivity() {

    var blackListItemList = LinkedList<BlackListItemModel>()
    lateinit var blackAdd : BlackAddCompanyFrag
    lateinit var blackAdditem : BlackAddCompanyItem
    var text1 :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"ソニー株式会社","東京都品川區南大井3-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"ソニー诛仙会社","東京都品川區南大井3-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"しん友教育","東京都品川區南小井1-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"1","1-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"2","2-27-14"))


        val outside = 1
        frameLayout {
            id = outside
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    relativeLayout {
                        relativeLayout {
                            backgroundColor = Color.parseColor("#FFF6F6F6")
                            imageView {
                                imageResource = R.mipmap.icon_search
                            }.lparams{
                                width = dip(15)
                                height = dip(15)
                                alignParentLeft()
                                leftMargin = dip(15)
                                centerVertically()
                            }
                            view { backgroundColor = Color.parseColor("#FF898989") }.lparams{
                                width = dip(1)
                                height = dip(20)
                                centerVertically()
                                leftMargin = dip(45)
                            }
                            var edit=editText{
                                textSize = 14f
                                textColor = Color.parseColor("#FF5C5C5C")
                                background = null
                                setSingleLine(true)
                                setOnTouchListener(object : View.OnTouchListener {
                                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                                        if (MotionEvent.ACTION_DOWN == event!!.getAction()) {
                                            setCursorVisible(true);// 再次点击显示光标
                                        }
                                        return false
                                    }
                                })
                                addTextChangedListener(object : TextWatcher{
                                    override fun afterTextChanged(s: Editable?) {

                                    }

                                    override fun beforeTextChanged(
                                        s: CharSequence?,
                                        start: Int,
                                        count: Int,
                                        after: Int
                                    ) {

                                    }

                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        text1 = s.toString()
                                    }

                                })
                                setOnFocusChangeListener { v, hasFocus -> println(11111) }
                            }.lparams{
                                width = dip(160)
                                height = matchParent
                                leftMargin = dip(55)
                                centerVertically()
                            }
                            imageView {
                                imageResource = R.mipmap.icon_delete_circle
                                onClick {
                                    edit.setText("")
                                    edit.setCursorVisible(false);
                                }
                            }.lparams{
                                width = dip(15)
                                height = dip(15)
                                alignParentRight()
                                rightMargin = dip(10)
                                centerVertically()
                            }
                        }.lparams {
                            width = dip(250)
                            height = dip(40)
                            alignParentLeft()
                            centerVertically()
                        }

                        textView {
                            text = "キャンセル"
                            gravity = Gravity.CENTER
                            textColor = Color.parseColor("#FF898989")
                            textSize = 14f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            alignParentRight()
                            centerVertically()


                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(64)
                }

                val a = 2
                frameLayout {
                    frameLayout {
                        id = a
                        blackAdditem = BlackAddCompanyItem.newInstance(text1,blackListItemList)
                        supportFragmentManager.beginTransaction().add(id, blackAdditem).commit()
                    }

                    frameLayout {
                        id = a
                        blackAdd = BlackAddCompanyFrag.newInstance()
                        supportFragmentManager.beginTransaction().add(id, blackAdd).commit()
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


}