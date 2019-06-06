package com.example.sk_android.utils

import android.content.Context
import android.graphics.*
import okhttp3.RequestBody
import android.widget.ImageView
import com.google.gson.JsonObject
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.FormBody
import okhttp3.MultipartBody
import com.pingerx.imagego.core.strategy.loadImage
import okhttp3.MediaType
import java.io.*

class UploadVoice{

    fun getVoiceData(str:String): RequestBody?{
        val file = File(str)
        val byteArray:ByteArray
        byteArray = readFile(file)
        var body = FormBody.create(MediaType.get("audio/AMR"),byteArray)
        return body;
    }
    /**
     * 将文件转化为byte[]
     *
     * @param file 输入文件
     */
    @Throws(IOException::class)
    private fun readFile(file: File): ByteArray {
        // Open file
        val f = RandomAccessFile(file, "r")
        try {
            // Get and check length
            val longlength = f.length()
            val length = longlength.toInt()
            if (length.toLong() != longlength)
                throw IOException("File size >= 2 GB")
            // Read file and return data
            val data = ByteArray(length)
            f.readFully(data)
            return data
        } finally {
            f.close()
        }
    }



}