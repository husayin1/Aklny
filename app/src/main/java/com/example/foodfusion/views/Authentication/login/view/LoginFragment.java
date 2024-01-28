package com.example.foodfusion.views.Authentication.login.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfusion.views.Authentication.login.presenter.LoginPresenter;
import com.example.foodfusion.views.Authentication.login.presenter.LoginPresenterInterface;
import com.example.foodfusion.MainActivity;
import com.example.foodfusion.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.makeramen.roundedimageview.RoundedImageView;

public class LoginFragment extends Fragment implements LoginViewInterface {
    TextView txt_login;
    TextView txt_login_description;
    TextView tv_signUp;
    TextInputEditText TextInputEditTextEmailLogin;
    TextInputEditText TextInputEditTextPasswordLogin;

    Button btn_login;
    LoginPresenterInterface loginPresenter;
//    GoogleSignInClient signInAccount;
    
//    RoundedImageView googleSignIN;
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

        loginPresenter = LoginPresenter.getInstance(this);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_login = view.findViewById(R.id.txt_login);
        txt_login_description = view.findViewById(R.id.txt_login_description);
        tv_signUp = view.findViewById(R.id.tv_signUp);
//        googleSignIN = view.findViewById(R.id.googleSignIN);



        TextInputEditTextEmailLogin = view.findViewById(R.id.TextInputEditTextEmailLogin);
        TextInputEditTextPasswordLogin = view.findViewById(R.id.TextInputEditTextPasswordLogin);
        btn_login = view.findViewById(R.id.btn_login);
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        btn_login.setOnClickListener(v->login());
//        googleSignIN.setOnClickListener(v->{
//            Intent intent = signInAccount.getSignInIntent();
//            activityResultLauncher.launch(intent);
//
//        });
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        Toast.makeText(this.getContext(), "SignedInSuccessfully", Toast.LENGTH_SHORT).show();
        goToMainActivity();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this.getContext(), "Cant SignIn", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessSignInGoogle() {
        Toast.makeText(this.getContext(), "Sign In with google Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.getContext().startActivity(intent);
    }

    @Override
    public void OnFailureSignInGoogle(String message) {
        Toast.makeText(requireContext(),"Failed to signin with google", Toast.LENGTH_SHORT).show();
    }

    private void login(){
        String userName = TextInputEditTextEmailLogin.getText().toString();
        String password = TextInputEditTextPasswordLogin.getText().toString();
        if(checkValidation()&&isValidEmail(userName)){
            loginPresenter.login(userName,password);
        }
    }
    private boolean checkValidation() {
        if (
                TextInputEditTextEmailLogin.getText() != null
                        && !TextInputEditTextEmailLogin.getText().toString().isEmpty()
                        && TextInputEditTextPasswordLogin.getText() != null
                        && !TextInputEditTextPasswordLogin.getText().toString().isEmpty()
        )
            return true;
        else {
            Toast.makeText(this.getContext(), "please fill data", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean isValidEmail(String valid){
        String email = valid.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private void goToMainActivity() {
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.getContext().startActivity(intent);
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try{
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    if(signInAccount!=null){
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
                        loginPresenter.signInWithGoogle(authCredential);
                    }
                }catch (ApiException e){
                    e.printStackTrace();
                }
            }
        }
    });
}