package com.example.sk_android.mvp.view.adapter.privacyset

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.sk_android.R
import com.example.sk_android.mvp.model.PagedList
import com.example.sk_android.mvp.model.privacySet.BlackCompanyInformation
import com.example.sk_android.mvp.model.privacySet.BlackCompanyModel
import com.example.sk_android.mvp.model.privacySet.BlackListModel
import com.example.sk_android.mvp.model.privacySet.ListItemModel
import com.example.sk_android.mvp.view.activity.privacyset.PrivacyApi
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.imageResource
import retrofit2.HttpException
import java.util.*

class RecyclerAdapter(
    context : Context,
    createList: MutableList<BlackCompanyInformation>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mDataSet : MutableList<BlackCompanyInformation> = createList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context
    private val binderHelper = ViewBinderHelper()
    private var image: ImageView? = null
    private lateinit var list: ListAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        list = mContext as ListAdapter
            val view = mInflater.inflate(R.layout.row_list, parent, false)
            return ViewHolder(view)
    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {
        val holder = h as ViewHolder

        if (mDataSet != null && 0 <= position && position < mDataSet.size) {
            val data = mDataSet[position]

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            binderHelper.bind(holder.swipeLayout, data.toString())

            // Bind your data here
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return if (mDataSet == null) 0 else mDataSet.size
    }

    fun getData():  MutableList<BlackCompanyInformation> {
        return mDataSet
    }
    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onSaveInstanceState]
     */
    fun saveStates(outState: Bundle) {
        binderHelper.saveStates(outState)
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onRestoreInstanceState]
     */
    fun restoreStates(inState: Bundle) {
        binderHelper.restoreStates(inState)
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipeLayout: SwipeRevealLayout = itemView.findViewById(R.id.swipe_layout) as SwipeRevealLayout
        private val frontLayout: View
        private val deleteLayout: View
        private val texttop: TextView
        private val textbottom: TextView

        init {
            frontLayout = itemView.findViewById(R.id.front_layout)
            deleteLayout = itemView.findViewById(R.id.delete_layout)
            image = itemView.findViewById(R.id.image) as ImageView
            texttop = itemView.findViewById(R.id.texttop) as TextView
            textbottom = itemView.findViewById(R.id.textbottom) as TextView
        }

        fun bind(data: BlackCompanyInformation) {
            deleteLayout.setOnClickListener {
                GlobalScope.launch {
                    deleteCompany(data.id.toString())
                }
                mDataSet.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
            interPic(data.model.logo)
            texttop.text = data.model.name
            textbottom.text = data.address

            frontLayout.setOnClickListener {
                val displayText = "${data.model.name} clicked"
                Toast.makeText(mContext, displayText, Toast.LENGTH_SHORT).show()
                Log.d("RecyclerAdapter", displayText)
            }
        }

        // 删除黑名单公司
        private suspend fun deleteCompany(id: String){
            try{
                val retrofitUils = RetrofitUtils(mContext,"https://user.sk.cgland.top/")
                val it = retrofitUils.create(PrivacyApi::class.java)
                    .deleteBlackList(id)
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .awaitSingle()
                // Json转对象
                if(it.code() in 200..299){
                    println("获取成功")
                }
            }catch (throwable : Throwable){
                if(throwable is HttpException){
                    println("code--------------"+throwable.code())
                }
            }
        }
    }
    //获取网络图片
    private fun interPic(url: String) {
        Glide.with(mContext)
            .asBitmap()
            .load(url)
            .placeholder(R.mipmap.default_avatar)
            .into(image!!)
    }

}