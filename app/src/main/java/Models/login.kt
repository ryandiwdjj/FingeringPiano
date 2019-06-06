package Models

class login {
    var access_token: String = "null"
    var token_type: String = "null"
    var role: String = "null"
    var status: String = "null"
    var id: Int = 0

    constructor(access_token: String, token_type: String, role: String, status: String, id: Int) {
        this.access_token = access_token
        this.token_type = token_type
        this.role = role
        this.status = status
        this.id = id
    }
}