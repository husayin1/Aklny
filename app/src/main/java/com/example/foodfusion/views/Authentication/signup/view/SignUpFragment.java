package com.example.foodfusion.views.Authentication.signup.view;

import android.app.Activity;
import android.content.Context;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodfusion.views.Authentication.AuthenticationActivity;
import com.example.foodfusion.views.Authentication.signup.presenter.SignUpPresenter;
import com.example.foodfusion.views.Authentication.signup.presenter.SignUpPresenterInterface;
import com.example.foodfusion.MainActivity;
import com.example.foodfusion.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.makeramen.roundedimageview.RoundedImageView;


public class SignUpFragment extends Fragment implements SignUpViewInterface {
    Button backBtn;
    TextInputEditText textInputEditTextEmailSignUp;
    TextInputEditText textInputEditTextPasswordSignUp;
    TextInputEditText textInputEditTextConfirmPasswordSignUp;
    Button signupBtn;
    TextView txt_signup;
    TextView txt_signup_word;
    SignUpPresenterInterface signUpPresenter;
    FirebaseAuth firebaseAuth;
    Context context;
    GoogleSignInClient signInAccount;
    RoundedImageView googleSignIN;
    public static final String TAG = "SignUpFragment";

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
        signUpPresenter = SignUpPresenter.getInstance(this);
        return  inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = view.findViewById(R.id.haveAccountButton);
        signupBtn = view.findViewById(R.id.btnSignUp);
//        googleSignIN = view.findViewById(R.id.googleSignIN);

        txt_signup = view.findViewById(R.id.txt_signup);
        txt_signup_word = view.findViewById(R.id.txt_signup_word);

        textInputEditTextEmailSignUp = view.findViewById(R.id.textInputEditTextEmailSignUp);
        textInputEditTextConfirmPasswordSignUp = view.findViewById(R.id.textInputEditTextConfirmPasswordSignUp);
        textInputEditTextPasswordSignUp = view.findViewById(R.id.textInputEditTextPasswordSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigateUp();
            }
        });
        signupBtn.setOnClickListener(v->signUp());
//        googleSignIN.setOnClickListener(v->{
//            Intent intent = signInAccount.getSignInIntent();
//            activityResultLauncher.launch(intent);
//        });

    }


    private void signUp() {
        String userName = textInputEditTextEmailSignUp.getText().toString();
        String password = textInputEditTextPasswordSignUp.getText().toString();
        if (isAllDataFilled() && checkValidation(userName, password)) {
            signUpPresenter.signUp(userName, password);
        }
    }

    private boolean checkValidation(String userName, String pass) {
        return isValidEmail(userName) && isPassLengthGT7(pass)&&checkEquality()  ;
    }

    private boolean isAllDataFilled() {
        Log.d(TAG, "isAllDataFilled: ");
        if (
                textInputEditTextEmailSignUp.getText() != null &&
                !textInputEditTextEmailSignUp.getText().toString().isEmpty() &&
                        textInputEditTextConfirmPasswordSignUp.getText() != null &&
                !textInputEditTextConfirmPasswordSignUp.getText().toString().isEmpty() &&
                        textInputEditTextPasswordSignUp.getText() != null &&
                !textInputEditTextPasswordSignUp.getText().toString().isEmpty()
        )
            return true;
        else {
            Toast.makeText(context, "please fill data", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkEquality() {
        return textInputEditTextPasswordSignUp.getText().toString().equals(textInputEditTextConfirmPasswordSignUp.getText().toString());
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
                        signUpPresenter.signUpWithGoogle(authCredential);
                    }
                }catch (ApiException e){
                    e.printStackTrace();
                }
            }
        }
    });


    public boolean isPassLengthGT7(String pass) {
        return pass.length() >= 7;
    }


    private boolean isValidEmail(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    @Override
    public void onSignUpSuccess(FirebaseUser user) {
        Toast.makeText(this.getContext(), "SignUpSuccessfully", Toast.LENGTH_SHORT).show();
        goToMainActivity();
    }

    @Override
    public void OnSignUpFailure(String message) {
        Toast.makeText(this.getContext(), "Cant SignUp", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessSignUpGoogle() {
        Toast.makeText(this.getContext(), "Sign up with google Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.getContext().startActivity(intent);
    }

    @Override
    public void OnFailureSignUpGoogle(String message) {
        Toast.makeText(requireContext(),"Failed to signup with google", Toast.LENGTH_SHORT).show();
    }
}