package com.example.fingeringpiano.Models

class user {
    var id: Int = 0
    var name: String = "null"
    var email: String = "null"
    var password: String = "null"
    var detail: detailUser =
        detailUser("null", "null", "null", "null", "null", 0)

    constructor(id: Int,
                name: String,
                email: String,
                password: String
    ) {
        this.name = name
        this.email = email
        this.password = password
    }
}

class detailUser {
    var dateofbirth: String = "null"
    var address: String = "null"
    var gender: String = "null"
    var phoneNumber: String = "null"
    var city: String = "null"
    var role_id: Int = 0

    constructor(dateofbirth: String,
                address: String,
                gender: String,
                phoneNumber: String,
                city: String,
                role_id: Int
    ) {
        this.dateofbirth = dateofbirth
        this.address = address
        this.gender = gender
        this.phoneNumber = phoneNumber
        this.city = city
        this.role_id = role_id
    }
}