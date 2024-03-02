package com.example.foodfusion.model.authentication_repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodfusion.network.remoteFireBaes.auth.FireBaseAuthenticationWrapper;
import com.example.foodfusion.network.remoteFireBaes.auth.LoginNetworkCallBack;
import com.example.foodfusion.network.remoteFireBaes.auth.SignInWithGoogleNetworkCallBack;
import com.example.foodfusion.network.remoteFireBaes.auth.SignUpNetworkCallBack;
import com.example.foodfusion.network.remoteFireBaes.auth.SignUpWithGoogleNetworkCallBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationFireBaseRepo implements AuthenticationRepository {
    private static final String TAG = "AuthenticationFireBase";
    private static AuthenticationFireBaseRepo authenticationFireBaseRepo = null;
    private FireBaseAuthenticationWrapper fireBaseAuthenticationWrapper;

    private AuthenticationFireBaseRepo() {
        this.fireBaseAuthenticationWrapper = FireBaseAuthenticationWrapper.getInstance();
    }

    public static synchronized AuthenticationFireBaseRepo getInstance() {
        if (authenticationFireBaseRepo == null) {
            authenticationFireBaseRepo = new AuthenticationFireBaseRepo();
        }
        return authenticationFireBaseRepo;
    }

    @Override
    public void signIn(String email, String password, LoginNetworkCallBack loginNetwork) {
        Log.i(TAG, "login:  Login in with : " + email + " password is : " + password);
        fireBaseAuthenticationWrapper.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: signIn Failed");
                        loginNetwork.onFailureLogin(e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.i(TAG, "onSuccess: signIn Success");
                        loginNetwork.onSuccessLogin(authResult.getUser());
                    }
                });
    }

    @Override
    public void signUp(String email, String password, SignUpNetworkCallBack signupNetwork) {
        fireBaseAuthenticationWrapper.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.i(TAG, "onSuccess: SignUp success");
                        signupNetwork.onSuccessSignUp(authResult.getUser());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: SignUp Failed");
                        signupNetwork.onFailureSignUp(e.getMessage());
                    }
                });
    }

    @Override
    public void signOut() {
        Log.i(TAG, "signOut: SignOut Success");
        fireBaseAuthenticationWrapper.logout();
    }


    public FirebaseUser getUser() {
        return fireBaseAuthenticationWrapper.getCurrentUser();
    }

    @Override
    public void signUpWithGoogle(AuthCredential authCredential, SignUpWithGoogleNetworkCallBack signUpWithGoogleCallBack) {
        fireBaseAuthenticationWrapper.getFirebaseAuth().signInWithCredential(authCredential)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: SignUp With Google Failed");
                        signUpWithGoogleCallBack.onFailureGoogle(e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.i(TAG, "onSuccess: SignIn With Google Success");
                        signUpWithGoogleCallBack.onSuccessGoogle();
                    }
                });
    }

    @Override
    public void signInWithGoogle(AuthCredential authCredential, SignInWithGoogleNetworkCallBack signInWithGoogleNetworkCallBack) {
        fireBaseAuthenticationWrapper.getFirebaseAuth().signInWithCredential(authCredential)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: SignUp With Google Failed");
                        signInWithGoogleNetworkCallBack.onFailureGoogle(e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.i(TAG, "onSuccess: SignIn With Google Success");
                        signInWithGoogleNetworkCallBack.onSuccessGoogle();
                    }
                });
    }


    public boolean isAuthenticated() {
        return AuthenticationFireBaseRepo.getInstance().getUser() != null;
    }


}
