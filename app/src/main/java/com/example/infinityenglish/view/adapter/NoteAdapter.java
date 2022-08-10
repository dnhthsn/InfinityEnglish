package com.example.infinityenglish.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.infinityenglish.control.Repository;
import com.example.infinityenglish.databinding.ItemNotesBinding;
import com.example.infinityenglish.models.Notes;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Const;
import com.example.infinityenglish.view.activity.NoteActivity;
import com.example.infinityenglish.view.activity.UpdateNoteActivity;
import com.example.infinityenglish.viewmodel.UserViewModel;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
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
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Repository repository = new Repository(holder.binding.getRoot().getContext());
        UserViewModel userViewModel = new ViewModelProvider((ViewModelStoreOwner) holder.binding.getRoot().getContext()).get(UserViewModel.class);
        userViewModel.init(holder.binding.getRoot().getContext());
        boolean state = userViewModel.getStateLogin();
        Users users = userViewModel.getCurrentUser();

        viewBinderHelper.bind(holder.binding.swipeNote, String.valueOf(notes.get(position).getId()));

        holder.binding.titleNote.setText(notes.get(position).getTitle());
        holder.binding.contentNote.setText(notes.get(position).getContent());

        holder.binding.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state){
                    repository.deleteOnlineNote(users, notes.get(position).getId(), holder.binding.getRoot().getRootView());
                    notes.remove(position);
                    notifyDataSetChanged();
                } else {
                    repository.deleteNote(notes.get(position).getId(), holder.binding.getRoot().getRootView());
                    notes.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        holder.binding.clickEdit.setOnClickListener(new View.OnClickListener() {
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
        if (notes == null){
            return 0;
        }
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
