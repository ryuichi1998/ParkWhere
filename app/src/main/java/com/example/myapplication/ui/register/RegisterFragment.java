package com.example.myapplication.ui.register;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.myapplication.R;
import com.example.myapplication.db.carpark.DBEngine;
import com.example.myapplication.db.user.User;
import com.example.myapplication.db.user.UserRepository;

import java.io.IOException;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private UserRepository userRepository;

    // initialize variable
    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btRegister;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);

        try {
            userRepository= new UserRepository(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        btRegister = v.findViewById(R.id.registerButton);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRepository.insert(registerUser());
                Toast.makeText(getActivity(), "User succesfully registered", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
        //mViewModel.insert(registerUser());
    }

    private User registerUser() {
        etName = (EditText) getView().findViewById(R.id.etName);
        etEmail = (EditText) getView().findViewById(R.id.etEmail);
        etPassword = (EditText) getView().findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) getView().findViewById(R.id.etConfirmPassword);
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        User user = new User(name, email, pass, 1);
        return user;
    }


//    private boolean validateEmail() {
//        String emailInput = textInputEmail.getEditText().getText().toString().trim();
//
//        if (emailInput.isEmpty()) {
//            textInputEmail.setError("Field can't be empty");
//            return false;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
//            textInputEmail.setError("Please enter a valid email address");
//            return false;
//        } else {
//            textInputEmail.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validateUsername() {
//        String usernameInput = textInputUsername.getEditText().getText().toString().trim();
//
//        if (usernameInput.isEmpty()) {
//            textInputUsername.setError("Field can't be empty");
//            return false;
//        } else if (usernameInput.length() > 15) {
//            textInputUsername.setError("Username too long");
//            return false;
//        } else {
//            textInputUsername.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validatePassword() {
//        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
//
//        if (passwordInput.isEmpty()) {
//            textInputPassword.setError("Field can't be empty");
//            return false;
//        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
//            textInputPassword.setError("Password too weak");
//            return false;
//        } else {
//            textInputPassword.setError(null);
//            return true;
//        }
//    }
//
//    public void confirmInput(View v) {
//        if (!validateEmail() | !validateUsername() | !validatePassword()) {
//            return;
//        }
//
//        String input = "Email: " + textInputEmail.getEditText().getText().toString();
//        input += "\n";
//        input += "Username: " + textInputUsername.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + textInputPassword.getEditText().getText().toString();
//
//        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
//    }
}