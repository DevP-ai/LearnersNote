package com.developer.android.dev.softcoderhub.androidapp.learnersnote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteResponse
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.repository.NoteRepository
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotesViewmodel @Inject constructor(private val noteRepository: NoteRepository):ViewModel(){

//    val notesResponseLiveData:LiveData<NetworkResult<List<NoteResponse>>> = noteRepository.notesLiveData
//
//    val statusLiveData : LiveData<NetworkResult<String>> = noteRepository.statusLiveData

    val noteLiveData get() = noteRepository.notesLiveData
    val statusLiveData get() =  noteRepository.statusLiveData

    suspend fun getNotes(){
        viewModelScope.launch {
            noteRepository.getNotes()
        }
    }

    suspend fun createNote(noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.createNote(noteRequest)
        }
    }

    suspend fun updateNote(noteId:String,noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.updateNote(noteId,noteRequest)
        }
    }

    suspend fun deleteNote(noteId: String){
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }
}