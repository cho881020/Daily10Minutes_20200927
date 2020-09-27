package kr.co.tjoeun.daily10minutes_20200927.utils

import okhttp3.FormBody
import okhttp3.OkHttpClient

class ServerUtil {

    companion object {

        val HOST_URL = "http://15.164.153.174"

        fun postRequestLogin(id:String, pw:String) {

            val client = OkHttpClient()

//            로그인 주소 : http://15.164.153.174/user
            val urlString = "${HOST_URL}/user"

            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

        }

    }

}