package kr.co.tjoeun.daily10minutes_20200927.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {

    companion object {

//          가공 양식은, 변경될 일이 없다. => 한번만 만들어두고 계속 재사용.
        private val sdf = SimpleDateFormat("yyyy년 M월 d일 a h시 m분")

//        Calendar 변수 하나를 넣으면 => 현재 시간과의 차이를 구해서
//        적절한 String으로 변환해서 돌려주는 기능.

        fun getTimeAgoByCalendar(cal: Calendar) : String {

//            현재 시간 - cal의 시간값과의 시차

//        20초 이내에 쓴 글 - 방금 전
//        1분 이내에 쓴 글 - ?초 전
//        1시간 이내에 쓴 글 - ?분 전
//        24시간 이내에 쓴글 - ?시간 전
//        1주일이내에 쓴글 - ?일 전
//        그보다 더 오래전에 쓴 글 - ~년 ~월 ~일 오전/오후 ~시 ~분

//        현재시간 - 게시글작성시간 시차가 얼마냐?

            val now = Calendar.getInstance() // 현재 시간
//        재료로 들어오는 비교할 시간 : cal

//        둘의 시간 차이? now->정수변환  - proofTime->정수변환 => ?밀리초 차이인지 계산됨.
            val diffTime = now.timeInMillis - cal.timeInMillis

            if (diffTime < 20 * 1000) {
//            20초 이내의 시차 - 방금 전
                return "방금 전"
            }
            else if (diffTime < 1 * 60 * 1000) {
//            1분 이내의 시차 - ?초 전
//            밀리초 -> ?초 로 계산 => 밀리초 / 1000
                val sec = diffTime / 1000
                return "${sec}초 전"
            }
            else if (diffTime < 1 * 60 * 60 * 1000) {
//            1시간 이내 - ?분 전
//            밀리초 -> ?분 전 => 밀리초 / 1000 / 60

                val minute = diffTime / 1000 / 60
                return "${minute}분 전"
            }
            else if (diffTime < 24 * 60 * 60 * 1000) {
//            하루 이내 - ?시간 전 => 밀리초 / 1000 / 60 / 60
                val hour = diffTime / 1000 / 60 / 60
                return "${hour}시간 전"
            }
            else if (diffTime < 7 * 24 * 60 * 60 * 1000) {
//            1주일 이내
                val day = diffTime / 1000 / 60 / 60 / 24
                return "${day}일 전"
            }
            else {
//            1주일이 넘어가는 경우 - 날짜 양식 가공
                return sdf.format(cal.time)
            }




        }

    }

}