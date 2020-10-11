package kr.co.tjoeun.daily10minutes_20200927

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_project_detail.*
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import org.json.JSONObject

class ViewProjectDetailActivity : BaseActivity() {

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        giveUpBtn.setOnClickListener {

//            포기 신청 API 호출 - DELETE 메쏘드 예제

            ServerUtil.deleteRequestGiveUpProject(mContext, mProject.id, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {

//                        서버에서 변경된 프로젝트 정보를 안내려준다. 새로고침 못하는가?
//                        새로 데이터를 다시 확인해보자. => 서버에 현재 상태 다시 조회

                        getProjectInfoFromServer()

                    }
                    else {
                        runOnUiThread {
                            Toast.makeText(mContext, "참여중인 프로젝트가 아닙니다.", Toast.LENGTH_SHORT).show()
                        }

                    }

                }

            })

        }

        applyBtn.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("프로젝트 참가 신청")
            alert.setMessage("${mProject.title} 프로젝트에 도전 하시겠습니까?")
            alert.setPositiveButton("도전", DialogInterface.OnClickListener { dialog, which ->

//                참여 신청 API 호출

                ServerUtil.postRequestApplyProject(mContext, mProject.id, object : ServerUtil.JsonResponseHandler {

                    override fun onResponse(json: JSONObject) {

                        val code = json.getInt("code")

                        if (code == 200) {

                            runOnUiThread {
                                Toast.makeText(mContext, "프로젝트에 참가했습니다.", Toast.LENGTH_SHORT).show()

//                                참가 신청 성공시, 변경된 프로젝트 상태를 파싱해서 새로 반영
//                                mProject를 갈아엎자

                                val dataObj = json.getJSONObject("data")
                                val projectObj = dataObj.getJSONObject("project")

                                mProject = Project.getProjectFromJSON(projectObj)

//                                참여자 수 등 데이터 새로 써주자.

                                refreshUIProjectData()


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
            alert.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(mContext, "준비가 되면 도전해주세요.", Toast.LENGTH_SHORT).show()
            })
            alert.show()

        }

        viewMembersBtn.setOnClickListener {

            val myIntent = Intent(mContext, ViewProjectMemberActivity::class.java)
            myIntent.putExtra("project", mProject)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("projectInfo") as Project

//        화면 데이터 반영
        titleTxt.text = mProject.title
        descTxt.text = mProject.desc

        Glide.with(mContext).load(mProject.imageURL).into(projectImg)

        proofMethodTxt.text = mProject.proofMethod

        getProjectInfoFromServer()

    }

//    현재 참여 인원 등 최신정보 (프로젝트 상세 정보)를 서버에서 가져오는 함수

    fun getProjectInfoFromServer() {

        ServerUtil.getRequestProjectInfoById(mContext, mProject.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val dataObj = json.getJSONObject("data")

                val projectObj = dataObj.getJSONObject("project")

//                mProject 내용물 전부 교체.

                mProject = Project.getProjectFromJSON(projectObj)

                runOnUiThread {

                    refreshUIProjectData()

                }

            }

        })

    }

//    mProject의 데이터가 바뀌면 실행하게 해서, 데이터 수정을 하나의 코드로 관리하는 함수.

    fun refreshUIProjectData() {
        //                    새로 갱신된 mProject를 이용해 화면에 새로 데이터 반영
        userCountTxt.text = "${mProject.ongoingUserCount}명"

        proofMethodTxt.text = mProject.proofMethod

//        내 참여 상태 : ONGOING - 진행중이니 중도포기만 활성화
//        그 외 (null - 신청 한번도 안함, FAIL - 중도 포기 / 실패)

        if (mProject.myLastStatus == "ONGOING") {
            giveUpBtn.isEnabled = true
            applyBtn.isEnabled = false
        }
        else {
            applyBtn.isEnabled = true
            giveUpBtn.isEnabled = false
        }

    }

}