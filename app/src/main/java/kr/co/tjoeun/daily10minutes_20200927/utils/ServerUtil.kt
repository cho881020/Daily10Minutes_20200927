package kr.co.tjoeun.daily10minutes_20200927.utils

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class ServerUtil {

    companion object {

        val HOST_URL = "http://15.164.153.174"

        fun postRequestLogin(id:String, pw:String) {

            val client = OkHttpClient()


//            로그인 주소 : http://15.164.153.174/user
//            Intent(mContext, 목적지) => 목적지 대응 : 기능 주소
            val urlString = "${HOST_URL}/user"

//            putExtra("이름표", 실제값) => 폼데이터.add("서버요구이름표", 실제첨부값)
            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

//            요청의 모든 정보를 담고있는 Request를 생성하자.
//            Intent 덩어리에 대응되는 개념.

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
//                .header() // API가 header 데이터를 요구하면 담아주는 곳
                .build()

//            완성된 Request를 가지고 실제 서버 연결 (클라이언트의 일) 코드

            client.newCall(request)

        }

    }

}