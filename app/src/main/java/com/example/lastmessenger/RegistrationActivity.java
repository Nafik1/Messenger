package com.example.lastmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText name;
    private FirebaseAuth mAuth;
    private EditText lastname;
    private EditText age;
    private Button signUp;
    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        ObserveRegistration();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = getTrimmedValue(name);
                String email1 = getTrimmedValue(email);
                String password1 = getTrimmedValue(password);
                String lastname1 = getTrimmedValue(lastname);
                String age1 = getTrimmedValue(age);
                registrationViewModel.register(email1,password1,name1,lastname1,age1);
            }
        });
    }
    public void ObserveRegistration(){
        registrationViewModel.getError2().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            s,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
        registrationViewModel.getUser2().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newintent3(RegistrationActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public static Intent newintent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }
    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }
    private void initViews() {
        email = findViewById(R.id.vvodemail);
        password = findViewById(R.id.vvodpassword);
        name = findViewById(R.id.vvodname);
        signUp = findViewById(R.id.buttonRegistration);
        age = findViewById(R.id.vvodage);
        lastname = findViewById(R.id.vvodlastname);
    }
}