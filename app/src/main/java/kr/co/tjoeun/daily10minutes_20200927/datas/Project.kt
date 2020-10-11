package kr.co.tjoeun.daily10minutes_20200927.datas

import org.json.JSONObject
import java.io.Serializable

class Project : Serializable {

//    서버에서 내려주는 참여가능 프로젝트의 데이터(하위정보) 를 담기 위한 변수들.

    var id = 0 // id는 Int라고 명시.
    var title = "" // title은 String이라고 명시.
    var imageURL = ""
    var desc = ""

    var ongoingUserCount = 0 // 현재 프로젝트 참여 인원 수
    var proofMethod = "" // 인증 방법 - String

//    JSONObject를 넣으면 => 파싱을 통해서 => Project 객체로 변환해주는 (단순)기능.

    companion object {

        fun getProjectFromJSON(json:JSONObject) : Project {

//            기본적인 Project 객체 생성
            val p = Project()

//            재료로 들어오는 json을 이용해서 내용 변수 채워주기
            p.id = json.getInt("id")
            p.title = json.getString("title")
            p.imageURL = json.getString("img_url")
            p.desc = json.getString("description")

//            현재 인원수도 추가 파싱
            p.ongoingUserCount = json.getInt("ongoing_users_count")

            p.proofMethod = json.getString("proof_method")


//            완성된 Project 객체를 결과로 리턴.
            return p

        }

    }

}











