package com.example.sk_android.mvp.view.activity.onlineresume

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.onlineresume.EditBasicInformation
import com.example.sk_android.mvp.view.fragment.onlineresume.CommonBottomButton
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeBackgroundFragment
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeListFragment
import org.jetbrains.anko.*

class EditBasicInformation : AppCompatActivity(), EditBasicInformation.Middleware, ResumeListFragment.CancelTool {

    lateinit var resumebutton: CommonBottomButton
    lateinit var editList: EditBasicInformation

    lateinit var baseFragment: FrameLayout
    var wsBackgroundFragment: ResumeBackgroundFragment? = null
    var wsListFragment: ResumeListFragment? = null
    var mTransaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val base = 3
        baseFragment =frameLayout {
            id = base
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
                        text = "基本情報を編集"
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

                val itemList = 1
                val button = 2
                frameLayout {
                    frameLayout {
                        id = itemList
                        editList = EditBasicInformation.newInstance(this@EditBasicInformation)
                        supportFragmentManager.beginTransaction().add(itemList, editList).commit()
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        bottomMargin = dip(70)
                    }
                    frameLayout {
                        id = button
                        resumebutton = CommonBottomButton.newInstance("セーブ", 0)
                        supportFragmentManager.beginTransaction().add(button, resumebutton).commit()
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

    @SuppressLint("ResourceType")
    override fun addListFragment() {
        if (mTransaction == null) {
            mTransaction = supportFragmentManager.beginTransaction()

            wsBackgroundFragment = ResumeBackgroundFragment.newInstance()

            mTransaction!!.add(baseFragment.id, wsBackgroundFragment!!)

            mTransaction!!.setCustomAnimations(
                R.anim.bottom_in, R.anim.bottom_in
            )

            wsListFragment = ResumeListFragment.newInstance()
            mTransaction!!.add(baseFragment.id, wsListFragment!!)

            mTransaction!!.commit()
        }
    }

    override fun cancelList() {
        mTransaction = supportFragmentManager.beginTransaction()

        if (wsListFragment != null) {

            mTransaction!!.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )

            mTransaction!!.remove(wsListFragment!!)
            wsListFragment = null
        }

        if (wsBackgroundFragment != null) {

            mTransaction!!.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )

            mTransaction!!.remove(wsBackgroundFragment!!)
            wsBackgroundFragment = null

        }

        mTransaction!!.commit()
        mTransaction = null
    }
}