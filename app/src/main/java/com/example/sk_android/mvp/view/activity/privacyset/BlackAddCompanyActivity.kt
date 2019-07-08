package com.example.sk_android.mvp.view.activity.privacyset

//import com.example.sk_android.mvp.view.fragment.privacyset.BlackAddCompanyThree
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import com.alibaba.fastjson.JSON
import com.example.sk_android.R
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.privacySet.BlackCompanyAdd
import com.example.sk_android.mvp.model.privacySet.BlackCompanyModel
import com.example.sk_android.mvp.view.fragment.privacyset.BlackAddCompanyFrag
import com.example.sk_android.mvp.view.fragment.privacyset.BlackAddCompanyItem
import com.example.sk_android.mvp.view.fragment.privacyset.CommonAddCompanyThree
import com.example.sk_android.utils.MimeType
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException
import java.io.Serializable
import java.util.*


class BlackAddCompanyActivity : AppCompatActivity(), BlackAddCompanyItem.BlackOnRecycleClickListener,
    BlackAddCompanyFrag.BlackButtonClickListener, CommonAddCompanyThree.CheckBoxStatus {

    var blackListItemList = mutableListOf<BlackCompanyAdd>()
    var bubianlist = mutableListOf<BlackCompanyAdd>()
    lateinit var blackAdd: BlackAddCompanyFrag
    lateinit var blackAdditem: BlackAddCompanyItem
    var text1: String = ""
    lateinit var frag: FrameLayout
    lateinit var edit: EditText
    var isTrueNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            getAllCompany()
        }

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
                                onClick {
                                    for (item in bubianlist) {
                                        println(item.toString())
                                        if (edit.text.toString().equals(item.model.name)) {
                                            //创建新的实例,然后replace替换掉,实现输入文字,列表刷新
                                            var new =
                                                CommonAddCompanyThree.newInstance(edit.text.toString(), item, null)
                                            var id = 1
                                            supportFragmentManager.beginTransaction().replace(id, new).commit()
                                            edit.setCursorVisible(false)
                                        }
                                    }
                                }
                            }.lparams {
                                width = dip(15)
                                height = dip(15)
                                alignParentLeft()
                                leftMargin = dip(15)
                                centerVertically()
                            }
                            view { backgroundColor = Color.parseColor("#FF898989") }.lparams {
                                width = dip(1)
                                height = dip(20)
                                centerVertically()
                                leftMargin = dip(45)
                            }
                            edit = editText {
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
                                addTextChangedListener(object : TextWatcher {
                                    override fun afterTextChanged(s: Editable?) {}

                                    override fun beforeTextChanged(
                                        s: CharSequence?,
                                        start: Int,
                                        count: Int,
                                        after: Int
                                    ) {
                                    }

                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        text1 = s.toString()
                                        val list = sreachItem(text1)
                                        if (isCursorVisible) {
                                            //创建新的实例,然后replace替换掉,实现输入文字,列表刷新
                                            var new = BlackAddCompanyItem.newInstance(text1, list)
                                            var id = 1
                                            supportFragmentManager.beginTransaction().replace(id, new).commit()
                                        }
                                    }
                                })
                            }.lparams {
                                width = dip(160)
                                height = matchParent
                                leftMargin = dip(55)
                                centerVertically()
                            }
                            imageView {
                                imageResource = R.mipmap.icon_delete_circle
                                onClick {
                                    edit.setText("")
                                    edit.isCursorVisible = false
                                    //图标X 点击清除文字，显示所以列表
                                    var new = BlackAddCompanyItem.newInstance("", blackListItemList)
                                    var id = 1
                                    supportFragmentManager.beginTransaction().replace(id, new).commit()
                                }
                            }.lparams {
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
                            textColor = Color.parseColor("#FF333333")
                            textSize = 14f
                            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                            onClick {
                                finish()
                            }
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
                        blackAdditem = BlackAddCompanyItem.newInstance(text1, blackListItemList)
                        supportFragmentManager.beginTransaction().add(id, blackAdditem).commit()
                    }
                    frameLayout {
                        isTrueNumber = 0
                        for (list in blackListItemList) {
                            if (list.isTrueChecked == true) {
                                isTrueNumber++
                            }
                        }
                        id = b
                        blackAdd = BlackAddCompanyFrag.newInstance(isTrueNumber)
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


    override fun blackOnCycleClick(data: BlackCompanyAdd) {
        text1 = data.model.name
        var new = CommonAddCompanyThree.newInstance(text1, data, null)
        var id = 1
        supportFragmentManager.beginTransaction().replace(id, new).commit()
        edit.setText(text1)
    }


    //点击添加按钮,跳转回黑名单列表页面
    override suspend fun blackOkClick() {
        for (item in blackListItemList) {
            if (item.isTrueChecked == true) {
                addBlackCompany(item.model.id)
            }
        }
        val intent = Intent(this@BlackAddCompanyActivity, BlackListActivity::class.java)
        startActivity(intent)
                        overridePendingTransition(R.anim.right_in, R.anim.left_out)

    }

    override fun blackcancelClick(bool: Boolean) {
        for (item in blackListItemList) {
            println(item.toString())
            if (edit.text.toString().equals(item.model.name)) {
                var new = CommonAddCompanyThree.newInstance(text1, item, bool)
                var id = 1
                supportFragmentManager.beginTransaction().replace(id, new).commit()
                edit.setText(text1)
            }
            item.isTrueChecked = null
        }
        //改变底部按钮选取数量
        isTrueNumber = 0
        for (list in blackListItemList) {
            if (list.isTrueChecked == true) {
                isTrueNumber++
            }
        }
        val id = 2
        blackAdd = BlackAddCompanyFrag.newInstance(isTrueNumber)
        supportFragmentManager.beginTransaction().replace(id, blackAdd).commit()
    }

    //点击多选框，改变底部button选取数量
    override fun updateCheckStatus(item: BlackCompanyAdd, boolean: Boolean?) {
        for (list in blackListItemList) {
            if (list == item) {
                list.isTrueChecked = boolean
            }
        }
        isTrueNumber = 0
        for (list in blackListItemList) {
            if (list.isTrueChecked == true) {
                isTrueNumber++
            }
        }
        val id = 2
        blackAdd = BlackAddCompanyFrag.newInstance(isTrueNumber)
        supportFragmentManager.beginTransaction().replace(id, blackAdd).commit()
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

    //获取所有公司(暂时为指定数据的)
    private suspend fun getAllCompany() {
        try {
            val retrofitUils = RetrofitUtils(this@BlackAddCompanyActivity, "https://org.sk.cgland.top/")
            val it = retrofitUils.create(PrivacyApi::class.java)
                .getAllCompany()
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            // Json转对象
            if (it.code() in 200..299) {
                println("获取成功")
                val page = Gson().fromJson(it.body(), PagedList::class.java)
                if (page.data.size > 0) {
                    for (item in page.data) {
                        val model = BlackCompanyModel(
                            item.get("id").asString?:"",
                            item.get("name").asString?:"",
                            item.get("acronym").asString?:"",
                            item.get("logo").asString?:""
                        )
                        blackListItemList.add(BlackCompanyAdd(null, model, false))
                    }

                    bubianlist = blackListItemList
                    val id = 1
                    val black = BlackAddCompanyItem.newInstance("",blackListItemList)
                    supportFragmentManager.beginTransaction().replace(id,black).commit()
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
        }
    }

    // 添加黑名单公司
    private suspend fun addBlackCompany(id: String) {
        try {
            var params = mapOf(
                "blackedOrganizationId" to id
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@BlackAddCompanyActivity, "https://user.sk.cgland.top/")
            val it = retrofitUils.create(PrivacyApi::class.java)
                .addBlackCompany(body)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .awaitSingle()
            // Json转对象
            if (it.code() in 200..299) {
                println("更新成功")
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("code--------------" + throwable.code())
            }
        }
    }
    //点击其他位置关闭光标
    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
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

    //模糊查找名字
    private fun sreachItem(text: String): LinkedList<BlackCompanyAdd> {
        var newList = LinkedList<BlackCompanyAdd>()
        for (item in bubianlist) {
            if (item.model.name.indexOf(text) != -1) {
                newList.add(item)
            }
        }
        return newList
    }
}