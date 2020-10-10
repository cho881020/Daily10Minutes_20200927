package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minutes_20200927.adapters.ProjectAdapter
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjectList = ArrayList<Project>()

    lateinit var mAdapter : ProjectAdapter

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

        mAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjectList)
        projectListView.adapter = mAdapter

    }

//    서버에서 프로젝트 목록 가져오는 코드를 별도 함수로 분리.

    fun getProjectListFromServer() {

//        서버에 프로젝트 목록 요청 => 응답을 분석 => 목록에 담아주는 코드

        ServerUtil.getRequestProjectList(object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val dataObj = json.getJSONObject("data")
                val projectsArr = dataObj.getJSONArray("projects")

//                projectsArr의 내용물을 0~끝번까지 뽑아내자.

                for (i in    0 until projectsArr.length()) {

//                    상황에 맞는 JSONObject를 추출
                    val projectObj = projectsArr.getJSONObject(i)

//                    추출된 JSONObject를 가지고 => Project 클래스로 변환.

                    val tempProject = Project()
                    tempProject.id = projectObj.getInt("id")
                    tempProject.title = projectObj.getString("title")
                    tempProject.imageURL = projectObj.getString("img_url")
                    tempProject.desc = projectObj.getString("description")

//                    완성된 Project 클래스를 => mProjectList 에 추가.

                    mProjectList.add(tempProject)

                }

//                서버에 다녀오는 작업이, 어댑터 연결보다 늦게 완료될 수 있다. (거리상 문제)
//                서버에 다녀오면 어댑터 연결 이후 => 데이터 추가 상황 발생 가능.
//                내용물로 쓰이는 목록에 변경 발생. => 리스트뷰 새로고침 코드 추가

                runOnUiThread {
                    mAdapter.notifyDataSetChanged()
                }



            }

        })

    }

}