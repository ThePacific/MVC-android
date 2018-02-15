package com.pacific.arch.kotlin

import com.squareup.moshi.Json

data class Note constructor(@Json(name = "note_title") var title: String?,
                            @Json(name = "note_star") var star: Long,
                            @Json(name = "note_content") var content: String?)

data class SimplePage constructor(@Json(name = "my_list") var list: List<Note>)