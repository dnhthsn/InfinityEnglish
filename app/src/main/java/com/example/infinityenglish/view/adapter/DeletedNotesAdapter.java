package com.example.infinityenglish.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.databinding.ItemDeletedNotesBinding;
import com.example.infinityenglish.models.Notes;

import java.util.List;

public class DeletedNotesAdapter extends RecyclerView.Adapter<DeletedNotesAdapter.DeletedNotesViewHolder> {
    private List<Notes> notes;

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public DeletedNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDeletedNotesBinding binding = ItemDeletedNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DeletedNotesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeletedNotesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Repository repository = new Repository(holder.binding.getRoot().getContext());
        holder.binding.titleNote.setText(notes.get(position).getTitle());
        holder.binding.contentNote.setText(notes.get(position).getContent());
        holder.binding.deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.deleteNotePermanently(holder.binding.getRoot(), notes.get(position).getId());
                notes.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.binding.restoreNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.addNote(notes.get(position));
                repository.deleteNotePermanently(holder.binding.getRoot(), notes.get(position).getId());
                notes.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class DeletedNotesViewHolder extends RecyclerView.ViewHolder {
        private ItemDeletedNotesBinding binding;
        public DeletedNotesViewHolder(ItemDeletedNotesBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
