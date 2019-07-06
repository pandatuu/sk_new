package com.example.sk_android.mvp.view.activity.jobselect

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.widget.*
import com.example.sk_android.R
//import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.activity.person.PersonInformation
import com.example.sk_android.mvp.view.fragment.common.BottomMenuFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import com.example.sk_android.utils.MyDialog
import org.jetbrains.anko.*
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import org.json.JSONObject

class RecruitInfoShowActivity : AppCompatActivity(), ShadowFragment.ShadowClick,
    RecruitInfoSelectbarFragment.SelectBar,
    BottomMenuFragment.RecruitInfoBottomMenu,
    RecruitInfoSelectBarMenuPlaceFragment.RecruitInfoSelectBarMenuPlaceSelect,
    RecruitInfoSelectBarMenuCompanyFragment.RecruitInfoSelectBarMenuCompanySelect,
    RecruitInfoSelectBarMenuRequireFragment.RecruitInfoSelectBarMenuRequireSelect,
    RecruitInfoSelectBarMenuEmploymentTypeFragment.RecruitInfoSelectBarMenuEmploymentTypeSelect {
    private lateinit var myDialog: MyDialog


    var selectBarShow1: String = ""
    var selectBarShow2: String = ""
    var selectBarShow3: String = ""
    var selectBarShow4: String = ""

    var selectedItemsJson1 = JSONObject()
    var selectedItemsJson3 = JSONObject()
    var selectedItemsJson4 = JSONObject()


    lateinit var mainBody: FrameLayout
    lateinit var selectBar: FrameLayout

    // 0:有 1：无
    var condition: Int = 0
    /////

    //顶部actionBar
    lateinit var recruitInfoActionBarFragment: RecruitInfoActionBarFragment
    /////

    //4个下拉选择框
    var recruitInfoSelectBarMenuEmploymentTypeFragment: RecruitInfoSelectBarMenuEmploymentTypeFragment? = null
    var recruitInfoSelectBarMenuPlaceFragment: RecruitInfoSelectBarMenuPlaceFragment? = null
    var recruitInfoSelectBarMenuCompanyFragment: RecruitInfoSelectBarMenuCompanyFragment? = null
    var recruitInfoSelectBarMenuRequireFragment: RecruitInfoSelectBarMenuRequireFragment? = null
    /////

    //阴影
    var shadowFragment: ShadowFragment? = null
    /////

    //主体LIST展示
    var recruitInfoListFragment: RecruitInfoListFragment? = null
    /////

    //下面是筛选的条件
    var filterParamRecruitMethod: String?=null
    var filterParamWorkingType: String?=null
    var filterParamWorkingExperience: Int?=null
    var filterParamCurrencyType: String?=null
    var filterParamSalaryType: String?=null
    var filterParamSalaryMin: Int?=null
    var filterParamSalaryMax: Int?=null
    var filterParamAuditState: String?=null
    var filterParamEducationalBackground: String?=null
    var filterParamIndustryId: String?=null
    var filterParamAddress: String?=null
    var filterParamRadius: Number?=null
    var filterParamFinancingStage: String?=null
    var filterParamSize: String?=null

    /////








    //第一个选项被选择 招聘类型
    override fun getEmploymentTypeSelectedItems(jso: JSONObject?) {

        println("第一个被选择")
        println(jso)

        var key1="仕事のタイプ"
        if(jso!=null && jso!!.has(key1)){
            //工作类型（全职、兼职）
            var  value=jso.getJSONObject(key1).getString("value")
            var  index=jso.getJSONObject(key1).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                filterParamRecruitMethod=null

            }else{
                filterParamRecruitMethod=value
            }

        }else{
            filterParamRecruitMethod=null
        }

        recruitInfoListFragment!!.filterData(filterParamRecruitMethod,filterParamWorkingType, filterParamWorkingExperience, null, filterParamSalaryType, filterParamSalaryMin,
            filterParamSalaryMax, null, filterParamEducationalBackground, filterParamIndustryId, filterParamAddress, null,filterParamFinancingStage,filterParamSize
        )




        var mTransaction = supportFragmentManager.beginTransaction()

        var iterator = jso!!.keys().iterator()

        //获取选择的数量
        var i = 0
        while (iterator.hasNext()) {
            var key = iterator.next()
            if (jso.getJSONObject(key).getInt("index") != -1) {
                i = i + 1
            }
        }
        selectBarShow1 = i.toString()
        //选中的选项
        selectedItemsJson1 = jso!!

        if (i == 0) {
            selectBarShow1 = ""
        }



        var recruitInfoSelectbarFragment =
            RecruitInfoSelectbarFragment.newInstance(selectBarShow1, selectBarShow2, selectBarShow3, selectBarShow4);
        mTransaction.replace(selectBar.id, recruitInfoSelectbarFragment!!)

        if (recruitInfoSelectBarMenuEmploymentTypeFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(recruitInfoSelectBarMenuEmploymentTypeFragment!!)
            recruitInfoSelectBarMenuEmploymentTypeFragment = null
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


    //seleced 地点 收回下拉框
    override fun getPlaceSelected(item: SelectedItem) {

        println("第二个被选择")
        println(item)



        val value=item.value
        if(value!=null && !value.equals("") && !value.equals("ALL")){
            filterParamAddress=value

        }else{
            filterParamAddress=null
        }

        recruitInfoListFragment!!.filterData(filterParamRecruitMethod,filterParamWorkingType, filterParamWorkingExperience, null, filterParamSalaryType, filterParamSalaryMin,
            filterParamSalaryMax, null, filterParamEducationalBackground, filterParamIndustryId, filterParamAddress, null,filterParamFinancingStage,filterParamSize
        )




        selectBarShow2 = item.name

        var mTransaction = supportFragmentManager.beginTransaction()
        var recruitInfoSelectbarFragment =
            RecruitInfoSelectbarFragment.newInstance(selectBarShow1, item.name, selectBarShow3, selectBarShow4);
        mTransaction.replace(selectBar.id, recruitInfoSelectbarFragment!!)

        if (recruitInfoSelectBarMenuPlaceFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(recruitInfoSelectBarMenuPlaceFragment!!)
            recruitInfoSelectBarMenuPlaceFragment = null
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

    //seleced 公司要求选项 并 收回下拉框
    override fun getCompanySelectedItems(jso: JSONObject?) {


        println("第三个被选择")
        println(jso)



        var key4="求人手段"
        if(jso!=null && jso!!.has(key4)){
            //求人手段(岗位类型)
            var  value=jso.getJSONObject(key4).getString("value")
            var  index=jso.getJSONObject(key4).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                filterParamWorkingType=null
            }else{
                filterParamWorkingType=value
            }

        }else{
            filterParamWorkingType=null
        }



        var key3="業種"
        if(jso!=null && jso!!.has(key3)){
            //融资情况（上市、未上市）
            var  value=jso.getJSONObject(key3).getString("value")
            var  index=jso.getJSONObject(key3).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                filterParamIndustryId=null
            }else{
                filterParamIndustryId=value
            }

        }else{
            filterParamIndustryId=null
        }



        var key2="会社規模"
        if(jso!=null && jso!!.has(key2)){
            //公司规模
            var  value=jso.getJSONObject(key2).getString("value")
            var  index=jso.getJSONObject(key2).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                filterParamSize=null
            }else{
                filterParamSize=value
            }

        }else{
            filterParamSize=null
        }


        var key1="融資段階"
        if(jso!=null && jso!!.has(key1)){
            //融资情况（上市、未上市）
            var  value=jso.getJSONObject(key1).getString("value")
            var  index=jso.getJSONObject(key1).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                filterParamFinancingStage=null

            }else{
                filterParamFinancingStage=value
            }

        }else{
            filterParamFinancingStage=null
        }


        recruitInfoListFragment!!.filterData(filterParamRecruitMethod,filterParamWorkingType, filterParamWorkingExperience, null, filterParamSalaryType, filterParamSalaryMin,
            filterParamSalaryMax, null, filterParamEducationalBackground, filterParamIndustryId, filterParamAddress, null,filterParamFinancingStage,filterParamSize
        )









        var mTransaction = supportFragmentManager.beginTransaction()

        var iterator = jso!!.keys().iterator()

        //获取选择的数量
        var i = 0
        while (iterator.hasNext()) {
            var key = iterator.next()
            if (jso.getJSONObject(key).getInt("index") != -1) {
                i = i + 1
            }
        }
        selectBarShow3 = i.toString()
        //选中的选项
        selectedItemsJson3 = jso!!

        if (i == 0) {
            selectBarShow3 = ""
        }


        var recruitInfoSelectbarFragment =
            RecruitInfoSelectbarFragment.newInstance(selectBarShow1, selectBarShow2, selectBarShow3, selectBarShow4);
        mTransaction.replace(selectBar.id, recruitInfoSelectbarFragment!!)

        if (recruitInfoSelectBarMenuCompanyFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(recruitInfoSelectBarMenuCompanyFragment!!)
            recruitInfoSelectBarMenuCompanyFragment = null
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

    //seleced 要求选项 并 收回下拉框
    override fun getRequireSelectedItems(json: JSONObject?) {



        println("第四个被选择")
        println(json)






        var key4="学歴"
        if(json!=null && json!!.has(key4)){
            //教育经历
            var  value=json.getJSONObject(key4).getString("value")
            var  index=json.getJSONObject(key4).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                filterParamEducationalBackground=null
            }else{
                filterParamEducationalBackground=value
            }

        }else{
            filterParamEducationalBackground=null
        }



        var key3="経験"
        if(json!=null && json!!.has(key3)){
            //融资情况（上市、未上市）
            var  value=json.getJSONObject(key3).getString("value")
            var  index=json.getJSONObject(key3).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                filterParamWorkingExperience=null
            }else{
                filterParamWorkingExperience=value.toInt()
            }

        }else{
            filterParamWorkingExperience=null
        }



        var key2="薪资类型"
        if(json!=null && json!!.has(key2)){
            //公司规模
            var  value=json.getJSONObject(key2).getString("value")
            var  index=json.getJSONObject(key2).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                filterParamSalaryType=null
            }else{
                filterParamSalaryType=value
            }

        }else{
            filterParamSalaryType=null
        }


        var key1="希望月収"
        if(json!=null && json!!.has(key1)){
            //融资情况（上市、未上市）
            var  value=json.getJSONObject(key1).getString("value")
            var  index=json.getJSONObject(key1).getInt("index")
            if(value==null || "".equals(value) || "ALL".equals(value) || index<0){
                 filterParamSalaryMin=null
                 filterParamSalaryMax=null
            }else{
                var strs=value.split("-")
                filterParamSalaryMin=strs[0].toInt()
                filterParamSalaryMax=strs[1].toInt()
            }

        }else{
            filterParamSalaryMin=null
            filterParamSalaryMax=null
        }


        recruitInfoListFragment!!.filterData(filterParamRecruitMethod,filterParamWorkingType, filterParamWorkingExperience, null, filterParamSalaryType, filterParamSalaryMin,
            filterParamSalaryMax, null, filterParamEducationalBackground, filterParamIndustryId, filterParamAddress, null,filterParamFinancingStage,filterParamSize
        )






        var mTransaction = supportFragmentManager.beginTransaction()
        var iterator = json!!.keys().iterator()

        //获取选择的数量
        var i = 0
        while (iterator.hasNext()) {
            var key = iterator.next()
            if (json.getJSONObject(key).getInt("index") != -1) {
                i = i + 1
            }
        }
        selectBarShow4 = i.toString()
        //选中的选项
        selectedItemsJson4 = json!!

        if (i == 0) {
            selectBarShow4 = ""
        }


        var recruitInfoSelectbarFragment =
            RecruitInfoSelectbarFragment.newInstance(selectBarShow1, selectBarShow2, selectBarShow3, selectBarShow4);
        mTransaction.replace(selectBar.id, recruitInfoSelectbarFragment!!)

        if (recruitInfoSelectBarMenuRequireFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(recruitInfoSelectBarMenuRequireFragment!!)
            recruitInfoSelectBarMenuRequireFragment = null
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

    override fun getSelectedMenu() {

    }

    //根据点击的类型，弹出不同的下拉框
    override fun getSelectBarItem(index: Int) {
        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if (recruitInfoSelectBarMenuEmploymentTypeFragment != null && index.equals(0)) {
            if (recruitInfoSelectBarMenuEmploymentTypeFragment != null) {
                mTransaction.setCustomAnimations(
                    R.anim.top_out, R.anim.top_out
                )
                mTransaction.remove(recruitInfoSelectBarMenuEmploymentTypeFragment!!)
                recruitInfoSelectBarMenuEmploymentTypeFragment = null
            }
            if (shadowFragment != null) {
                mTransaction.setCustomAnimations(
                    R.anim.fade_in_out, R.anim.fade_in_out
                )
                mTransaction.remove(shadowFragment!!)
                shadowFragment = null

            }
            mTransaction.commit()
            return
        }

        if (recruitInfoSelectBarMenuPlaceFragment != null && index.equals(1)) {
            if (recruitInfoSelectBarMenuPlaceFragment != null) {
                mTransaction.setCustomAnimations(
                    R.anim.top_out, R.anim.top_out
                )
                mTransaction.remove(recruitInfoSelectBarMenuPlaceFragment!!)
                recruitInfoSelectBarMenuPlaceFragment = null
            }
            if (shadowFragment != null) {
                mTransaction.setCustomAnimations(
                    R.anim.fade_in_out, R.anim.fade_in_out
                )
                mTransaction.remove(shadowFragment!!)
                shadowFragment = null

            }
            mTransaction.commit()
            return
        }

        if (recruitInfoSelectBarMenuCompanyFragment != null && index.equals(2)) {
            if (recruitInfoSelectBarMenuCompanyFragment != null) {
                mTransaction.setCustomAnimations(
                    R.anim.top_out, R.anim.top_out
                )
                mTransaction.remove(recruitInfoSelectBarMenuCompanyFragment!!)
                recruitInfoSelectBarMenuCompanyFragment = null
            }
            if (shadowFragment != null) {
                mTransaction.setCustomAnimations(
                    R.anim.fade_in_out, R.anim.fade_in_out
                )
                mTransaction.remove(shadowFragment!!)
                shadowFragment = null

            }
            mTransaction.commit()
            return
        }

        if (recruitInfoSelectBarMenuRequireFragment != null && index.equals(3)) {
            if (recruitInfoSelectBarMenuRequireFragment != null) {
                mTransaction.setCustomAnimations(
                    R.anim.top_out, R.anim.top_out
                )
                mTransaction.remove(recruitInfoSelectBarMenuRequireFragment!!)
                recruitInfoSelectBarMenuRequireFragment = null
            }
            if (shadowFragment != null) {
                mTransaction.setCustomAnimations(
                    R.anim.fade_in_out, R.anim.fade_in_out
                )
                mTransaction.remove(shadowFragment!!)
                shadowFragment = null

            }
            mTransaction.commit()
            return
        }


        if (recruitInfoSelectBarMenuEmploymentTypeFragment != null) {
            mTransaction.remove(recruitInfoSelectBarMenuEmploymentTypeFragment!!)
            recruitInfoSelectBarMenuEmploymentTypeFragment = null
        }
        if (recruitInfoSelectBarMenuPlaceFragment != null) {
            mTransaction.remove(recruitInfoSelectBarMenuPlaceFragment!!)
            recruitInfoSelectBarMenuPlaceFragment = null

        }
        if (recruitInfoSelectBarMenuCompanyFragment != null) {
            mTransaction.remove(recruitInfoSelectBarMenuCompanyFragment!!)
            recruitInfoSelectBarMenuCompanyFragment = null
        }
        if (recruitInfoSelectBarMenuRequireFragment != null) {
            mTransaction.remove(recruitInfoSelectBarMenuRequireFragment!!)
            recruitInfoSelectBarMenuRequireFragment = null
        }

        if (shadowFragment == null) {
            shadowFragment = ShadowFragment.newInstance();
            mTransaction.add(mainBody.id, shadowFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.top_in,
            R.anim.top_in
        )

        if (index.equals(0)) {
            recruitInfoSelectBarMenuEmploymentTypeFragment =
                RecruitInfoSelectBarMenuEmploymentTypeFragment.newInstance(selectedItemsJson1);
            mTransaction.add(mainBody.id, recruitInfoSelectBarMenuEmploymentTypeFragment!!)
        }
        if (index.equals(1)) {
            recruitInfoSelectBarMenuPlaceFragment = RecruitInfoSelectBarMenuPlaceFragment.newInstance(selectBarShow2);
            mTransaction.add(mainBody.id, recruitInfoSelectBarMenuPlaceFragment!!)
        }
        if (index.equals(2)) {

            recruitInfoSelectBarMenuCompanyFragment =
                RecruitInfoSelectBarMenuCompanyFragment.newInstance(selectedItemsJson3);
            mTransaction.add(mainBody.id, recruitInfoSelectBarMenuCompanyFragment!!)
        }
        if (index.equals(3)) {
            recruitInfoSelectBarMenuRequireFragment =
                RecruitInfoSelectBarMenuRequireFragment.newInstance(selectedItemsJson4);
            mTransaction.add(mainBody.id, recruitInfoSelectBarMenuRequireFragment!!)
        }

        mTransaction.commit()
    }


    //收回下拉框
    override fun shadowClicked() {

        var mTransaction = supportFragmentManager.beginTransaction()
        if (recruitInfoSelectBarMenuEmploymentTypeFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(recruitInfoSelectBarMenuEmploymentTypeFragment!!)
            recruitInfoSelectBarMenuEmploymentTypeFragment = null
        }
        if (recruitInfoSelectBarMenuPlaceFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(recruitInfoSelectBarMenuPlaceFragment!!)
            recruitInfoSelectBarMenuPlaceFragment = null
        }
        if (recruitInfoSelectBarMenuCompanyFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(recruitInfoSelectBarMenuCompanyFragment!!)
            recruitInfoSelectBarMenuCompanyFragment = null
        }
        if (recruitInfoSelectBarMenuRequireFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(recruitInfoSelectBarMenuRequireFragment!!)
            recruitInfoSelectBarMenuRequireFragment = null
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


    //回调传值
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            getIntentData(data)
        }
    }

    //获取Intent数据
    fun getIntentData(intent: Intent) {
        if (intent != null) {
            var position = intent.getIntExtra("position", -1)
            var isCollection = intent.getBooleanExtra("isCollection", false)
            var collectionId = intent.getStringExtra("collectionId")


            println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
            println(position)
            println(isCollection)
            println(collectionId)

            recruitInfoListFragment!!.getCallBackData(position, isCollection, collectionId)
        }
    }


    override fun onStart() {
        super.onStart()
        setActionBar(recruitInfoActionBarFragment.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@RecruitInfoShowActivity, 0, recruitInfoActionBarFragment.toolbar1)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {

        condition = intent.getIntExtra("condition", 0)


        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();


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

        frameLayout {
            backgroundColor = Color.WHITE
            verticalLayout {
                //ActionBar
                var actionBarId = 2
                frameLayout {
                    id = actionBarId
                    recruitInfoActionBarFragment = RecruitInfoActionBarFragment.newInstance();
                    supportFragmentManager.beginTransaction().replace(id, recruitInfoActionBarFragment).commit()


                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                //selectBar
                var selectBarId = 3
                selectBar = frameLayout {
                    id = selectBarId
                    var recruitInfoSelectbarFragment = RecruitInfoSelectbarFragment.newInstance("", "勤務地", "", "");
                    supportFragmentManager.beginTransaction().replace(id, recruitInfoSelectbarFragment!!).commit()
                }.lparams {
                    height = wrapContent
                    width = matchParent
                }

                var mainBodyId = 6
                mainBody = frameLayout {
                    id = mainBodyId
                    verticalLayout {
                        //list
                        var listParentId = 4
                        frameLayout {
                            id = listParentId
                            recruitInfoListFragment = RecruitInfoListFragment.newInstance(null,null,null);
                            supportFragmentManager.beginTransaction().replace(id, recruitInfoListFragment!!).commit()
                        }.lparams {
                            height = 0
                            weight = 1f
                            width = matchParent
                        }
                        //menu
                        var bottomMenuId = 5
                        frameLayout {
                            id = bottomMenuId
                            var recruitInfoBottomMenuFragment = BottomMenuFragment.newInstance(0);
                            supportFragmentManager.beginTransaction().replace(id, recruitInfoBottomMenuFragment!!)
                                .commit()
                        }.lparams {
                            height = wrapContent
                            width = matchParent
                        }

                    }.lparams {
                        height = matchParent
                        width = matchParent
                    }
                }.lparams {
                    height = 0
                    weight = 1f
                    width = matchParent
                }


            }.lparams() {
                width = matchParent
                height = matchParent
            }

        }


        decisionState()
    }

    private fun decisionState() {
        if (condition == 0) {
            println("个人有状态")
//            afterShowLoading()
        } else {
            println("个人无状态")
            afterShowLoading()
        }
    }

    //弹出更新窗口
    fun afterShowLoading() {
        val myDialog = MyDialog(this@RecruitInfoShowActivity)
        myDialog.show()
        var test = myDialog.startPage()
        test.setOnClickListener {
            startActivity<PersonInformation>()
        }

    }

}