package com.example.instagram.manager

import android.graphics.Color
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.example.instagram.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class FirebaseConfig(var ll: LinearLayout, var tv: TextView) {

    var remoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
            fetchTimeoutInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun applyConfig(){
        val bg_color = remoteConfig.getString("main_background_color")
        val font_size = remoteConfig.getLong("welcome_font_size")
        val text = remoteConfig.getString("welcome_text")

        Log.d("TAG", "applyConfig: $bg_color")
        Log.d("TAG", "applyConfig: $font_size")
        Log.d("TAG", "applyConfig: $text")

        this.ll.setBackgroundColor(Color.parseColor(bg_color))
        this.tv.text = text
        this.tv.textSize = font_size.toFloat()
    }

    fun updateConfig(){
        remoteConfig.fetch(0).addOnCompleteListener {
          //  if (it.isSuccessful){
                remoteConfig.activate()
                applyConfig()
//            }else{
                Log.d("TAG", "updateConfig: Fetching failed ${it.isSuccessful}")
//            }
        }
    }
}