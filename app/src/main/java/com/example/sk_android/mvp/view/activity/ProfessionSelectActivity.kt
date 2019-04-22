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
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.custom.layout.FlowLayout
import com.example.sk_android.custom.layout.flowLayout
import com.example.sk_android.custom.layout.recyclerView
import com.example.sk_android.mvp.model.Club
import com.example.sk_android.mvp.view.adapter.ClubAdapter

import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import com.jaeger.library.StatusBarUtil


class ProfessionSelectActivity : AppCompatActivity() {

    private lateinit var relativeLayout: RelativeLayout
    private lateinit var listView: ListView
    private lateinit var choseNum: TextView
    private lateinit var selectedItemShowArea: FlowLayout

    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()

    @SuppressLint("ResourceAsColor", "RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
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

                    selectedItemShowArea= flowLayout{


                    }


                }.lparams {
                    width = matchParent
                    height = dip(190)
                    leftMargin = 50
                    rightMargin = 50
                }

                var manager= LinearLayoutManager(this@ProfessionSelectActivity)
                manager.setOrientation(LinearLayoutManager.VERTICAL)
                var clubs: MutableList<Club> = mutableListOf()
                var c=Club(1,"","")
                var c1=Club(1,"","")
                var c2=Club(1,"","")
                clubs.add(c)
                clubs.add(c1)
                clubs.add(c2)
                recyclerView{
                    layoutManager=manager
                    adapter = ClubAdapter(this,selectedItemShowArea,choseNum,clubs){ club ->
                      toast("11")
                    }
                } .lparams() {
                    width = matchParent
                    height = matchParent
                }

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
