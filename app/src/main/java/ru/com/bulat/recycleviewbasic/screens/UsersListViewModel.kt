package ru.com.bulat.recycleviewbasic.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.com.bulat.recycleviewbasic.model.User
import ru.com.bulat.recycleviewbasic.model.UsersListener
import ru.com.bulat.recycleviewbasic.model.UsersService
import ru.com.bulat.recycleviewbasic.tascks.EmptyResult

data class UserListItem(
    val user: User,
    val isInProgress : Boolean
)

class UsersListViewModel (
    private val usersService: UsersService
) : ViewModel() {

    private val _users = MutableLiveData<List<UserListItem>>()
    val users : LiveData<List<UserListItem>> = _users

    private val userIdsInProgress = mutableSetOf<Long>()
    private var usersResult  = EmptyResult<List<User>>()
        set(value) {
            field = value
        }


    private val listener: UsersListener ={
        _users.value = it
    }

    init {
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        usersService.removeListener(listener)
    }

    fun loadUsers(){
        usersService.addListener(listener)
    }

    fun moveUsers(user: User, moveBy:Int){
        usersService.moveUser(user, moveBy)
    }

    fun deleteUser (user: User){
        usersService.deleteUser(user)
    }

    fun fireUser (user: User){
        usersService.fireUser(user)
    }
}