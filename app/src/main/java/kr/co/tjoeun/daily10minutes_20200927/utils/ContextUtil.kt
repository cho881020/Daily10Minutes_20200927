package kr.co.tjoeun.daily10minutes_20200927.utils

import android.content.Context

class ContextUtil {

    companion object {

//        메모장의 파일이름처럼, SharedPreferences의 이름을 짓고 변수로 만들어서 활용.
//        ContextUtil 내부에서만 사용하기 위한 변수. (외부 공개  X)

        private val prefName = "Daily10MinutesPref"

//        저장해줄 항목 이름을 변수로 생성. (오타 방지용)
        private val LOGIN_USER_TOKEN = "LOGIN_USER_TOKEN"

//        실제 데이터 저장 함수 (setter) 생성

        fun setLoginUserToken(context:Context, token:String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_USER_TOKEN, token).apply()
        }

    }

}