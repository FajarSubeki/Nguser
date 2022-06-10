package com.example.nguser.ui

import androidx.lifecycle.ViewModel
import com.example.nguser.data.model.User
import com.example.nguser.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){

    fun getUser() = userRepository.getUserList()

    fun getAllUser() = userRepository.getUsers()

    suspend fun addUser(user: User) = userRepository.addUser(user)

    suspend fun addAllUser(user: MutableList<User>) = userRepository.addAllUser(user)

    suspend fun updateUser(user: User) = userRepository.updateUser(user)

    suspend fun deleteUser(user: User) = userRepository.deleteUser(user)

    suspend fun deleteAllUser() = userRepository.deleteAllUser()

}