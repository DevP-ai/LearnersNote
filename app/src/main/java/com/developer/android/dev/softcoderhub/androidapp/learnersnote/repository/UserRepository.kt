package com.developer.android.dev.softcoderhub.androidapp.learnersnote.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.UserAPI
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserResponse
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.Constant.TAG
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
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
        handleResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest){
        val response = userAPI.signIn(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }


}