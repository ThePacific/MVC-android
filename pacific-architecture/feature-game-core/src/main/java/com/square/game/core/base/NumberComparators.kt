package com.square.game.core.base

/**
 * 升序，1，2，3
 */
val ascComparatorInt: Comparator<Int> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> 1
            o1 == o2 -> 0
            else -> -1
        }
    }
}

/**
 * 降序，3，2，1
 */
val desComparatorInt: Comparator<Int> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> -1
            o1 == o2 -> 0
            else -> 1
        }
    }
}

/**
 * 升序，1，2，3
 */
val ascComparatorLong: Comparator<Long> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> 1
            o1 == o2 -> 0
            else -> -1
        }
    }
}

/**
 * 降序，3，2，1
 */
val desComparatorLong: Comparator<Long> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> -1
            o1 == o2 -> 0
            else -> 1
        }
    }
}

/**
 * 升序，1，2，3
 */
val ascComparatorFloat: Comparator<Float> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> 1
            o1 == o2 -> 0
            else -> -1
        }
    }
}

/**
 * 降序，3，2，1
 */
val desComparatorFloat: Comparator<Float> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> -1
            o1 == o2 -> 0
            else -> 1
        }
    }
}

/**
 * 升序，1，2，3
 */
val ascComparatorDouble: Comparator<Double> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> 1
            o1 == o2 -> 0
            else -> -1
        }
    }
}

/**
 * 降序，3，2，1
 */
val desComparatorDouble: Comparator<Double> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> -1
            o1 == o2 -> 0
            else -> 1
        }
    }
}

/**
 * 升序，1，2，3
 */
val ascComparatorShort: Comparator<Short> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> 1
            o1 == o2 -> 0
            else -> -1
        }
    }
}

/**
 * 降序，3，2，1
 */
val desComparatorShort: Comparator<Short> by lazy {
    Comparator { o1, o2 ->
        return@Comparator when {
            o1 > o2 -> -1
            o1 == o2 -> 0
            else -> 1
        }
    }
}