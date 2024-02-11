package com.example.waluty.extensions

fun String?.orDefault(defaultValue: String): String {
    return this ?: run {
        defaultValue
    }
}