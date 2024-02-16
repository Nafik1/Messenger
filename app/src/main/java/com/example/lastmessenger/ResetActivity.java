package com.example.lastmessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.time.chrono.MinguoChronology;

public class ResetActivity extends AppCompatActivity {
    private EditText youremail;
    private Button reset;
    private static final String EXTRA_TEXTz = "extrach";
    private ResetViewModel resetViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        initViews();
        String email = getIntent().getStringExtra(EXTRA_TEXTz);
        youremail.setText(email);
        resetViewModel = new ViewModelProvider(this).get(ResetViewModel.class);
        observeRes();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = youremail.getText().toString().trim();
                resetViewModel.resetpass(email);
            }
        });

    }
    private void observeRes(){
        resetViewModel.getError2().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(
                            ResetActivity.this,
                            s,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
        resetViewModel.getUser2().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Toast.makeText(
                            ResetActivity.this,
                            R.string.nice_ok,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }
    private void initViews() {
        youremail = findViewById(R.id.youremailplease);
        reset = findViewById(R.id.buttonForgot);
    }
    public static Intent newintent2(Context context, String email) {
        Intent intent2 = new Intent(context,ResetActivity.class);
        intent2.putExtra(EXTRA_TEXTz,email);
        return intent2;
    }
}