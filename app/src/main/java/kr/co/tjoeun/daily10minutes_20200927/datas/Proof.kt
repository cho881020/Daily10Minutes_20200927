package kr.co.tjoeun.daily10minutes_20200927.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Proof {
    var id = 0
    var content = ""

//    사진 주소 목록
    val imageList = ArrayList<String>()

//    인증시간 (String으로 내려오지만, Calendar로 보관)
    var proofTime = Calendar.getInstance()

//    작성자 정보 - User형태로 보관
    var writer = User()

    companion object {

        fun getProofFromJson(json: JSONObject) : Proof {

            val proof = Proof()

            proof.id = json.getInt("id")
            proof.content = json.getString("content")

//            인증글 파싱할때, 작성자 정보도 파싱하자.
            proof.writer = User.getUserFromJSON(json.getJSONObject("user"))


//            작성일시 : 서버-String => Proof-Calendar
//            SimpleDateFormat의 parse 기능 활용

            val proofTimeStr = json.getString("proof_time")

//            서버가 주는 시간 분석용 sdf
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

//            proof의 proofTime의 시간값을 => sdf의 parse기능 실행결과로 대입.
            proof.proofTime.time = sdf.parse(proofTimeStr)


            return proof

        }

    }

}