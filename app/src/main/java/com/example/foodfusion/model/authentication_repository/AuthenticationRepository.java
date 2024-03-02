package com.example.foodfusion.model.authentication_repository;

import com.example.foodfusion.network.remoteFireBaes.auth.LoginNetworkCallBack;
import com.example.foodfusion.network.remoteFireBaes.auth.SignInWithGoogleNetworkCallBack;
import com.example.foodfusion.network.remoteFireBaes.auth.SignUpNetworkCallBack;
import com.example.foodfusion.network.remoteFireBaes.auth.SignUpWithGoogleNetworkCallBack;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationRepository {

    void signIn(String email, String password, LoginNetworkCallBack loginNetwork);

    void signUp(String email, String password, SignUpNetworkCallBack signupNetwork);

    void signOut();

    FirebaseUser getUser();

    void signUpWithGoogle(AuthCredential authCredential, SignUpWithGoogleNetworkCallBack signUpWithGoogleNetworkCallBack);

    void signInWithGoogle(AuthCredential authCredential, SignInWithGoogleNetworkCallBack signInWithGoogleNetworkCallBack);
}
