package com.example.foodfusion.features.Authentication.signup.view;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfusion.features.Authentication.signup.presenter.SignUpPresenter;
import com.example.foodfusion.features.Authentication.signup.presenter.SignUpPresenterInterface;
import com.example.foodfusion.MainActivity;
import com.example.foodfusion.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


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
    GoogleSignInClient googleSignInClient;
    ImageView googleSignUpImage;
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
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backBtn = view.findViewById(R.id.haveAccountButton);
        signupBtn = view.findViewById(R.id.btnSignUp);
        googleSignUpImage = view.findViewById(R.id.googleSignUpImage);

        txt_signup = view.findViewById(R.id.txt_signup);
        txt_signup_word = view.findViewById(R.id.txt_signup_word);

        textInputEditTextEmailSignUp = view.findViewById(R.id.textInputEditTextEmailSignUp);
        textInputEditTextConfirmPasswordSignUp = view.findViewById(R.id.textInputEditTextConfirmPasswordSignUp);
        textInputEditTextPasswordSignUp = view.findViewById(R.id.textInputEditTextPasswordSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });
        signupBtn.setOnClickListener(v -> signUp());
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.client_id)).requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);

        googleSignUpImage.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
            Toast.makeText(this.getContext(), "This Is SignupNBy Google", Toast.LENGTH_SHORT).show();
        });

    }


    private void signUp() {
        String userName = String.valueOf(textInputEditTextEmailSignUp.getText());
        String password = String.valueOf(textInputEditTextPasswordSignUp.getText());
        if (checkValidation(userName, password) && isDataFilled()) {
            signUpPresenter.signUp(userName, password);
        }
    }

    private boolean checkValidation(String userName, String password) {
        Toast.makeText(this.getContext(), "email or password is not valid", Toast.LENGTH_SHORT).show();
        return isValidEmail(userName) && isPassLengthGT7(password) && checkPasswordEquality();
    }

    private boolean isDataFilled() {
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
            Toast.makeText(this.getContext(), "please fill data", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkPasswordEquality() {

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
            if (result.getResultCode() == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    if (signInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                        signUpPresenter.signUpWithGoogle(authCredential);
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });


    @Override
    public void onSignUpSuccess(FirebaseUser user) {
        Toast.makeText(this.getContext(), "Sign Up Successfully", Toast.LENGTH_SHORT).show();
        goToMainActivity();
    }

    @Override
    public void OnSignUpFailure(String message) {
        Toast.makeText(this.getContext(), "Cant SignUp now", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessSignUpGoogle() {
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void OnFailureSignUpGoogle(String message) {
        Toast.makeText(requireContext(), "SignUp with google Failed!", Toast.LENGTH_SHORT).show();
    }

    public boolean isPassLengthGT7(String pass) {
        return pass.length() >= 7;
    }

    private boolean isValidEmail(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

}