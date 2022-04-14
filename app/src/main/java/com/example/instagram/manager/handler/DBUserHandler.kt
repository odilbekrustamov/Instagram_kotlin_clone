package com.example.instagram.manager.handler

import com.example.instagram.model.User

interface DBUserHandler {
    fun onSuccess(user: User? = null)
    fun onError(e: Exception)
}