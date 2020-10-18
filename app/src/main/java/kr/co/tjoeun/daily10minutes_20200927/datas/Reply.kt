package kr.co.tjoeun.daily10minutes_20200927.datas

import org.json.JSONObject
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Reply : Serializable {

    var id = 0
    var content = ""
    var likeCount = 0
    var isMyLike = false

    var writer = User()

    val createdAt = Calendar.getInstance()


    companion object {

        private val serverTimeFormat = SimpleDateFormat("yyyy-MM-ss HH:mm:ss")

        fun getReplyFromJSON(json: JSONObject) : Reply {

            val reply = Reply()

            reply.id = json.getInt("id")
            reply.content = json.getString("content")
            reply.likeCount = json.getInt("like_count")
            reply.isMyLike = json.getBoolean("my_like")

            reply.writer = User.getUserFromJSON(json.getJSONObject("user"))

            reply.createdAt.time = serverTimeFormat.parse(json.getString("created_at"))

            return reply
        }

    }



}