package com.square.game.core.driver.mark6

import androidx.collection.ArrayMap
import com.square.guava.date.toEpochMilli1
import com.square.guava.date.toZonedDateTime
import com.square.guava.date.toZonedDateTime1
import com.square.guava.domain.unsupportedOperationException
import org.threeten.bp.ZonedDateTime

const val ZODIAC_RAT = 1
const val ZODIAC_OX = 2
const val ZODIAC_TIGER = 3
const val ZODIAC_RABBIT = 4
const val ZODIAC_DRAGON = 5
const val ZODIAC_SNAKE = 6
const val ZODIAC_HORSE = 7
const val ZODIAC_GOAT = 8
const val ZODIAC_MONKEY = 9
const val ZODIAC_ROOSTER = 10
const val ZODIAC_DOG = 11
const val ZODIAC_PIG = 12

var zodiacsEpochMilli: Long = -1L
    private set

var zodiacsDateTime: ZonedDateTime? = null
    private set

val redNumbers: IntArray by lazy {
    intArrayOf(1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40, 45, 46)
}

val blueNumbers: IntArray by lazy {
    intArrayOf(3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47, 48)
}

val greenNumbers: IntArray by lazy {
    intArrayOf(5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49)
}

val zodiacs: IntArray by lazy {
    intArrayOf(
        ZODIAC_RAT, ZODIAC_OX, ZODIAC_TIGER, ZODIAC_RABBIT, ZODIAC_DRAGON,
        ZODIAC_SNAKE, ZODIAC_HORSE, ZODIAC_GOAT, ZODIAC_MONKEY, ZODIAC_ROOSTER,
        ZODIAC_DOG, ZODIAC_PIG
    )
}

val havenZodiacs: IntArray by lazy {
    intArrayOf(ZODIAC_OX, ZODIAC_RABBIT, ZODIAC_DRAGON, ZODIAC_HORSE, ZODIAC_MONKEY, ZODIAC_PIG)
}

val groundZodiacs: IntArray by lazy {
    intArrayOf(ZODIAC_RAT, ZODIAC_TIGER, ZODIAC_SNAKE, ZODIAC_GOAT, ZODIAC_ROOSTER, ZODIAC_DOG)
}

val startZodiacs: IntArray by lazy {
    intArrayOf(ZODIAC_RAT, ZODIAC_OX, ZODIAC_TIGER, ZODIAC_RABBIT, ZODIAC_DRAGON, ZODIAC_SNAKE)
}

val endZodiacs: IntArray by lazy {
    intArrayOf(ZODIAC_HORSE, ZODIAC_GOAT, ZODIAC_MONKEY, ZODIAC_ROOSTER, ZODIAC_DOG, ZODIAC_PIG)
}

val beastZodiacs: IntArray by lazy {
    intArrayOf(ZODIAC_RAT, ZODIAC_TIGER, ZODIAC_RABBIT, ZODIAC_DRAGON, ZODIAC_SNAKE, ZODIAC_MONKEY)
}

val homeZodiacs: IntArray by lazy {
    intArrayOf(ZODIAC_OX, ZODIAC_HORSE, ZODIAC_GOAT, ZODIAC_ROOSTER, ZODIAC_DOG, ZODIAC_PIG)
}


val zodiacNames: Array<String> by lazy {
    arrayOf("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪")
}

val zodiacNumbers: ArrayMap<Int, IntArray> by lazy {
    groupZodiacNumbers()
}

val preZodiacNumbers: ArrayMap<Int, IntArray> by lazy {
    if (zodiacsDateTime == null) {
        println(zodiacNumbers.size.toString())
    }
    groupZodiacNumbers(toEpochMilli1("${zodiacsDateTime!!.year - 1}-10-01 00:00:00"))
}

/**
 * 生肖号码组
 */
@JvmOverloads
fun groupZodiacNumbers(ms: Long = System.currentTimeMillis()): ArrayMap<Int, IntArray> {
    val zodiac49 = zodiacOf49(ms)
    val list = ArrayList<Int>()
    var zodiac49Index: Int = -1
    for (i in zodiacs.indices) {
        if (zodiacs[i] == zodiac49) {
            zodiac49Index = i
            break
        }
    }
    for (j in zodiac49Index downTo 0) {
        list.add(zodiacs[j])
    }
    for (j in zodiacs.size - 1 downTo zodiac49Index + 1) {
        list.add(zodiacs[j])
    }

    val map = ArrayMap<Int, IntArray>(12)
    val arrayOfArrays = arrayOf(
        intArrayOf(1, 13, 25, 37, 49),
        intArrayOf(2, 14, 26, 38),
        intArrayOf(3, 15, 27, 39),
        intArrayOf(4, 16, 28, 40),
        intArrayOf(5, 17, 29, 41),
        intArrayOf(6, 18, 30, 42),
        intArrayOf(7, 19, 31, 43),
        intArrayOf(8, 20, 32, 44),
        intArrayOf(9, 21, 33, 45),
        intArrayOf(10, 22, 34, 46),
        intArrayOf(11, 23, 35, 47),
        intArrayOf(12, 24, 36, 48)
    )
    for (q in 0 until list.size) {
        map[list[q]] = arrayOfArrays[q]
    }
    return map
}

/**
 * 当年49属于哪个生肖，官方是农历春节当天更换
 */
fun zodiacOf49(ms: Long): Int {
    val now = ms.toZonedDateTime()
    val years = arrayOf(
        "2017-01-28 00:00:00", "2018-02-16 00:00:00", "2019-02-05 00:00:00", "2020-01-25 00:00:00",
        "2021-02-12 00:00:00", "2022-02-01 00:00:00", "2023-01-22 00:00:00", "2024-02-10 00:00:00",
        "2025-01-29 00:00:00", "2026-02-17 00:00:00", "2027-02-06 00:00:00", "2028-02-25 00:00:00",
        "2029-02-13 00:00:00", "2030-02-03 00:00:00"
    )
    var start = toZonedDateTime1(years[0])
    var end = toZonedDateTime1(years[1])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_ROOSTER
    }
    start = toZonedDateTime1(years[1])
    end = toZonedDateTime1(years[2])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_DOG
    }
    start = toZonedDateTime1(years[2])
    end = toZonedDateTime1(years[3])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_PIG
    }
    start = toZonedDateTime1(years[3])
    end = toZonedDateTime1(years[4])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_RAT
    }
    start = toZonedDateTime1(years[4])
    end = toZonedDateTime1(years[5])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_OX
    }
    start = toZonedDateTime1(years[5])
    end = toZonedDateTime1(years[6])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_TIGER
    }
    start = toZonedDateTime1(years[6])
    end = toZonedDateTime1(years[7])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_RABBIT
    }
    start = toZonedDateTime1(years[7])
    end = toZonedDateTime1(years[8])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_DRAGON
    }
    start = toZonedDateTime1(years[8])
    end = toZonedDateTime1(years[9])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_SNAKE
    }
    start = toZonedDateTime1(years[9])
    end = toZonedDateTime1(years[10])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_HORSE
    }
    start = toZonedDateTime1(years[10])
    end = toZonedDateTime1(years[11])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_GOAT
    }
    start = toZonedDateTime1(years[11])
    end = toZonedDateTime1(years[12])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_MONKEY
    }
    start = toZonedDateTime1(years[12])
    end = toZonedDateTime1(years[13])
    if (now >= start && now < end) {
        setZodiacsDateTime(start)
        return ZODIAC_ROOSTER
    }
    return unsupportedOperationException()
}

private fun setZodiacsDateTime(dateTime: ZonedDateTime) {
    if (zodiacsDateTime == null) {
        zodiacsDateTime = dateTime
        zodiacsEpochMilli = dateTime.toInstant().toEpochMilli()
    }
}