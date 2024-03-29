package com.developer.android.dev.softcoderhub.androidapp.learnersnote.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserResponse
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.repository.UserRepository
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
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

    fun validateCredential(emailAddress: String, userName: String, password: String, isLogin: Boolean):Pair<Boolean,String>{
        var result = Pair(true,"")
        if((!isLogin && TextUtils.isEmpty(userName)) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(password)){
            result = Pair(false,"Please provide credentials")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            result = Pair(false,"Please provide valid email")
        }
        else if(password.length<=5){
            result = Pair(false,"Password length should be greater than 5")
        }
        return  result
    }

}