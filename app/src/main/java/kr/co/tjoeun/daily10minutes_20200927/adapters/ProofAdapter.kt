package kr.co.tjoeun.daily10minutes_20200927.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minutes_20200927.R
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import kr.co.tjoeun.daily10minutes_20200927.datas.Proof
import kr.co.tjoeun.daily10minutes_20200927.datas.User
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class ProofAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<Proof>) : ArrayAdapter<Proof>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.proof_list_item, null)
        }

        val row = tempRow!!

        val proofData = mList[position]

        val writerProfileImg = row.findViewById<ImageView>(R.id.writerProfileImg)
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val writtenDateTimeTxt = row.findViewById<TextView>(R.id.writtenDateTimeTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val proofImg = row.findViewById<ImageView>(R.id.proofImg)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val replyBtn = row.findViewById<Button>(R.id.replyBtn)

        contentTxt.text = proofData.content

        Glide.with(mContext).load(proofData.writer.profileImageList[0]).into(writerProfileImg)
        writerNickNameTxt.text = proofData.writer.nickName

//        인증글 시간정보 => 2020년 5월 8일 오후 3시 1분 양식으로 출력

//        기능 추가
//        20초 이내에 쓴 글 - 방금 전
//        1분 이내에 쓴 글 - ?초 전
//        1시간 이내에 쓴 글 - ?분 전
//        24시간 이내에 쓴글 - ?시간 전
//        1주일이내에 쓴글 - ?일 전
//        그보다 더 오래전에 쓴 글 - ~년 ~월 ~일 오전/오후 ~시 ~분

//        현재시간 - 게시글작성시간 시차가 얼마냐?

        val now = Calendar.getInstance() // 현재 시간
//        게시글 작성 시간? proofData.proofTime

//        둘의 시간 차이? now->정수변환  - proofTime->정수변환 => ?밀리초 차이인지 계산됨.
        val diffTime = now.timeInMillis - proofData.proofTime.timeInMillis

        if (diffTime < 20 * 1000) {
//            20초 이내의 시차 - 방금 전
            writtenDateTimeTxt.text = "방금 전"
        }
        else if (diffTime < 1 * 60 * 1000) {
//            1분 이내의 시차 - ?초 전
//            밀리초 -> ?초 로 계산 => 밀리초 / 1000
            val sec = diffTime / 1000
            writtenDateTimeTxt.text = "${sec}초 전"
        }
        else if (diffTime < 1 * 60 * 60 * 1000) {
//            1시간 이내 - ?분 전
//            밀리초 -> ?분 전 => 밀리초 / 1000 / 60

            val minute = diffTime / 1000 / 60
            writtenDateTimeTxt.text = "${minute}분 전"
        }
        else if (diffTime < 24 * 60 * 60 * 1000) {
//            하루 이내 - ?시간 전 => 밀리초 / 1000 / 60 / 60
            val hour = diffTime / 1000 / 60 / 60
            writtenDateTimeTxt.text = "${hour}시간 전"
        }
        else if (diffTime < 7 * 24 * 60 * 60 * 1000) {
//            1주일 이내
            val day = diffTime / 1000 / 60 / 60 / 24
            writtenDateTimeTxt.text = "${day}시간 전"
        }
        else {
//            1주일이 넘어가는 경우 - 날짜 양식 가공
            val sdf = SimpleDateFormat("yyyy년 M월 d일 a h시 m분")
            writtenDateTimeTxt.text = sdf.format(proofData.proofTime.time)
        }


//        인증글의 이미지가 0개 : 이미지뷰 숨김
//        그렇지 않다 (1개 이상) : 이미지뷰 보여주기 + Glide 이미지 세팅 (편의상 0번째)

        if (proofData.imageList.size == 0) {
            proofImg.visibility = View.GONE
        }
        else {
            proofImg.visibility = View.VISIBLE
            Glide.with(mContext).load(proofData.imageList[0]).into(proofImg)
        }

//        내 좋아요 여부에 따른 배경색 / 글씨 색 변경

        if (proofData.isMyLike) {
//            res 폴더 의 자원으로 배경 설정 : setBackgroundResource
            likeBtn.setBackgroundResource(R.drawable.red_border_box)

//            res 폴더의 자원으로 글씨 색 설정 : 제공되는 함수 X
//            (Context의 도움을 받아서) 직접 res폴더로 가서 => 그 안의 color값 추출
            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
        }
        else {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }

//        좋아요 갯수

        likeBtn.text = "좋아요 ${proofData.likeCount}개"

//        좋아요 클릭 이벤트 처리
        likeBtn.setOnClickListener {

            ServerUtil.postRequestLikeProof(mContext, proofData.id, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    서버에서 주는 응답을 보면 이 인증글의 최신상태를 내려줌.
//                    이 글을 파싱해서 => 리스트 내용 일부 변경

//                    proofData 변수가 목록에 등장 => proofData 변수의 일부 내용 변경 : 목록에 변경 반영

                    proofData.likeCount = json.getJSONObject("data").getJSONObject("like").getInt("like_count")
                    proofData.isMyLike = json.getJSONObject("data").getJSONObject("like").getBoolean("my_like")

                    val message = json.getString("message")

//                    어댑터는 액티비티가 아님. runOnUiThread 기능을 갖고있지 않다.
//                    그럼에도 UI쓰레드에서 토스트를 띄워야함.
//                    쓰레드처럼 동작 : Handler 를 이용해 UIThread에 접근하자.

//                    getMainLooper 를 통해 이 Handler는 UI쓰레드로 접근
                    val myHandler = Handler(Looper.getMainLooper())

//                    UI쓰레드에 post {  } 내부의 코드를 실행하도록 요청
                    myHandler.post {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

//                        변수의 변경 내용을 리스트뷰에 반영
                        notifyDataSetChanged()
                    }

                }

            })

        }

        return row
    }

}