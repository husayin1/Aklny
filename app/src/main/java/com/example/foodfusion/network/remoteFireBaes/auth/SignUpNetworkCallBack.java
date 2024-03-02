package com.example.foodfusion.network.remoteFireBaes.auth;

import com.google.firebase.auth.FirebaseUser;

public interface SignUpNetworkCallBack {
    void onSuccessSignUp(FirebaseUser user);

    void onFailureSignUp(String message);
}
