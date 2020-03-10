package com.timeus.models


class Diary : Comparable<Diary> {
    var id: String? = null
    var title: String? = null
    var content: String? = null
    var author: Author? = null
    var time: String? = null

    constructor()

    constructor(title: String?, content: String?, author: Author?, time: String?) {
        this.title = title
        this.content = content
        this.author = author
        this.time = time
    }

    override fun compareTo(o: Diary): Int {
        return time!!.compareTo(o.time!!)
    }


}