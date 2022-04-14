package com.example.instagram.manager.handler

interface StorageHandler {
    fun onSuccess(userImg: String)
    fun onError(e: Exception)
}