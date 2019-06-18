package com.example.sk_android.mvp.view.activity.jobselect

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity
import android.view.View
import android.widget.*
import com.airsaid.pickerviewlibrary.OptionsPickerView
import com.example.sk_android.R
import com.example.sk_android.mvp.view.adapter.jobselect.ListAdapter
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.JobInfoDetailAccuseDialogFragment
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class JobWantedManageActivity : AppCompatActivity(), BottomSelectDialogFragment.BottomSelectDialogSelect,
    ShadowFragment.ShadowClick {


    override fun shadowClicked() {
        closeBottomSelector()
    }


    override fun getBottomSelectDialogSelect() {
        closeBottomSelector()

    }

    override fun getback(index: Int, list: MutableList<String>) {

        var str=list.get(index)

        nowState.text=str
        closeBottomSelector()
    }

    private lateinit var toolbar1: Toolbar
    lateinit var imageView: ImageView
    lateinit var nowState: TextView
    lateinit var outerContainer: FrameLayout


    var shadowFragment: ShadowFragment? = null
    var bottomSelectDialogFragment: BottomSelectDialogFragment? = null


    var list = LinkedList<String>()


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        var outerContainerId = 11
        outerContainer = frameLayout() {
            id = outerContainerId
            verticalLayout {
                relativeLayout() {
                    backgroundColor = Color.BLUE
                    imageView = imageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.pic_top)
                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                    relativeLayout() {
                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back_white
                        }.lparams() {
                            width = matchParent
                            height = dip(65)
                            alignParentBottom()

                        }
                        textView {
                            text = "求職意思を管理する"
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                        }.lparams() {
                            width = matchParent
                            height = wrapContent
                            height = dip(65 - getStatusBarHeight(this@JobWantedManageActivity))
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

                relativeLayout {
                    backgroundResource = R.color.underToolBar
                    textView {
                        text = "求職状態"
                        textSize = 13f
                        gravity = Gravity.CENTER
                        textColorResource = R.color.underToolBarTextColorLeft
                    }.lparams() {
                        alignParentLeft()
                        leftMargin = 50
                        width = wrapContent
                        height = matchParent

                    }

                    var verticalCornerId = 1
                    linearLayout() {
                        id = verticalCornerId
                        gravity = Gravity.CENTER_VERTICAL


                        setOnClickListener(object : View.OnClickListener {

                            override fun onClick(v: View?) {
                                showBottomSelector()
                            }

                        })

                        nowState = textView {
                            text = "しばらくは考えない"
                            textSize = 13f
                            gravity = Gravity.CENTER
                            textColorResource = R.color.underToolBarTextColorRight
                            backgroundColor = Color.TRANSPARENT
                        }.lparams() {
                            width = wrapContent
                            height = matchParent
                            rightMargin = dip(5)


                        }

                        imageView() {
                            setImageResource(R.mipmap.icon_go_position)


                        }.lparams() {
                            width = wrapContent
                            height = wrapContent
                            rightMargin = dip(15)
                        }
                    }.lparams() {
                        width = wrapContent
                        height = matchParent
                        alignParentRight()

                    }


                }.lparams() {
                    width = matchParent
                    height = dip(53)

                }

                relativeLayout {
                    backgroundColor = Color.WHITE
                    val listView = listView() {
                        setVerticalScrollBarEnabled(false)
                        dividerHeight = 0
                        setOnItemClickListener { parent, view, position, id ->

                        }
                    }.lparams() {
                        width = matchParent
                        height = matchParent
                        rightMargin = 50
                        leftMargin = 50
                        topMargin = 10
                    }
                    list.add("11111")
                    list.add("11111")
                    list.add("11111")
                    list.add("11111")
                    list.add("11111")

                    listView.adapter = ListAdapter(list)

                    relativeLayout() {
                        onClick {
                            toast("xxxx")
                        }
                        backgroundResource = R.drawable.radius_button_theme
                        relativeLayout {
                            imageView {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageResource(R.mipmap.icon_add_position)
                            }.lparams() {
                                width = wrapContent
                                height = wrapContent
                                alignParentLeft()
                                centerVertically()
                            }
                            textView() {
                                text = "希望職種"
                                textColor = Color.WHITE
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                gravity = Gravity.CENTER
                            }.lparams() {
                                alignParentRight()
                                centerVertically()
                                width = wrapContent
                                height = matchParent
                            }
                        }.lparams() {
                            centerInParent()
                            width = dip(85)
                            height = matchParent
                        }
                    }.lparams() {
                        width = matchParent
                        height = dip(47)
                        leftMargin = 50
                        rightMargin = 50
                        bottomMargin = dip(15)
                        alignParentBottom()
                    }
                }.lparams() {
                    width = matchParent
                    height = matchParent
                }
            }.lparams() {
                width = matchParent
                height = matchParent
            }
        }
        setActionBar(toolbar1)
        getActionBar()!!.setDisplayHomeAsUpEnabled(true);
        StatusBarUtil.setTranslucentForImageView(this@JobWantedManageActivity, 0, toolbar1)


        toolbar1!!.setNavigationOnClickListener {
            finish()//返回
        }

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

    fun showBottomSelector() {
        var mTransaction = supportFragmentManager.beginTransaction()

        if (shadowFragment != null) {
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }

        if (bottomSelectDialogFragment != null) {
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment = null
        }


        shadowFragment = ShadowFragment.newInstance()
        mTransaction.add(outerContainer.id, shadowFragment!!)


        var title = "求職状態"
        var strArray: MutableList<String> = mutableListOf("離職-いつでも入社可能", "在職-１ヶ月以内入社可能", "在職-転職を考えている", "在職-今は転職しない")

        bottomSelectDialogFragment = BottomSelectDialogFragment.newInstance(title, strArray)

        mTransaction.setCustomAnimations(R.anim.bottom_in, R.anim.bottom_in)
        mTransaction.add(outerContainer.id, bottomSelectDialogFragment!!)


        mTransaction.commit()
    }


    fun closeBottomSelector() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (bottomSelectDialogFragment != null) {
            mTransaction.setCustomAnimations(R.anim.bottom_out, R.anim.bottom_out)
            mTransaction.remove(bottomSelectDialogFragment!!)
            bottomSelectDialogFragment = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(R.anim.fade_in_out, R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }


        mTransaction.commit()
    }

}
