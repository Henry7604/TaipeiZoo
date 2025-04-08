package com.example.taipeizoo

val String.replaceHttp: String
    get() = replace("http://", "https://")

fun String.toHalfWidth(): String {
    return this.map {
        when (it) {
            '（' -> '('
            '）' -> ')'
            else -> it
        }
    }.joinToString("")
}