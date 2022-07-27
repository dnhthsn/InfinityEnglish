package com.example.infinityenglish.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.databinding.ItemNotesBinding;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.activity.NoteActivity;
import com.example.infinityenglish.view.activity.SearchActivity;
import com.example.infinityenglish.view.activity.UpdateNoteActivity;
import com.example.infinityenglish.view.activity.WriteNoteActivity;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Notes> notes;

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotesBinding binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Repository repository = new Repository(holder.binding.getRoot().getContext());
        holder.binding.titleNote.setText(notes.get(position).getTitle());
        holder.binding.contentNote.setText(notes.get(position).getContent());
        holder.binding.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.deleteNote(notes.get(position).getId(), holder.binding.getRoot());
                notes.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.binding.getRoot().getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.binding.getRoot().getContext(), UpdateNoteActivity.class);
                i.putExtra(Const.Sender.noteId, notes.get(position).getId());
                i.putExtra(Const.Sender.noteTitle, notes.get(position).getTitle());
                i.putExtra(Const.Sender.noteContent, notes.get(position).getContent());
                holder.binding.getRoot().getContext().startActivity(i);
                NoteActivity.finishActivity((Activity) holder.binding.getRoot().getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private ItemNotesBinding binding;
        public NoteViewHolder(ItemNotesBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
