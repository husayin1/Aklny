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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodfusion.databinding.FragmentSignupBinding;
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
    SignUpPresenterInterface signUpPresenter;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    FragmentSignupBinding binding;
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
        signUpPresenter = new SignUpPresenter(this);
        binding = FragmentSignupBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textInputEditTextEmailSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isValidEmail(editable.toString())){
                    binding.textInputEditTextEmailSignUp.setError("Please Enter valid email");
                }

            }
        });
        binding.textInputEditTextPasswordSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isPassLengthGT7(editable.toString()))
                    binding.textInputEditTextPasswordSignUp.setError("At least 7 numbers");
                else
                    binding.textInputEditTextPasswordSignUp.setError(null);
            }
        });
        binding.textInputEditTextConfirmPasswordSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!checkPasswordEquality())
                    binding.textInputEditTextConfirmPasswordSignUp.setError("Passwords are not equal");
                else
                    binding.textInputEditTextConfirmPasswordSignUp.setError(null);
            }
        });
        binding.haveAccountButton.setOnClickListener(v->
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment));
        binding.btnSignUp.setOnClickListener(v -> signUp());
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.client_id)).requestEmail().build();



        binding.googleSignUpImage.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
        });

        firebaseAuth = FirebaseAuth.getInstance();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);

    }


    private void signUp() {
        String userName = String.valueOf(binding.textInputEditTextEmailSignUp.getText());
        String password = String.valueOf(binding.textInputEditTextPasswordSignUp.getText());
        if (checkValidation(userName, password) && isDataFilled()) {
            signUpPresenter.signUp(userName, password);
        }
    }

    private boolean checkValidation(String userName, String password) {
        return isValidEmail(userName) && isPassLengthGT7(password) && checkPasswordEquality();
    }

    private boolean isDataFilled() {
        if (
                binding.textInputEditTextEmailSignUp.getText() != null &&
                        !binding.textInputEditTextEmailSignUp.getText().toString().isEmpty() &&
                        binding.textInputEditTextConfirmPasswordSignUp.getText() != null &&
                        !binding.textInputEditTextConfirmPasswordSignUp.getText().toString().isEmpty() &&
                        binding.textInputEditTextPasswordSignUp.getText() != null &&
                        !binding.textInputEditTextPasswordSignUp.getText().toString().isEmpty()
        )
            return true;
        else {
            Toast.makeText(requireActivity(), "Please Fill data", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkPasswordEquality() {
        return binding.textInputEditTextPasswordSignUp.getText().toString().equals(binding.textInputEditTextConfirmPasswordSignUp.getText().toString());
    }

    private void goToMainActivity() {
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        requireActivity().startActivity(intent);
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
        Toast.makeText(requireActivity(), "Sign Up Successfully", Toast.LENGTH_SHORT).show();
        goToMainActivity();
    }

    @Override
    public void OnSignUpFailure(String message) {
        Toast.makeText(requireActivity(), "this email exist", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessSignUpGoogle() {
        Toast.makeText(requireActivity(), "Sign Up Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void OnFailureSignUpGoogle(String message) {
        Toast.makeText(requireActivity(), "SignUp with google Failed!", Toast.LENGTH_SHORT).show();
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