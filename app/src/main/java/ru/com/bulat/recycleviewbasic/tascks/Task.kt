package ru.com.bulat.recycleviewbasic.tascks

typealias Callback<T> = (T) -> Unit

interface Task<T> {

    fun onSuccess(callback : Callback<T>) : Task<T>

    fun onError(callback: Callback<Throwable>) : Task<T>

    fun cansel()

    fun await() : T

}