package com.example.sk_android.mvp.view.fragment.jobselect

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import click
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.view.activity.jobselect.JobSearchWithHistoryActivity
import com.example.sk_android.mvp.view.activity.jobselect.JobWantedManageActivity
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.activity.message.MessageChatRecordActivity
import com.example.sk_android.mvp.view.adapter.jobselect.JobWantAdapter
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.toast
import org.json.JSONArray
import org.json.JSONObject
import withTrigger

class RecruitInfoActionBarFragment : Fragment() {

    var toolbar1: Toolbar? = null
    private var mContext: Context? = null
    var titleList = mutableListOf<String>()

    lateinit var jobWantedFilter: JobWantedFilter

    //加载中的图标
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        // if (thisDialog?.isShowing!!){
        //     val toast = Toast.makeText(applicationContext, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
        //     toast.setGravity(Gravity.CENTER, 0, 0)
        //     toast.show()
        // }
        DialogUtils.hideLoading(thisDialog)
    }

    lateinit var textViewLeft: TextView
    lateinit var textViewCenter: TextView
    lateinit var textViewRight: TextView

    var selectedIndex = -1


    override fun onStart() {
        super.onStart()
        getJobWantedInfo(1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        jobWantedFilter = activity as JobWantedFilter

    }

    companion object {
        var jobWanted: MutableList<JSONObject> = mutableListOf()

        var selectedItem = "-"

        fun newInstance(): RecruitInfoActionBarFragment {
            var f = RecruitInfoActionBarFragment()
            return f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {


        var view = UI {
            linearLayout {

                relativeLayout() {


                    imageView {

                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setImageResource(R.mipmap.pic_top)

                    }.lparams() {
                        width = matchParent
                        height = dip(65)

                    }


                    relativeLayout() {


                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                        }.lparams() {
                            width = matchParent
                            height = dip(65)
                            alignParentBottom()

                        }


                        linearLayout {


                            var textViewLeftId = 1
                            textViewLeft = textView {
                                id = textViewLeftId
                                text = ""
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.WHITE
                                textSize = 14f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                                setOnClickListener(object : View.OnClickListener {
                                    /**
                                     * Called when a view has been clicked.
                                     *
                                     * @param v The view that was clicked.
                                     */
                                    override fun onClick(v: View?) {

                                        (jobWantedFilter as RecruitInfoShowActivity).shadowClicked()


                                        textViewRight.textColorResource = R.color.transparentWhite
                                        textViewCenter.textColorResource = R.color.transparentWhite


                                        if (jobWanted.size >= 1) {
                                            if (selectedIndex != 0) {

                                                selectedIndex = 0
                                                jobWantedFilter.getIndustryIdOfJobWanted(
                                                    jobWanted.get(selectedIndex).getString("id"),
                                                    false
                                                )
                                                textViewLeft.textColor = Color.WHITE
                                                selectedItem = text.toString()
                                            } else {
                                                textViewLeft.textColorResource =
                                                    R.color.transparentWhite
                                                jobWantedFilter.getIndustryIdOfJobWanted("", false)
                                                selectedIndex = -1
                                                selectedItem = "-"
                                            }
                                        }
                                    }

                                })
                                leftPadding = dip(7)
                                rightPadding = dip(7)
                            }.lparams() {
                                width = wrapContent
                                height = matchParent

                                leftMargin = dip(7)
                            }
                            var textViewCenterId = 2
                            textViewCenter = textView {
                                id = textViewCenterId
                                text = ""
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColorResource = R.color.transparentWhite
                                textSize = 14f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                                visibility = View.GONE

                                setOnClickListener(object : View.OnClickListener {

                                    /**
                                     * Called when a view has been clicked.
                                     *
                                     * @param v The view that was clicked.
                                     */
                                    override fun onClick(v: View?) {

                                        (jobWantedFilter as RecruitInfoShowActivity).shadowClicked()

                                        textViewLeft.textColorResource = R.color.transparentWhite
                                        textViewRight.textColorResource = R.color.transparentWhite
                                        if (jobWanted.size >= 2) {

                                            if (selectedIndex != 1) {

                                                selectedIndex = 1
                                                jobWantedFilter.getIndustryIdOfJobWanted(
                                                    jobWanted.get(selectedIndex).getString("id"),
                                                    false
                                                )
                                                textViewCenter.textColor = Color.WHITE
                                                selectedItem = text.toString()
                                            } else {
                                                textViewCenter.textColorResource =
                                                    R.color.transparentWhite
                                                jobWantedFilter.getIndustryIdOfJobWanted("", false)
                                                selectedIndex = -1
                                                selectedItem = "-"
                                            }


                                        }
                                    }

                                })
                                leftPadding = dip(7)
                                rightPadding = dip(7)
                            }.lparams() {
                                width = wrapContent
                                height = matchParent


                            }


                            var textViewRightId = 13
                            textViewRight = textView {
                                id = textViewRightId
                                text = ""
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColorResource = R.color.transparentWhite
                                textSize = 14f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                                visibility = View.GONE

                                setOnClickListener(object : View.OnClickListener {
                                    /**
                                     * Called when a view has been clicked.
                                     *
                                     * @param v The view that was clicked.
                                     */
                                    override fun onClick(v: View?) {

                                        (jobWantedFilter as RecruitInfoShowActivity).shadowClicked()

                                        textViewLeft.textColorResource = R.color.transparentWhite
                                        textViewCenter.textColorResource = R.color.transparentWhite
                                        if (jobWanted.size >= 3) {


                                            if (selectedIndex != 2) {

                                                selectedIndex = 2
                                                jobWantedFilter.getIndustryIdOfJobWanted(
                                                    jobWanted.get(selectedIndex).getString("id"),
                                                    false
                                                )
                                                textViewRight.textColor = Color.WHITE
                                                selectedItem = text.toString()
                                            } else {
                                                textViewRight.textColorResource =
                                                    R.color.transparentWhite
                                                jobWantedFilter.getIndustryIdOfJobWanted("", false)
                                                selectedIndex = -1
                                                selectedItem = "-"
                                            }


                                        }
                                    }

                                })
                                leftPadding = dip(7)
                                rightPadding = dip(7)
                            }.lparams() {
                                width = wrapContent
                                height = matchParent

                            }


                        }.lparams {
                            width = wrapContent
                            height =
                                dip(65 - getStatusBarHeight(this@RecruitInfoActionBarFragment.context!!))
                            alignParentLeft()
                            alignParentBottom()
                            leftMargin = dip(8)
                        }



                        relativeLayout {

                            var addImageId = 2

                            var addImage = linearLayout {
                                id = addImageId
                                gravity = Gravity.CENTER
                                this.withTrigger().click {


                                    //跳转到求职意向管理
                                    var intent =
                                        Intent(mContext, JobWantedManageActivity::class.java)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(
                                        R.anim.right_in,
                                        R.anim.left_out
                                    )
                                    (jobWantedFilter as RecruitInfoShowActivity).shadowClicked()

                                }


                                imageView {

                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.icon_add_home)


                                }.lparams() {
                                    width = dip(17)
                                    height = dip(17)
                                    leftMargin = dip(8)
                                    rightMargin = dip(8)

                                }

                            }.lparams {
                                alignParentRight()
                                centerVertically()
                                height = matchParent
                                width = wrapContent
                            }


                            linearLayout {
                                gravity = Gravity.CENTER
                                this.withTrigger().click {


                                    //跳转到只为搜索
                                    var intent =
                                        Intent(mContext, JobSearchWithHistoryActivity::class.java)
                                    intent.putExtra("searchType", 1)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(
                                        R.anim.right_in,
                                        R.anim.left_out
                                    )
                                    (jobWantedFilter as RecruitInfoShowActivity).shadowClicked()

                                }


                                imageView {

                                    backgroundColor = Color.TRANSPARENT
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.icon_search_home)


                                }.lparams() {
                                    width = dip(17)
                                    height = dip(17)
                                    rightMargin = dip(8)
                                    leftMargin = dip(8)
                                }

                            }.lparams {
                                centerVertically()
                                leftOf(addImage)
                                height = matchParent
                                width = wrapContent
                            }


                        }.lparams() {
                            width = wrapContent
                            height =
                                dip(65 - getStatusBarHeight(this@RecruitInfoActionBarFragment.context!!))
                            alignParentRight()
                            alignParentBottom()
                            rightMargin = dip(8)
                        }


                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }


            }
        }.view


        return view

    }


    fun getJobWantedInfo(from: Int) {
//75891889-cbdb-431d-946f-e1e0aa09cbdd  9aabe51e-21b5-4691-8ea4-b057d44d4b15 e4a413c8-48d6-41e3-8d42-703e0b0e2111

        var findIt = false//找到了相同的项


        if (from == 1) {
            var application: App? = null
            application = App.getInstance()
            application!!.setRecruitInfoActionBarFragment(this)

        }



        if ((jobWanted.isEmpty() || jobWanted.size == 0) && from == 1  && App.RecruitInfoActionBarFragment_jobWanted==true)  {

            if (jobWanted.size == 0) {
                textViewLeft.text = "全て"
                textViewLeft.textColorResource = R.color.white

                textViewCenter.visibility = View.GONE
                textViewRight.visibility = View.GONE


                jobWantedFilter.getIndustryIdOfJobWanted(null,true)
            }

        } else {

            var modelList: MutableList<JSONObject> = mutableListOf()
            modelList.addAll(jobWanted)

            for (i in 0 until modelList.size) {

                try{
                    if (!jobWanted.get(i).has("name") || !jobWanted.get(i).has("id")) {
                        jobWanted.remove(jobWanted.get(i))
                    }
                }catch (e:Exception){
                    continue
                }


            }


            if (jobWanted.size == 0) {


                return

//                textViewLeft.text = "全て"
//                textViewLeft.textColorResource = R.color.transparentWhite
//
//                textViewCenter.visibility = View.GONE
//                textViewRight.visibility = View.GONE
//
//                jobWantedFilter.resetJobWanted()

            } else if (jobWanted.size == 1) {

                textViewCenter.visibility = View.GONE
                textViewRight.visibility = View.GONE

            } else if (jobWanted.size == 2) {

                textViewRight.visibility = View.GONE
            }




            for (i in 0 until jobWanted.size) {

                if (i > 2) {
                    break
                }

                if (!jobWanted.get(i).has("name")) {
                    continue;
                }

                var industryName = jobWanted.get(i).getString("name")
                if (industryName.length > 4) {
                    industryName = industryName.substring(0, 4) + "..."
                }

                if (i == 0) {
                    textViewLeft.text = industryName
                    textViewLeft.textColorResource = R.color.transparentWhite
                    if (industryName == selectedItem) {
                        textViewLeft.textColor = Color.WHITE
                        selectedIndex = 0
                        findIt = true
                    } else {
                        textViewLeft.textColorResource = R.color.transparentWhite
                    }
                } else if (i == 1) {
                    textViewCenter.text = industryName
                    textViewCenter.visibility = View.VISIBLE
                    if (industryName == selectedItem) {
                        textViewCenter.textColor = Color.WHITE
                        selectedIndex = 1
                        findIt = true

                    } else {
                        textViewCenter.textColorResource = R.color.transparentWhite
                    }
                } else if (i == 2) {
                    textViewRight.text = industryName
                    textViewRight.visibility = View.VISIBLE
                    if (industryName == selectedItem) {
                        textViewRight.textColor = Color.WHITE
                        selectedIndex = 2
                        findIt = true

                    } else {
                        textViewRight.textColorResource = R.color.transparentWhite
                    }
                }
            }

            if (findIt) {
                jobWantedFilter.getIndustryIdOfJobWanted(
                    jobWanted.get(selectedIndex).getString("id"),
                    true
                )
            } else {
                textViewLeft.textColor = Color.WHITE
                selectedIndex = 0
                jobWantedFilter.getIndustryIdOfJobWanted(jobWanted.get(0).getString("id"), true)
            }

        }


//
//
//
//
//        thisDialog = DialogUtils.showLoading(mContext!!)
//        mHandler.postDelayed(r, 20000)
//        var retrofitUils = RetrofitUtils(activity!!, this.getString(R.string.userUrl))
//        // 获取用户的求职列表
//        retrofitUils.create(RegisterApi::class.java)
//            .jobIntentIons
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    println("获取求职意向成功")
//                    println(it)
//                    var array = JSONArray(it.toString())
//
//
//                    if (array.length() == 0) {
//                        textViewLeft.text = "全て"
//                        textViewLeft.textColorResource = R.color.transparentWhite
//
//                        textViewCenter.visibility = View.GONE
//                        textViewRight.visibility = View.GONE
//
//                        jobWantedFilter.resetJobWanted()
//
//                        DialogUtils.hideLoading(thisDialog)
//
//
//                    } else if (array.length() == 1) {
//
//                        textViewCenter.visibility = View.GONE
//                        textViewRight.visibility = View.GONE
//
//                    } else if (array.length() == 2) {
//
//                        textViewRight.visibility = View.GONE
//                    }
//
//                    titleList.clear()
//
//
//                    var requestComplete = mutableListOf<Boolean>()
//
//                    for (i in 0..array.length() - 1) {
//                        requestComplete.add(false)
//                        titleList.add("")
//                    }
//
//                    for (i in 0..array.length() - 1) {
//                        if (i > 2) {
//                            break
//                        }
//                        var item = array.getJSONObject(i)
//                        var industryIds = item.getJSONArray("industryIds")
//                        if (industryIds.length() > 0) {
//                            var industryId = industryIds.getString(0)
//
//
//                            var industryRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.industryUrl))
//                            industryRetrofitUils.create(RegisterApi::class.java)
//                                .getIndusTryById(industryId)
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe({
//                                    println("获取求职意向的行业成功")
//                                    println(it)
//                                    titleList.set(i, industryId)
//
//
//                                    var industryName = it.get("name").toString().replace("\"", "")
//                                    if (array.length() > 1) {
//                                        if (industryName.length > 4) {
//                                            industryName = industryName.substring(0, 4) + "..."
//                                        }
//                                    }
//
//                                    if (i == 0) {
//                                        textViewLeft.text = industryName
//                                        textViewLeft.textColorResource = R.color.transparentWhite
//                                        if (industryName == selectedItem) {
//                                            textViewLeft.textColor = Color.WHITE
//                                            selectedIndex = 0
//                                            findIt = true
//                                        } else {
//                                            textViewLeft.textColorResource = R.color.transparentWhite
//                                        }
//                                    } else if (i == 1) {
//                                        textViewCenter.text = industryName
//                                        textViewCenter.visibility = View.VISIBLE
//                                        if (industryName == selectedItem) {
//                                            textViewCenter.textColor = Color.WHITE
//                                            selectedIndex = 1
//                                            findIt = true
//
//                                        } else {
//                                            textViewCenter.textColorResource = R.color.transparentWhite
//                                        }
//                                    } else if (i == 2) {
//                                        textViewRight.text = industryName
//                                        textViewRight.visibility = View.VISIBLE
//                                        if (industryName == selectedItem) {
//                                            textViewRight.textColor = Color.WHITE
//                                            selectedIndex = 2
//                                            findIt = true
//
//                                        } else {
//                                            textViewRight.textColorResource = R.color.transparentWhite
//                                        }
//                                    }
//                                    requestComplete.set(i, true)
//                                    for (k in 0..requestComplete.size - 1) {
//                                        if (requestComplete.get(k) == false) {
//                                            break
//                                        }
//                                        if (k == requestComplete.size - 1) {
//                                            if (findIt) {
//                                                jobWantedFilter.getIndustryIdOfJobWanted(titleList.get(selectedIndex),true)
//                                            } else {
//                                                textViewLeft.textColor = Color.WHITE
//                                                selectedIndex = 0
//                                                jobWantedFilter.getIndustryIdOfJobWanted(titleList.get(0),true)
//                                            }
//                                            DialogUtils.hideLoading(thisDialog)
//
//                                        }
//                                    }
//
//
//                                }, {
//                                    println("获取求职意向的行业错误")
//                                    println(it)
//                                    requestComplete.set(i, true)
//                                    for (k in 0..requestComplete.size - 1) {
//                                        if (requestComplete.get(k) == false) {
//                                            break
//                                        }
//                                        if (k == requestComplete.size - 1) {
//                                            if (findIt) {
//                                                jobWantedFilter.getIndustryIdOfJobWanted(titleList.get(selectedIndex),true)
//                                            } else {
//                                                textViewLeft.textColor = Color.WHITE
//                                                selectedIndex = 0
//                                                jobWantedFilter.getIndustryIdOfJobWanted(titleList.get(0),true)
//                                            }
//                                            DialogUtils.hideLoading(thisDialog)
//
//                                        }
//                                    }
//                                })
//
//
//                        } else {
//                            titleList.removeAt(i)
//                            requestComplete.set(i, true)
//                            for (k in 0..requestComplete.size - 1) {
//                                if (requestComplete.get(k) == false) {
//                                    break
//                                }
//                                if (k == requestComplete.size - 1) {
//                                    if (findIt) {
//                                        jobWantedFilter.getIndustryIdOfJobWanted(titleList.get(selectedIndex),true)
//                                    } else {
//                                        textViewLeft.textColor = Color.WHITE
//                                        selectedIndex = 0
//                                        jobWantedFilter.getIndustryIdOfJobWanted(titleList.get(0),true)
//                                    }
//                                    DialogUtils.hideLoading(thisDialog)
//
//                                }
//                            }
//                        }
//                    }
//                },
//
//                {
//                    println("获取求职意向错误")
//                    println(it)
//                    DialogUtils.hideLoading(thisDialog)
//                    if (findIt) {
//                        jobWantedFilter.getIndustryIdOfJobWanted(titleList.get(selectedIndex),true)
//                    } else {
//                        if (titleList.size > 0) {
//                            textViewLeft.textColor = Color.WHITE
//                            selectedIndex = 0
//                            jobWantedFilter.getIndustryIdOfJobWanted(titleList.get(0),true)
//                        } else {
//                            jobWantedFilter.resetJobWanted()
//                        }
//
//                    }
//                }
//            )


    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId =
            context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }


    interface JobWantedFilter {
        fun getIndustryIdOfJobWanted(id: String?, initRquest: Boolean)
        fun resetJobWanted()
    }

    override fun onDestroy() {
        super.onDestroy()
        var application: App? = null
        application = App.getInstance()
        application!!.setRecruitInfoActionBarFragment(null)
    }


}




