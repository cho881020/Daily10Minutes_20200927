package kr.co.tjoeun.daily10minutes_20200927.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCM : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

//        푸시알림이 들어왔을때 실행되는 함수.
//        앱이 켜진상태에서 => 알림이 오면 실행됨.

//        알림에 전송한 제목 로그 테스트
        Log.d("푸시알림", p0.notification!!.title!!)

    }

}