package com.example.foodfusion.remoteDataSource.remoteFireBaes.auth;

import com.google.firebase.auth.FirebaseUser;

public interface SignUpNetworkCallBack {
    void onSuccessSignUp(FirebaseUser user);

    void onFailureSignUp(String message);
}
