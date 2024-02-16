package com.example.lastmessenger;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {
    private FirebaseAuth fAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReferens;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<List<User>> users = new MutableLiveData<>();

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public UsersViewModel() {
        fAuth = FirebaseAuth.getInstance();
        fAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null) {
                    user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReferens = firebaseDatabase.getReference("Users");
        usersReferens.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentfirebaseUser = fAuth.getCurrentUser();
                if (currentfirebaseUser == null) {
                    return;
                }
                List<User> usersFromDb = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null) {
                        return;
                    }
                    if (!user.getId().equals(currentfirebaseUser.getUid())) {
                        usersFromDb.add(user);
                    }
                }
                users.setValue(usersFromDb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void setUserOnline(boolean isOnline) {
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        if(firebaseUser == null) {
            return;
        }
        usersReferens.child(firebaseUser.getUid()).child("online").setValue(isOnline);
    }

    public void logout(){
        setUserOnline(false);
        fAuth.signOut();
    }
}
