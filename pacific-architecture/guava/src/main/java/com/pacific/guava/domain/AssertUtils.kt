package com.pacific.guava.domain

import java.io.IOException
import java.util.*
import java.util.concurrent.TimeoutException

@JvmOverloads
fun throwError(message: String = "Error"): Nothing {
    throw Error(message)
}

@JvmOverloads
fun throwException(message: String = "Exception"): Nothing {
    throw Exception(message)
}

@JvmOverloads
fun runtimeException(message: String = "RuntimeException"): Nothing {
    throw RuntimeException(message)
}

@JvmOverloads
fun illegalArgumentException(message: String = "IllegalArgumentException"): Nothing {
    throw IllegalArgumentException(message)
}

@JvmOverloads
fun illegalStateException(message: String = "IllegalStateException"): Nothing {
    throw IllegalStateException(message)
}

@JvmOverloads
fun indexOutOfBoundsException(message: String = "IndexOutOfBoundsException"): Nothing {
    throw IndexOutOfBoundsException(message)
}

@JvmOverloads
fun unsupportedOperationException(message: String = "UnsupportedOperationException"): Nothing {
    throw UnsupportedOperationException(message)
}

@JvmOverloads
fun arithmeticException(message: String = "ArithmeticException"): Nothing {
    throw ArithmeticException(message)
}

@JvmOverloads
fun numberFormatException(message: String = "NumberFormatException"): Nothing {
    throw NumberFormatException(message)
}

@JvmOverloads
fun nullPointerException(message: String = "NullPointerException"): Nothing {
    throw NullPointerException(message)
}

@JvmOverloads
fun classCastException(message: String = "ClassCastException"): Nothing {
    throw ClassCastException(message)
}

@JvmOverloads
fun assertionError(message: String = "AssertionError"): Nothing {
    throw AssertionError(message)
}

@JvmOverloads
fun noSuchElementException(message: String = "NoSuchElementException"): Nothing {
    throw NoSuchElementException(message)
}

@JvmOverloads
fun concurrentModificationException(message: String = "ConcurrentModificationException"): Nothing {
    throw ConcurrentModificationException(message)
}

@JvmOverloads
fun ioException(message: String = "IOException"): Nothing {
    throw IOException(message)
}

@JvmOverloads
fun timeoutException(message: String = "TimeoutException"): Nothing {
    throw TimeoutException(message)
}