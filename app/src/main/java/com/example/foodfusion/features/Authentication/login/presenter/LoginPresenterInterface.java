package com.example.foodfusion.features.Authentication.login.presenter;

import com.google.firebase.auth.AuthCredential;

public interface    LoginPresenterInterface {

    void login(String email, String password);

    void signInWithGoogle(AuthCredential authCredential);
}
