package kr.co.tjoeun.daily10minutes_20200927.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minutes_20200927.R
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import kr.co.tjoeun.daily10minutes_20200927.datas.Reply
import kr.co.tjoeun.daily10minutes_20200927.utils.TimeUtil

class ReplyAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<Reply>) : ArrayAdapter<Reply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.reply_list_item, null)
        }

        val row = tempRow!!

        val replyData = mList[position]

        val userProfileImg = row.findViewById<ImageView>(R.id.userProfileImg)
        val userNickNameTxt = row.findViewById<TextView>(R.id.userNickNameTxt)
        val replyContentTxt = row.findViewById<TextView>(R.id.replyContentTxt)
        val myLikeImg = row.findViewById<ImageView>(R.id.myLikeImg)
        val createdAtTxt = row.findViewById<TextView>(R.id.createdAtTxt)
        val likeCountTxt = row.findViewById<TextView>(R.id.likeCountTxt)

        Glide.with(mContext).load(replyData.writer.profileImageList[0]).into(userProfileImg)
        userNickNameTxt.text = replyData.writer.nickName
        replyContentTxt.text = replyData.content

//        내가 좋아요 한 경우 : red_heart
//        아닌 경우 : black_heart

        if (replyData.isMyLike) {
//            Glide 이용한 경우 - 큰 이미지는 이쪽 추천 (Splash화면 이미지 등)
            Glide.with(mContext).load(R.drawable.red_heart).into(myLikeImg)
        }
        else {
//            안드로이드 기본 기능 이용한 경우 - 작은 이미지는 이방식도 OK
//            이미지 파일이 너무 크면 Out Of Memory 로 앱 강제종료.
            myLikeImg.setImageResource(R.drawable.black_heart)
        }

//        작성시간 : ?분전 등 => TimeUtil 기능 재활용
        createdAtTxt.text = TimeUtil.getTimeAgoByCalendar(replyData.createdAt)

        likeCountTxt.text = "좋아요 ${replyData.likeCount}개"

        return row
    }

}