package com.example.foodfusion.Authentication.login.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodfusion.R;
public class LoginFragment extends Fragment implements LoginViewInterface {
    TextView txt_login;
    TextView txt_login_description;
    EditText edt_txt_login;
    EditText edt_txt_password_login;
    Button btn_login;
    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_login = view.findViewById(R.id.txt_login);
        txt_login_description = view.findViewById(R.id.txt_login_description);



        edt_txt_login = view.findViewById(R.id.edt_txt_login);
        edt_txt_password_login = view.findViewById(R.id.edt_txt_password_login);
        btn_login = view.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: Login From login page");
            }
        });
    }
}