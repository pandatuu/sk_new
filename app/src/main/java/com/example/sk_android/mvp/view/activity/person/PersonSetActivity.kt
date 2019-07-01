package com.example.sk_android.mvp.view.activity.person

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.fragment.onlineresume.ResumeBackgroundFragment
import com.example.sk_android.mvp.view.fragment.person.JobListFragment
import com.example.sk_android.mvp.view.fragment.person.PersonApi
import com.example.sk_android.mvp.view.fragment.person.PsActionBarFragment
import com.example.sk_android.mvp.view.fragment.person.PsMainBodyFragment
import com.example.sk_android.utils.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import android.content.pm.PackageManager
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import com.example.sk_android.mvp.view.fragment.common.BottomMenuFragment
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import retrofit2.adapter.rxjava2.HttpException


class PersonSetActivity : AppCompatActivity(), PsMainBodyFragment.JobWanted, JobListFragment.CancelTool,
    ShadowFragment.ShadowClick,
    BottomMenuFragment.RecruitInfoBottomMenu,
    BottomSelectDialogFragment.BottomSelectDialogSelect {

    lateinit var baseFragment: FrameLayout
    var wsBackgroundFragment: ResumeBackgroundFragment? = null
    var wsListFragment: JobListFragment? = null
    var mTransaction: FragmentTransaction? = null
    var editAlertDialog: BottomSelectDialogFragment? = null
    var shadowFragment: ShadowFragment? = null
    lateinit var psMainBodyFragment:PsMainBodyFragment

    var statusList: MutableList<String> = mutableListOf()

    var psActionBarFragment: PsActionBarFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        statusList.add(this.getString(R.string.IiStatusOne))
        statusList.add(this.getString(R.string.IiStatusTwo))
        statusList.add(this.getString(R.string.IiStatusThree))
        statusList.add(this.getString(R.string.IiStatusFour))


        var mainScreenId = 1

        PushAgent.getInstance(this).onAppStart()

        baseFragment = frameLayout {

            id = mainScreenId
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    psActionBarFragment = PsActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, psActionBarFragment!!).commit()

                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var newFragmentId = 3
                frameLayout {
                    id = newFragmentId
                    psMainBodyFragment = PsMainBodyFragment.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, psMainBodyFragment).commit()
                }.lparams(width = matchParent, height = 0){
                    weight = 1f
                }

                //menu
                var bottomMenuId=5
                frameLayout {
                    id=bottomMenuId
                    var recruitInfoBottomMenuFragment= BottomMenuFragment.newInstance(3)
                    supportFragmentManager.beginTransaction().replace(id,recruitInfoBottomMenuFragment!!).commit()
                }.lparams {
                    height=wrapContent
                    width= matchParent
                }


            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }
    }


    override fun onStart() {
        super.onStart()
        initData()
        setActionBar(psActionBarFragment!!.toolbar)
        StatusBarUtil.setTranslucentForImageView(this@PersonSetActivity, 0, psActionBarFragment!!.toolbar)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun jobItem() {
        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance()
            mTransaction.add(baseFragment.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        editAlertDialog = BottomSelectDialogFragment.newInstance(this.getString(R.string.jobSearchStatus), statusList)
        mTransaction.add(baseFragment.id, editAlertDialog!!)
        mTransaction.commit()
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

    //关闭弹窗
    fun closeAlertDialog() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (editAlertDialog != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(editAlertDialog!!)
            editAlertDialog = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }
        mTransaction.commit()
    }


    @SuppressLint("CheckResult")
    private fun initData() {
        var personMap = mapOf<String, String>()
        var workStatu = ""
        var retrofitUils = RetrofitUtils(this, this.getString(R.string.userUrl))
        retrofitUils.create(PersonApi::class.java)
            .information
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                var imageUrl: String
                var name: String

                var statu = it.get("auditState").toString().replace("\"","")
                if(statu.equals("PENDING")){
                    imageUrl = it.get("changedContent").asJsonObject.get("avatarURL").toString().replace("\"","")
                    name = it.get("changedContent").asJsonObject.get("displayName").toString().replace("\"","")
                }else{
                    imageUrl = it.get("avatarURL").toString().replace("\"","")
                    name = it.get("displayName").toString().replace("\"", "")
                }

                // 测试图片  "https://sk-user-head.s3.ap-northeast-1.amazonaws.com/19d14846-a932-43ed-b04b-88245846c587"
                psActionBarFragment!!.changePage(imageUrl, name)
            }, {
                if(it is HttpException){
                    if(it.code() == 401){
                        
                    }
                }
            })

        retrofitUils.create(PersonApi::class.java)
            .jobStatu
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                psMainBodyFragment.initStatu(it)
            }, {

            })

        var jobRetrofitUils = RetrofitUtils(this, this.getString(R.string.jobUrl))
        jobRetrofitUils.create(PersonApi::class.java)
            .getPersonFavorites("ORGANIZATION_POSITION")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                psMainBodyFragment.initFour(it.get("total").toString())
            }, {

            })

        var interRetrofitUtils = RetrofitUtils(this,this.getString(R.string.interUrl))
        interRetrofitUtils.create(PersonApi::class.java)
            .getPersonInformation(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                psMainBodyFragment.initTwo(it.get("total").toString())
            }, {

            })

        var interViewRetrofitUtils = RetrofitUtils(this,this.getString(R.string.interUrl))
        interViewRetrofitUtils.create(PersonApi::class.java)
            .getExchangedInformation("EXCHANGED")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                psMainBodyFragment.initThree(it.get("total").toString())
            }, {

            })



    }

    override fun shadowClicked() {
        closeAlertDialog()
    }

    override fun getBottomSelectDialogSelect() {
        closeAlertDialog()
    }

    override fun getback(index: Int, list: MutableList<String>) {
        println(list)
        println(list[index])

        psMainBodyFragment.setData(list[index])
        closeAlertDialog()
    }

    override fun getSelectedMenu() {
    }
}