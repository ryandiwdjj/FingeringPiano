package Models

class video {
    var id: Int = 0
    var name: String = "null"
    var fileUrl: String = "null"
    var fileId: String = "null"
    var thumbnail: String = "null"
    var category_id: Int = 0
    var category = Models.category(category_id)


    constructor(id: Int, name: String, fileUrl: String, fileId: String, thumbnail: String, category_id: Int) {
        this.id = id
        this.name = name
        this.fileUrl = fileUrl
        this.fileId = fileId
        this.thumbnail = thumbnail
        this.category_id = category_id
    }
}