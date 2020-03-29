package com.square.game.core.entities

import com.square.game.core.base.emptyIntArray
import com.square.game.core.base.verifyNumbers

/**
 * 去除等待开奖
 */
fun List<GResult>.dropEmpty(): List<GResult> {
    return this.filter {
        it.numbers.isNotEmpty()
    }
}

/**
 * 去除等待开奖和错误号码
 */
fun List<GResult>.dropEmptyOrError(length: Int): List<GResult> {
    require(length > 0)
    return this.filter {
        it.numbers.size == length
    }
}

/**
 * 去除错误号码
 */
fun List<GResult>.dropError(length: Int): List<GResult> {
    require(length > 0)
    return this.filter {
        it.numbers.verifyNumbers(length)
    }
}

/**
 * 修正错误号码为等待开奖
 */
fun GResult.correctErrorNumbers(length: Int) {
    if (!this.numbers.verifyNumbers(length)) {
        setNumbers(emptyIntArray)
    }
}

fun List<GNumber>.toTuple2(): Array<List<GNumber>> {
    return this.toTupleN(1, 2)
}

fun List<GNumber>.toTuple3(): Array<List<GNumber>> {
    return this.toTupleN(1, 2, 3)
}

fun List<GNumber>.toTuple4(): Array<List<GNumber>> {
    return this.toTupleN(1, 2, 3, 4)
}

fun List<GNumber>.toTuple5(): Array<List<GNumber>> {
    return this.toTupleN(1, 2, 3, 4, 5)
}

fun List<GNumber>.toTuple6(): Array<List<GNumber>> {
    return this.toTupleN(1, 2, 3, 4, 5, 6)
}

fun List<GNumber>.toTuple7(): Array<List<GNumber>> {
    return this.toTupleN(1, 2, 3, 4, 5, 6, 7)
}

fun List<GNumber>.toTuple8(): Array<List<GNumber>> {
    return this.toTupleN(1, 2, 3, 4, 5, 6, 7, 8)
}

fun List<GNumber>.toTuple9(): Array<List<GNumber>> {
    return this.toTupleN(1, 2, 3, 4, 5, 6, 7, 8, 9)
}

fun List<GNumber>.toTuple10(): Array<List<GNumber>> {
    return this.toTupleN(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
}

fun List<GNumber>.toTupleN(vararg indices: Int): Array<List<GNumber>> {
    val arrayOfArrayLists = Array(indices.size) {
        ArrayList<GNumber>()
    }
    this.forEach {
        arrayOfArrayLists[indices.indexOf(it.index)].add(it)
    }
    return Array(indices.size) {
        arrayOfArrayLists[it].toList()
    }
}