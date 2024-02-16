package com.example.lastmessenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChatViewModelFactory implements ViewModelProvider.Factory {

    private String currentuserid;
    private String otheruserid;

    public ChatViewModelFactory(String currentuserid, String otheruserid) {
        this.currentuserid = currentuserid;
        this.otheruserid = otheruserid;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatViewModel(currentuserid, otheruserid);
    }
}
