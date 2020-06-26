package chapter1Basics

/*  👀
*   If you use 'while'    or 'for',           then 'break' and 'continue'     CAN be used.
*   If you use 'forEach'  or 'repeat',        then 'break' and 'continue'     CANNOT be used.
*
*
*   🦧 You can replace 'forEach' and 'repeat' with 'for'
*            something.forEach	->      for(it in something)
*            repeat (time)	    ->      for(it in time)
*
*
*   🌗 while and for are completely different from forEach and repeat.
*   while and for are syntax defined as Kotlin.
*   forEach and repeat are higher order functions defined in Kotlin standard library.
*
*   🌈 repeat (3) {
*       // this is function
*      }
*    repeat takes two arguments. First is integer, second is a function which is shown above with {  }
*
*
*   💥 return@labelOfLambda is continue-like control in lambda.
*
*   🎸 You are recommended look at Tutorial4_1HighOrderFunctions_2.kt for usage of repeat, high order function, return and exception concepts together
*
* https://improve-future.com/en/kotlin-why-while-break-or-continue-cant-be-used-in-foreacn-repeat-which-can-be-used-in-while-and-for.html
* https://kotlinlang.org/docs/reference/returns.html
* */

fun main() {
    forEachSample1()
    forEachSample2()
    forEachSample3()
    forEachSample4()
    forEachSample5()
    forEachSample6()
}

/*
*   Prints :
*   1
* */
fun forEachSample1() {
    println()
    listOf(1, 2, 3).forEach {
        println(it)
        return          // 💀 finish forEachSample1() function
        println(it)     // Unreachable code
    }
    println("finish - forEachSample1")  // Unreachable code
}

/*
*   Prints :
*   1
*   2
*   3
*   finished - forEachSample2
*/
fun forEachSample2() {
    println()
    listOf(1, 2, 3).forEach {
        println(it)
        return@forEach  // 🤡 finish forEach function
        println(it)     // Unreachable code
    }
    println("finish - forEachSample2")
}

/** Double forEach
 *  If we use two nested forEach, we can give a name to the label for function paramter.
 *  In the following example, we give name 'outer' to the function parameter of outer forEach.
 *  Thus when we call return @outer inside
 *  In the following code, continue works for outer forEach loop.
 *   Prints :
 *   1
 *   2
 *   3
 *   finished - forEachSample3
 * */
fun forEachSample3() {
    println()
    listOf(1, 2, 3).forEach outer@{ a ->
        listOf(4, 5, 6).forEach { b ->
            println(a)
            return@outer
            println(b)  // Unreachable code
        }
    }
    println("finished - forEachSample3")
}

/*
* If you change lambda to anonymous function, you can simply use return for continuation.
*   Prints :
*   1
*   2
*   3
*   finished - forEachSample4
* */
fun forEachSample4() {
    println()
    listOf(1, 2, 3).forEach(
        fun(value: Int) {
            println(value)
            return          // go back to the top of forEach block
            println(value)  // Unreachable code
        }
    )
    println("finished - forEachSample4")
}

/*
* Usage of break for lambda
* Surround with run, and write return@run.
* Prints :
* 1
* finished - forEachSample5
* */
fun forEachSample5() {
    println()
    run {
        listOf(1, 2, 3).forEach {
            println(it)
            return@run  // finish the loop
            println(it) // Unreachable code
        }
    }
    println("finished - forEachSample5")
}

/*
* break for anonymous function
* Use label like when we use lambda.
* Prints :
* 1
* finished - forEachSample6
* */
fun forEachSample6() {
    println()
    run {
        listOf(1, 2, 3).forEach(
            fun(value) {
                println(value)
                return@run      // finish the loop
                println(value)  // Unreachable code
            }
        )
    }
    println("finished - forEachSample6")
}