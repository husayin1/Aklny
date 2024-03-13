package com.example.foodfusion.features.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.foodfusion.R;

public class AuthenticationActivity extends AppCompatActivity {
    NavController navController;
    NavHostFragment navHostFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
         navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
         navController = navHostFragment.getNavController();


    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp()||super.onSupportNavigateUp();
    }
}