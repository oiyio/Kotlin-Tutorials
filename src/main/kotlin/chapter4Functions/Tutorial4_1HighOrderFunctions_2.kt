package chapter4Functions

import java.lang.RuntimeException


fun main() {
    retryExample1()
    retryExample2()
}

fun retryExample1() {
    println()
    try {
        retry(
            times = 3,
            block = {it ->
                if(it<2)
                    throw RuntimeException("error in retryExample1")
                println("retryExampleTest1 - x")
            }
        )

    } catch (exception: Exception) {
        println("retryExampleTest1 - 4")
    }
    println("retryExampleTest1 - retryExample1")
}

fun retryExample2() {
    println()
    try {
        retry(
            times = 3,
            block = {it ->
                throw RuntimeException("error in retryExample2")
                println("retryExampleTest2 - x")   // Unreachable code
            }
        )

    } catch (exception: Exception) {
        println("retryExampleTest2 - 4")
    }
    println("retryExampleTest2 - retryExample2")
}

private fun <T> retry(
    times: Int,
    block: (Int) -> T?
): T? {

    var retryCount = 0

    repeat(times) {

        try{
            println("retryExampleTest - 1")
            retryCount++
            return block(retryCount)
            // if exception is thrown inside block() function then return does not work, below catch catches the exception, repeat continue to loop
            // if block() does not throw exception, retry function returns, loop ends, following lines are not executed.
        }
        catch (exception : java.lang.Exception){
            println("retryExampleTest - 2")
        }

        println("retryExampleTest - 3")
    }

    throw RuntimeException("exception505")
}


