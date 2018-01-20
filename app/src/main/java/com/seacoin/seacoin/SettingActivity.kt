package com.seacoin.seacoin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //                val fingerAuthHelper = FingerprintAuthHelper.Builder(this, object : FahListener {
//            override fun onFingerprintListening(listening: Boolean, milliseconds: Long) {
//                Log.e("Asdfa", "listening : " + listening)
//            }
//
//            override fun onFingerprintStatus(authSuccessful: Boolean, errorType: Int, errorMess: CharSequence?) {
//                Log.e("Asdfa", "result : " + authSuccessful)
//            }
//        })
//                .setTryTimeOut(45000)
//                .build()
//        fingerAuthHelper.showSecuritySettingsDialog()
//        if (fingerAuthHelper.isHardwareEnable) {
//            fingerAuthHelper.startListening()
//    }
    }
}
