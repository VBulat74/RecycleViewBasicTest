package ru.com.bulat.recycleviewbasic

import ru.com.bulat.recycleviewbasic.model.User

interface Navigator {
    fun showDetails(user: User)

    fun goBack()

    fun toast (messageRes: Int)

}