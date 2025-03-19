package dev.gbenga.endurely

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val statusCode = 200
        assertEquals(200, statusCode)
    }

    @Test
    fun addition_isInCorrect() {
        val statusCode = 404
        assertEquals(200, statusCode)
    }
}