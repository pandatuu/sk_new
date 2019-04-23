package com.example.sk_android.mvp.view.activity


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import com.biao.pulltorefresh.PtrLayout
import com.example.sk_android.R
import com.example.sk_android.custom.layout.*
import com.example.sk_android.mvp.model.Profession
import com.example.sk_android.mvp.view.adapter.ProfessionSelectAdapter

import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import com.jaeger.library.StatusBarUtil


class ProfessionSelectActivity : AppCompatActivity() {

    private lateinit var relativeLayout: RelativeLayout
    private lateinit var listView: ListView
    private lateinit var choseNum: TextView
    private lateinit var selectedItemShowArea: FlowLayout
    private lateinit var nothingSelectedShowArea: TextView

    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()

    @SuppressLint("ResourceAsColor", "RestrictedApi", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        relativeLayout {
            verticalLayout {
                backgroundColor=Color.WHITE
                relativeLayout() {
                    textView() {
                        backgroundResource = R.drawable.actionbar_bottom_border
                    }.lparams() {
                        width = matchParent
                        height = dip(65)

                    }

                    relativeLayout() {


                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back


                        }.lparams() {
                            width = matchParent
                            height = dip(65)
                            alignParentBottom()

                        }

                        textView {
                            text = "業種を選択"
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColorResource = R.color.toolBarTextColor
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                        }.lparams() {
                            width = matchParent
                            height = wrapContent
                            height = dip(65 - getStatusBarHeight(this@ProfessionSelectActivity))
                            alignParentBottom()
                        }

                        textView {
                            text = "セーブ"
                            textColorResource = R.color.saveButtonTextColor
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER_VERTICAL
                            textSize = 13f
                            onClick {
                                toast("bbb")
                            }
                        }.lparams() {
                            width = dip(52)
                            height = dip(65 - getStatusBarHeight(this@ProfessionSelectActivity))
                            alignParentRight()
                            alignParentBottom()
                        }
                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }


                verticalLayout {
                    backgroundColor = Color.WHITE
                    relativeLayout {
                        textView {
                            text = "選んだ業種"
                        }.lparams {
                            alignParentLeft()
                            centerVertically()

                        }
                        var rightPartId = 1
                        var rightPart = textView {
                            id = rightPartId
                            text = "/3）"
                        }.lparams {
                            alignParentRight()
                            centerVertically()

                        }
                        var middlePartId = 2
                        choseNum = textView {
                            id = middlePartId
                            text = "0"
                            textColorResource = R.color.themeColor

                            addTextChangedListener(object :TextWatcher{
                                override fun afterTextChanged(s: Editable?) {
                                    if(text.equals("0")){
                                        nothingSelectedShowArea.visibility=View.VISIBLE
                                        selectedItemShowArea.visibility=View.INVISIBLE

                                    }else{
                                        nothingSelectedShowArea.visibility=View.INVISIBLE
                                        selectedItemShowArea.visibility=View.VISIBLE
                                    }
                                }
                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                                }
                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                }
                            })
                        }.lparams {
                            leftOf(rightPart)
                            centerVertically()
                        }
                        textView {
                            text = "（"
                        }.lparams {
                            leftOf(choseNum)
                            centerVertically()
                        }

                    }.lparams {
                        width = matchParent
                        height = dip(54)
                    }

                    relativeLayout {
                        nothingSelectedShowArea=textView {
                            text = "业界を选んで、最大3つ!"
                        }
                        selectedItemShowArea= flowLayout{

                        }



                    }

                }.lparams {
                    width = matchParent
                    height = dip(190)
                    leftMargin = 50
                    rightMargin = 50
                }

                var manager= LinearLayoutManager(this@ProfessionSelectActivity)
                manager.setOrientation(LinearLayoutManager.VERTICAL)
                var professions: MutableList<Profession> = mutableListOf()
                var p0=Profession("インターネット/IT/电子/通信",
                    arrayOf("電子商取引","ソフトウエア","メディア","販売促進","データ分析","データ分析","移动インターネット","ソフトウエア","インターネット"))
                var p1=Profession("金融",
                    arrayOf("银行","保险","证券/期货","基金","信托","互联网金融","投资/融资","租赁/拍卖/典当/担保"))
                var p2=Profession("汽车",
                    arrayOf("汽车生产","汽车零部件","4S店/期后市场"))
                var p3=Profession("建筑/房地产",
                    arrayOf("房地产开发","工程施工","建筑设计","装修装饰","建材","地产经纪/中介","物业服务"))

                professions.add(p0)
                professions.add(p1)
                professions.add(p2)
                professions.add(p3)

//
//               ptrLayout{
//                    recyclerView {
//                        backgroundColor = Color.WHITE
//                        overScrollMode = View.OVER_SCROLL_NEVER
//                        layoutManager = manager
//                        adapter = ProfessionSelectAdapter(this, selectedItemShowArea, choseNum, professions) { club ->
//                            toast("11")
//                        }
//
//                    }
//                }.lparams() {
//                    width = matchParent
//                    height = matchParent
//
//                }

                var springbackRecyclerView=  LayoutInflater.from(context).inflate(R.layout.springback_recycler_view, null);
                var recyclerView = springbackRecyclerView.findViewById<View>(R.id.SBRecyclerView) as RecyclerView

                recyclerView.overScrollMode = View.OVER_SCROLL_NEVER
                recyclerView.setLayoutManager(LinearLayoutManager(springbackRecyclerView.getContext()))
                recyclerView.setAdapter(ProfessionSelectAdapter(recyclerView, selectedItemShowArea, choseNum, professions) { club ->
                   toast("11")
                })
                addView(springbackRecyclerView)

            }.lparams() {
                alignParentTop()
                width = matchParent
                height = matchParent
            }



        }

        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@ProfessionSelectActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)


    }


    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }



}
