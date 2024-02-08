package com.example.foodfusion.model.repositories.authentication_repository;

import com.example.foodfusion.remoteDataSource.remoteFireBaes.auth.LoginNetworkCallBack;
import com.example.foodfusion.remoteDataSource.remoteFireBaes.auth.SignInWithGoogleNetworkCallBack;
import com.example.foodfusion.remoteDataSource.remoteFireBaes.auth.SignUpNetworkCallBack;
import com.example.foodfusion.remoteDataSource.remoteFireBaes.auth.SignUpWithGoogleNetworkCallBack;
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
