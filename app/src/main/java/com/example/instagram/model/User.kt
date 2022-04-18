package com.example.instagram.model

class User {
    var uid: String = ""
    var fullname: String = ""
    var email: String = ""
    var password: String = ""
    var userImg: String = ""

    var isFollow: Boolean = false

    constructor(fullname: String, email: String){
        this.fullname = fullname
        this.email = email
    }

    constructor(fullname: String, email: String, userImg: String){
        this.fullname = fullname
        this.email = email
        this.userImg = userImg
    }

    constructor(fullname: String, email: String, password: String, userImg: String){
        this.fullname = fullname
        this.email = email
        this.userImg = userImg
        this.password = password
    }
}