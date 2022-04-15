package com.example.instagram.manager.handler

import com.example.instagram.model.Post

interface DBPostHandler {
    fun onSuccess(post: Post)
    fun onError(e: Exception)
}