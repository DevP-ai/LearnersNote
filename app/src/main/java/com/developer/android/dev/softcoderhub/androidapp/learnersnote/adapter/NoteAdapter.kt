package com.developer.android.dev.softcoderhub.androidapp.learnersnote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.databinding.NoteItemBinding
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteResponse

class NoteAdapter(private val onNoteClicked:(NoteResponse) -> Unit): ListAdapter<NoteResponse, NoteAdapter.NoteViewHolder>(ComparatorDiffUtil()){

    inner class NoteViewHolder(val binding: NoteItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.binding.title.text = note.title
        holder.binding.desc.text = note.description
        holder.binding.root.setOnClickListener {
            onNoteClicked(note)
        }
    }


}