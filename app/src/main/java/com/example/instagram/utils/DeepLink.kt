package com.example.instagram.utils


import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.TextView
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase

object DeepLink {

    fun createLongLink(partnerId: String) {
        val dynamicLink = Firebase.dynamicLinks.dynamicLink {
            link = Uri.parse("https://www.paybek.io/?partnerId=$partnerId")
            domainUriPrefix = "https://androidinstagram.page.link"
            // Open links with this app on Android
            androidParameters("com.example.instagram") {
                var minimumVersion = 100
            }
            // Open links with com.example.ios on iOS
            iosParameters("com.example.ios") {
                appStoreId = "123456789"
                minimumVersion = "1.0.1"
            }
        }

        val longLink = dynamicLink.uri
        Log.d("DeepLink ", longLink.toString())
    }

    fun createShortLink(partnerId: String) {
        Firebase.dynamicLinks.shortLinkAsync {
            link = Uri.parse("https://www.paybek.io/?partnerId=$partnerId")
            domainUriPrefix = "https://androidinstagram.page.link"
            // Open links with this app on Android
            androidParameters("com.example.instagram") {
                minimumVersion = 100
            }
            // Open links with com.example.ios on iOS
            iosParameters("com.example.ios") {
                appStoreId = "123456789"
                minimumVersion = "1.0.1"
            }
        }.addOnSuccessListener { (shortLink, flowchartLink) ->
            Log.d("DeepLink ", shortLink.toString())
        }.addOnFailureListener {
            Log.d("DeepLink", it.toString())
        }
    }

    fun retrieveLink(intent: Intent, tv_insta:TextView) {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener {
                var deepLink: Uri? = null
                if (it != null) {
                    deepLink = it.link
                }
                if (deepLink != null){
                    val uri = Uri.parse(it.link.toString())
                    tv_insta.text = uri.toString()
                    val productID = uri.getQueryParameter("partnerId") // productID will be 61 as from the URL
                    tv_insta.text = productID
                }else{
                    Log.d("DeepLink", "no link")
                    tv_insta.text = "no link"
                }
            }
            .addOnFailureListener {
                Log.d("DeepLink", it.toString())
                tv_insta.text = "exception"
            }
    }
}