package com.example.foodfusion.Authentication.signup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodfusion.Authentication.AuthFragment;
import com.example.foodfusion.Authentication.AuthenticationActivity;
import com.example.foodfusion.R;

public class SignUpFragment extends Fragment {
    Button backBtn;
    EditText edt_name_txt;
    EditText edt_email_txt;
    EditText edt_password_txt;
    EditText edt_confirm_password_txt;
    Button signupBtn;

    TextView txt_signup;
    TextView txt_signup_word;

    public SignUpFragment() {
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
        return  inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = view.findViewById(R.id.haveAccountButton);
        signupBtn = view.findViewById(R.id.btnSignUp);

        txt_signup = view.findViewById(R.id.txt_signup);
        txt_signup_word = view.findViewById(R.id.txt_signup_word);

        edt_name_txt = view.findViewById(R.id.edt_txt_name);
        edt_email_txt = view.findViewById(R.id.edt_txt_email);
        edt_confirm_password_txt = view.findViewById(R.id.edt_txt_confirm_password);
        edt_password_txt = view.findViewById(R.id.edt_txt_password);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((AuthenticationActivity)requireActivity()).navigateToFragment(new AuthFragment());
                Log.i("TAG", "onClick: back to auth page  ");
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: signup successfully ");
            }
        });

    }
}