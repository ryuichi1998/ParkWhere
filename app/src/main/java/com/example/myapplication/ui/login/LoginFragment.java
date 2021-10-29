package com.example.myapplication.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.view.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.user.AsyncResponse;
import com.example.myapplication.db.user.User;
import com.example.myapplication.db.user.UserRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

public class LoginFragment extends Fragment {
    private Button loginBtn;

    private LoginViewModel mViewModel;
    private TextView tv;
    private UserRepository userEngine;
    EditText etPass, etEmail;
    TextInputLayout loginEmail_TIL, loginPass_TIL;

    // Username to be displayed in profile
    public static String loginUser;
    public static int loginUserId;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        try {
            userEngine = new UserRepository(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        NavController navController = Navigation.findNavController(view);
        tv = (TextView) getView().findViewById(R.id.textView7);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });


        loginBtn = view.findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //loginTest();
                openMainActivity();

            }
        });


    }

    public void loginTest(){

        boolean isValid = true;

        etPass = (EditText) getView().findViewById(R.id.etLoginPass);
        etEmail = (EditText) getView().findViewById(R.id.etLoginEmail);
        loginEmail_TIL = (TextInputLayout) getView().findViewById(R.id.loginEmailTextInputLayout);
        loginPass_TIL = (TextInputLayout) getView().findViewById(R.id.loginPassTextInputLayout);
        System.out.println("LOGIN START");
        String email = etEmail.getText().toString();
        String psswd = etPass.getText().toString();

        if (email.isEmpty()) {
            loginEmail_TIL.setError("Email is required");
            isValid = false;
        } else {
            loginEmail_TIL.setErrorEnabled(false);
        }

        if (psswd.isEmpty()) {
            loginPass_TIL.setError("Password is required");
            isValid = false;
        } else {
            loginPass_TIL.setErrorEnabled(false);
        }

        if (isValid) {

            AsyncResponse query = new AsyncResponse() {
                @Override
                public void queryFinish(User user){
                    System.out.println("LOGIN RESULT: FINISH");
                    // no corresponding email and passwd in the database
                    if (user == null){
                        loginEmail_TIL.setError("Email or Password is incorrect");
                        loginPass_TIL.setError("Email or Password is incorrect");
                        return;
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    System.out.printf("USER INFO: %s, %s\n", user.getEmail(), user.getPass());
                    loginUser = user.getName();
                    loginUserId = user.getUserId();
                    openMainActivity();
                }
            };

            userEngine.login(query, email, psswd);
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}