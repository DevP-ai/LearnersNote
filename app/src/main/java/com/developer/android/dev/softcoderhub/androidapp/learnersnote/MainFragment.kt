package com.developer.android.dev.softcoderhub.androidapp.learnersnote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.NotesAPI
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.Constant.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var notesAPI: NotesAPI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        CoroutineScope(Dispatchers.IO).launch {
            val response = notesAPI.getNotes()
            Log.d(TAG,response.body().toString())
        }
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


}