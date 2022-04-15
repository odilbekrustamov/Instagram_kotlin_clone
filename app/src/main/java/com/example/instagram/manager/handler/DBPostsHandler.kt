package com.example.instagram.manager.handler

import com.example.instagram.model.Post

interface DBPostsHandler {
    fun onSuccess(posts: ArrayList<Post>)
    fun onError(e: Exception)
}