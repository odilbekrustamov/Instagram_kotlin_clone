package com.example.instagram.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagram.R
import com.example.instagram.manager.AuthHandler
import com.example.instagram.manager.AuthManager
import com.example.instagram.model.User
import com.example.instagram.utils.Extensions.toast
import java.lang.Exception

/**
 * In SignUpActivity, user can sighup using fullname, email, password
 */

class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.simpleName
    lateinit var et_fullname: EditText
    lateinit var et_email: EditText
    lateinit var et_password: EditText
    lateinit var et_cpassword: EditText
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        context = this
        initViews()
    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_fullname = findViewById(R.id.et_cpassword)

        val b_signup = findViewById<Button>(R.id.b_signup)
        b_signup.setOnClickListener {
            val fullname = et_fullname.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                val user = User(fullname, email, password, "")
                firebaseSignUp(user)
            }
        }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener { finish() }
    }

    private fun firebaseSignUp(user: User) {
        showLoading(this)
        AuthManager.signUp(user.email, user.password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                user.uid = uid
                storeUserToDB(user)
                toast(getString(R.string.str_signup_success))
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast(getString(R.string.str_signup_failed))
            }
        })
    }

    private fun storeUserToDB(user: User) {
        dismissLoading()
        callMainActivity(context)
    }
}