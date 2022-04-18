package com.example.instagram.manager

import java.lang.Exception

interface DBFollowHandler {
    fun onSuccess(isFollowed: Boolean)
    fun onError(e: Exception)
}