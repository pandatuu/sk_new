package com.example.sk_android.mvp.view.activity.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.model.privacySet.ListItemModel
import com.example.sk_android.mvp.view.fragment.onlineresume.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

class ResumePreview : AppCompatActivity(),ResumeShareFragment.CancelTool, ResumePreviewBackground.BackgroundBtn{
    override fun clickButton() {
        toast("1111111")
    }

    override fun cancelList() {
        closeAlertDialog()
    }

    lateinit var baseFragment: FrameLayout
    var wsBackgroundFragment: ResumeBackgroundFragment? = null
    var wsListFragment: ResumeShareFragment? = null
    val mainId = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bottomBeha =  BottomSheetBehavior<View>(this@ResumePreview,null)
        bottomBeha.setPeekHeight(dip(370))

        val url = getIntent().getSerializableExtra("imageUrl") as ArrayList<String>
        var imageurl : String? = null
        if(url.size>0)
            imageurl = url.get(0)

        baseFragment = frameLayout {
            id = mainId
            coordinatorLayout {
                appBarLayout {
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
                        toolbar {
                            navigationIconResource = R.mipmap.icon_share_zwxq
                            onClick {
                                addListFragment()
                            }
                        }.lparams {
                            width = dip(20)
                            height = dip(20)
                            alignParentRight()
                            centerVertically()
                            rightMargin = dip(15)
                        }
                    }.lparams(matchParent, matchParent){
                        scrollFlags = 0
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }

                val back = 1
                frameLayout {
                    id = back
                    var resumeItem = ResumePreviewBackground.newInstance(imageurl)
                    supportFragmentManager.beginTransaction().add(back, resumeItem).commit()
                }.lparams(matchParent, dip(370)){
                    topMargin = dip(54)
                }
                val resumeListid = 2
                nestedScrollView {
                    id = resumeListid
                    backgroundResource = R.drawable.twenty_three_radius_top
                    var resumeItem = ResumePreviewItem.newInstance()
                    supportFragmentManager.beginTransaction().add(resumeListid, resumeItem).commit()
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    behavior = bottomBeha
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }

    //打开弹窗
    fun addListFragment(){
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if(wsBackgroundFragment==null){
            wsBackgroundFragment= ResumeBackgroundFragment.newInstance()
            mTransaction.add(baseFragment.id, wsBackgroundFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in)

        wsListFragment= ResumeShareFragment.newInstance()
        mTransaction.add(baseFragment.id,wsListFragment!!)

        mTransaction.commit()
    }

    //关闭弹窗
    fun closeAlertDialog(){
        var mTransaction=supportFragmentManager.beginTransaction()
        if(wsListFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(wsListFragment!!)
            wsListFragment=null
        }

        if(wsBackgroundFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(wsBackgroundFragment!!)
            wsBackgroundFragment=null
        }
        mTransaction.commit()
    }
}