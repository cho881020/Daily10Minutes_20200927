package kr.co.tjoeun.daily10minutes_20200927.datas

import org.json.JSONObject
import java.io.Serializable

class User : Serializable {

    var id = 0
    var email = ""
    var nickName = ""

    companion object {

        fun getUserFromJSON(json: JSONObject) : User {

            val u = User()

            u.id = json.getInt("id")
            u.email = json.getString("email")
            u.nickName = json.getString("nick_name")

            return u

        }

    }

}