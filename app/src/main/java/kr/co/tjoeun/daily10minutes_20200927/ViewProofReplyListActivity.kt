package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

                for (i in   0 until repliesArr.length()) {
//                    MainActivity의 Project 파싱 부분과 비교해보자. (같은 기능임)
                    mReplyArrayList.add(Reply.getReplyFromJSON(repliesArr.getJSONObject(i)))
                }

//                목록 새로고침

                runOnUiThread {
                    mReplyAdapter.notifyDataSetChanged()
                }

            }

        })

    }

}