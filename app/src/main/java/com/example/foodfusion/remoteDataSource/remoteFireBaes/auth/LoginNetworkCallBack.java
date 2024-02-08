package com.example.foodfusion.remoteDataSource.remoteFireBaes.auth;

import com.google.firebase.auth.FirebaseUser;

public interface LoginNetworkCallBack {

    void onSuccessLogin(FirebaseUser user);

    void onFailureLogin(String message);
}
