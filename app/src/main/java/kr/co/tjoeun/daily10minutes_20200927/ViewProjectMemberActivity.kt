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



            }
        })

    }

}