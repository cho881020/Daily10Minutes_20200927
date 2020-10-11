package kr.co.tjoeun.daily10minutes_20200927

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kr.co.tjoeun.daily10minutes_20200927.utils.ContextUtil

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

//        자동로그인 O? MainActivity 이동
//         => 로그인 화면의 체크박스 체크 + 로그인 성공 (저장된 토큰이 빈칸 "" X)
//        로그인 필요? LoginActivity 이동

//        Intent를 상황에 따라 다른 화면으로 넣어주기 위한 변수
            val myIntent : Intent

            if (ContextUtil.isAutoLogin(mContext) && ContextUtil.getLoginUserToken(mContext) != "") {
//            자동로그인 OK
                myIntent = Intent(mContext, MainActivity::class.java)
            }
            else {
//            로그인 필요
                myIntent = Intent(mContext, LoginActivity::class.java)
            }

//        어떤 화면이던 출발시키자.
            startActivity(myIntent)
//            다른화면으로 이동하면 로딩화면은 종료
            finish()

        }, 2500)



    }

}