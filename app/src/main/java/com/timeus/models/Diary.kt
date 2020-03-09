package com.timeus.models

import java.time.LocalDateTime


class Diary : Comparable<Diary> {
    var id: String? = null
    var title: String? = null
    var content: String? = null
    var author: Author? = null
    var time: LocalDateTime? = null

    constructor()

    constructor(title: String?, content: String?, author: Author?, time: LocalDateTime) {
        this.title = title
        this.content = content
        this.author = author
        this.time = time
    }

    override fun compareTo(o: Diary): Int {
        return time!!.compareTo(o.time)
    }


}