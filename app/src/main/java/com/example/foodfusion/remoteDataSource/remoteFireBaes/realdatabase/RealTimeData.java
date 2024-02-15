package com.example.foodfusion.remoteDataSource.remoteFireBaes.realdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodfusion.model.repositories.authentication_repository.AuthenticationFireBaseRepo;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;
import com.example.foodfusion.model.repositories.meal_models.pojos.PojoPlanner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class RealTimeData {
    private static final String TAG = "RealTimeData";
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference favDatabase;
    private DatabaseReference planDatabase;
    private AuthenticationFireBaseRepo authenticationFireBaseRepo;

    public RealTimeData() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        authenticationFireBaseRepo = AuthenticationFireBaseRepo.getInstance();
        if (authenticationFireBaseRepo.isAuthenticated()) {
            this.favDatabase = firebaseDatabase.getReference().child("user").child(authenticationFireBaseRepo.getUser().getUid()).child("favorite");
            this.planDatabase = firebaseDatabase.getReference().child("user").child(authenticationFireBaseRepo.getUser().getUid()).child("plan");
        }
    }

    public void addFavMeal(PojoMeal pojoMeal, RealTimeInsertDelegation realTimeInsertDelegation) {
        if (authenticationFireBaseRepo.isAuthenticated()) {
            favDatabase.child(pojoMeal.idMeal).setValue(pojoMeal)
                    .addOnCompleteListener(result -> realTimeInsertDelegation.onSuccess())
                    .addOnFailureListener(err -> realTimeInsertDelegation.onFailure(err.getMessage().toString()));
        }

    }

    public void addPlannerMeal(PojoPlanner pojoMeal, RealTimeInsertDelegation realTimeInsertDelegation) {
        if (authenticationFireBaseRepo.isAuthenticated()) {
            planDatabase.child(pojoMeal.id).setValue(pojoMeal)
                    .addOnCompleteListener(result -> realTimeInsertDelegation.onSuccess())
                    .addOnFailureListener(err -> realTimeInsertDelegation.onFailure(err.getMessage().toString()));
        }
    }

    public void deleteFavMeal(String id, RealTimeIDeletionDelegation realTimeIDeletionDelegation) {
        favDatabase.child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        realTimeIDeletionDelegation.onSuccess();
                    }
                }).start();
            }
        });
    }

    public void deletePlannedMeal(String id, RealTimeIDeletionDelegation realTimeIDeletionDelegation) {
        planDatabase.child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                realTimeIDeletionDelegation.onSuccess();
            }
        });
    }

    public void getFavoriteMeals(RealTimeFavoriteDelegation realTimeFavoriteDelegation) {
        ValueEventListener _listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<PojoMeal> meals = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    meals.add(data.getValue(PojoMeal.class));
                }
                realTimeFavoriteDelegation.onSuccess(meals);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                realTimeFavoriteDelegation.onFailure(error.getMessage().toString());
            }
        };
        favDatabase.addValueEventListener(_listener);
    }

    public void getWeekPlanner(RealTimePlannerDelegation realTimePlannerDelegation) {
        ValueEventListener _listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PojoPlanner> meal = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    meal.add(snapshot.getValue(PojoPlanner.class));
                }
                realTimePlannerDelegation.onSuccess(meal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                realTimePlannerDelegation.onFailure(databaseError.toException().toString());
            }
        };
        planDatabase.addValueEventListener(_listener);
    }
}
