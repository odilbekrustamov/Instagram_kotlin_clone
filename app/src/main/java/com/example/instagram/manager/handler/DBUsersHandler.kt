package com.example.instagram.manager.handler

import com.example.instagram.model.User

interface DBUsersHandler {
    fun onSuccess(users: ArrayList<User>)
    fun onError(e: Exception)
}