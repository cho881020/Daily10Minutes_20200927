package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        loginBtn.setOnClickListener {

            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()

//            ServerUtil을 이용해서 실제 로그인 시도

            ServerUtil.postRequestLogin(inputId, inputPw, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    로그인 실행 결과에 따라 행동할 내용을 적는 공간
//                    code 라는 이름으로 적힌 Int값을 받아서, 200이냐 아니냐에 따라 다른 행동.

                    val codeNum = json.getInt("code")

                    Log.d("서버가알려주는코드값", codeNum.toString())

                    if (codeNum == 200) {

                    }
                    else {
//                        로그인 실패 => 토스트로 로그인 실패 안내.
//                        토스트 : UI 동작 -> UI Thread가 실행하도록 해야함.

                        runOnUiThread {
                            Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }



                }

            })

        }

    }

    override fun setValues() {

    }

}