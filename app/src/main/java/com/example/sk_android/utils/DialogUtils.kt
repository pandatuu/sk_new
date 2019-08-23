package com.example.sk_android.utils

import android.content.Context
import android.os.Handler
import com.example.sk_android.custom.layout.MyDialog
import java.util.*
import android.support.v4.os.HandlerCompat.postDelayed




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
            val dialog = builder.create()

            try {
                dialog.show()

                Handler().postDelayed({
                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                }, 120000)// 12s

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return dialog
        }

        //关闭等待转圈窗口
        fun hideLoading(d:MyDialog?) {
            try {

                if (d != null) {
                    if (d.isShowing) {
                        d.dismiss()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }





}
