package com.example.lastmessenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetViewModel extends ViewModel {
    private MutableLiveData<String> error2 = new MutableLiveData<>();
    private MutableLiveData<Boolean> user2 = new MutableLiveData<>();
    private FirebaseAuth fb = FirebaseAuth.getInstance();

    public MutableLiveData<String> getError2() {
        return error2;
    }

    public MutableLiveData<Boolean> getUser2() {
        return user2;
    }
    public void resetpass(String email) {
        fb.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user2.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        error2.setValue(e.getMessage());
                    }
                });
    }
}
