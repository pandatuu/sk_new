package com.example.sk_android.mvp.view.adapter.privacyset

import android.content.Context
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.privacyset.PrivacyApi
import com.example.sk_android.mvp.model.privacySet.BlackCompanyInformation
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException

class RecyclerAdapter(
    context: Context,
    private val mDataSet: MutableList<BlackCompanyInformation> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ApdaterClick{
        fun delete(text: String)
    }

    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context
    private val binderHelper = ViewBinderHelper()
    private lateinit var adap: ApdaterClick
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(mContext, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        adap = mContext as ApdaterClick
        val view = mInflater.inflate(R.layout.row_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {
        val holder = h as ViewHolder

        val data = mDataSet[position] ?: return

        // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
        // put an unique string id as value, can be any string which uniquely define the data
        binderHelper.bind(holder.swipeLayout, data.toString())

        // Bind your data here
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipeLayout: SwipeRevealLayout = itemView.find(R.id.swipe_layout)

        private val deleteLayout: View = itemView.find(R.id.delete_layout)
        private val image: ImageView = itemView.find(R.id.image)
        private val texttop: TextView = itemView.find(R.id.texttop)
        private val textbottom: TextView = itemView.find(R.id.textbottom)

        fun bind(data: BlackCompanyInformation) {
            deleteLayout.onClick {

                thisDialog=DialogUtils.showLoading(mContext)
                mHandler.postDelayed(r, 12000)
                val bool = deleteCompany(data.id.toString())
                if(bool){
                    mDataSet.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    adap.delete("キャンセルは成功しました")
                }else{
                    adap.delete("キャンセルは失敗しました")
                }
            }
            Glide.with(image)
                .load(data.model.logo)
                .into(image)
            texttop.text = data.model.name
            textbottom.text = data.address
        }

        // 删除黑名单公司
        private suspend fun deleteCompany(id: String): Boolean {
            try {
                val retrofitUils = RetrofitUtils(mContext, mContext.getString(R.string.userUrl))
                val it = retrofitUils.create(PrivacyApi::class.java)
                    .deleteBlackList(id)
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .awaitSingle()
                // Json转对象
                if (it.code() in 200..299) {
                    DialogUtils.hideLoading(thisDialog)
                    println("获取成功")
                    return true
                }
                return false
            } catch (throwable: Throwable) {
                if (throwable is HttpException) {
                    println("code--------------" + throwable.code())
                }
                return false
            }
        }
    }

    fun setItems(items: List<BlackCompanyInformation>) {
        mDataSet.clear()
        mDataSet.addAll(items)

        notifyDataSetChanged()
    }

    fun addItem(item: BlackCompanyInformation) {
        val list = mutableListOf<BlackCompanyInformation>()
        list.add(item)
        list.addAll(mDataSet)
        mDataSet.clear()
        mDataSet.addAll(list)
        notifyDataSetChanged()
    }
}