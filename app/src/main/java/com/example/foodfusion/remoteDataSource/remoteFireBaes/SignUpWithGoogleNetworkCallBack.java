package com.example.foodfusion.remoteDataSource.remoteFireBaes;

import com.google.firebase.auth.FirebaseUser;

public interface SignUpWithGoogleNetworkCallBack {
    void onSuccessGoogle();
    void onFailureGoogle(String message);
}
