package com.example.myapplication.ui.profile;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ui.login.LoginFragment;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel mViewModel;

    private View root;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (root == null){
            root = inflater.inflate(R.layout.profile_fragment, container, false);
        }
        intializeComponents(root);

        Button account_setting_btn = root.findViewById(R.id.account_btn);
        Button change_psswd_btn = root.findViewById(R.id.change_psswd_btn);

        account_setting_btn.setOnClickListener(this);
        change_psswd_btn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    private void intializeComponents(View view) {
        TextView tvUsername = view.findViewById(R.id.username);
        if(LoginFragment.loginUser != null) {
            tvUsername.setText(LoginFragment.loginUser);
        }
        else {
            tvUsername.setText("User");
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_btn:
                replaceFragement(new AccountSettingFragment());
                break;
            case R.id.change_psswd_btn:
                replaceFragement(new ChangePsswdFragment());
                break;
        }
    }

    private void replaceFragement(Fragment frag) {
        FragmentManager frg_mgr = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = frg_mgr.beginTransaction();
        transaction.replace(R.id.profile_start_fragment, frag);
        transaction.commit();
    }
}