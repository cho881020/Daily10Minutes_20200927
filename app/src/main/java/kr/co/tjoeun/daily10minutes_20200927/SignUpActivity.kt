package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
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

//                    응용문제
//                    검사를 통과하고 나서 이메일 입력값이 변경되면 재검사 요구.
//                    이메일 입력값 변경 감지 (구글링) => "중복 검사를 해주세요." 문구 변경
//        EditText 문구 변경 이벤트 감지?

        idEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Log.d("변경된내용", s.toString())

//                입력값이 바뀌면 무조건 검사를 다시 요구하는 문구로 변경.
                emailCheckResultTxt.text = "중복 검사를 해주세요."

            }

        })


        emailCheckBtn.setOnClickListener {

            val inputEmail = idEdt.text.toString()

            ServerUtil.getRequestEmailCheck(inputEmail, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    연습문제
//                    이메일 사용 가능 : "사용해도 좋은 이메일입니다." 문구 변경
//                    불가 : "사용할 수 없는 이메일입니다. 다른 이메일로 다시 검사해주세요." 문구 변경
//                    Toast X, 텍스트뷰 문구 변경

                    val code = json.getInt("code")

                    if (code == 200) {

                        runOnUiThread {
                            emailCheckResultTxt.text = "사용해도 좋은 이메일입니다."
                        }
                    }
                    else {

                        runOnUiThread {
                            emailCheckResultTxt.text = "사용할 수 없는 이메일입니다. 다른 이메일로 다시 검사해주세요."
                        }
                    }

                }

            })

        }

        signUpBtn.setOnClickListener {

//            아이디/비번/닉네임을 => 회원가입 API에 전달 => 결과 확인 / 화면 처리

            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()
            val inputNickName = nickNameEdt.text.toString()

            ServerUtil.putRequestSignUp(inputId, inputPw, inputNickName, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    가입 성공/실패 처리

                    val codeNum = json.getInt("code")

                    if (codeNum == 200) {
//                        회원가입에 성공했습니다. 토스트 출력 + 가입화면 종료 (로그인 화면 복귀)

                        runOnUiThread {
                            Toast.makeText(mContext, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    }
                    else {
//                        실패 사유를 서버가 알려주는대로 토스트로 띄워주기만.
                        val message = json.getString("message")

                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }


                }

            })


        }

    }

    override fun setValues() {

    }

}