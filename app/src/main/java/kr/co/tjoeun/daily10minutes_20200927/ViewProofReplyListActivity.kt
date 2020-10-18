package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_proof_reply_list.*
import kr.co.tjoeun.daily10minutes_20200927.adapters.ReplyAdapter
import kr.co.tjoeun.daily10minutes_20200927.datas.Proof
import kr.co.tjoeun.daily10minutes_20200927.datas.Reply
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import kr.co.tjoeun.daily10minutes_20200927.utils.TimeUtil
import org.json.JSONObject

class ViewProofReplyListActivity : BaseActivity() {

    lateinit var mProof : Proof

    val mReplyArrayList = ArrayList<Reply>()
    lateinit var mReplyAdapter : ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_proof_reply_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        입력버튼이 눌리면 => 댓글 입력 내용을 서버에 전송 => 리스트뷰 새로고침

        inputBtn.setOnClickListener {

            val inputReply = replyContentEdt.text.toString()

//            댓글이 5글자 미만이라면 , 너무 짧다고 거부처리 (토스트로 안내).

            if (inputReply.length < 5) {
                Toast.makeText(mContext, "댓글은 5자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()

//                return : 결과 지정 => 함수의 종료 기능도 겸함.
//                return은 반대로, 함수를 강제종료 시키고 싶을때도 추가함.
                return@setOnClickListener
            }

//            이 줄이 실행된다 : 강제종료 되지 않았다 : 댓글이 5자 이상이다.
            ServerUtil.postRequestWriteProofReply(mContext, mProof.id, inputReply, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    댓글은 작성되기만 하면, 무조건 내용 새로고침 하자.
//                    댓글 내용을 다시 불러서 => 리스트뷰에 반영.

                    getRepliesFromServer()

                }

            })

        }

    }

    override fun setValues() {

        mProof = intent.getSerializableExtra("proof") as Proof

        Glide.with(mContext).load(mProof.writer.profileImageList[0]).into(writerProfileImg)
        writerNickNameTxt.text = mProof.writer.nickName
        contentTxt.text = mProof.content

        if (mProof.imageList.size == 0) {
            proofImg.visibility = View.GONE
        }
        else {
            proofImg.visibility = View.VISIBLE
            Glide.with(mContext).load(mProof.imageList[0]).into(proofImg)
        }

        writtenDateTimeTxt.text = TimeUtil.getTimeAgoByCalendar(mProof.proofTime)


        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyArrayList)
        replyListView.adapter = mReplyAdapter

        getRepliesFromServer()

    }

    fun getRepliesFromServer() {

        ServerUtil.getRequestReplyListByProofId(mContext, mProof.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val dataObj = json.getJSONObject("data")
                val proofObj = dataObj.getJSONObject("project")
                val repliesArr = proofObj.getJSONArray("replies")

//                기존에 들어있던 댓글은 전부 삭제 -> 불러내자
                mReplyArrayList.clear()

                for (i in   0 until repliesArr.length()) {
//                    MainActivity의 Project 파싱 부분과 비교해보자. (같은 기능임)
                    mReplyArrayList.add(Reply.getReplyFromJSON(repliesArr.getJSONObject(i)))
                }

//                목록 새로고침

                runOnUiThread {
                    mReplyAdapter.notifyDataSetChanged()

//                    불편사항 개선
//                    1. 입력되어있는 내용 삭제 => EditText는 setText로 내용 설정.
                    replyContentEdt.setText("")
//                    2. 리스트뷰 바닥으로 (애니메이션으로) 끌어내려주기 => 스크롤을 맨 밑으로 내리자.
//                    맨 밑 : 마지막 댓글을 보러 가자. => ex. 10개 댓글 : 0~9번 => 9번 댓글로 이동.
//                    전체 갯수 - 1번째 댓글로 이동.
                    replyListView.smoothScrollToPosition(mReplyArrayList.size - 1)

                }

            }

        })

    }

}