package com.example.sk_android.mvp.view.activity.jobselect


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.sk_android.R

import com.example.sk_android.mvp.model.jobselect.JobSearchUnderSearching
import com.example.sk_android.mvp.model.jobselect.SelectedItem
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoListFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectBarMenuFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.*
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class JobSearchWithHistoryActivity : AppCompatActivity(), JobSearcherWithHistoryFragment.SendSearcherText,
    JobSearcherHistoryFragment.HistoryText,
    JobSearchSelectbarFragment.JobSearchSelectBar,
    JobSearchUnderSearchingDisplayFragment.UnderSearching, ShadowFragment.ShadowClick,
    RecruitInfoSelectBarMenuCompanyFragment.RecruitInfoSelectBarMenuCompanySelect,
    RecruitInfoSelectBarMenuRequireFragment.RecruitInfoSelectBarMenuRequireSelect,
    CompanyInfoSelectbarFragment.SelectBar, CompanyInfoSelectBarMenuFragment.SelectBarMenuSelect {


    var type_job_or_company_search = 2  //1:职位 2,公司

    var jobSearcherWithHistoryFragment: JobSearcherWithHistoryFragment? = null
    var jobSearcherHistoryFragment: JobSearcherHistoryFragment? = null
    var recruitInfoListFragment: RecruitInfoListFragment? = null
    var jobSearchUnderSearchingDisplayFragment: JobSearchUnderSearchingDisplayFragment? = null
    var jobSearchSelectbarFragment: JobSearchSelectbarFragment? = null
    var recruitInfoSelectBarMenuCompanyFragment: RecruitInfoSelectBarMenuCompanyFragment? = null
    var recruitInfoSelectBarMenuRequireFragment: RecruitInfoSelectBarMenuRequireFragment? = null
    var companyInfoSelectbarFragment: CompanyInfoSelectbarFragment? = null

    var companyInfoListFragment: CompanyInfoListFragment? = null

    var shadowFragment: ShadowFragment? = null

    lateinit var recycleViewParent: FrameLayout
    lateinit var searchBarParent: FrameLayout


    var selectBarShow1: String = ""
    var selectBarShow2: String = ""

    var selectBarShow3: String = ""
    var selectBarShow4: String = ""

    var list = LinkedList<Map<String, Any>>()
    var histroyList: Array<String> = arrayOf("公司会计", "医師", "演员", "搬砖工", "架构师", "漫画家", "动漫", "インターネット")


    var selectedItemsJson3: JSONObject = JSONObject()


    var selectedJson4: JSONObject = JSONObject()

    var companyInfoSelectBarMenuFragment1: CompanyInfoSelectBarMenuFragment? = null
    var companyInfoSelectBarMenuFragment2: CompanyInfoSelectBarMenuFragment? = null
    var companyInfoSelectBarMenuFragment3: CompanyInfoSelectBarMenuFragment? = null
    var companyInfoSelectBarMenuFragment4: CompanyInfoSelectBarMenuFragment? = null


    var selectedItem1: MutableList<String> = mutableListOf()
    var selectedItem2: MutableList<String> = mutableListOf()
    var selectedItem3: MutableList<String> = mutableListOf()
    var selectedItem4: MutableList<String> = mutableListOf()


    //下面是筛选的条件
    var filterParamRecruitMethod: String? = null
    var filterParamWorkingType: String? = null
    var filterParamWorkingExperience: Int? = null
    var filterParamCurrencyType: String? = null
    var filterParamSalaryType: String? = null
    var filterParamSalaryMin: Int? = null
    var filterParamSalaryMax: Int? = null
    var filterParamAuditState: String? = null
    var filterParamEducationalBackground: String? = null
    var filterParamIndustryId: String? = null
    var filterParamAddress: String? = null
    var filterParamRadius: Number? = null
    var filterParamFinancingStage: String? = null
    var filterParamSize: String? = null

    /////
    //东京的地区ID
    var cityId = ""


    //筛选的参数
    var companyFilterParamAcronym: String? = null
    var companyFilterParamSize: String? = null
    var companyFilterParamFinancingStage: String? = null
    var companyFilterParamType: String? = null
    var companyFilterParamCoordinate: String? = null
    var companyFilterParamRadius: Number? = null
    var companyFilterParamIndustryId: String? = null


    //更改 公司搜索 的 select bar 的显示
    override fun getSelectedItems(index: Int, list: MutableList<SelectedItem>) {
        var mTransaction = supportFragmentManager.beginTransaction()

        var sizeString = ""
        if (list != null && list.size != 0) {
            sizeString = list.size.toString()


            var value = list.get(0).value


            if (index == 0) {
                if (value != null && !value.equals("") && !value.equals("ALL")) {
                    companyFilterParamFinancingStage = value
                } else {
                    companyFilterParamFinancingStage = null
                }
            } else if (index == 1) {
                if (value != null && !value.equals("") && !value.equals("ALL")) {
                    companyFilterParamSize = value
                } else {
                    companyFilterParamSize = null
                }
            } else if (index == 2) {
                if (value != null && !value.equals("") && !value.equals("ALL")) {
                    companyFilterParamIndustryId = value
                } else {
                    companyFilterParamIndustryId = null
                }
            } else if (index == 3) {
                if (value != null && !value.equals("") && !value.equals("ALL")) {
                    companyFilterParamType = value
                } else {
                    companyFilterParamType = null
                }
            }

        } else {
            if (index == 0) {
                companyFilterParamFinancingStage = null
            } else if (index == 1) {
                companyFilterParamSize = null

            } else if (index == 2) {
                companyFilterParamIndustryId = null

            } else if (index == 3) {
                companyFilterParamType = null

            }
        }


        println(filterParamAddress.toString())


        companyInfoListFragment!!.filterData(
            companyFilterParamAcronym,
            companyFilterParamSize,
            companyFilterParamFinancingStage,
            companyFilterParamType,
            companyFilterParamCoordinate,
            companyFilterParamRadius,
            companyFilterParamIndustryId,
            filterParamAddress
        )

        if (index == 0) {
            selectBarShow1 = sizeString
            if (list.size == 0) {
                selectedItem1 = mutableListOf("")
            } else {
                selectedItem1 = mutableListOf(list.get(0).name)
            }
            fragmentTopOut(companyInfoSelectBarMenuFragment1)
            companyInfoSelectBarMenuFragment1 = null
        } else if (index == 1) {
            selectBarShow2 = sizeString
            if (list.size == 0) {
                selectedItem2 = mutableListOf("")
            } else {
                selectedItem2 = mutableListOf(list.get(0).name)
            }
            fragmentTopOut(companyInfoSelectBarMenuFragment2)
            companyInfoSelectBarMenuFragment2 = null
        } else if (index == 2) {
            selectBarShow3 = sizeString
            if (list.size == 0) {
                selectedItem3 = mutableListOf("")
            } else {
                selectedItem3 = mutableListOf(list.get(0).name)
            }
            fragmentTopOut(companyInfoSelectBarMenuFragment3)
            companyInfoSelectBarMenuFragment3 = null
        } else if (index == 3) {
            selectBarShow4 = sizeString
            if (list.size == 0) {
                selectedItem4 = mutableListOf("")
            } else {
                selectedItem4 = mutableListOf(list.get(0).name)
            }
            fragmentTopOut(companyInfoSelectBarMenuFragment4)
            companyInfoSelectBarMenuFragment4 = null
        }



        companyInfoSelectbarFragment =
            CompanyInfoSelectbarFragment.newInstance(selectBarShow1, selectBarShow2, selectBarShow3, selectBarShow4);
        mTransaction.replace(searchBarParent.id, companyInfoSelectbarFragment!!)


        fragmentFadeOut(shadowFragment)
        shadowFragment = null

        mTransaction.commit()

    }


    //职位搜索 条件选择
    override fun getRequireSelectedItems(json: JSONObject?) {


        var key4 = "学歴"
        if (json != null && json!!.has(key4)) {
            //教育经历
            var value = json.getJSONObject(key4).getString("value")
            var index = json.getJSONObject(key4).getInt("index")
            if (value == null || "".equals(value) || "ALL".equals(value) || index < 0) {
                filterParamEducationalBackground = null
            } else {
                filterParamEducationalBackground = value
            }

        } else {
            filterParamEducationalBackground = null
        }


        var key3 = "経験"
        if (json != null && json!!.has(key3)) {
            //融资情况（上市、未上市）
            var value = json.getJSONObject(key3).getString("value")
            var index = json.getJSONObject(key3).getInt("index")
            if (value == null || "".equals(value) || "ALL".equals(value) || index < 0) {
                filterParamWorkingExperience = null
            } else {
                filterParamWorkingExperience = value.toInt()
            }

        } else {
            filterParamWorkingExperience = null
        }


        var key2 = "薪资类型"
        if (json != null && json!!.has(key2)) {
            //公司规模
            var value = json.getJSONObject(key2).getString("value")
            var index = json.getJSONObject(key2).getInt("index")
            if (value == null || "".equals(value) || "ALL".equals(value) || index < 0) {
                filterParamSalaryType = null
            } else {
                filterParamSalaryType = value
            }

        } else {
            filterParamSalaryType = null
        }


        var key1 = "希望月収"
        if (json != null && json!!.has(key1)) {
            //融资情况（上市、未上市）
            var value = json.getJSONObject(key1).getString("value")
            var index = json.getJSONObject(key1).getInt("index")
            if (value == null || "".equals(value) || "ALL".equals(value) || index < 0) {
                filterParamSalaryMin = null
                filterParamSalaryMax = null
            } else {
                var strs = value.split("-")
                filterParamSalaryMin = strs[0].toInt()
                filterParamSalaryMax = strs[1].toInt()
            }

        } else {
            filterParamSalaryMin = null
            filterParamSalaryMax = null
        }


        recruitInfoListFragment!!.filterData(
            filterParamRecruitMethod,
            filterParamWorkingType,
            filterParamWorkingExperience,
            null,
            filterParamSalaryType,
            filterParamSalaryMin,
            filterParamSalaryMax,
            null,
            filterParamEducationalBackground,
            filterParamIndustryId,
            filterParamAddress,
            null,
            filterParamFinancingStage,
            filterParamSize
        )


        var mTransaction = supportFragmentManager.beginTransaction()

        var iterator = json!!.keys().iterator()

        var i = 0
        while (iterator.hasNext()) {
            var key = iterator.next()
            if (json.getJSONObject(key).getInt("index") != -1) {
                i = i + 1
            }
        }
        selectBarShow4 = i.toString()
        //选中的选项
        selectedJson4 = json!!

        if (i == 0) {
            selectBarShow4 = ""
        }

        jobSearchSelectbarFragment = JobSearchSelectbarFragment.newInstance(selectBarShow3, selectBarShow4);
        mTransaction.replace(searchBarParent.id, jobSearchSelectbarFragment!!)

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

    //职位搜索 条件选择
    override fun getCompanySelectedItems(jso: JSONObject?) {


        var key4 = "求人手段"
        if (jso != null && jso!!.has(key4)) {
            //求人手段(岗位类型)
            var value = jso.getJSONObject(key4).getString("value")
            var index = jso.getJSONObject(key4).getInt("index")
            if (value == null || "".equals(value) || "ALL".equals(value) || index < 0) {
                filterParamWorkingType = null
            } else {
                filterParamWorkingType = value
            }

        } else {
            filterParamWorkingType = null
        }


        var key3 = "業種"
        if (jso != null && jso!!.has(key3)) {
            //融资情况（上市、未上市）
            var value = jso.getJSONObject(key3).getString("value")
            var index = jso.getJSONObject(key3).getInt("index")
            if (value == null || "".equals(value) || "ALL".equals(value) || index < 0) {
                filterParamIndustryId = null
            } else {
                filterParamIndustryId = value
            }

        } else {
            filterParamIndustryId = null
        }


        var key2 = "会社規模"
        if (jso != null && jso!!.has(key2)) {
            //公司规模
            var value = jso.getJSONObject(key2).getString("value")
            var index = jso.getJSONObject(key2).getInt("index")
            if (value == null || "".equals(value) || "ALL".equals(value) || index < 0) {
                filterParamSize = null
            } else {
                filterParamSize = value
            }

        } else {
            filterParamSize = null
        }


        var key1 = "融資段階"
        if (jso != null && jso!!.has(key1)) {
            //融资情况（上市、未上市）
            var value = jso.getJSONObject(key1).getString("value")
            var index = jso.getJSONObject(key1).getInt("index")
            if (value == null || "".equals(value) || "ALL".equals(value) || index < 0) {
                filterParamFinancingStage = null

            } else {
                filterParamFinancingStage = value
            }

        } else {
            filterParamFinancingStage = null
        }


        recruitInfoListFragment!!.filterData(
            filterParamRecruitMethod,
            filterParamWorkingType,
            filterParamWorkingExperience,
            null,
            filterParamSalaryType,
            filterParamSalaryMin,
            filterParamSalaryMax,
            null,
            filterParamEducationalBackground,
            filterParamIndustryId,
            filterParamAddress,
            null,
            filterParamFinancingStage,
            filterParamSize
        )


        var mTransaction = supportFragmentManager.beginTransaction()


        var iterator = jso!!.keys().iterator()


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


        jobSearchSelectbarFragment = JobSearchSelectbarFragment.newInstance(selectBarShow3, selectBarShow4);
        mTransaction.replace(searchBarParent.id, jobSearchSelectbarFragment!!)

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


    override fun shadowClicked() {
        var mTransaction = supportFragmentManager.beginTransaction()

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


    override fun getSelectBarItem(index: Int) {
        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)



        if (type_job_or_company_search == 1) {
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
                mTransaction.add(recycleViewParent.id, shadowFragment!!)
            }

            mTransaction.setCustomAnimations(
                R.anim.top_in,
                R.anim.top_in
            )


            if (index.equals(2)) {
                recruitInfoSelectBarMenuCompanyFragment =
                    RecruitInfoSelectBarMenuCompanyFragment.newInstance(selectedItemsJson3);
                mTransaction.add(recycleViewParent.id, recruitInfoSelectBarMenuCompanyFragment!!)
            }
            if (index.equals(3)) {
                recruitInfoSelectBarMenuRequireFragment =
                    RecruitInfoSelectBarMenuRequireFragment.newInstance(selectedJson4);
                mTransaction.add(recycleViewParent.id, recruitInfoSelectBarMenuRequireFragment!!)
            }

        } else if (type_job_or_company_search == 2) {
            if (companyInfoSelectBarMenuFragment1 != null && index.equals(0)) {
                fragmentTopOut(companyInfoSelectBarMenuFragment1)
                companyInfoSelectBarMenuFragment1 = null

                fragmentFadeOut(shadowFragment)
                shadowFragment = null
                mTransaction.commit()
                return
            }

            if (companyInfoSelectBarMenuFragment2 != null && index.equals(1)) {
                fragmentTopOut(companyInfoSelectBarMenuFragment2)
                companyInfoSelectBarMenuFragment2 = null


                fragmentFadeOut(shadowFragment)
                shadowFragment = null
                mTransaction.commit()
                return
            }

            if (companyInfoSelectBarMenuFragment3 != null && index.equals(2)) {
                fragmentTopOut(companyInfoSelectBarMenuFragment3)
                companyInfoSelectBarMenuFragment3 = null


                fragmentFadeOut(shadowFragment)
                shadowFragment = null
                mTransaction.commit()
                return
            }

            if (companyInfoSelectBarMenuFragment4 != null && index.equals(3)) {
                fragmentTopOut(companyInfoSelectBarMenuFragment4)
                companyInfoSelectBarMenuFragment4 = null

                fragmentFadeOut(shadowFragment)
                shadowFragment = null
                mTransaction.commit()
                return
            }


            if (companyInfoSelectBarMenuFragment1 != null) {
                mTransaction.remove(companyInfoSelectBarMenuFragment1!!)
                companyInfoSelectBarMenuFragment1 = null
            }
            if (companyInfoSelectBarMenuFragment2 != null) {
                mTransaction.remove(companyInfoSelectBarMenuFragment2!!)
                companyInfoSelectBarMenuFragment2 = null

            }
            if (companyInfoSelectBarMenuFragment3 != null) {
                mTransaction.remove(companyInfoSelectBarMenuFragment3!!)
                companyInfoSelectBarMenuFragment3 = null
            }
            if (companyInfoSelectBarMenuFragment4 != null) {
                mTransaction.remove(companyInfoSelectBarMenuFragment4!!)
                companyInfoSelectBarMenuFragment4 = null
            }

            if (shadowFragment == null) {
                shadowFragment = ShadowFragment.newInstance();
                mTransaction.add(recycleViewParent.id, shadowFragment!!)
            }

            mTransaction.setCustomAnimations(
                R.anim.top_in,
                R.anim.top_in
            )

            if (index.equals(0)) {


                companyInfoSelectBarMenuFragment1 = CompanyInfoSelectBarMenuFragment.newInstance(0, selectedItem1);
                mTransaction.add(recycleViewParent.id, companyInfoSelectBarMenuFragment1!!)
            }
            if (index.equals(1)) {

                companyInfoSelectBarMenuFragment2 = CompanyInfoSelectBarMenuFragment.newInstance(1, selectedItem2);
                mTransaction.add(recycleViewParent.id, companyInfoSelectBarMenuFragment2!!)
            }
            if (index.equals(2)) {

                companyInfoSelectBarMenuFragment3 = CompanyInfoSelectBarMenuFragment.newInstance(2, selectedItem3);
                mTransaction.add(recycleViewParent.id, companyInfoSelectBarMenuFragment3!!)
            }
            if (index.equals(3)) {

                companyInfoSelectBarMenuFragment4 = CompanyInfoSelectBarMenuFragment.newInstance(3, selectedItem4);
                mTransaction.add(recycleViewParent.id, companyInfoSelectBarMenuFragment4!!)
            }

        }



        mTransaction.commit()
    }

    //退出
    override fun cancle() {

    }

    //选中 搜索中展示的结果   展示出主信息
    override fun getUnderSearchingItem(item: JobSearchUnderSearching) {

        //通过条件删选出职位列表
        var mTransaction = supportFragmentManager.beginTransaction()
        if (jobSearcherHistoryFragment != null)
            mTransaction.remove(jobSearcherHistoryFragment!!)
        if (recruitInfoListFragment != null){
            mTransaction.remove(recruitInfoListFragment!!)
            recruitInfoListFragment=null
        }
        if (jobSearchSelectbarFragment != null)
            mTransaction.remove(jobSearchSelectbarFragment!!)
        if (companyInfoSelectbarFragment != null)
            mTransaction.remove(companyInfoSelectbarFragment!!)
        if (companyInfoListFragment != null){
            mTransaction.remove(companyInfoListFragment!!)
            companyInfoListFragment=null
        }

        if (type_job_or_company_search == 1) {
            jobSearchSelectbarFragment = JobSearchSelectbarFragment.newInstance("", "");
            mTransaction.replace(searchBarParent.id, jobSearchSelectbarFragment!!)

            recruitInfoListFragment = RecruitInfoListFragment.newInstance(item.name, null,filterParamAddress)
            mTransaction.replace(recycleViewParent.id, recruitInfoListFragment!!)
        } else if (type_job_or_company_search == 2) {
            companyInfoSelectbarFragment = CompanyInfoSelectbarFragment.newInstance("", "", "", "");
            mTransaction.replace(searchBarParent.id, companyInfoSelectbarFragment!!)
            companyInfoListFragment = CompanyInfoListFragment.newInstance(item.name, filterParamAddress);
            mTransaction.replace(recycleViewParent.id, companyInfoListFragment!!)
        }


        jobSearcherWithHistoryFragment!!.setEditeTextShow(item.name)
        var imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            jobSearcherWithHistoryFragment!!.editText.getApplicationWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        );

        jobSearcherWithHistoryFragment!!.editText.clearFocus()
        mTransaction.commit()
    }

    /**
     * 选中 历史搜索  展示出主信息
     */
    override fun sendHistoryText(item: String) {

        getRecruitListByKeyWord(item)
    }

    //直接通过输入的值进行查询
    override fun sendInputText(text: String) {

        getRecruitListByKeyWord(text)

    }


    //清楚历史
    override fun clearHistroy() {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (jobSearcherHistoryFragment != null)
            mTransaction.remove(jobSearcherHistoryFragment!!)

        var list: Array<String> = arrayOf()
        jobSearcherHistoryFragment = JobSearcherHistoryFragment.newInstance(list)
        mTransaction.replace(recycleViewParent.id, jobSearcherHistoryFragment!!)
        mTransaction.commit()
    }

    /**
     * 输入框 搜索职位
     */
    override fun sendMessage(msg: String, list: JSONArray) {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (jobSearcherHistoryFragment != null)
            mTransaction.remove(jobSearcherHistoryFragment!!)
        if (recruitInfoListFragment != null){
            mTransaction.remove(recruitInfoListFragment!!)
            recruitInfoListFragment=null
        }
        if (jobSearchSelectbarFragment != null)
            mTransaction.remove(jobSearchSelectbarFragment!!)
        if (companyInfoSelectbarFragment != null)
            mTransaction.remove(companyInfoSelectbarFragment!!)
        if (companyInfoListFragment != null){
            mTransaction.remove(companyInfoListFragment!!)
            companyInfoListFragment=null
        }


        if (msg.trim().isEmpty()) {
            //复原
            jobSearcherHistoryFragment = JobSearcherHistoryFragment.newInstance(histroyList)
            mTransaction.replace(recycleViewParent.id, jobSearcherHistoryFragment!!)
        } else {
            //展示搜索结果
            var searchList: MutableList<JobSearchUnderSearching> = mutableListOf()

            for (i in 0..list.length() - 1) {
                var item = JobSearchUnderSearching(msg, list.getJSONObject(i).getString(("name")))
                searchList.add(item)
            }



            jobSearchUnderSearchingDisplayFragment = JobSearchUnderSearchingDisplayFragment.newInstance(searchList)
            mTransaction.replace(recycleViewParent.id, jobSearchUnderSearchingDisplayFragment!!)

        }
        mTransaction.commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            getIntentData(data!!)
        }
    }


    override fun onStart() {
        super.onStart()
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    @SuppressLint("ResourceAsColor", "RestrictedApi", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        getIntentData()

        verticalLayout {
            backgroundColor = Color.WHITE

            //搜索框
            var searchId = 1
            frameLayout {
                id = searchId
                jobSearcherWithHistoryFragment = JobSearcherWithHistoryFragment.newInstance();
                supportFragmentManager.beginTransaction().replace(id, jobSearcherWithHistoryFragment!!).commit()

            }.lparams {
                height = wrapContent
                width = matchParent
            }

            //search bar
            var searchBarParentId = 3
            searchBarParent = frameLayout {
                id = searchBarParentId

            }.lparams {
                height = wrapContent
                width = matchParent
            }

            //list
            var recycleViewParentId = 4
            recycleViewParent = frameLayout {
                id = recycleViewParentId
                jobSearcherHistoryFragment = JobSearcherHistoryFragment.newInstance(histroyList);
                supportFragmentManager.beginTransaction().replace(id, jobSearcherHistoryFragment!!).commit()

            }.lparams {
                height = matchParent
                width = matchParent
            }
        }
    }


    fun fragmentTopOut(fra: Fragment?) {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (fra != null) {
            mTransaction.setCustomAnimations(
                R.anim.top_out, R.anim.top_out
            )
            mTransaction.remove(fra!!)
        }
        mTransaction.commit()
    }


    fun fragmentFadeOut(fra: Fragment?) {
        var mTransaction = supportFragmentManager.beginTransaction()
        if (fra != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(fra!!)
        }
        mTransaction.commit()
    }

    //得到传递的数据
    fun getIntentData() {
        var intent = intent
        type_job_or_company_search = intent.getIntExtra("searchType", 1)
    }

    //获取Intent数据
    fun getIntentData(intent: Intent) {

        if (intent != null) {
            if (intent.hasExtra("cityModel")) {
                var arryStr = intent.getStringExtra("cityModel")
                var array = JSONArray(arryStr)
                var cityName = array.getJSONObject(0).getString("name")
                cityId = array.getJSONObject(0).getString("id")
                jobSearcherWithHistoryFragment!!.setCityName(cityName)

                filterParamAddress = cityId


                if (type_job_or_company_search == 1 && recruitInfoListFragment != null) {

                    recruitInfoListFragment!!.filterData(
                        filterParamRecruitMethod,
                        filterParamWorkingType,
                        filterParamWorkingExperience,
                        null,
                        filterParamSalaryType,
                        filterParamSalaryMin,
                        filterParamSalaryMax,
                        null,
                        filterParamEducationalBackground,
                        filterParamIndustryId,
                        filterParamAddress,
                        null,
                        filterParamFinancingStage,
                        filterParamSize
                    )

                } else if (type_job_or_company_search == 2 && companyInfoListFragment != null) {
                    companyInfoListFragment!!.filterData(
                        companyFilterParamAcronym,
                        companyFilterParamSize,
                        companyFilterParamFinancingStage,
                        companyFilterParamType,
                        companyFilterParamCoordinate,
                        companyFilterParamRadius,
                        companyFilterParamIndustryId,
                        filterParamAddress
                    )
                }

            }
        }
    }


    //通过一个关键字(职位名称,查询职位列表)
    fun getRecruitListByKeyWord(item: String) {


        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (jobSearcherHistoryFragment != null)
            mTransaction.remove(jobSearcherHistoryFragment!!)
        if (recruitInfoListFragment != null){
            mTransaction.remove(recruitInfoListFragment!!)
            recruitInfoListFragment=null
        }
        if (jobSearchSelectbarFragment != null)
            mTransaction.remove(jobSearchSelectbarFragment!!)
        if (companyInfoSelectbarFragment != null)
            mTransaction.remove(companyInfoSelectbarFragment!!)
        if (companyInfoListFragment != null){
            mTransaction.remove(companyInfoListFragment!!)
            companyInfoListFragment=null
        }


        if (type_job_or_company_search == 1) {
            jobSearchSelectbarFragment = JobSearchSelectbarFragment.newInstance("", "");
            mTransaction.replace(searchBarParent.id, jobSearchSelectbarFragment!!)

            recruitInfoListFragment = RecruitInfoListFragment.newInstance(item, null,filterParamAddress)
            mTransaction.replace(recycleViewParent.id, recruitInfoListFragment!!)
        } else if (type_job_or_company_search == 2) {
            companyInfoSelectbarFragment = CompanyInfoSelectbarFragment.newInstance("", "", "", "");
            mTransaction.replace(searchBarParent.id, companyInfoSelectbarFragment!!)
            companyInfoListFragment = CompanyInfoListFragment.newInstance(item, filterParamAddress);
            mTransaction.replace(recycleViewParent.id, companyInfoListFragment!!)

        }

        //把选中的历史搜索关键词  展示在搜索框中
        jobSearcherWithHistoryFragment!!.setEditeTextShow(item)

        var imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            jobSearcherWithHistoryFragment!!.editText.getApplicationWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        );


        jobSearcherWithHistoryFragment!!.editText.clearFocus()
        mTransaction.commit()
    }

}
