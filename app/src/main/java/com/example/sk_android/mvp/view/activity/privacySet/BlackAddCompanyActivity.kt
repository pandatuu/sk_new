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
import android.widget.FrameLayout
import com.example.sk_android.mvp.view.fragment.privacyset.BlackAddCompanyFrag
import com.example.sk_android.mvp.view.fragment.privacyset.BlackAddCompanyItem
import android.widget.EditText
import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.example.sk_android.mvp.view.adapter.privacyset.BlackAddItemAdapter
import com.example.sk_android.mvp.view.fragment.privacyset.BlackAddCompanyThree


class BlackAddCompanyActivity : AppCompatActivity(), BlackAddCompanyItem.OnRecycleClickListener {

    var blackListItemList = LinkedList<BlackListItemModel>()
    var bubianlist = LinkedList<BlackListItemModel>()
    lateinit var blackAdd : BlackAddCompanyFrag
    lateinit var blackAdditem : BlackAddCompanyItem
    var text1 :String = ""
    lateinit var frag :FrameLayout
    lateinit var edit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"ソニー株式会社","東京都品川區南大井3-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"ソニー诛仙会社","東京都品川區南大井3-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"しん友教育","東京都品川區南小井1-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"1","1-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"2","2-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"你二大爷","2-27-14"))
        blackListItemList.add(BlackListItemModel(R.mipmap.sk,"北堂堂堂堂","2-27-14"))
        bubianlist=blackListItemList

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
//                                onClick {
//                                    for(item in bubianlist){
//                                        if(edit.text.toString().equals(item.companyName)){
//                                            edit.setText("")
//                                            edit.setCursorVisible(false)
//                                            //创建新的实例,然后replace替换掉,实现输入文字,列表刷新
//                                            var new = BlackAddCompanyThree.newInstance(edit.text, item)
//                                            var id = 1
//                                            supportFragmentManager.beginTransaction().replace(id, new).commit()
//                                        }
//                                    }
//                                }
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
                            edit=editText{
                                textSize = 14f
                                textColor = Color.parseColor("#FF5C5C5C")
                                background = null
                                setSingleLine(true)
                                setCursorVisible(false)
                                setOnTouchListener(object : View.OnTouchListener {
                                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                                        if (MotionEvent.ACTION_DOWN == event!!.getAction()) {
                                            setCursorVisible(true);// 再次点击显示光标
                                        }
                                        return false
                                    }
                                })
                                addTextChangedListener(object : TextWatcher{
                                    override fun afterTextChanged(s: Editable?) {}

                                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        text1 = s.toString()
                                        val list = sreachItem(text1)
                                        println("isCursorVisible----------------------"+isCursorVisible)
                                        if(isCursorVisible) {
                                            //创建新的实例,然后replace替换掉,实现输入文字,列表刷新
                                            var new = BlackAddCompanyItem.newInstance(text1, list)
                                            var id = 1
                                            supportFragmentManager.beginTransaction().replace(id, new).commit()
                                        }
                                    }
                                })
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
                                    edit.setCursorVisible(false)
                                    //创建新的实例,然后replace替换掉,实现输入文字,列表刷新
                                    var new = BlackAddCompanyItem.newInstance("", bubianlist)
                                    var id = 1
                                    supportFragmentManager.beginTransaction().replace(id, new).commit()
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

                val a = 1
                val b = 2
                frameLayout {
                    frag = frameLayout {
                        id = a
                        blackAdditem = BlackAddCompanyItem.newInstance(text1,blackListItemList)
                        supportFragmentManager.beginTransaction().add(id, blackAdditem).commit()
                    }
                    frameLayout {
                        id = b
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

    override fun OnCycleClick(data: BlackListItemModel) {
        toast(data.companyName)
        text1 = data.companyName
        var new = BlackAddCompanyThree.newInstance(text1, data)
        var id = 1
        supportFragmentManager.beginTransaction().replace(id, new).commit()
//        frag.addView()
        edit.setText(text1)
    }

    //点击其他位置关闭光标
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            //当isShouldHideInput(v, ev)为true时，表示的是点击输入框区域，则需要显示键盘，同时显示光标，反之，需要隐藏键盘、光标
            if (isShouldHideInput(v, ev)) {

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v!!.windowToken, 0)
                    //处理Editext的光标隐藏、显示逻辑
                    edit.setCursorVisible(false)
                }
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }

    //点击其他位置关闭光标
    fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.height
            val right = left + v.width
            return if (event.x > left && event.x < right
                && event.y > top && event.y < bottom
            ) {
                // 点击的是输入框区域，保留点击EditText的事件
                false
            } else {
                true
            }
        }
        return false
    }


    fun sreachItem(text: String) : LinkedList<BlackListItemModel>{
        var newList = LinkedList<BlackListItemModel>()
        for( item in bubianlist){
            if(item.companyName.indexOf(text)!=-1){
                newList.add(item)
            }
        }
        return newList
    }
}