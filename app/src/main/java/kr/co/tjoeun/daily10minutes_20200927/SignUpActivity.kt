package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        signUpBtn.setOnClickListener {

//            아이디/비번/닉네임을 => 회원가입 API에 전달 => 결과 확인 / 화면 처리

            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()
            val inputNickName = nickNameEdt.text.toString()

            ServerUtil.putRequestSignUp(inputId, inputPw, inputNickName, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                }

            })


        }

    }

    override fun setValues() {

    }

}