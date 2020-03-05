package com.fingering.fingeringpiano.Models

class userResponse {
    var username: String = "null"
    var userdata: user= user(0, "null", "null", "null", "null")
    var role: String = "null"

    constructor(username: String, userdata: user, role: String) {
        this.username = username
        this.userdata = userdata
        this.role = role
    }
}

class updateResponse {
    var msg: String = "null"

    constructor(msg: String) {
        this.msg = msg
    }
}