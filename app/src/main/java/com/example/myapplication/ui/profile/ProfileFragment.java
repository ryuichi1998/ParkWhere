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

public class ProfileFragment extends Fragment {

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


        replaceFragement(new StartFragment());

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

    private void replaceFragement(Fragment frag) {
        FragmentManager frg_mgr = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = frg_mgr.beginTransaction();
        transaction.replace(R.id.profile_start_fragment, frag);
        transaction.commit();
    }
}