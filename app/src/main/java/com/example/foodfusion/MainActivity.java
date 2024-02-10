package com.example.foodfusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodfusion.features.Authentication.AuthenticationActivity;
import com.example.foodfusion.model.repositories.authentication_repository.AuthenticationFireBaseRepo;
import com.example.foodfusion.model.repositories.local_repo.FavAndPlannerRepo;
import com.example.foodfusion.utilities.Connectivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    NavController navController;
    ImageView endIcon;
    GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("555667483551-p94tuutqrtkt2gvl3iicu0ejipcr3q8n.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        endIcon = findViewById(R.id.endIcon);
        endIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AuthenticationFireBaseRepo.getInstance().isAuthenticated()){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.logout)
                            .setMessage(R.string.are_you_sure_you_want_to_log_out)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i("TAG", "onClick: after logout"+ AuthenticationFireBaseRepo.getInstance().getUser());
                                    AuthenticationFireBaseRepo.getInstance().signOut();
                                    googleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FavAndPlannerRepo.getInstance(MainActivity.this).deleteAllFav();
                                            FavAndPlannerRepo.getInstance(MainActivity.this).deleteAllWeekPlan();
                                            goToAuthActivity();
                                        }
                                    });
                                }
                            }).setNegativeButton(R.string.no,null).show();
                }
            }
        });

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.HomeFragment, R.id.SearchFragment, R.id.FavoriteFragment, R.id.MealPlanFragment).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(bottom_navigation, navController);

        navController.addOnDestinationChangedListener((controller,destination,args)->{
            if (destination.getId() == R.id.searchMealFragment) {
                toolbar.setVisibility(View.GONE);
                bottom_navigation.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.MealDetailsFragment) {
                toolbar.setVisibility(View.GONE);
                bottom_navigation.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.ingredientFragment) {
                toolbar.setVisibility(View.GONE);
                bottom_navigation.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.countryFragment) {
                toolbar.setVisibility(View.GONE);
                bottom_navigation.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.searchResultFragment) {
                toolbar.setVisibility(View.GONE);
                bottom_navigation.setVisibility(View.GONE);
            } else if(destination.getId() == R.id.MealPlanFragment) {
                toolbar.setVisibility(View.GONE);
                bottom_navigation.setVisibility(View.VISIBLE);
            }else if(destination.getId()==R.id.HomeFragment){
                toolbar.setVisibility(View.VISIBLE);
                bottom_navigation.setVisibility(View.VISIBLE);
            }else{
                toolbar.setVisibility(View.GONE);
                bottom_navigation.setVisibility(View.VISIBLE);
            }
        });

        /*bottom_navigation.setOnNavigationItemSelectedListener(item->{

        });
        */

        if(!AuthenticationFireBaseRepo.getInstance().isAuthenticated()){
            toolbar.setVisibility(View.GONE);
            Log.i("TAG", "onClick: get user from main activity after guest"+ AuthenticationFireBaseRepo.getInstance().getUser());
            Log.i("TAG", "onClick: get isauth from main after guest"+ AuthenticationFireBaseRepo.getInstance().isAuthenticated());
            navController.addOnDestinationChangedListener((controller,destination,args)->{
                if (destination.getId() == R.id.FavoriteFragment||destination.getId()==R.id.MealPlanFragment) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.sign_up_for_more_features)
                            .setMessage(R.string.add_your_food_preferences_plan_your_meals_and_more)
                            .setPositiveButton(R.string.sign_up, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    goToAuthActivity();
                                }
                            }).setNegativeButton(R.string.cancel,null).show();
                    navController.navigateUp();

                }
            });

        }

        if(AuthenticationFireBaseRepo.getInstance().isAuthenticated()){
            FavAndPlannerRepo.getInstance(this.getApplicationContext()).refreshMeals();
            FavAndPlannerRepo.getInstance(this.getApplicationContext()).refreshPlanner();
        }

    }
    private void goToAuthActivity() {
        Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }



    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

}