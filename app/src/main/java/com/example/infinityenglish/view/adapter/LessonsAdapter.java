package com.example.infinityenglish.view.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.databinding.ItemLessonsBinding;
import com.example.infinityenglish.view.activity.PronunciationActivity;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder> {
    private List<String> lessons;

    public void setLessons(List<String> lessons) {
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public LessonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLessonsBinding binding = ItemLessonsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LessonsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.lesson.setText(lessons.get(position));
        holder.binding.lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = String.valueOf(position+1);
                Bundle bundle = new Bundle();
                bundle.putString("pme1", String.valueOf(position+1));

                Intent intent = new Intent(holder.binding.getRoot().getContext(), PronunciationActivity.class);
                intent.putExtras(bundle);
                holder.binding.getRoot().getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public class LessonsViewHolder extends RecyclerView.ViewHolder {
        private ItemLessonsBinding binding;
        public LessonsViewHolder(ItemLessonsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
