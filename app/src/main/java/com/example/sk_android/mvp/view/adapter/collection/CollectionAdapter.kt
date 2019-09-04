package com.example.sk_android.mvp.view.adapter.collection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.sk_android.R
import com.example.sk_android.mvp.api.collection.CollectionApi
import com.example.sk_android.mvp.model.privacySet.BlackCompanyInformation
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.HttpException

class CollectionAdapter(
    context: Context,
    createList: MutableList<BlackCompanyInformation>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ApdaterClick {
        fun delete(text: String)
    }

    private var mDataSet: MutableList<BlackCompanyInformation> = createList
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context
    private val binderHelper = ViewBinderHelper()
    private var image: ImageView? = null
    private lateinit var adap: ApdaterClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        adap = mContext as ApdaterClick
        val view = mInflater.inflate(R.layout.collection_company, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {
        val holder = h as ViewHolder

        if (0 <= position && position < mDataSet.size) {
            val data = mDataSet[position]

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            binderHelper.bind(holder.swipeLayout, data.toString())

            // Bind your data here
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    fun getData(): MutableList<BlackCompanyInformation> {
        return mDataSet
    }

    private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipeLayout: SwipeRevealLayout = itemView.findViewById(R.id.collection) as SwipeRevealLayout
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
            deleteLayout.onClick {
                val bool = deleteCompany(data.id.toString())
                if (bool) {
                    mDataSet.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    adap.delete("キャンセルは成功しました")
                } else {
                    adap.delete("キャンセルは失敗しました")
                }
            }
            interPic(data.model.logo)
            texttop.text = data.model.name
            textbottom.text = data.address

        }

        // 删除白名单公司
        private suspend fun deleteCompany(id: String): Boolean {
            try {
                val retrofitUils = RetrofitUtils(mContext, mContext.getString(R.string.jobUrl))
                val it = retrofitUils.create(CollectionApi::class.java)
                    .deleteFavoritesCompany(id)
                    .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .awaitSingle()

                if (it.code() in 200..299) {
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

    //获取网络图片
    private fun interPic(url: String) {
        Glide.with(mContext)
            .asBitmap()
            .load(url)
            .placeholder(R.mipmap.ico_company_default_logo)
            .into(image!!)
    }

}