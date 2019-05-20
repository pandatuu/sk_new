package com.example.sk_android.mvp.view.activity.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ResumePreview : AppCompatActivity(), ResumeShareFragment.CancelTool{
    lateinit var baseFragment: FrameLayout
    var wsBackgroundFragment: ResumeBackgroundFragment? = null
    var wsListFragment: ResumeShareFragment? = null
    var mTransaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val base = 3
        baseFragment = frameLayout {
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
                    imageView {
                        imageResource = R.mipmap.icon_share_zwxq
                        onClick {
                            if (mTransaction == null) {
                                mTransaction = supportFragmentManager.beginTransaction()
                                wsBackgroundFragment = ResumeBackgroundFragment.newInstance()
                                mTransaction!!.add(baseFragment.id, wsBackgroundFragment!!)
                                mTransaction!!.setCustomAnimations(
                                    R.anim.bottom_in, R.anim.bottom_in
                                )
                                wsListFragment = ResumeShareFragment.newInstance()
                                mTransaction!!.add(baseFragment.id, wsListFragment!!)
                                mTransaction!!.commit()
                            }
                        }
                    }.lparams {
                        width = dip(20)
                        height = dip(20)
                        alignParentRight()
                        centerVertically()
                        rightMargin = dip(15)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }

                val resumeListid = 1
                frameLayout {
                    frameLayout {
                        id = resumeListid
                        var resumeItem = ResumePreviewItem.newInstance()
                        supportFragmentManager.beginTransaction().add(resumeListid, resumeItem).commit()
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