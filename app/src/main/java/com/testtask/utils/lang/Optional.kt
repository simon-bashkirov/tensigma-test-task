package com.testtask.utils.lang

/**
 * Wrapper for optional value of T.
 * Call constructor without parameter to create an empty instance
 */
data class Optional<T>(val value: T? = null)