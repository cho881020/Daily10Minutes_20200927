package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_view_proof_list.*
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

//        이 화면이 열릴때는, 오늘 날짜를 알아내서 => 2020-08-08 형태로 가공해서 출력.

//        1) 오늘 날짜 알아내기 => 날짜를 어떻게 변수에?

//        2) 날짜를 => "2020-08-08" String으로 바꾸는 방법? => 텍스트뷰에 반영

        val today = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        selectedDateTxt.text = sdf.format(today.time)

    }

}