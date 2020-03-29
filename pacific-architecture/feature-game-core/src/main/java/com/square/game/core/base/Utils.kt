package com.square.game.core.base

const val SSS1 = -101
const val SSS2 = -102
const val SSS3 = -103
const val SSS4 = -104
const val SSS5 = -105
const val SSS6 = -106
const val SSS7 = -107
const val SSS8 = -108
const val SSS9 = -109
const val SSS10 = -110
const val SSS11 = -111
const val SSS12 = -112
const val SSS13 = -113
const val SSS14 = -114
const val SSS15 = -115
const val SSS_UNKNOWN = -116

const val STR_UNDERSCORE = "_"
const val STR_DOT = "."
const val STR_COLON = ":"
const val STR_COMMA = ","
const val STR_HASH = "#"
const val STR_AT = "@"
const val STR_VERTICAL_BAR = "|"
const val STR_DOLLAR = "$"
const val STR_SPACE = " "
const val STR_EMPTY = ""

val STR_UNDERSCORE_REG by lazy { STR_UNDERSCORE.toRegex() }

val STR_DOT_REG by lazy { STR_DOT.toRegex() }

val STR_COLON_REG by lazy { STR_COLON.toRegex() }

val STR_COMMA_REG by lazy { STR_COMMA.toRegex() }

val STR_HASH_REG by lazy { STR_HASH.toRegex() }

val STR_AT_REG by lazy { STR_AT.toRegex() }

val STR_VERTICAL_BAR_REG by lazy { STR_VERTICAL_BAR.toRegex() }

val STR_DOLLAR_REG by lazy { STR_DOLLAR.toRegex() }

val STR_SPACE_REG by lazy { STR_SPACE.toRegex() }

val numbers0To9 by lazy { range(0, 9) }

val numbers0To18 by lazy { range(0, 18) }

val numbers0To27 by lazy { range(0, 27) }

val numbers1To10 by lazy { range(1, 10) }

val numbers1To6 by lazy { range(1, 6) }

val numbers1To11 by lazy { range(1, 11) }

val numbers1To16 by lazy { range(1, 16) }

val numbers1To17 by lazy { range(1, 17) }

val numbers1To18 by lazy { range(1, 18) }

val numbers1To26 by lazy { range(1, 26) }

val numbers1To33 by lazy { range(1, 33) }

val numbers1To49 by lazy { range(1, 49) }

val numbers1To80 by lazy { range(1, 80) }

val emptyIntArray: IntArray by lazy { intArrayOf() }
val emptyLongArray: LongArray by lazy { longArrayOf() }
val emptyFloatArray: FloatArray by lazy { floatArrayOf() }
val emptyDoubleArray: DoubleArray by lazy { doubleArrayOf() }
val emptyStringArray: Array<String> by lazy { emptyArray() }

val emptyIntList: List<Int> by lazy { emptyList() }
val emptyLongList: List<Long> by lazy { emptyList() }
val emptyFloatList: List<Float> by lazy { emptyList() }
val emptyDoubleList: List<Double> by lazy { emptyList() }
val emptyStringList: List<String> by lazy { emptyList() }