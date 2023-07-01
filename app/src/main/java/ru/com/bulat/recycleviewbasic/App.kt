package ru.com.bulat.recycleviewbasic

import android.app.Application
import ru.com.bulat.recycleviewbasic.model.UsersService

class App : Application() {
    val usersService = UsersService()
}