package com.example.sk_android.custom.layout

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.sk_android.R

/**
 * @author : zlf
 * date    : 2019/4/17
 * github  : https://github.com/mamumu
 * blog    : https://www.jianshu.com/u/281e9668a5a6
 */
class MMLoading : Dialog {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    class Builder(private val context: Context) {
        private var message: String? = null
        private var isShowMessage = true
        private var isCancelable = false
        private var isCancelOutside = false

        /**
         * 设置提示信息
         * @param message
         * @return
         */

        fun setMessage(message: String): Builder {
            this.message = message
            return this@Builder
        }

        /**
         * 设置是否显示提示信息
         * @param isShowMessage
         * @return
         */
        fun setShowMessage(isShowMessage: Boolean): Builder {
            this.isShowMessage = isShowMessage
            return this@Builder
        }

        /**
         * 设置是否可以按返回键取消
         *
         * @param isCancelable
         * @return
         */

        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this@Builder
        }

        /**
         * 设置是否可以点击外部取消
         * @param isCancelOutside
         * @return
         */
        fun setCancelOutside(isCancelOutside: Boolean): Builder {
            this.isCancelOutside = isCancelOutside
            return this@Builder
        }

        fun create(): MMLoading {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.dialog_loading, null)
            val mmLoading = MMLoading(context, R.style.MyDialogStyle)
            val msgText = view.findViewById<View>(R.id.tipTextView) as TextView
            if (isShowMessage) {
                msgText.text = message
            } else {
                msgText.visibility = View.GONE
            }
            mmLoading.setContentView(view)
            mmLoading.setCancelable(isCancelable)
            mmLoading.setCanceledOnTouchOutside(isCancelOutside)
            //实现loading的透明度
            //            WindowManager.LayoutParams lp=mmLoading.getWindow().getAttributes();
            //            lp.alpha = 0.6f;
            //            mmLoading.getWindow().setAttributes(lp);
            return mmLoading
        }
    }
}
