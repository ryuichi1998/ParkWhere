package com.example.myapplication.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.repo.UserRepository;
import com.example.myapplication.ui.login.LoginFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePsswdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePsswdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;

    private User user;

    private UserRepository repository;

    private LinearLayout layout_check_psswd;
    private LinearLayout layout_new_psswd;


    private TextInputLayout user_psswd_input_layout;
    private TextInputLayout new_psswd_input_layout;
    private TextInputLayout confirm_psswd_input_layout;

    private TextInputEditText user_psswd_input;
    private TextInputEditText new_psswd_input;
    private TextInputEditText confirm_psswd_input;

    private Button check_user_psswd_btn;
    private Button update_psswd_btn;

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

    public ChangePsswdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePsswdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePsswdFragment newInstance(String param1, String param2) {
        ChangePsswdFragment fragment = new ChangePsswdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_change_psswd, container, false);

        user = LoginFragment.current_user;

        repository = LoginFragment.userEngine;

        layout_check_psswd = root.findViewById(R.id.layout_check_psswd);
        layout_new_psswd = root.findViewById(R.id.layout_new_psswd);

        user_psswd_input_layout = root.findViewById(R.id.user_psswd_input_layout);
        new_psswd_input_layout = root.findViewById(R.id.new_psswd_input_layout);
        confirm_psswd_input_layout = root.findViewById(R.id.confirm_pswwd_input_layout);

        user_psswd_input = root.findViewById(R.id.user_psswd_input);
        new_psswd_input = root.findViewById(R.id.new_psswd_input);
        confirm_psswd_input = root.findViewById(R.id.confirm_pswwd_input);

        check_user_psswd_btn = root.findViewById(R.id.check_user_psswd_btn);
        check_user_psswd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserPassword();
            }
        });

        update_psswd_btn = root.findViewById(R.id.update_psswd_btn);
        update_psswd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

        return root;
    }

    public void checkUserPassword(){
        String pass = user_psswd_input.getText().toString();

        if (pass.isEmpty()) {
            user_psswd_input_layout.setError("Password is required");
            return;
        } else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            user_psswd_input_layout.setError("wrong password, please re-enter");
            user_psswd_input.setText("");
            return;
        }
        else {
            user_psswd_input_layout.setErrorEnabled(false);
        }

        if (user != null && pass.equals(user.getPass())){
            layout_new_psswd.setVisibility(View.VISIBLE);
            layout_check_psswd.setVisibility(View.GONE);
        }
    }

    public void updatePassword(){
        String pass = new_psswd_input.getText().toString();
        String confirm_pass = confirm_psswd_input.getText().toString();


        if (pass.isEmpty()) {
            new_psswd_input_layout.setError("Password is required");
        } else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            new_psswd_input_layout.setError("At least 1 digit, 1 uppercase, 1 lowercase, 1 special character" +
                    "and 6 letter long");
        }
        else {
            new_psswd_input_layout.setErrorEnabled(false);
        }

        if (confirm_pass.isEmpty()) {
            confirm_psswd_input_layout.setError("Please confirm password");
            return;
        } else if (!confirm_pass.equals(pass)) {
            confirm_psswd_input_layout.setError("Passwords do not match");
            return;
        }
        else {
            confirm_psswd_input_layout.setErrorEnabled(false);
        }

        user.setPass(pass);

        if (repository != null){
            repository.update(user);
        }

        Toast.makeText(getActivity(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();

        replaceFragement(new StartFragment());
    }

    private void replaceFragement(Fragment frag) {
        FragmentManager frg_mgr = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = frg_mgr.beginTransaction();
        transaction.replace(R.id.change_password_fragment, frag);
        transaction.commit();
    }
}