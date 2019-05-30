package com.example.sk_android.custom.layout

import android.app.Application
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.example.sk_android.R
import com.lcw.library.imagepicker.utils.ImageLoader

class PictruePicker : ImageLoader {
    private val mOptions = RequestOptions()
        .centerCrop()
        .dontAnimate()
        .format(DecodeFormat.PREFER_RGB_565)
        .placeholder(R.mipmap.icon_image_default)
        .error(R.mipmap.icon_image_error)

    private val mPreOptions = RequestOptions()
        .skipMemoryCache(true)
        .error(R.mipmap.icon_image_error)

    override fun loadImage(imageView: ImageView, imagePath: String) {
        //小图加载
        Glide.with(imageView.context).load(imagePath).apply(mOptions).into(imageView)
    }

    override fun loadPreImage(imageView: ImageView, imagePath: String) {
        //大图加载
        Glide.with(imageView.context).load(imagePath).apply(mPreOptions).into(imageView)

    }

    override fun clearMemoryCache() {
        //清理缓存
        Glide.get(MApplication()).clearMemory()
    }

}