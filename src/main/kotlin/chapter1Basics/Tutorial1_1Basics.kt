package chapter1Basics

fun main() {

    var a: Int = 1000;

    a += 10
    println("a: $a")

    if (true is Boolean) {
        print("true is boolean\n")
    }

    /*
        *** Characters ***
     */
    var letterGrade: Char = 'A'

    // instance of -> is
    println("BaseClassA is a Char : ${letterGrade is Char} ")

    // Get ASCII code of character
    println("BaseClassA ascii: ${letterGrade.toInt()}")

    println("3.14 to int ${3.14.toInt()}")

    /*
        *** Strings ***
    */

    println("********** Strings **********")


    var rawString: String = "I am Raw String!"
    val escapedString: String = "I am escaped String!\n"

    // Escaped String has end of line character
    println("Hello!$escapedString")
    println("Hey!!$rawString")

    val obj = SomeObj()
    takeObject(obj)
    println("obj after call: ${obj.x}") // obj after call: 1
}

data class SomeObj(var x: Int = 0)

/*1 - members of object can be changed inside a function
  2 - the object which parameter o refers can not be changed. In other words, new object cannot be assigned to o. */
fun takeObject(o: SomeObj) {
    o.x = 1
    var o1 = o
    o1 = SomeObj(2)
}