package com.example.lastmessenger;

import android.util.MutableBoolean;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MovieViewHolder>{
    private static final int VIEW_TYPE_MY_MESSAGE = 100;
    private static final int VIEW_TYPE_OTHER_MESSAGE = 200;
    private String currentUserId;

    private List<Message> messages = new ArrayList<>();

    public MessageAdapter(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int LayoutResId;
        if(viewType == VIEW_TYPE_MY_MESSAGE) {
            LayoutResId = R.layout.my_messege_item;
        } else {
            LayoutResId = R.layout.other_messege_item;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(LayoutResId,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSenderId().equals(currentUserId)) {
            return VIEW_TYPE_MY_MESSAGE;
        } else {
            return VIEW_TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.TextViewMessenge.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView TextViewMessenge;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            TextViewMessenge = itemView.findViewById(R.id.TextViewMessenge);
        }
    }
}
