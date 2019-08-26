package com.example.sk_android.mvp.view.fragment.jobselect

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.jobselect.JobApi
import com.example.sk_android.mvp.model.jobselect.FavoriteType
import com.example.sk_android.mvp.view.fragment.common.DialogLoading
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class JobInfoDetailActionBarFragment : Fragment() {

    var toolbar1: Toolbar? = null
    private var mContext: Context? = null
    private var dialogLoading: DialogLoading? = null

    private lateinit var actionBarSelecter: ActionBarSelecter
    var mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")

    private lateinit var collectImageView:ImageView
    val mainId = 1

    private var collectionId=""

    private var isCollection=false

    private var recruitMessageId=""

    private var position=-1


    //加载中的图标
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!)
            toast("ネットワークエラー") //网路出现问题
        DialogUtils.hideLoading(thisDialog)
    }

    fun getCollectionId():String{
        return this.collectionId
    }


    fun getIsCollection():Boolean{
        return this.isCollection
    }


    fun getPosition():Int{
        return position
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance(): JobInfoDetailActionBarFragment {
            return JobInfoDetailActionBarFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        actionBarSelecter = activity as ActionBarSelecter

        return fragmentView
    }

    private fun createView(): View {


        var intent=activity!!.intent
        isCollection=intent.getBooleanExtra("isCollection",false)
        recruitMessageId=intent.getStringExtra("recruitMessageId")
        collectionId=intent.getStringExtra("collectionId")
        position=intent.getIntExtra("position",-1)




        return UI {
            linearLayout {
                id = mainId
                relativeLayout {
                    textView() {
                        backgroundResource = R.drawable.actionbar_bottom_border
                    }.lparams() {
                        width = matchParent
                        height = dip(65)

                    }
                    relativeLayout() {

                        toolbar1 = toolbar {
                            isEnabled = true
                            title = ""
                            navigationIconResource = R.mipmap.icon_back


                        }.lparams() {
                            width = matchParent
                            height = dip(65)
                            alignParentBottom()

                        }

                        linearLayout() {


                            gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
                            linearLayout {

                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        if(collectImageView.isSelected){
                                            //取消搜藏
                                            collectImageView.isSelected=false
                                            if(collectionId!=null  && !"".equals(collectionId)){
                                                unlikeAPositionInfo(collectionId)
                                            }
                                        }else{
                                            //搜藏
                                            collectImageView.isSelected=true
                                            if(recruitMessageId!=null  && !"".equals(recruitMessageId)) {
                                                toCollectAPositionInfo(recruitMessageId)
                                            }
                                        }
                                    }
                                })


                                gravity = Gravity.CENTER_VERTICAL
                                collectImageView=imageView {

                                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                                    if(isCollection){
                                        isSelected=true
                                        setImageResource(R.mipmap.icon_collection)
                                    }else{
                                        isSelected=false
                                        setImageResource(R.mipmap.icon_collect_zwxq)
                                    }

                                }.lparams() {
                                    width = dip(20)
                                    leftMargin = dip(7)
                                    rightMargin = dip(7)
                                }
                            }.lparams() {
                                width = wrapContent
                                height = matchParent
                            }
                            linearLayout {
                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        actionBarSelecter.gerActionBarSelectedItem(1)
                                    }
                                })
                                gravity = Gravity.CENTER_VERTICAL
                                imageView {
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.icon_report_zwxq)


                                }.lparams() {
                                    width = dip(18)
                                    height = dip(18)
                                    leftMargin = dip(7)
                                    rightMargin = dip(7)

                                }
                            }.lparams() {
                                width = wrapContent
                                height = matchParent
                            }

                            linearLayout {
                                setOnClickListener(object : View.OnClickListener {
                                    override fun onClick(v: View?) {
                                        actionBarSelecter.gerActionBarSelectedItem(2)
                                    }
                                })
                                gravity = Gravity.CENTER_VERTICAL
                                imageView {

                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    setImageResource(R.mipmap.icon_share_zwxq)

                                }.lparams() {
                                    width = dip(20)
                                    height = dip(20)
                                    leftMargin = dip(7)
                                    rightMargin = dip(7)
                                }
                            }.lparams() {
                                width = wrapContent
                                height = matchParent
                                rightMargin = dip(7)
                            }

                        }.lparams() {
                            width = wrapContent
                            height = dip(65 - getStatusBarHeight(this@JobInfoDetailActionBarFragment.context!!))
                            alignParentRight()
                            alignParentBottom()
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


    interface ActionBarSelecter {

        fun gerActionBarSelectedItem(index: Int)
    }




    //搜藏职位
    fun toCollectAPositionInfo(id: String) {
        thisDialog=DialogUtils.showLoading(context!!)
        mHandler.postDelayed(r, 12000)
        val request = JSONObject()
        val detail = JSONObject()
        detail.put("targetEntityId", id)
        detail.put("targetEntityType", FavoriteType.Key.ORGANIZATION_POSITION.toString())
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
                DialogUtils.hideLoading(thisDialog)
                collectImageView.setImageResource(R.mipmap.icon_collection)
                collectionId=it.toString()
                isCollection=true

                var  toast = Toast.makeText(activity!!, "フォローしました", Toast.LENGTH_SHORT)
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
        thisDialog=DialogUtils.showLoading(context!!)
        mHandler.postDelayed(r, 12000)
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
                DialogUtils.hideLoading(thisDialog)
                collectImageView.setImageResource(R.mipmap.icon_collect_zwxq)
                isCollection=false
                collectionId=""

                var  toast = Toast.makeText(activity!!, "フォローを解除しました", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            }, {
                //失败
                println("取消搜藏失败")
                println(it)
            })

    }


}




