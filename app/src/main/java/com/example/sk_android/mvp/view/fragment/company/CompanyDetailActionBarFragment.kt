package com.example.sk_android.mvp.view.fragment.jobselect

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import click
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.model.jobselect.FavoriteType
import com.example.sk_android.mvp.view.activity.company.VideoShowActivity
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.startActivity
import org.json.JSONObject
import retrofit2.http.Url
import withTrigger
import java.lang.Exception

class CompanyDetailActionBarFragment : Fragment() {

    var toolbar1: Toolbar? = null
    private var mContext: Context? = null

    lateinit var mainLayout: RelativeLayout
    lateinit var select: CompanyDetailActionBarSelect
    var videoRela: RelativeLayout? = null
    lateinit var video: VideoView
    lateinit var image: ImageView
    lateinit var rela: RelativeLayout

    private var myDialog: MyDialog? = null

    lateinit var collectImageView: Toolbar

    var mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")

    var collectionId = ""
    var companyId = ""
    var isCollection = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): CompanyDetailActionBarFragment {
            return CompanyDetailActionBarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        select = activity as CompanyDetailActionBarSelect
        return fragmentView
    }


    fun setUrl(url: String) {
        if (url != "") {
            val view = UI {
                videoRela = relativeLayout {
                    linearLayout() {
                        gravity = Gravity.CENTER
                        image = imageView {
                            imageResource = R.mipmap.player
                            this.withTrigger().click {
                                var intent = Intent(activity!!, VideoShowActivity::class.java)
                                intent.putExtra("url", url)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

                            }
                        }.lparams(dip(70), dip(70)) {
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }

                }
            }.view
            rela.addView(view)
        }
    }

    private fun createView(): View {

        companyId = activity!!.intent.getStringExtra("companyId")

        var view = UI {
            relativeLayout {
                mainLayout = relativeLayout() {
                    backgroundResource = R.mipmap.company_bg


                    rela = relativeLayout {

                    }.lparams(matchParent, wrapContent) {
                        centerInParent()
                    }

                    relativeLayout() {
                        gravity = Gravity.CENTER_VERTICAL
                        toolbar1 = toolbar {
                            backgroundResource = R.color.transparent
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back_white

                        }.lparams() {
                            width = matchParent
                            height = dip(65)

                        }

                        var textViewLeftId = 1
                        var textViewLeft = textView {
                            id = textViewLeftId
                            text = ""
                            backgroundColor = Color.TRANSPARENT
                            gravity = Gravity.CENTER
                            textColor = Color.WHITE
                            textSize = 16f
                            setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                        }.lparams() {
                            width = wrapContent
                            height = dip(65 - getStatusBarHeight(this@CompanyDetailActionBarFragment.context!!))
                            alignParentBottom()
                            centerInParent()
                            leftMargin = dip(15)
                        }

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL

                            var soucangId = 1
                            collectImageView = toolbar {
                                id = soucangId
                                backgroundColor = Color.TRANSPARENT
                                navigationIconResource = R.mipmap.soucang_no


                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        if (isCollection) {
                                            //取消搜藏
                                            if (collectionId != null && !"".equals(collectionId)) {
                                                unlikeAPositionInfo(collectionId)
                                            }
                                        } else {
                                            //搜藏
                                            if (companyId != null && !"".equals(companyId)) {
                                                toCollectAPositionInfo(companyId)
                                            }
                                        }
                                    }
                                })

                            }.lparams(dip(25), dip(25)) {
                                rightMargin = dip(10)

                            }

                            var jubaoId = 2
                            var juba = toolbar {
                                id = jubaoId
                                navigationIconResource = R.mipmap.jubao
                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        navigationIconResource = R.mipmap.jubao_light
                                        select.jubaoSelect()
                                    }

                                })
                            }.lparams(dip(25), dip(25)) {

                                rightMargin = dip(10)

                            }

                            var pingbiId = 3
                            var pingbi = toolbar {
                                id = pingbiId
                                backgroundColor = Color.TRANSPARENT
                                navigationIconResource = R.mipmap.pingbi

                            }.lparams(dip(25), dip(25)) {

                            }

                        }.lparams() {
                            width = wrapContent
                            height = dip(65 - getStatusBarHeight(this@CompanyDetailActionBarFragment.context!!))
                            alignParentRight()
                            alignParentBottom()
                            rightMargin = dip(15)
                        }

                    }.lparams() {
                        width = matchParent
                        height = dip(65)
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(383)
                }


            }
        }.view

        getCollection()

        return view
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }


    interface CompanyDetailActionBarSelect {
        fun jubaoSelect()
    }


    private fun getCollection() {

        //请求搜藏
        var requestAddress = RetrofitUtils(mContext!!, "https://job.sk.cgland.top/")
        requestAddress.create(JobApi::class.java)
            .getFavorites(
                1, 1000000, FavoriteType.Key.ORGANIZATION.toString()
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("搜藏请求成功")
                println(it)
                var responseStr = org.json.JSONObject(it.toString())
                var soucangData = responseStr.getJSONArray("data")

                for (i in 0..soucangData.length() - 1) {
                    var item = soucangData.getJSONObject(i)
                    var targetEntityId = item.getString("targetEntityId")
                    var id = item.getString("id")
                    println("-----------------------------------")

                    println(companyId+"  "+targetEntityId )
                    if (companyId.equals(targetEntityId)) {
                        collectionId = id
                        collectImageView.navigationIconResource = R.mipmap.icon_zan_h_home
                        isCollection = true

                    }

                }


            }, {
                //失败
                println("搜藏请求失败")
                println(it)
            })

    }


    //搜藏职位
    fun toCollectAPositionInfo(id: String) {
        showLoading()
        val request = JSONObject()
        val detail = JSONObject()
        detail.put("targetEntityId", id)
        detail.put("targetEntityType", FavoriteType.Key.ORGANIZATION.toString())
        request.put("body", detail)

        val body = RequestBody.create(mediaType, detail.toString())
        //请求搜藏职位
        var requestAddress = RetrofitUtils(mContext!!, "https://job.sk.cgland.top/")
        requestAddress.create(JobApi::class.java)
            .addFavorite(
                body
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("创建搜藏成功")
                println(it)
                hideLoading()
                collectImageView.navigationIconResource = R.mipmap.icon_zan_h_home
                collectionId = it.toString()
                isCollection = true

                var toast = Toast.makeText(activity!!, "收藏成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            }, {
                //失败
                println("创建搜藏失败")
                println(it)
            })

    }


    //取消搜藏职位
    fun unlikeAPositionInfo(id: String) {
        showLoading()
        //取消搜藏职位
        var requestAddress = RetrofitUtils(mContext!!, "https://job.sk.cgland.top/")
        requestAddress.create(JobApi::class.java)
            .unlikeFavorite(
                id
            )
            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
            .subscribe({
                println("取消搜藏成功")
                println(it.toString())
                hideLoading()
                collectImageView.navigationIconResource = R.mipmap.soucang_no
                isCollection = false
                collectionId = ""

                var toast = Toast.makeText(activity!!, "取消收藏", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            }, {
                //失败
                println("取消搜藏失败")
                println(it)
            })

    }

    //关闭等待转圈窗口
    private fun hideLoading() {
        if (myDialog != null) {
            if (myDialog!!.isShowing()) {
                myDialog!!.dismiss()
                myDialog = null
            }
        }
    }

    //弹出等待转圈窗口
    private fun showLoading() {
        try {
            if (myDialog != null && myDialog!!.isShowing()) {
                myDialog!!.dismiss()
                myDialog = null
                val builder = MyDialog.Builder(context!!)
                    .setCancelable(false)
                    .setCancelOutside(false)
                myDialog = builder.create()

            } else {
                val builder = MyDialog.Builder(context!!)
                    .setCancelable(false)
                    .setCancelOutside(false)

                myDialog = builder.create()
            }
            myDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}




