package com.example.foodfusion.remoteDataSource.remoteFireBaes.auth;

import com.google.firebase.auth.FirebaseUser;

public interface SignUpWithGoogleNetworkCallBack {
    void onSuccessGoogle();

    void onFailureGoogle(String message);
}
