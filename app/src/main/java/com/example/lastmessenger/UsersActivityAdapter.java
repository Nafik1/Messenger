package com.example.lastmessenger;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersActivityAdapter extends RecyclerView.Adapter<UsersActivityAdapter.MovieViewHolder>{
    private List<User> users = new ArrayList<>();
    private OnUserClickListener onUserClickListener;

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.user_item, parent, false
        );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        User user = users.get(position);
        String userinfo = String.format("%s %s, %s",user.getName(),user.getLastname(), user.getAge());
        holder.name.setText(userinfo);
        int backgroundresid;
        if (user.isStatus()) {
            backgroundresid = R.drawable.circle_green;
        } else {
            backgroundresid = R.drawable.circle_red;
        }
        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundresid);
        holder.status.setBackground(drawable);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onUserClickListener != null) {
                    onUserClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    interface OnUserClickListener {
        void onUserClick(User user);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        private View status;
        private TextView name;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.statusViewModel);
            name = itemView.findViewById(R.id.textViewUserInfo);
        }
    }
}
