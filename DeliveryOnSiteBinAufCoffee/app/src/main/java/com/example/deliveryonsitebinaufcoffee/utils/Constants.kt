package com.example.deliveryonsitebinaufcoffee.utils

object ApiConstants {
    const val BASE_URL = "http://192.168.111.198:8001/" // Ganti dengan domain API Anda

    fun getFullImageUrl(imagePath: String?): String? {
        return imagePath?.let { path ->
            if (path.startsWith("http")) {
                path
            } else {
                "${BASE_URL}$path"
            }
        }
    }
}