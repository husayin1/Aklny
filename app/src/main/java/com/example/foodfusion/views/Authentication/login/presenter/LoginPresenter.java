package com.example.foodfusion.views.Authentication.login.presenter;

import com.example.foodfusion.remoteDataSource.remoteFireBaes.SignInWithGoogleNetworkCallBack;
import com.example.foodfusion.views.Authentication.login.view.LoginViewInterface;
import com.example.foodfusion.model.repositories.authentication_repository.AuthenticationFireBaseRepo;
import com.example.foodfusion.model.repositories.authentication_repository.AuthenticationRepository;
import com.example.foodfusion.remoteDataSource.remoteFireBaes.LoginNetworkCallBack;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements  LoginPresenterInterface, LoginNetworkCallBack, SignInWithGoogleNetworkCallBack {
    private LoginViewInterface loginViewInterface;
    private AuthenticationRepository authenticationRepository;
    private static LoginPresenter loginPresenter=null;
    private LoginPresenter(LoginViewInterface loginViewInterface){
        this.loginViewInterface = loginViewInterface;
        authenticationRepository = AuthenticationFireBaseRepo.getInstance();
    }
    public static LoginPresenter getInstance(LoginViewInterface loginViewInterface){
        if(loginPresenter==null){
            loginPresenter = new LoginPresenter(loginViewInterface);
        }
        return loginPresenter;
    }


    @Override
    public void login(String email, String password) {
        authenticationRepository.signIn(email,password,this);
    }

    @Override
    public void signInWithGoogle(AuthCredential authCredential) {
        authenticationRepository.signInWithGoogle(authCredential,this);
    }

    @Override
    public void onSuccessLogin(FirebaseUser user) {
        loginViewInterface.onSuccess(user);
    }

    @Override
    public void onFailureLogin(String message) {
        loginViewInterface.onFailure(message);
    }

    @Override
    public void onSuccessGoogle() {
        loginViewInterface.onSuccessSignInGoogle();
    }

    @Override
    public void onFailureGoogle(String message) {
        loginViewInterface.OnFailureSignInGoogle(message);

    }
}
