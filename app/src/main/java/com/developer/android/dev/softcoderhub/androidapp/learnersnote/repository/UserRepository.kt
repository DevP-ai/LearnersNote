package com.developer.android.dev.softcoderhub.androidapp.learnersnote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.UserAPI
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserResponse
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.Constant.TAG
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()

//    val userResponseLiveData : LiveData<NetworkResult<UserResponse>> = _userResponseLiveData

    /*
     Below code also can be write as above commented line code
    */
    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
    get() = _userResponseLiveData


    suspend fun registerUser(userRequest: UserRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.signup(userRequest)
        if(response.isSuccessful && response.body()!=null){
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if (response.errorBody() !=null){
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
        else{
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun loginUser(userRequest: UserRequest){
        val response = userAPI.signIn(userRequest)
        Log.d(TAG,response.body().toString())
    }
}