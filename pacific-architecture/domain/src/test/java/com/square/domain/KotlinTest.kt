package com.square.domain

import kotlinx.coroutines.*
import org.junit.Test

class KotlinTest {

    @Test
    fun main() {
        runBlocking {
            //sampleStart
            val job = launch {
                val child = launch {
                    try {
                        delay(Long.MAX_VALUE)
                    } catch (e:Exception) {
                        println("Child is cancelled")
                    }
                }
                yield()
                println("Cancelling child")
                child.cancel()
                child.join()
                yield()
                println("Parent is not cancelled")
            }
            job.join()
            // sampleEnd
        }
    }

    @Test
    fun main2() {
        runBlocking {
            //sampleStart
            val handler = CoroutineExceptionHandler { _, exception ->
                println("Caught $exception")
            }
            val job = GlobalScope.launch(handler) {
                launch { // the first child
                    withContext(NonCancellable) {
                        println("Children are cancelled, but exception is not handled until all children terminate")
                        delay(100)
                        println("The first child finished its non cancellable block")
                    }
                }
                launch { // the second child
                    delay(10)
                    println("Second child throws an exception")
                    throw ArithmeticException()
                }
            }
            job.join()
//sampleEnd
        }
    }
}
