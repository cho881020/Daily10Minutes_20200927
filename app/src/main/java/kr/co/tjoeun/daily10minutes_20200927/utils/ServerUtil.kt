package kr.co.tjoeun.daily10minutes_20200927.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    화면(액티비티)의 입장에서, 서버응답이 돌아왔을때 어떤 행동을 실행할지
//    행동 지침을 담아주기 위한 인터페이스 (가이드북/매뉴얼) 정의

    interface JsonResponseHandler {
        fun onResponse(json : JSONObject)
    }

    companion object {

        val HOST_URL = "http://15.164.153.174"

        fun postRequestLogin(id:String, pw:String, handler: JsonResponseHandler?) {

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

//                    어떤 서버응답 처리를 해줄지 가이드북 (인터페이스)가 존재한다면,
//                    그 가이드북에 적힌 내용을 실제로 실행해달라는 코드.

                    handler?.onResponse(jsonObj)

                }

            })

        }

        fun putRequestSignUp(id:String, pw:String, nickName:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()


//            로그인 주소 : http://15.164.153.174/user
//            Intent(mContext, 목적지) => 목적지 대응 : 기능 주소
            val urlString = "${HOST_URL}/user"

//            putExtra("이름표", 실제값) => 폼데이터.add("서버요구이름표", 실제첨부값)
            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .add("nick_name", nickName)
                .build()

//            요청의 모든 정보를 담고있는 Request를 생성하자.
//            Intent 덩어리에 대응되는 개념.

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
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

//                    어떤 서버응답 처리를 해줄지 가이드북 (인터페이스)가 존재한다면,
//                    그 가이드북에 적힌 내용을 실제로 실행해달라는 코드.

                    handler?.onResponse(jsonObj)

                }

            })

        }

        fun getRequestEmailCheck(emailAddress: String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

//            어느 주소로 가야하는가? 동일.
//            차이점 : 주소를 적을때 => 어떤 데이터가 첨부되는지 (파라미터)도 같이 적어야함.
//            POST / PUT 등은 formData 를 이용하지만, GET에서는 주소에 적는다.

//            URL에 파라미터들을 쉽게 첨부하도록 도와주는 URL가공기 생성.
            val urlBuilder = "${HOST_URL}/email_check".toHttpUrlOrNull()!!.newBuilder()
//            URL 가공기를 이용해서 필요한 파라미터들을 쉽게 첨부.
            urlBuilder.addEncodedQueryParameter("email", emailAddress)

//            가공이 끝난 URL을 urlString으로 완성.
            val urlString = urlBuilder.build().toString()

//            임시 확인 : 어떻게 url이 완성되었는지 확인
            Log.d("완성된URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
//                .header() // 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

        fun getRequestProjectList(context:Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

//            어느 주소로 가야하는가? 동일.
//            차이점 : 주소를 적을때 => 어떤 데이터가 첨부되는지 (파라미터)도 같이 적어야함.
//            POST / PUT 등은 formData 를 이용하지만, GET에서는 주소에 적는다.

//            URL에 파라미터들을 쉽게 첨부하도록 도와주는 URL가공기 생성.
            val urlBuilder = "${HOST_URL}/project".toHttpUrlOrNull()!!.newBuilder()
//            URL 가공기를 이용해서 필요한 파라미터들을 쉽게 첨부.
//            urlBuilder.addEncodedQueryParameter("email", emailAddress)

//            가공이 끝난 URL을 urlString으로 완성.
            val urlString = urlBuilder.build().toString()

//            임시 확인 : 어떻게 url이 완성되었는지 확인
            Log.d("완성된URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context)) // 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

        fun getRequestProjectInfoById(context:Context, projectId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

//            어느 주소로 가야하는가? 동일.
//            차이점 : 주소를 적을때 => 어떤 데이터가 첨부되는지 (파라미터)도 같이 적어야함.
//            POST / PUT 등은 formData 를 이용하지만, GET에서는 주소에 적는다.

//            URL에 파라미터들을 쉽게 첨부하도록 도와주는 URL가공기 생성.
            val urlBuilder = "${HOST_URL}/project/${projectId}".toHttpUrlOrNull()!!.newBuilder()
//            URL 가공기를 이용해서 필요한 파라미터들을 쉽게 첨부.
//            urlBuilder.addEncodedQueryParameter("email", emailAddress)

//            가공이 끝난 URL을 urlString으로 완성.
            val urlString = urlBuilder.build().toString()

//            임시 확인 : 어떻게 url이 완성되었는지 확인
            Log.d("완성된URL", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getLoginUserToken(context)) // 필요시 첨부
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답본문", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

    }

}