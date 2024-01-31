package com.example.foodfusion.remoteDataSource.remoteFireBaes;

import com.google.firebase.auth.FirebaseUser;

public interface SignUpNetworkCallBack {
    void onSuccessSignUp(FirebaseUser user);

    void onFailureSignUp(String message);
}
