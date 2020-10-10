package kr.co.tjoeun.daily10minutes_20200927.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minutes_20200927.R
import kr.co.tjoeun.daily10minutes_20200927.datas.Project

class ProjectAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<Project>) : ArrayAdapter<Project>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView

        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.project_list_item, null)
        }

        val row = tempRow!!

//        뿌려줄 데이터
        val projectData = mList[position]

//        내용이 뿌려질 뷰 가져오기
        val projectImg = row.findViewById<ImageView>(R.id.projectImg)
        val projectTitleTxt = row.findViewById<TextView>(R.id.projectTitleTxt)
        val projectDescTxt = row.findViewById<TextView>(R.id.projectDescTxt)

//        실 데이터 반영
        projectTitleTxt.text = projectData.title
        projectDescTxt.text = projectData.desc

        Glide.with(mContext).load(projectData.imageURL).into(projectImg)
        
        return row
    }

}