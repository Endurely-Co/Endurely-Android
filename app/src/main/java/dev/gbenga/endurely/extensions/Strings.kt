package dev.gbenga.endurely.extensions

fun String.hasNChars(n: Int): Boolean = this.length >= n

fun  Array<String>.hasNChars(n: Int): Boolean{
    val temp = this.clone()
    return filter { it.hasNChars(n) }.size == temp.size
}

fun hasEmptyString(vararg strings: String) = strings.filter { it.trim().isEmpty() }.size == strings.size
