package com.example.sk_android.mvp.view.activity.jobselect

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.Person
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.*
import com.example.sk_android.R
import com.example.sk_android.mvp.model.jobselect.UserJobIntention
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.BaseTool
import com.example.sk_android.utils.RetrofitUtils
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang.StringUtils
import org.json.JSONArray

class JobWantedEditActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    JobWantedListFragment.DeleteButton, JobWantedDialogFragment.ConfirmSelection,
    RollOneChooseFrag.DemoClick, RollThreeChooseFrag.DemoClick, ThemeActionBarFragment.headTest{
    //类型 1修改/2添加
    var condition = 1


    lateinit var mainScreen: FrameLayout
    var shadowFragment: ShadowFragment? = null
    var jobWantedDeleteDialogFragment: JobWantedDialogFragment? = null
    var jobWantedChangeDialogFragment: JobWantedDialogFragment? = null
    var rollone: RollOneChooseFrag? = null
    var rollthree: RollThreeChooseFrag? = null
    var jobWantedListFragment: JobWantedListFragment? = null

    lateinit var themeActionBarFragment: ThemeActionBarFragment
    lateinit var tool: BaseTool

    @SuppressLint("CheckResult")
    override fun confirmResult(b: Boolean, condition: String) {
        if (b) {
            var retrofitUils = RetrofitUtils(this, this.getString(R.string.jobUrl))

            retrofitUils.create(PersonApi::class.java)
                .deleteJobIntention(condition)
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    if (it.code() in 200..299) {
                        println("删除求职意向成功！！")
                        val intent = Intent()
                        intent.putExtra("result", "result")
                        setResult(1001, intent)// 设置resultCode，onActivityResult()中能获取到
                        this.finish()
                        this.overridePendingTransition(R.anim.left_in,R.anim.right_out)


                    } else {
                        println("删除求职意向失败！！")
                    }
                }, {

                })
        }
        closeDialog()
    }

    override fun delete(id: String) {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (shadowFragment != null || jobWantedDeleteDialogFragment != null) {
            return
        }

        shadowFragment = ShadowFragment.newInstance()
        jobWantedDeleteDialogFragment = JobWantedDialogFragment.newInstance(JobWantedDialogFragment.DELETE, id)
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainScreen.id, shadowFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.fade_in_out, R.anim.fade_in_out
        )
        mTransaction.add(mainScreen.id, jobWantedDeleteDialogFragment!!).commit()

    }

    override fun shadowClicked() {
    }

    override fun onStart() {
        super.onStart()
        setActionBar(themeActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@JobWantedEditActivity, 0, themeActionBarFragment.toolbar1)

        themeActionBarFragment.toolbar1!!.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(R.anim.left_in,R.anim.right_out)
        }


    }

    /**
     *  得到返回的值
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            getIntentData(data!!)
        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras!!.get("bundle") as Bundle
        val userJobIntention = bundle.getParcelable<Parcelable>("userJobIntention") as UserJobIntention
        condition = bundle.getInt("condition")

        var mainScreenId = 1
        mainScreen = frameLayout {
            backgroundColor = Color.WHITE
            id = mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    themeActionBarFragment = ThemeActionBarFragment.newInstance(condition)
                    supportFragmentManager.beginTransaction().replace(id, themeActionBarFragment).commit()
                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                //list
                var recycleViewParentId = 3
                frameLayout {
                    id = recycleViewParentId
                    jobWantedListFragment = JobWantedListFragment.newInstance(userJobIntention, condition);
                    supportFragmentManager.beginTransaction().replace(id, jobWantedListFragment!!).commit()
                }.lparams {
                    height = matchParent
                    width = matchParent
                }

            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
    }

    //获取Intent数据
    fun getIntentData(intent: Intent) {
        if (intent != null) {
            if (intent.hasExtra("jobName")) {
                //在这里获取 选中行业的名字 和 ID
                //todoo
                var jobName = intent.getStringExtra("jobName")
                var jobId = intent.getStringExtra("jobId")
                jobWantedListFragment!!.setWantJobText(jobName)
                jobWantedListFragment!!.setJobIdText(jobId)
            }

            if (intent.hasExtra("cityModel")) {
                //在这里获取 选中城市的名字 和 ID
                //todoo
                var cityModel = intent.getStringExtra("cityModel")
                var cityArray = JSONArray(cityModel)
                println(cityArray)
                var addressArray = mutableListOf<String>()
                var addressIdArray = mutableListOf<String>()
                for (i in 0..cityArray.length() - 1) {
//                    if(cityArray.getJSONObject(i).getString("id") != ""){
                        addressArray.add(cityArray.getJSONObject(i).getString("name"))
//                    }
                }

                for (i in 0..cityArray.length() - 1) {
                    if(cityArray.getJSONObject(i).getString("id") != "") {
                        addressIdArray.add(cityArray.getJSONObject(i).getString("id"))
                    }
                }

                var myAddress = StringUtils.join(addressArray, "●")
                var myAddressId = StringUtils.join(addressIdArray, ",")
                jobWantedListFragment!!.setCity(myAddress)
                jobWantedListFragment!!.setAddressIdText(myAddressId)
            }
        }
    }

    // 点击选择只有单列的滚动选择器弹窗
    override fun oneDialogCLick(s: String) {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (shadowFragment != null || rollone != null) {
            return
        }
        shadowFragment = ShadowFragment.newInstance()

        when (s) {
            this.getString(R.string.jlWorkType) -> {
                val list = mutableListOf(this.getString(R.string.partTime), this.getString(R.string.fullTime))
                rollone = RollOneChooseFrag.newInstance(s, list)
            }
            this.getString(R.string.jlFindType) -> {
                val list = mutableListOf(
                    this.getString(R.string.personFullTime),
                    this.getString(R.string.personContract),
                    this.getString(R.string.personThree),
                    this.getString(R.string.personShort),
                    this.getString(R.string.personOther)
                )
                rollone = RollOneChooseFrag.newInstance(s, list)
            }
            "海外招聘" -> {
                val list = mutableListOf("接受", "不接受")
                rollone = RollOneChooseFrag.newInstance(s, list)
            }
        }

        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainScreen.id, shadowFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in, R.anim.bottom_in
        )
        mTransaction.add(mainScreen.id, rollone!!).commit()
    }

    // 点击选择有两列的滚动选择器弹窗
    override fun twoDialogCLick(s: String) {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (shadowFragment != null || rollone != null) {
            return
        }
        shadowFragment = ShadowFragment.newInstance()

        val list1 = mutableListOf(
            this.getString(R.string.hourly),
            this.getString(R.string.daySalary),
            this.getString(R.string.monthSalary),
            this.getString(R.string.yearSalary)
        )
        val list2 = mutableListOf("300", "2000", "3000", "4000", "5000")
        val list3 = mutableListOf("600", "10000", "15000", "20000")
        rollthree = RollThreeChooseFrag.newInstance(s, list1, list2, list3)

        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainScreen.id, shadowFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.bottom_in, R.anim.bottom_in
        )
        mTransaction.add(mainScreen.id, rollthree!!).commit()
    }

    // 单列滚动弹窗的取消按钮
    override fun rollOneCancel() {
        closeDialog()
    }

    // 单列滚动弹窗的确定按钮
    override fun rollOneConfirm(title: String, text: String) {
        when (title) {
            this.getString(R.string.jlWorkType) -> {
                jobWantedListFragment?.setJobtype(text)
            }
            this.getString(R.string.jlFindType) -> {
                jobWantedListFragment?.setRecruitWay(text)
            }
            "海外招聘" -> {
                jobWantedListFragment?.setOverseasRecruit(text)
            }
        }
        closeDialog()
    }

    // 两列滚动弹窗的取消按钮
    override fun rollThreeCancel() {
        closeDialog()
    }

    // 两列滚动弹窗的确定按钮
    override fun rollThreeConfirm(text1: String, text2: String, text3: String) {
        tool = BaseTool()
        var minResult = tool.moneyToString(text2.trim())
        var maxResult = tool.moneyToString(text3.trim())
        var result = mutableMapOf(
            "moneyType" to text1,
            "minResult" to minResult,
            "maxResult" to maxResult

        )
        jobWantedListFragment?.setSalary(result)
        closeDialog()
    }

    // 关闭所有fragment弹窗有关效果
    private fun closeDialog() {

        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if (jobWantedDeleteDialogFragment != null) {
            mTransaction.remove(jobWantedDeleteDialogFragment!!)
            jobWantedDeleteDialogFragment = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }

        if (rollone != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(rollone!!)
            rollone = null
        }

        if (rollthree != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(rollthree!!)
            rollthree = null
        }

        mTransaction.commit()
    }

    override fun submit() {
        jobWantedListFragment!!.getResult()
    }

    // 弹出更改确认窗口
    fun change() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (shadowFragment != null || jobWantedChangeDialogFragment != null) {
            return
        }

        shadowFragment = ShadowFragment.newInstance()
        jobWantedChangeDialogFragment = JobWantedDialogFragment.newInstance(JobWantedDialogFragment.CANCLE,"0")
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        mTransaction.add(mainScreen.id, shadowFragment!!)

        mTransaction.setCustomAnimations(
            R.anim.fade_in_out, R.anim.fade_in_out
        )
        mTransaction.add(mainScreen.id, jobWantedChangeDialogFragment!!).commit()
    }

    override fun changeResult(condition: Boolean) {
        if(condition){
            jobWantedListFragment!!.getResult()
        }
        closeDialog()
    }


}
