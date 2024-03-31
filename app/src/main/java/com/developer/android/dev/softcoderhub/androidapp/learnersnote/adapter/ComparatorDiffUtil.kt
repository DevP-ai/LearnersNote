package com.developer.android.dev.softcoderhub.androidapp.learnersnote.adapter

import androidx.recyclerview.widget.DiffUtil
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.NoteResponse

class ComparatorDiffUtil: DiffUtil.ItemCallback<NoteResponse>() {
    override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
        return oldItem == newItem
    }
}