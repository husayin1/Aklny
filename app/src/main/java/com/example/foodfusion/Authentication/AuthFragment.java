package com.example.foodfusion.Authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodfusion.Authentication.login.view.LoginFragment;
import com.example.foodfusion.Authentication.signup.view.SignUpFragment;
import com.example.foodfusion.R;

public class AuthFragment extends Fragment {

    Button btn_guest;
    Button btn_auth_signup;
    Button btn_google;
    Button btn_auth_login;
    TextView txt_app_name;
    TextView txt_description_auth;
    public AuthFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_guest = view.findViewById(R.id.btn_guest);
        btn_auth_signup = view.findViewById(R.id.btn_auth_signup);
        btn_auth_login = view.findViewById(R.id.btn_auth_login);
        btn_google = view.findViewById(R.id.btn_google);

        txt_app_name = view.findViewById(R.id.txt_app_name);
        txt_description_auth = view.findViewById(R.id.txt_description_auth);

        btn_auth_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((AuthenticationActivity)requireActivity()).navigateToFragment(new LoginFragment());
                Log.i("TAG", "onClick: go to login page ");
            }
        });
        btn_auth_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthenticationActivity)requireActivity()).navigateToFragment(new SignUpFragment());
                Log.i("TAG", "onClick: go to signup page ");
            }
        });
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: go to signup with google page ");
            }
        });
        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: go as guest page ");
            }
        });

    }
}