package com.example.lastmessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String EXTA_CURRENT_USER_ID = "current_id";
    private static final String EXTA_OTHER_USER_ID = "other_id";
    private TextView textViewTitle;
    private View onlineStatus;
    private RecyclerView recyclerViewMessage;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;
    private MessageAdapter messageAdapter;

    private String currentUserId;
    private String otherUserId;
    private ChatViewModel chatViewModel;
    private ChatViewModelFactory chatViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        otherUserId = getIntent().getStringExtra(EXTA_OTHER_USER_ID);
        currentUserId = getIntent().getStringExtra(EXTA_CURRENT_USER_ID);
        chatViewModelFactory = new ChatViewModelFactory(currentUserId,otherUserId);
        chatViewModel = new ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel.class);
        messageAdapter = new MessageAdapter(currentUserId);
        recyclerViewMessage.setAdapter(messageAdapter);
        observeViewMode();
        imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(editTextMessage.getText().toString().trim(), currentUserId, otherUserId);
                chatViewModel.sendMessage(message);
            }
        });

    }
    private void observeViewMode() {
        chatViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messageAdapter.setMessages(messages);
            }
        });
        chatViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null) {
                    Toast.makeText(
                            ChatActivity.this,
                            s,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
        chatViewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    editTextMessage.setText("");
                }
            }
        });
        chatViewModel.getOther_user().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userInfo = String.format("%s %s",user.getName(), user.getLastname());
                textViewTitle.setText(userInfo);
                int backgroundresid;
                if (user.isStatus()) {
                    backgroundresid = R.drawable.circle_green;
                } else {
                    backgroundresid = R.drawable.circle_red;
                }
                Drawable drawable = ContextCompat.getDrawable(ChatActivity.this, backgroundresid);
                onlineStatus.setBackground(drawable);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        chatViewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.setUserOnline(false);
    }
    private void initViews(){
        textViewTitle = findViewById(R.id.textViewTitle);
        onlineStatus = findViewById(R.id.onlineStatus);
        recyclerViewMessage = findViewById(R.id.RecyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }
    public static Intent newintent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTA_OTHER_USER_ID, otherUserId);
        return intent;
    }
}
