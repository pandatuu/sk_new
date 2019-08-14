package com.example.sk_android.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.preference.PreferenceManager
import anet.channel.util.Utils.context
import com.example.sk_android.custom.layout.MyDialog
import com.orhanobut.logger.Logger
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.commons.lang.StringUtils
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

class DialogUtils {


    companion object {
        private var myDialog: MyDialog? = null

        //关闭等待转圈窗口
        fun hideLoading_old() {
            try {

                if (myDialog != null) {
                    if (myDialog!!.isShowing()) {
                        myDialog!!.dismiss()
                        myDialog = null
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        //弹出等待转圈窗口
        fun showLoading_old(context: Context) {
            try {
                if (myDialog != null && myDialog!!.isShowing()) {
                    myDialog!!.dismiss()
                    myDialog = null
                    val builder = MyDialog.Builder(context)
                        .setCancelable(false)
                        .setCancelOutside(false)
                    myDialog = builder.create()

                } else {
                    val builder = MyDialog.Builder(context)
                        .setCancelable(false)
                        .setCancelOutside(false)

                    myDialog = builder.create()
                }
                myDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //弹出等待转圈窗口(可点击)
        fun showLoadingClick(context: Context) {
            try {
                if (myDialog != null && myDialog!!.isShowing()) {
                    myDialog!!.dismiss()
                    myDialog = null
                    val builder = MyDialog.Builder(context)
                        .setCancelOutside(true)
                    myDialog = builder.create()

                } else {
                    val builder = MyDialog.Builder(context)
                        .setCancelOutside(true)

                    myDialog = builder.create()
                }
                myDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        //弹出等待转圈窗口
        fun showLoading(context: Context):MyDialog {
            val builder = MyDialog.Builder(context)
                .setCancelable(false)
                .setCancelOutside(false)
            var  dialog = builder.create()

            try {
                dialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return dialog
        }


        //关闭等待转圈窗口
        fun hideLoading(d:MyDialog?) {
            try {

                if (d != null) {
                    if (d!!.isShowing()) {
                        d!!.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }





}
