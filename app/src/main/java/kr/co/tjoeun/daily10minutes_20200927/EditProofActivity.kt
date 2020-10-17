package kr.co.tjoeun.daily10minutes_20200927

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_proof.*
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import org.json.JSONObject

class EditProofActivity : BaseActivity() {

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_proof)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        okBtn.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("하루에 인증은 한번만 가능합니다.\n정말 인증 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->

                val inputContent = contentEdt.text.toString()

                ServerUtil.postRequestWriteProof(mContext, mProject.id, inputContent, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

                        val code = json.getInt("code")
                        if (code == 200) {

//                            인증 완료 토스트 + finish로 복귀
                            runOnUiThread {
                                Toast.makeText(mContext, "오늘의 인증을 완료 했습니다.", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                        }
                        else {
                            val message = json.getString("message")
                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                })

            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("project") as Project

        projectTitleTxt.text = mProject.title

    }

}