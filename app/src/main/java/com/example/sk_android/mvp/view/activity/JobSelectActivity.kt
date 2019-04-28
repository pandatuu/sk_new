package com.example.sk_android.mvp.view.activity


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.*
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.model.JobContainer
import com.example.sk_android.mvp.view.fragment.jobSelect.*
import org.jetbrains.anko.*
import java.util.*
import com.jaeger.library.StatusBarUtil


class JobSelectActivity : AppCompatActivity(), SendSearcherText, JobListFragment.ItemSelected,
    ShadowFragment.ShadowClick {


    var jobTypeDetailFragment:JobTypeDetailFragment?=null
     var shadowFragment: ShadowFragment?=null


    override fun shadowClicked() {
        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if(jobTypeDetailFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.right_out,  R.anim.right_out)
            mTransaction.remove(jobTypeDetailFragment!!)

        }

        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)

        }

        mTransaction.commit()
    }


    override fun getSelectedItem(item: JobContainer) {
        toast(item.containerName)
        var mTransaction=supportFragmentManager.beginTransaction()
        if(jobTypeDetailFragment!=null)
            mTransaction.remove(jobTypeDetailFragment!!)
        if(shadowFragment!=null)
            mTransaction.remove(shadowFragment!!)

        jobTypeDetailFragment= JobTypeDetailFragment.newInstance();
        shadowFragment= ShadowFragment.newInstance();
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(recycleViewParent.id,shadowFragment!!)
        mTransaction.setCustomAnimations(
            R.anim.right_in,
            R.anim.right_out)
        mTransaction.add(recycleViewParent.id, jobTypeDetailFragment!!).commit()
    }


    override fun sendMessage(msg: String) {
       toast(msg)
    }

    lateinit var actionBarChildFragment:ActionBarFragment
    lateinit var recycleViewParent:FrameLayout
    private lateinit var toolbar1: Toolbar
    var list = LinkedList<Map<String, Any>>()


    override fun onStart() {
        super.onStart()

        setActionBar(actionBarChildFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobSelectActivity, 0, actionBarChildFragment.toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }


    @SuppressLint("ResourceAsColor", "RestrictedApi", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



            verticalLayout {
                backgroundColor = Color.WHITE
                //ActionBar
                var actionBarId=2
                frameLayout{
                    id=actionBarId
                    actionBarChildFragment= ActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,actionBarChildFragment).commit()


                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                //搜索框
                var searchId=1
                frameLayout{
                    id=searchId
                    var childFragment=JobSearcherFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,childFragment).commit()

                }.lparams {
                    height= wrapContent
                    width= matchParent
                }

                //list
                var recycleViewParentId=3
                recycleViewParent=frameLayout {
                    id=recycleViewParentId
                    var childFragment= JobListFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,childFragment).commit()


                }.lparams {
                    height=matchParent
                    width= matchParent
                }


        }








    }









}
