package com.example.foodfusion.remoteDataSource.API;

public interface MealsRemoteDataSourceInterface {
    void makeRandomCall(NetworkCallBack networkCallBack);

    void makeCategoriesCall(NetworkCallBack networkCallBack);

    void makeIngredientsCall(NetworkCallBack networkCallBack);
}
