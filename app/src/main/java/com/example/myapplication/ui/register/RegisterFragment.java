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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.repo.UserRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private UserRepository userRepository;

    // initialize variable
    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btRegister;
    TextInputLayout vehTextInputLayout, nameTextInputLayout, passTextInputLayout, confirmTextInputLayout, emailTextInputLayout;
    AutoCompleteTextView actvVehicle;
    String[] arrayList_vehicle;
    ArrayAdapter<String> arrayAdapter_vehicle;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +          //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +     //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);
        intializeComponents(v);

        try {
            userRepository= new UserRepository(getActivity().getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerUser()) {
                    Toast.makeText(getActivity(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
        //mViewModel.insert(registerUser());
    }

    private void intializeComponents(View view) {
        // Edit Text
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPass);
        etConfirmPassword = view.findViewById(R.id.etConfirm);

        btRegister = view.findViewById(R.id.registerBtn);

        // TextInputLayout
        vehTextInputLayout = view.findViewById(R.id.vehTextInputLayout);
        nameTextInputLayout = view.findViewById(R.id.nameTextInputLayout);
        passTextInputLayout = view.findViewById(R.id.passTextInputLayout);
        confirmTextInputLayout = view.findViewById(R.id.confirmTextInputLayout);
        emailTextInputLayout = view.findViewById(R.id.emailTextInputLayout);

        actvVehicle = view.findViewById(R.id.actvVehicle);
        arrayList_vehicle = getResources().getStringArray(R.array.vehicleType);
        arrayAdapter_vehicle = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, arrayList_vehicle);
        actvVehicle.setAdapter(arrayAdapter_vehicle);

    }

    private boolean registerUser() {
        boolean isValid = true;
        int vehType = 0;
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        String confrimPassword = etConfirmPassword.getText().toString();
        String veh = ((AutoCompleteTextView)vehTextInputLayout.getEditText()).getText().toString();

        if (name.isEmpty()) {
            nameTextInputLayout.setError("Name is required");
            isValid = false;
        } else {
            nameTextInputLayout.setErrorEnabled(false);
        }

        if (email.isEmpty()) {
            emailTextInputLayout.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTextInputLayout.setError("Invalid Email");
            isValid = false;

        }
        else {
            nameTextInputLayout.setErrorEnabled(false);
        }

        if (veh.isEmpty()) {
            vehTextInputLayout.setError("Please select a vehicle type");
            isValid = false;
        } else {
            vehTextInputLayout.setErrorEnabled(false);
            switch (veh)
            {
                case "Car":
                    vehType = 1;
                    break;
                case "Heavy Vehicle":
                    vehType = 2;
                    break;
                case "Motorcycle":
                    vehType = 3;
                    break;
            }
        }

        if (pass.isEmpty()) {
            passTextInputLayout.setError("Password is required");
            isValid = false;
        } else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            passTextInputLayout.setError("At least 1 digit, 1 uppercase, 1 lowercase, 1 special character" +
                    "and 6 letter long");
        }

        else {
            passTextInputLayout.setErrorEnabled(false);
        }

        if (confrimPassword.isEmpty()) {
            confirmTextInputLayout.setError("Please confirm password");
            isValid = false;
        } else if (!confrimPassword.equals(pass)) {
            confirmTextInputLayout.setError("Passwords do not match");
            isValid = false;
        }
        else {
            confirmTextInputLayout.setErrorEnabled(false);
        }
        if (isValid) {
            User user = new User(name, email, pass, vehType);
            userRepository.insert(user);
        }
        return isValid;
    }


}