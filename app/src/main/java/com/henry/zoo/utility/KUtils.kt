package com.henry.zoo.utility

import com.google.gson.reflect.TypeToken

class KUtils {
    companion object {
        inline fun <reified T> getTypeToken() = object: TypeToken<T>() {}.type
    }
}