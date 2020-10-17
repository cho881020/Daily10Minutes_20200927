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
        val sdf = SimpleDateFormat("yyyy년 M월 d일 a h시 m분")
        writtenDateTimeTxt.text = sdf.format(proofData.proofTime.time)

//        인증글의 이미지가 0개 : 이미지뷰 숨김
//        그렇지 않다 (1개 이상) : 이미지뷰 보여주기 + Glide 이미지 세팅 (편의상 0번째)

        if (proofData.imageList.size == 0) {
            proofImg.visibility = View.GONE
        }
        else {
            proofImg.visibility = View.VISIBLE
            Glide.with(mContext).load(proofData.imageList[0]).into(proofImg)
        }

//        좋아요 갯수

        likeBtn.text = "좋아요 ${proofData.likeCount}개"

//        좋아요 클릭 이벤트 처리
        likeBtn.setOnClickListener {

            ServerUtil.postRequestLikeProof(mContext, proofData.id, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val message = json.getString("message")

//                    어댑터는 액티비티가 아님. runOnUiThread 기능을 갖고있지 않다.
//                    그럼에도 UI쓰레드에서 토스트를 띄워야함.
//                    쓰레드처럼 동작 : Handler 를 이용해 UIThread에 접근하자.

//                    getMainLooper 를 통해 이 Handler는 UI쓰레드로 접근
                    val myHandler = Handler(Looper.getMainLooper())

//                    UI쓰레드에 post {  } 내부의 코드를 실행하도록 요청
                    myHandler.post {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }

                }

            })

        }

        return row
    }

}