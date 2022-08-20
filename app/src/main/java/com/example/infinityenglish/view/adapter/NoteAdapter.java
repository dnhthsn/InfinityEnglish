package com.example.infinityenglish.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.infinityenglish.R;
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

    @SuppressLint("NotifyDataSetChanged")
    public void setNotes(List<Notes> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotesBinding binding1 = ItemNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding1);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Repository repository = new Repository(holder.binding.getRoot().getContext());
        UserViewModel userViewModel = new ViewModelProvider((ViewModelStoreOwner) holder.binding.getRoot().getContext()).get(UserViewModel.class);
        userViewModel.init(holder.binding.getRoot().getContext());
        Users users = userViewModel.getCurrentUser();

        viewBinderHelper.bind(holder.binding.swipeNote, String.valueOf(notes.get(position).getId()));

        holder.binding.titleNote.setText(notes.get(position).getTitle());
        holder.binding.contentNote.setText(notes.get(position).getContent());

        holder.binding.deleteNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                userViewModel.getState().observe(holder.binding.getLifecycleOwner(), new Observer<Const.State>() {
                    @Override
                    public void onChanged(Const.State state) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                        builder.setView(LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_delete_permanent, null));
                        switch (state) {
                            case Main:
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        repository.deleteOnlineNote(users, notes.get(position).getId(), holder.binding.getRoot().getRootView());
                                        notes.remove(position);
                                        notifyDataSetChanged();
                                    }
                                });
                                break;
                            case Start:
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        repository.deleteNote(notes.get(position).getId(), holder.binding.getRoot().getRootView());
                                        notes.remove(position);
                                        notifyDataSetChanged();
                                    }
                                });
                                break;
                            case none:
                                break;
                        }

                        AlertDialog dialog = builder.create();
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_corner_white);
                        dialog.show();
                    }
                });
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
        if (notes == null) {
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
