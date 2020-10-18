package kr.co.tjoeun.daily10minutes_20200927

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minutes_20200927.adapters.ProjectAdapter
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import kr.co.tjoeun.daily10minutes_20200927.utils.ContextUtil
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

        projectListView.setOnItemClickListener { parent, view, position, id ->

//            "눌린 프로젝트" 정보를 가지고 상세화면으로 이동

            val clickedProject = mProjectList[position]

            val myIntent = Intent(mContext, ViewProjectDetailActivity::class.java)
            myIntent.putExtra("projectInfo", clickedProject)
            startActivity(myIntent)

        }

        logoutBtn.setOnClickListener {

//            로그아웃 버튼이 눌리면? 로그아웃 => 기기에 저장된 토큰값 삭제
//            연습문제 : 바로 로그아웃이 아니라, 진짜 로그아웃할건지 물어보고 처리.

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                ContextUtil.setLoginUserToken(mContext, "")

//            다시 로딩화면으로 돌아가기
                val myIntent = Intent(mContext, SplashActivity::class.java)
                startActivity(myIntent)

                finish()
            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {
        getProjectListFromServer()

        mAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjectList)
        projectListView.adapter = mAdapter

//        노티 아이콘 보여야함
        notiImg.visibility = View.VISIBLE
        getNotiCountFromServer()

    }

//    서버에서 안읽은 알림이 몇개인지 가져오는 기능

    fun getNotiCountFromServer() {

        ServerUtil.getRequestNotiCount(mContext, object : ServerUtil.JsonResponseHandler {

            override fun onResponse(json: JSONObject) {

            }

        })

    }

//    서버에서 프로젝트 목록 가져오는 코드를 별도 함수로 분리.

    fun getProjectListFromServer() {

//        서버에 프로젝트 목록 요청 => 응답을 분석 => 목록에 담아주는 코드

        ServerUtil.getRequestProjectList(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val dataObj = json.getJSONObject("data")
                val projectsArr = dataObj.getJSONArray("projects")

//                projectsArr의 내용물을 0~끝번까지 뽑아내자.

                for (i in    0 until projectsArr.length()) {

//                    상황에 맞는 JSONObject를 추출
                    val projectObj = projectsArr.getJSONObject(i)

//                    추출된 JSONObject를 가지고 => Project 클래스로 변환.

                    val tempProject = Project.getProjectFromJSON(projectObj)

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