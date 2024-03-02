package com.example.foodfusion.features.Authentication.login.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.foodfusion.features.Authentication.login.presenter.LoginPresenter;
import com.example.foodfusion.features.Authentication.login.presenter.LoginPresenterInterface;
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

public class LoginFragment extends Fragment implements LoginViewInterface {
    ImageView txt_login;
    TextView txt_login_description;
    TextView tv_signUp, skip_textView_login;
    TextInputEditText TextInputEditTextEmailLogin;
    TextInputEditText TextInputEditTextPasswordLogin;
    Button btn_login;
    LoginPresenterInterface loginPresenter;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    ImageView googleSignInImage;

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
        googleSignInImage = view.findViewById(R.id.googleSignInImage);
        TextInputEditTextEmailLogin = view.findViewById(R.id.TextInputEditTextEmailLogin);
        TextInputEditTextPasswordLogin = view.findViewById(R.id.TextInputEditTextPasswordLogin);
        btn_login = view.findViewById(R.id.btn_login);
        skip_textView_login = view.findViewById(R.id.skip_textView_login);
        TextInputEditTextEmailLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isValidEmail(editable.toString())) {
                    TextInputEditTextEmailLogin.setError("Please Enter valid email");
                } else {
                    TextInputEditTextEmailLogin.setError(null);
                }

            }
        });
        TextInputEditTextPasswordLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isPassLengthGT7(editable.toString())) {
                    TextInputEditTextPasswordLogin.setError("At least 7 numbers");
                } else {
                    TextInputEditTextPasswordLogin.setError(null);
                }
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.client_id)).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);

        googleSignInImage.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
//            Toast.makeText(this.getContext(), "This Is login Google", Toast.LENGTH_SHORT).show();
        });


        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

        btn_login.setOnClickListener(v -> {
            login();
            Log.i("TAG", "onViewCreated:succ ");
        });
        skip_textView_login.setOnClickListener(v -> {
            new AlertDialog.Builder(requireActivity())
                    .setTitle("Wait! Are you sure?")
                    .setMessage("You will miss out on personalized content and saving our delicious recipes.")
                    .setPositiveButton("Yes, Iam sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goToMainActivity();
                        }
                    }).setNegativeButton("No, Go Back", null).show();
        });
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        Log.i("TAG", "onSuccess: " + user.getDisplayName());
        goToMainActivity();
    }

    @Override
    public void onFailure(String message) {
        Log.i("TAG", "onFailure: ");
        Toast.makeText(requireActivity(), "email or password is not valid", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessSignInGoogle() {
      Toast.makeText(requireActivity(), "Sign up with google Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    //husayn@

    @Override
    public void OnFailureSignInGoogle(String message) {
        Toast.makeText(requireActivity(), "Failed to Sign in with google", Toast.LENGTH_SHORT).show();
    }

    private void login() {
        String userName = TextInputEditTextEmailLogin.getText().toString();
        String password = TextInputEditTextPasswordLogin.getText().toString();
        if (checkValidation() && isValidEmail(userName)) {
            loginPresenter.login(userName, password);
        }
        Log.i("TAG", "login: " + userName);
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
            Toast.makeText(requireActivity(), "Please fill data", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isValidEmail(String valid) {
        String email = valid.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
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
                        loginPresenter.signInWithGoogle(authCredential);
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public boolean isPassLengthGT7(String pass) {
        return pass.length() >= 7;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}