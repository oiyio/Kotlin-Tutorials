package chapter3Other

import java.awt.Color
import java.util.*

/*
    Function		Object Ref		Returns			    Extension Function
    let			    it			    Lambda result		Yes
    run			    this			Lambda result		Yes
    run			    -			    Lambda result		No:called without context object
    with			this			Lambda result		No: takes the context as argument
    apply			this			Context object		Yes
    also			it			    Context object		Yes



    //return arbitrary value R
    fun <T, R> T.let(block: (T) -> R): R                //T exposed as it
    fun <T, R> T.run(block: T.() -> R): R               //T exposed as this

    //return arbitrary value R, not an extension function
    fun <T, R> with(receiver: T, block: T.() -> R): R   //T exposed as this

        //return receiver T
    fun <T> T.also(block: (T) -> Unit): T               //T exposed as it
    fun <T> T.apply(block: T.() -> Unit): T             //T exposed as this

    Lambda result: Last called function     R
    Context object: object itself           T

 */
fun main() {

    val citizen1: Citizen? = Citizen("Hans", 30, "Berlin")
    val citizen2 = Citizen("Tom", 24, "Washington")

    // Info 🔥 There are 6 scoping functions : T.run, T.let, T.apply, T.also, with, run Functions
    // T is the receiver object

    // 🔥 1 - T.run   -   this, nothing -> Lambda result      -   returns anything
    val total: Int? = citizen1?.run {
        println("T.run - ${this.age} - $age")
        //println("The receiver string length: ${this.length}") // does the same
        this.name.length + age + this.residence.length
    }
    println("return value of run :  $total")


    // 🔥 2 - T.let   -   it -> Lambda result    -   returns anything
    val total2: Int = citizen1?.let {
        println("T.let - ${it.age}")
        it.age + it.residence.length + it.name.length
    } ?: -1
    println("return value of let :  $total2")


    // 🔥 3 - T.apply     -   this or nothing -> Context object      -   returns self(receiver object)
    val id: Int? = User().apply {
        id = 100
        this.id = 200
        println("T.apply - The receiver object is citizen4. id : $id - id : ${this.id}")
    }.id


    // 🔥 4 - T.also  -   it -> Context object       -   returns self(receiver object)
    val citizenNew: Citizen = citizen2.also {
        it.age = 77
    }
    println("T.also returns T -  name : ${citizenNew.age}")


    // 🔥5 - with    -   this or nothing -> Context object   -   returns anything
    val age = with(citizen2) {
        println("$name -  $age  $residence ")
        age = this.age + age
        residence = "Florida"
        age++
    }
    println("${citizen2.name} -  ${citizen2.age} - $age - ${citizen2.residence}  ")


    // 🔥 6 - run    -   returns anything
    var mood = "I am sad"
    var mood2 = run {
        val mood = "I am happy"
        println(mood) // I am happy
        "I am excited"
    }
    println("mood : $mood - mood2 : $mood2" )  // I am sad - I am excited


    /* 🔥🔥🔥 other examples are below 🔥🔥🔥*/



    val test = citizen1?.let {
        it.moveTo("Amsterdam")
        it.increment(it.age)
    }


    // INFO 🔥 let
    val numberList = mutableListOf("one", "two", "three", "four", "five")
    numberList.map { it.length }.filter { it > 3 }.let {
        println("NumberList let() it: $it")
        // and more function calls if needed
    }


//    If the code block contains a single function with it as an argument,
//    you can use the method reference ( :: ) instead of the lambda:
    numberList.map { it.length }.filter { it > 3 }.let(::println)

    val strNullable: String? = "Hello"
    //processNonNullString(str)             // compilation error: str can be null

    val length = strNullable?.let {
        println("let() called on $it")
        // processNonNullString(it)        // OK: 'it' is not null inside '?.let { }'
        it.length
    }


//    Another case for using let is introducing local variables with a limited scope for improving code readability.
//    To define a new variable for the context object,
//    provide its name as the lambda argument so that it can be used instead of the default it .

    val numbersLet = listOf("one", "two", "three", "four")

    val modifiedFirstItem = numbersLet.first().let { firstItem ->
        println("The first item of the list is '$firstItem'")
        if (firstItem.length >= 5) firstItem else "!$firstItem!"
    }.toUpperCase()
    println("First item after modifications: '$modifiedFirstItem'")


    // INFO 🔥 with

    val numbersWith = mutableListOf("one", "two", "three")

    with(numbersWith) {
        println("'with' is called with argument $this")
        println("It contains $size elements")
    }

    val numbersWith2 = mutableListOf("one", "two", "three")
    val firstAndLast = with(numbersWith2) {
        "The first element is ${first()}," +
                " the last element is ${last()}"
    }

    println(firstAndLast)

    // INFO 🔥 run

    // a. Idiomatic replacement for if (object != null) blocks
    val text: String? = "This text"

    val len = text?.run {
        println("get length of $this")
        length //`this` can be omitted
    } ?: 0
    println("Length of $text is $len")

    // b. Transformation
    val date: Int = Calendar.getInstance().run {
        set(Calendar.YEAR, 2030)
        get(Calendar.DAY_OF_YEAR) //return value of run
    }
    println(date)


    // INFO 🔥 apply

//    a. Initializing an object

    val bar: Bar = Bar().apply {
        foo1 = Color.RED
        foo2 = "Foo"
    }
    println(bar)

//    b. Builder-style usage of methods that return Unit
    val fooBar = FooBar().first(10).second("foobarValue")
    println(fooBar)


    // INFO 🔥 also

    val numbers = mutableListOf("one", "two", "three")
    numbers
        .also { println("The list elements before adding new one: $it") }
        .add("four")

    // INFO 🔥 takeIf and takeUnless

    val number = Random().nextInt(100)
    val evenOrNull = number.takeIf { it % 2 == 0 }
    val oddOrNull = number.takeUnless { it % 2 == 0 }
    println("number $number, even: $evenOrNull, odd: $oddOrNull")


//    When chaining other functions after takeIf and takeUnless ,
//    don't forget to perform the null check or the safe call ( ?. ) because their return value is nullable.
    val str = "Hello"
//    val caps = str.takeIf { it.isNotEmpty() }.toUpperCase() //compilation error println(caps)
    val caps = str.takeIf { it.isNotEmpty() }?.toUpperCase()


    displaySubstringPosition("010000011", "11")
    displaySubstringPosition("010000011", "12")


}

// INFO 🔥 Builder-style usage of methods that return Unit
data class FooBar(var a: Int = 0, var b: String? = null) {

    fun first(aArg: Int): FooBar = apply { a = aArg }
    fun second(bArg: String): FooBar = apply { b = bArg }
}

// INFO 🔥 takeIf and takeUnless

fun displaySubstringPosition(input: String, sub: String) {

    input.indexOf(sub).takeIf { it >= 0 }?.let {
        println("The substring $sub is found in $input.")
        println("Its start position is $it.")
    }
}

// 🔥 Without standard functions
fun displaySubstringPositionWithout(input: String, sub: String) {

    val index = input.indexOf(sub)

    if (index >= 0) {
        println("The substring $sub is found in $input.")
        println("Its start position is $index.")
    }
}


class Citizen(var name: String, var age: Int, var residence: String) {

    fun moveTo(city: String) {
        residence = city
    }

    fun increment(value: Int): Int {
        return value + 1
    }
}

class User(var id:Int?=-1)

class Bar() {

    var foo1: Color? = null
    var foo2: String? = null

}

fun test() {
    var mood = "I am sad"

    run {
        val mood = "I am happy"
        println(mood) // I am happy
    }
    println(mood)  // I am sad
}

