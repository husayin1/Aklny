package com.example.foodfusion.remoteDataSource.remoteFireBaes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireBaseAuthenticationWrapper {
    private FirebaseAuth firebaseAuth;
    private static FireBaseAuthenticationWrapper fireBaseAuthenticationWrapper = null;
    private FireBaseAuthenticationWrapper(){
        this.firebaseAuth = FirebaseAuth.getInstance();
    }
    public static synchronized FireBaseAuthenticationWrapper getInstance(){
        if(fireBaseAuthenticationWrapper ==null){
            fireBaseAuthenticationWrapper = new FireBaseAuthenticationWrapper();
        }
        return fireBaseAuthenticationWrapper;
    }

    public FirebaseAuth getFirebaseAuth(){return firebaseAuth;}
    public FirebaseUser getCurrentUser(){return firebaseAuth.getCurrentUser();}
    public void logout(){
        firebaseAuth.signOut();
    }


}
