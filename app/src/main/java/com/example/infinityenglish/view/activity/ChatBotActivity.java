package com.example.infinityenglish.view.activity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.remote.RequestChatBotManager;
import com.example.infinityenglish.databinding.ActivityChatBotBinding;
import com.example.infinityenglish.models.ChatsModel;
import com.example.infinityenglish.models.MessageModel;
import com.example.infinityenglish.models.Users;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.adapter.ChatAdapter;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.ChatBotViewModel;
import com.example.infinityenglish.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatBotActivity extends BaseActivity implements RequestChatBotManager.OnFetchDataListener {
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    private ActivityChatBotBinding binding;
    private ChatBotViewModel chatBotViewModel;
    private UserViewModel userViewModel;

    private List<ChatsModel> chatsModels;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;

    public static void starter(Context context) {
        Intent intent = new Intent(context, ChatBotActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        binding = DataBindingUtil.setContentView(ChatBotActivity.this, R.layout.activity_chat_bot);

        chatBotViewModel = new ViewModelProvider(this).get(ChatBotViewModel.class);
        chatBotViewModel.init(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(this);

        chatAdapter = new ChatAdapter();
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        chatsModels = new ArrayList<>();
        binding.chatRecycler.setLayoutManager(layoutManager);
        binding.chatRecycler.setAdapter(chatAdapter);

        binding.clickBack.setOnClickListener(view -> finish());

        binding.sendBtn.setOnClickListener(view -> {
            if (binding.edtMsg.getText().toString().isEmpty()) {
                Utility.Notice.snack(view, "Please enter your message");
                return;
            }
            chatsModels.add(new ChatsModel(binding.edtMsg.getText().toString(), USER_KEY));
            chatBotViewModel.getChatMessage(ChatBotActivity.this, binding.edtMsg.getText().toString());
            binding.edtMsg.setText("");
            binding.chatRecycler.scrollToPosition(chatsModels.size() - 1);
            chatAdapter.notifyDataSetChanged();
        });

        binding.chatRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int currentFirstVisible = layoutManager.findLastVisibleItemPosition();
                if (currentFirstVisible == chatsModels.size() - 1) {
                    binding.scrollDown.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy < 0) {
                    binding.scrollDown.setVisibility(View.VISIBLE);
                    binding.scrollDown.setOnClickListener(view -> {
                        binding.scrollDown.setVisibility(View.INVISIBLE);
                        binding.chatRecycler.scrollToPosition(chatsModels.size() - 1);
                    });
                }
            }
        });

        Users users = userViewModel.getCurrentUser();

        chatAdapter.setChatsmodalArrayList(chatsModels);
        if (users != null){
            chatBotViewModel.addChatBotMessage(chatsModels, users, binding.getRoot());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void showData(MessageModel messageModel) {
        ChatsModel chatsModel = new ChatsModel(messageModel.getCnt(), BOT_KEY);
        chatsModels.add(chatsModel);
        binding.chatRecycler.scrollToPosition(chatsModels.size() - 1);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFetchData(MessageModel messageModel, String message) {
        if (messageModel == null) {
            return;
        }

        showData(messageModel);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onError(String message) {
        chatsModels.add(new ChatsModel("no response", BOT_KEY));
        chatAdapter.notifyDataSetChanged();
    }
}