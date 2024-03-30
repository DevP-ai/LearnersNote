package com.developer.android.dev.softcoderhub.androidapp.learnersnote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.NotesAPI
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteResponse
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val notesAPI: NotesAPI) {

    private val _notesLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val notesLiveData : LiveData<NetworkResult<List<NoteResponse>>> = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData : LiveData<NetworkResult<String>> = _statusLiveData


    suspend fun getNotes(){
        _notesLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.getNotes()
        handleGetNotesResponse(response)
    }


    suspend fun createNote(noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.createNote(noteRequest)
        handleCUDResponse(response,"Note Created")
    }

    suspend fun updateNote(noteId: String,noteRequest: NoteRequest){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.updateNote(noteId,noteRequest)
        handleCUDResponse(response,"Note Updated")
    }

    suspend fun deleteNote(noteId:String){
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.deleteNote(noteId)
        handleCUDResponse(response,"Note Deleted")
    }


    private fun handleCUDResponse(response: Response<NoteResponse>,message:String){
        if(response.isSuccessful && response.body() != null){
            _statusLiveData.postValue(NetworkResult.Success(message))
        }else{
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
    private fun handleGetNotesResponse(response: Response<List<NoteResponse>>) {
        if(response.isSuccessful && response.body() != null){
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody() !=null){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        }
        else{
            _notesLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}