package com.developer.android.dev.softcoderhub.androidapp.learnersnote

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.adapter.NoteAdapter
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.api.NotesAPI
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.databinding.FragmentMainBinding
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteResponse
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.Constant.TAG
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.utils.NetworkResult
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.viewmodel.NotesViewmodel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding:FragmentMainBinding?= null
    private val binding get() = _binding!!

    private val notesViewmodel by viewModels<NotesViewmodel>()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater)
        noteAdapter = NoteAdapter(::onNoteClicked)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addNote.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }

        bindObserver()

        lifecycleScope.launch{
            notesViewmodel.getNotes()
        }

        bindRecyclerView()
    }

    private fun bindRecyclerView() {
        binding.noteList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.noteList.adapter = noteAdapter
    }

    private fun bindObserver() {
        notesViewmodel.noteLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success -> {
                    noteAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                   Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun onNoteClicked(noteResponse: NoteResponse){
        val bundle = Bundle()
        bundle.putString("note",Gson().toJson(noteResponse))
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment,bundle)

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}