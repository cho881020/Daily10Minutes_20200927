package kr.co.tjoeun.daily10minutes_20200927.utils

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

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

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버에 연결 자체를 실패한 경우. (물리적 실패)
//                    인터넷 연결 실패 등등.
//                    아이디/비번 틀림등 로그인 실패는 여기가 아님.
                }

                override fun onResponse(call: Call, response: Response) {

//                    검사 결과가 성공이던, 실패던 관계 없이 서버가 뭔가 답변을 해주면 무조건 실행.

//                    서버가 내려준 응답중 본문(body)만 String 형태로 저장.
                    val bodyString = response.body!!.string()

//                    받아낸 String을 => 분석하기 용이한 JSONObject로 변환.
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답본문", jsonObj.toString())

                }

            })

        }

    }

}