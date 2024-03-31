package com.developer.android.dev.softcoderhub.androidapp.learnersnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.databinding.FragmentNoteBinding
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteResponse
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.viewmodel.NotesViewmodel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding:FragmentNoteBinding?=null
    private val binding get() = _binding!!

    private var note: NoteResponse?=null

    private val notesViewmodel by viewModels<NotesViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         _binding = FragmentNoteBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandler()
        bindObserver()
    }

    private fun bindHandler() {
        binding.btnDelete.setOnClickListener {
            note.let {
                lifecycleScope.launch {
                    notesViewmodel.deleteNote(it!!._id)
                }
                findNavController().popBackStack()
            }
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val description = binding.txtDescription.text.toString()
            val noteRequest = NoteRequest(description,title)
            findNavController().popBackStack()
            if(note == null){
                lifecycleScope.launch{
                    notesViewmodel.createNote(noteRequest)
                }
            }
            else{
                lifecycleScope.launch {
                    notesViewmodel.updateNote(note!!._id,noteRequest)
                }
            }
        }
    }

    private fun bindObserver() {
        notesViewmodel.noteLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        })
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if(jsonNote != null){
            note = Gson().fromJson(jsonNote,NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        }
        else{
            binding.addEditText.text = "Add Note"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}