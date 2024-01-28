package com.example.foodfusion.views.Authentication.signup.presenter;

import com.google.firebase.auth.AuthCredential;

public interface SignUpPresenterInterface {

    void signUp(String email, String password);
    void signUpWithGoogle(AuthCredential authCredential);
}
