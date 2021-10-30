package com.example.myapplication.ui.profile;

import android.app.DownloadManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.repo.UserRepository;
import com.example.myapplication.ui.login.LoginFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View root;

    private TextInputLayout account_name_text;
    private TextInputLayout account_email_text;
    private TextInputLayout account_vehicle_text;

    private User user;

    public static UserRepository userRepository;

    public AccountSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountSettingFragment newInstance(String param1, String param2) {
        AccountSettingFragment fragment = new AccountSettingFragment();
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
        root =  inflater.inflate(R.layout.fragment_account_setting, container, false);
        account_name_text = (TextInputLayout)root.findViewById(R.id.account_name);
        account_email_text = (TextInputLayout)root.findViewById(R.id.account_email);
        account_vehicle_text = (TextInputLayout)root.findViewById(R.id.account_vehicle);

        user = LoginFragment.current_user;

        if (user != null){
            account_name_text.setHint(user.getName());
            account_email_text.setHint(user.getEmail());
            switch (user.getVehType()){
                case 1:
                    account_vehicle_text.setHint("Car");
                    break;
                case 2:
                    account_vehicle_text.setHint("Light Vehicle");
                    break;
                case 3:
                    account_vehicle_text.setHint("Motorcycle");
                    break;
            }
        }

        return root;
    }
}