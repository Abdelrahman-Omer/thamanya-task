package com.thamanya.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun <T> List<T>.replace(item: T, transform: (T) -> T): List<T> {
    val index = indexOf(item)
    val newList = toMutableList()
    newList[index] = item.run(transform)
    return newList
}

fun <T> List<T>.change(index: Int, transform: (T) -> T): List<T> {
    val newList = toMutableList()
    newList[index] = get(index).run(transform)
    return newList
}

fun <T> List<T>.change(index: Int, newValue: T): List<T> {
    val newList = toMutableList()
    newList[index] = newValue
    return newList
}

fun <T> List<T>.add(element: T, index: Int = -1): List<T> {
    val newList = toMutableList()
    if (index == -1) {
        newList.add(element)
    } else {
        newList.add(index, element)
    }
    return newList
}

fun <T> List<T>.addAll(elements: List<T>): List<T> {
    val newList = toMutableList()
    newList.addAll(elements)
    return newList
}
fun <T> List<T>.remove(element: T): List<T> {
    val newList = toMutableList()
    newList.remove(element)
    return newList
}

fun <T> List<T>.remove(predicate: (T) -> Boolean): List<T> {
    val newList = toMutableList()
    newList.removeIf(predicate)
    return newList
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullOrEmpty != null)
    }

    return !isNullOrEmpty()
}