package com.example.sk_android.utils

import okhttp3.MediaType

object MimeType {

    val MULTIPART_FORM_DATA = MediaType.parse("multipart/form-data")!!
    val IMAGE_JPEG = MediaType.parse("image/jpeg")!!
    val IMAGE_PNG = MediaType.parse("image/png")!!
    val IMAGE_WEBP = MediaType.parse("image/webp")!!
    val IMAGE_BMP = MediaType.parse("image/bmp")!!
    val IMAGE_GIF = MediaType.parse("image/gif")!!
    val APPLICATION_JSON = MediaType.parse("application/json")!!
    val TEXT_PLAIN = MediaType.parse("text/plain")!!
    val TEXT_HTML = MediaType.parse("text/html")!!
    val TEXT_XML = MediaType.parse("text/xml")!!

}
