package com.example.instagram.utils

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.example.instagram.R
import com.example.instagram.model.ScreenSize
import android.provider.Settings

object Utils {

    fun getDeviceID(context: Context):String{
        val device_id: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return device_id
    }

    fun fireToast(context: Context, msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun screenSize(context: Application): ScreenSize{
        val displayMetrics = DisplayMetrics()
        val windowsManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        windowsManager.defaultDisplay.getMetrics(displayMetrics)
        val deviceWith = displayMetrics.widthPixels
        val deviceHeight = displayMetrics.heightPixels
        return ScreenSize(deviceWith, deviceHeight)
    }

    fun dialogDouble(context: Context?, title: String?, callback: DialogListener) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.view_dialog_double)
        dialog.setCanceledOnTouchOutside(true)
        val d_title = dialog.findViewById<TextView>(R.id.d_title)
        val d_confirm = dialog.findViewById<TextView>(R.id.d_confirm)
        val d_cancel = dialog.findViewById<TextView>(R.id.d_cancel)
        d_title.text = title
        d_confirm.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(true)
        }
        d_cancel.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(false)
        }
        dialog.show()
    }

    fun dialogSingle(context: Context?, title: String?, callback: DialogListener) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.view_dialog_single)
        dialog.setCanceledOnTouchOutside(true)
        val d_title = dialog.findViewById<TextView>(R.id.d_title)
        val d_confirm = dialog.findViewById<TextView>(R.id.d_confirm)
        d_title.text = title
        d_confirm.setOnClickListener {
            dialog.dismiss()
            callback.onCallback(true)
        }
        dialog.show()
    }
}

interface DialogListener {
    fun onCallback(isChosen: Boolean)
}