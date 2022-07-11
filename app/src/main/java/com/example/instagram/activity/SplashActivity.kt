package com.example.instagram.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.instagram.R
import com.example.instagram.manager.AuthManager
import com.example.instagram.manager.PrefsManager
import com.example.instagram.utils.DeepLink
import com.example.instagram.utils.Logger
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

/**
 * In SplashActivity, user can visit to SignInActivity or MainActivity
 */

class SplashActivity : BaseActivity() {
    val TAG = SplashActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        initViews()
    }

    private fun initViews() {
        countDownTimer()
        loadFCMToken()
        //DeepLink.createLongLink("123456789")
        DeepLink.createShortLink("654321")
    }

    private fun countDownTimer() {
        object : CountDownTimer(2000, 1000){
            override fun onTick(millisUnilFinished: Long) {

            }

            override fun onFinish() {
                if (AuthManager.isSignedIn()){
                    callMainActivity(this@SplashActivity)
                }else{
                    callSignInActivity(this@SplashActivity)
                }
            }
        }.start()
    }

    private fun loadFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Logger.d(TAG, "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            // Save it in locally to use later
            val token = task.result
            Logger.d(TAG, token.toString())
            PrefsManager(this).storeDeviceToken(token.toString())
        })
    }
}