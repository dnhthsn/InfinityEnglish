package com.example.infinityenglish.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infinityenglish.R;
import com.example.infinityenglish.control.remote.RequestChatBotManager;
import com.example.infinityenglish.databinding.ActivityChatBotBinding;
import com.example.infinityenglish.models.ChatsModel;
import com.example.infinityenglish.models.MessageModel;
import com.example.infinityenglish.util.Utility;
import com.example.infinityenglish.view.adapter.ChatAdapter;
import com.example.infinityenglish.view.base.BaseActivity;
import com.example.infinityenglish.viewmodel.ChatBotViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatBotActivity extends BaseActivity implements RequestChatBotManager.OnFetchDataListener {
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    private ActivityChatBotBinding binding;
    private ChatBotViewModel chatBotViewModel;

    private List<ChatsModel> chatsModels;
    private ChatAdapter chatAdapter;

    public static void starter(Context context) {
        Intent intent = new Intent(context, ChatBotActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        binding = DataBindingUtil.setContentView(ChatBotActivity.this, R.layout.activity_chat_bot);

        chatBotViewModel = new ViewModelProvider(this).get(ChatBotViewModel.class);

        chatAdapter = new ChatAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        chatsModels = new ArrayList<>();
        binding.chatRecycler.setLayoutManager(layoutManager);
        binding.chatRecycler.setAdapter(chatAdapter);

        binding.clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edtMsg.getText().toString().isEmpty()) {
                    Utility.Notice.snack(view, "Please enter your message");
                    return;
                }
                chatsModels.add(new ChatsModel(binding.edtMsg.getText().toString(), USER_KEY));
                chatBotViewModel.getChatMessage(ChatBotActivity.this, binding.edtMsg.getText().toString());
                binding.edtMsg.setText("");
                binding.chatRecycler.scrollToPosition(chatsModels.size() - 1);
                chatAdapter.notifyDataSetChanged();
            }
        });

        chatAdapter.setChatsmodalArrayList(chatsModels);
    }

    public void showData(MessageModel messageModel) {
        chatsModels.add(new ChatsModel(messageModel.getCnt(), BOT_KEY));
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

    @Override
    public void onError(String message) {
        chatsModels.add(new ChatsModel("no response", BOT_KEY));
        chatAdapter.notifyDataSetChanged();
    }
}