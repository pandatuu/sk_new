package com.example.sk_android.mvp.view.activity.jobselect

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.json.JSONArray

class JobWantedEditActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    JobWantedListFragment.DeleteButton, JobWantedDialogFragment.ConfirmSelection,
    RollOneChooseFrag.DemoClick, RollTwoChooseFrag.DemoClick{

    //类型 1修改/2添加
    var operateType:Int=1


    lateinit var mainScreen:FrameLayout
    var shadowFragment: ShadowFragment?=null
    var jobWantedDeleteDialogFragment:JobWantedDialogFragment?=null
    var rollone:RollOneChooseFrag?=null
    var rolltwo:RollTwoChooseFrag?=null
    var jobWantedListFragment:JobWantedListFragment?=null

    lateinit var themeActionBarFragment:ThemeActionBarFragment

    override fun confirmResult(b: Boolean) {
        closeDialog()
        toast(b.toString())
    }

    override fun delete() {
        toast("xxxxx")
        var mTransaction=supportFragmentManager.beginTransaction()
        if(shadowFragment!=null || jobWantedDeleteDialogFragment!=null){
            return
        }

        shadowFragment= ShadowFragment.newInstance()
        jobWantedDeleteDialogFragment=JobWantedDialogFragment.newInstance(JobWantedDialogFragment.CANCLE)
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainScreen.id,shadowFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.fade_in_out,  R.anim.fade_in_out)
        mTransaction.add(mainScreen.id,jobWantedDeleteDialogFragment!!).commit()

    }

    override fun shadowClicked() {
    }

    override fun onStart() {
        super.onStart()
        setActionBar(themeActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobWantedEditActivity, 0, themeActionBarFragment.toolbar1)

        themeActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.right_out,R.anim.right_out)
        }


    }

    /**
     *  得到返回的值
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data!=null){
            getIntentData(data!!)
        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        PushAgent.getInstance(this).onAppStart();

        var intent=intent
        operateType=intent.getIntExtra("type",1)

//if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT){
//透明状态栏
//getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
//getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//}
//getWindow().addFlags(WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE);
//注意要清除 FLAG_TRANSLUCENT_STATUS flag
//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//getWindow().setStatusBarColor(getResources().getColor(android.R.color.holo_red_light))
//getWindow().setNavigationBarColor(getResources().getColor(android.R.color.holo_red_light))

        var mainScreenId=1
        mainScreen=frameLayout {
            backgroundColor=Color.WHITE
            id=mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId=2
               frameLayout{
                    id=actionBarId
                    themeActionBarFragment= ThemeActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id,themeActionBarFragment).commit()


                }.lparams {
                    height= wrapContent
                    width= matchParent
                }
                //list
                var recycleViewParentId=3
                frameLayout {
                    id=recycleViewParentId
                    jobWantedListFragment= JobWantedListFragment.newInstance(operateType);
                    supportFragmentManager.beginTransaction().replace(id,jobWantedListFragment!!).commit()
                }.lparams {
                    height=matchParent
                    width= matchParent
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
//getActionBar()!!.setDisplayHomeAsUpEnabled(true);
//StatusBarUtil.setTranslucentForDrawerLayout(this, , 0)
//StatusBarUtil.setColor(this, R.color.transparent);
//StatusBarUtil.setColorForDrawerLayout(this, layout, 0)
    }

    //获取Intent数据
    fun getIntentData(intent:Intent){
        if(intent!=null){
            if(intent.hasExtra("jobName")){
                //在这里获取 选中行业的名字 和 ID
                //todoo
                var jobName=intent.getStringExtra("jobName")
                var jobId=intent.getStringExtra("jobId")
                jobWantedListFragment!!.setWantJobText(jobName)
            }

            if(intent.hasExtra("cityModel")){
                //在这里获取 选中城市的名字 和 ID
                //todoo
                var cityModel=intent.getStringExtra("cityModel")
                var cityArray=JSONArray(cityModel)
                var cityName=""
                for(i in 0..cityArray.length()-1){
                    cityName=cityName+","+cityArray.getJSONObject(i).getString("name")
                }
                jobWantedListFragment!!.setCity(cityName)
            }
        }
    }

    // 点击选择只有单列的滚动选择器弹窗
    override fun oneDialogCLick(s: String) {
        var mTransaction=supportFragmentManager.beginTransaction()
        if(shadowFragment!=null || rollone!=null){
            return
        }
        shadowFragment= ShadowFragment.newInstance()

        when(s){
            "工作类别" -> {
                val list = mutableListOf("不限","小时工","全职")
                rollone = RollOneChooseFrag.newInstance(s, list)
            }
            "招聘方式" -> {
                val list = mutableListOf("企业直聘","派遣公司代聘","猎头公司代聘")
                rollone = RollOneChooseFrag.newInstance(s, list)
            }
            "海外招聘" -> {
                val list = mutableListOf("接受","不接受")
                rollone = RollOneChooseFrag.newInstance(s, list)
            }
        }

        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainScreen.id,shadowFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,  R.anim.bottom_in)
        mTransaction.add(mainScreen.id,rollone!!).commit()
    }

    // 点击选择有两列的滚动选择器弹窗
    override fun twoDialogCLick(s: String) {
        var mTransaction=supportFragmentManager.beginTransaction()
        if(shadowFragment!=null || rollone!=null){
            return
        }
        shadowFragment= ShadowFragment.newInstance()

        val list1 = mutableListOf("时薪","日薪","月薪","年薪")
        val list2 = mutableListOf("面议","100","200","300")
        rolltwo = RollTwoChooseFrag.newInstance(s, list1, list2)

        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainScreen.id,shadowFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,  R.anim.bottom_in)
        mTransaction.add(mainScreen.id,rolltwo!!).commit()
    }

    // 单列滚动弹窗的取消按钮
    override fun rollOneCancel() {
        closeDialog()
    }
    // 单列滚动弹窗的确定按钮
    override fun rollOneConfirm(title: String, text: String) {
        toast(text)
        when(title){
            "工作类别" -> {
                jobWantedListFragment?.setJobtype(text)
            }
            "招聘方式" -> {
                jobWantedListFragment?.setRecruitWay(text)
            }
            "海外招聘" -> {
                jobWantedListFragment?.setOverseasRecruit(text)
            }
        }
        closeDialog()
    }

    // 两列滚动弹窗的取消按钮
    override fun rollTwoCancel() {
        closeDialog()
    }
    // 两列滚动弹窗的确定按钮
    override fun rollTwoConfirm(text1: String, text2: String) {
        val text = "$text1 $text2"
        toast(text)
        jobWantedListFragment?.setSalary(text)
        closeDialog()
    }
    // 关闭所有fragment弹窗有关效果
    private fun closeDialog(){

        var mTransaction=supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if(jobWantedDeleteDialogFragment!=null){
            mTransaction.remove(jobWantedDeleteDialogFragment!!)
            jobWantedDeleteDialogFragment=null
        }

        if(shadowFragment!=null){
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out,  R.anim.fade_in_out)
            mTransaction.remove(shadowFragment!!)
            shadowFragment=null
        }

        if(rollone!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(rollone!!)
            rollone=null
        }

        if(rolltwo!=null){
            mTransaction.setCustomAnimations(
                R.anim.bottom_out,  R.anim.bottom_out)
            mTransaction.remove(rolltwo!!)
            rolltwo=null
        }

        mTransaction.commit()
    }
}
