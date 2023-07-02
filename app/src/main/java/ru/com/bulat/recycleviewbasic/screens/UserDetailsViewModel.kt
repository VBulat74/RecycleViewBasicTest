package ru.com.bulat.recycleviewbasic.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.com.bulat.recycleviewbasic.UserNotFoundException
import ru.com.bulat.recycleviewbasic.model.User
import ru.com.bulat.recycleviewbasic.model.UserDetails
import ru.com.bulat.recycleviewbasic.model.UsersService

class UserDetailsViewModel(
    private val usersService: UsersService
) : ViewModel() {

    private val _userDetail = MutableLiveData<UserDetails>()
    val userDetail : LiveData<UserDetails> = _userDetail

    fun loadUser (userId:Long){
        if (_userDetail.value != null) return
        try {
            _userDetail.value = usersService.getById(userId)
        } catch (e: UserNotFoundException){e.printStackTrace()}

    }

    fun deleteUser(){
        val userDetails : UserDetails = this.userDetail.value ?: return
        usersService.deleteUser(userDetails.user)
    }
}