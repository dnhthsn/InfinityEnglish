package com.example.infinityenglish.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infinityenglish.R;
import com.example.infinityenglish.databinding.ItemBotChatBinding;
import com.example.infinityenglish.databinding.ItemUserChatBinding;
import com.example.infinityenglish.models.ChatsModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {
    private List<ChatsModel> chatsmodalArrayList;

    public void setChatsmodalArrayList(List<ChatsModel> chatsmodalArrayList) {
        this.chatsmodalArrayList = chatsmodalArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserChatBinding itemUserChatBinding = ItemUserChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ItemBotChatBinding itemBotChatBinding = ItemBotChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        switch (viewType) {
            case 0:
                return new userViewHolder(itemUserChatBinding);
            case 1:
                return new botViewHolder(itemBotChatBinding);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatsModel chatsModel = chatsmodalArrayList.get(position);
        switch (chatsmodalArrayList.get(position).getSender()) {
            case "user":
                ((userViewHolder) holder).binding.userMsg.setText(chatsModel.getMessage());
                break;
            case "bot":
                ((botViewHolder) holder).binding.botReply.setText(chatsModel.getMessage());
                break;

        }

    }

    @Override
    public int getItemCount() {
        return chatsmodalArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatsmodalArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    public static class userViewHolder extends RecyclerView.ViewHolder {
        private ItemUserChatBinding binding;

        public userViewHolder(ItemUserChatBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public static class botViewHolder extends RecyclerView.ViewHolder {
        private ItemBotChatBinding binding;

        public botViewHolder(ItemBotChatBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
