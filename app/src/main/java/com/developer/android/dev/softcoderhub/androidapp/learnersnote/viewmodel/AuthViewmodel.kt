package com.developer.android.dev.softcoderhub.androidapp.learnersnote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserResponse
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.repository.UserRepository
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewmodel @Inject constructor(private val userRepository: UserRepository):ViewModel(){

//    val userResponseLiveData: LiveData<NetworkResult<UserResponse>> = userRepository.userResponseLiveData
    val userResponseLiveData :LiveData<NetworkResult<UserResponse>>
    get() = userRepository.userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

   suspend fun loginUser(userRequest: UserRequest){
       viewModelScope.launch {
           userRepository.loginUser(userRequest)
       }
   }
}