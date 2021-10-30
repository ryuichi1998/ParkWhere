package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.repo.UserRepository;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;

    private UserRepository userEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
}