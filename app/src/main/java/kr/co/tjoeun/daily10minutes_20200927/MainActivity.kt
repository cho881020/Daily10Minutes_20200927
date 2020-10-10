package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjectList = ArrayList<Project>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        getProjectListFromServer()
    }

//    서버에서 프로젝트 목록 가져오는 코드를 별도 함수로 분리.

    fun getProjectListFromServer() {

//        서버에 프로젝트 목록 요청 => 응답을 분석 => 목록에 담아주는 코드

        ServerUtil.getRequestProjectList(object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

            }

        })

    }

}