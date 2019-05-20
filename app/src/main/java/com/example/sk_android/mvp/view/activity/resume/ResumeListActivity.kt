package com.example.sk_android.mvp.view.activity.resume

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.view.fragment.person.RlActionBarFragment
import com.example.sk_android.mvp.view.fragment.register.RlOpeartListFragment
import com.example.sk_android.mvp.view.fragment.resume.RlBackgroundFragment
import com.example.sk_android.mvp.view.fragment.resume.RlMainBodyFragment
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*


class ResumeListActivity:AppCompatActivity(),RlMainBodyFragment.Tool,RlOpeartListFragment.CancelTool {
    private lateinit var myDialog : MyDialog
    lateinit var rlActionBarFragment: RlActionBarFragment
    var rlBackgroundFragment:RlBackgroundFragment? = null
    lateinit var baseFragment:FrameLayout
    var rlOpeartListFragment:RlOpeartListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mainScreenId=1
        baseFragment = frameLayout {
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
                frameLayout{

                    id=actionBarId
                    rlActionBarFragment= RlActionBarFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id,rlActionBarFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    val rlMainBodyFragment = RlMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, rlMainBodyFragment).commit()
                }.lparams(width = matchParent, height = matchParent){
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
    }



    override fun onStart() {
        super.onStart()
        setActionBar(rlActionBarFragment.TrpToolbar)
        StatusBarUtil.setTranslucentForImageView(this@ResumeListActivity, 0, rlActionBarFragment.TrpToolbar)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    override fun addList() {
        addListFragment()
    }


    @SuppressLint("ResourceType")
    fun addListFragment() {

        var mTransaction=supportFragmentManager.beginTransaction()

        rlBackgroundFragment = RlBackgroundFragment.newInstance()

        mTransaction.add(baseFragment.id, rlBackgroundFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,  R.anim.bottom_in)


        rlOpeartListFragment = RlOpeartListFragment.newInstance()
        mTransaction.add(baseFragment.id, rlOpeartListFragment!!)

        mTransaction.commit()
    }

    override fun cancelList() {
        var mTransaction=supportFragmentManager.beginTransaction()

        if (rlOpeartListFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)

            mTransaction.remove(rlOpeartListFragment!!)
            rlOpeartListFragment = null
        }

        if (rlBackgroundFragment != null){

            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)

            mTransaction.remove(rlBackgroundFragment!!)
            rlBackgroundFragment = null

        }


        mTransaction.commit()
    }

    override fun sendEmail() {
        cancelList()
        startActivity<SendResumeActivity>()
    }

    override fun reName() {
        cancelList()
        afterShowLoading()
    }

    override fun delete() {
        cancelList()
        deleteShowLoading()
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.rl_rename, null)
        val mmLoading2 = MyDialog(this, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2
        myDialog.setCancelable(false)
        myDialog.show()
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
        determineBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
    }

    //弹出更新窗口
    fun deleteShowLoading() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.rl_delete, null)
        val mmLoading2 = MyDialog(this, R.style.MyDialogStyle)
        mmLoading2.setContentView(view)
        myDialog = mmLoading2
        myDialog.setCancelable(false)
        myDialog.show()
        var cancelBtn = view.findViewById<Button>(R.id.cancel_button)
        var determineBtn = view.findViewById<Button>(R.id.request_button)
        cancelBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
        determineBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myDialog.dismiss()
            }
        })
    }

    override fun addVideo() {
    }

}