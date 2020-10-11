package kr.co.tjoeun.daily10minutes_20200927.datas

import org.json.JSONObject
import java.io.Serializable

class User : Serializable {

    var id = 0
    var email = ""
    var nickName = ""

//    여러장의 프로필사진? => 한명의 User : 프사 ArrayList로 갖는다.
//    프사 데이터중, URL에 해당하는 String만 모아서 들고있자.
    val profileImageList = ArrayList<String>()

    companion object {

        fun getUserFromJSON(json: JSONObject) : User {

            val u = User()

            u.id = json.getInt("id")
            u.email = json.getString("email")
            u.nickName = json.getString("nick_name")

//            프사들을 파싱해서 목록에 채워주자.

            val profileImageArr = json.getJSONArray("profile_images")

//            JSON Array 내부를 반복으로 돌면서 => 프사를 한장씩 목록에 채우자.

            for (i in    0 until profileImageArr.length() ) {

                val profileImageObj = profileImageArr.getJSONObject(i)

//                평소대로면 클래스로 변환, 지금은 간략하게 URL만 String을 추출하자.
                val imageUrl = profileImageObj.getString("img_url")

//                지금 파싱중인 사용자 u의 프사로 등록
                u.profileImageList.add(imageUrl)

            }

            return u

        }

    }

}