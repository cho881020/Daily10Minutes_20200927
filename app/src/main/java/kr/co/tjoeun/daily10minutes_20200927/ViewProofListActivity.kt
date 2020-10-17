package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import java.text.SimpleDateFormat
import java.util.*

class ViewProofListActivity : BaseActivity() {

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_proof_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("project") as Project

//        이 화면이 열릴때는, 오늘 날짜를 알아내서 => 2020년 10월 17일 형태로 가공해서 출력.

//        1) 오늘 날짜 알아내기 => 날짜를 어떻게 변수에?

        val today = Calendar.getInstance() // 현재 시간 (오늘 포함)을 가지고 today에 저장.

//        today 내부의 데이터 조회? get

        Log.d("캘린더연습-년도", today.get(Calendar.YEAR).toString())
        Log.d("캘린더연습-월", today.get(Calendar.MONTH).toString()) // Calendar는 0~11월 까지 있다.
        Log.d("캘린더연습-일", today.get(Calendar.DAY_OF_MONTH).toString())

//        로그로 2020-10-17 양식으로 today를 출력.

        val sdf = SimpleDateFormat("yyyy-MM-dd")

        Log.d("양식1번", sdf.format(today.time))

//        로그로 200805 양식으로 today 출력

        val sdf2 = SimpleDateFormat("yyMMdd")
        Log.d("양식2번", sdf2.format(today.time))


//        오늘이 아닌, 다른 날짜를 세팅? set
//        1. 항목을 지정하고 그 항목의 값 변경 => 일을 1일로 변경.
        today.set(Calendar.DAY_OF_MONTH, 1)
//        2. 년/월/일 데이터 한꺼번에 변경 => 2020 / 6 / 10
        today.set(2020, Calendar.JUNE, 10) // 월은 영어 이름으로 해야 정확히 들어감



//        2) 날짜를 => "2020년 10월 17일" String으로 바꾸는 방법?

    }

}