package com.example.instagram.model

import java.text.SimpleDateFormat
import java.util.*

class Post {
    var id : String = ""
    var caption: String = ""
    var postImg: String = ""
    var currentDate: String = currentTime()
    var isLiked: Boolean = false

    var uid: String = ""
    var fullname: String = ""
    var userImg: String = ""

    constructor(caption:String, postImg: String){
        this.postImg = postImg
        this.caption = caption
    }

    constructor(id:String, caption:String, postImg: String){
        this.id = id
        this.postImg = postImg
        this.caption = caption
    }

    private fun currentTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh/mm")
        return sdf.format(Date())
    }
}