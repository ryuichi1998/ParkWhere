package com.example.myapplication.ui.register;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;
import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.myapplication.R;
import com.example.myapplication.db.user.UserRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private UserRepository userRepository;

    // initialize variable
    EditText etName, etEmail, etPassword, etConfirmPassword;
    Button btRegister;
    TextInputLayout vehTextInputLayout;
    AutoCompleteTextView actvVehicle;
    String[] arrayList_vehicle;
    ArrayAdapter<String> arrayAdapter_vehicle;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);

        vehTextInputLayout = v.findViewById(R.id.vehTextInputLayout);
        actvVehicle = v.findViewById(R.id.actvVehicle);
        arrayList_vehicle = getResources().getStringArray(R.array.vehicleType);
        arrayAdapter_vehicle = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, arrayList_vehicle);
        actvVehicle.setAdapter(arrayAdapter_vehicle);
        AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
        mAwesomeValidation.addValidation(getActivity(), R.id.nameTextInputLayout, RegexTemplate.NOT_EMPTY, R.string.invalid_name);

        ((AutoCompleteTextView)vehTextInputLayout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String indexid = String.valueOf(position);
                Toast.makeText(getActivity(), indexid, Toast.LENGTH_SHORT).show();
            }
        });


        btRegister = v.findViewById(R.id.registerBtn);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                registerUser();
                if(mAwesomeValidation.validate()) {
                    Toast.makeText(getActivity(), "User succesfully registered", Toast.LENGTH_SHORT).show();
                }
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

    private void validation() {
        AwesomeValidation mAwesomeValidation = new AwesomeValidation(TEXT_INPUT_LAYOUT);
        mAwesomeValidation.addValidation(getActivity(), R.id.etName, "[a-zA-Z\\s]+", R.string.invalid_name);

    }

}