package com.pacific.data.repository

const val PAGE_SIZE = 10

@JvmOverloads
fun <T> List<T>.forPage(requestPage: Int, pageSize: Int = PAGE_SIZE): List<T> {
    require(requestPage > 0)
    val expectSize = requestPage * pageSize
    val prePageExpectSize = (requestPage - 1) * pageSize
    return when {
        size >= expectSize -> subList(prePageExpectSize, expectSize)
        size in (prePageExpectSize + 1) until expectSize -> subList(prePageExpectSize, this.size)
        else -> emptyList()
    }
}
