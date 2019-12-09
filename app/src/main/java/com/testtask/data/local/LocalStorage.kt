package com.testtask.data.local

interface LocalStorage<T> {

    fun save (key: String, value: T)

    fun get(key: String): T?

}