package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.db.user.AsyncResponse;
import com.example.myapplication.db.carpark.CarParkDetails;
import com.example.myapplication.db.user.User;
import com.example.myapplication.db.user.UserRepository;

import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;

    private UserRepository userEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            userEngine = new UserRepository(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginTest();
            }
        });
    }

    public void loginTest(){
        System.out.println("LOGIN START");
        String email = "123@gmail.com";
        String psswd = "123Aa!@";

        AsyncResponse query = new AsyncResponse() {
            @Override
            public void queryFinish(User user){
                System.out.println("LOGIN RESULT: FINISH");
                // no corresponding email and passwd in the database
                if (user == null){
                    Toast.makeText(getApplicationContext(), "SORRY, you fucking email or password is fucking worng!", Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.printf("USER INFO: %s, %s\n", user.getEmail(), user.getPass());
                openMainActivity();
           }
        };

        userEngine.login(query, email, psswd);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}