package com.timeus.models

class Author {
    var id: String? = null
    var name: String? = null
    var picture: String? = null

    constructor()

    constructor(id: String?, name: String?, picture: String?) {
        this.id = id
        this.name = name
        this.picture = picture
    }
}