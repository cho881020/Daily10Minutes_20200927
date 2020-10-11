package kr.co.tjoeun.daily10minutes_20200927

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.tjoeun.daily10minutes_20200927.datas.Project
import kr.co.tjoeun.daily10minutes_20200927.datas.User
import kr.co.tjoeun.daily10minutes_20200927.utils.ServerUtil
import org.json.JSONObject

class ViewProjectMemberActivity : BaseActivity() {

    val mMemberList = ArrayList<User>()

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_member)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("project") as Project

        getMembersFromServer()
    }

//    프로젝트의 참여한 사람들이 누구누구있는지 (서버에서) 불러내는 함수

    fun getMembersFromServer() {

        ServerUtil.getRequestProjectMembersById(mContext, mProject.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val dataObj = json.getJSONObject("data")
                val projectObj = dataObj.getJSONObject("project")

                val ongoingUsersArr = projectObj.getJSONArray("ongoing_users")

                for (i in    0 until ongoingUsersArr.length()) {

                    val ongoingUserObj = ongoingUsersArr.getJSONObject(i)

//                    진행중인 사람의 JSONObj 추출된 상황. => User 형태로 변환
                    val user = User.getUserFromJSON(ongoingUserObj)

//                    만들어진 User 객체를 => 목록에 추가

                    mMemberList.add(user)

                }

            }
        })

    }

}