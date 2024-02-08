package com.example.foodfusion.utilities;

import com.example.foodfusion.model.repositories.meal_models.pojos.PojoMeal;

public class ConvertMealToString
{
    public static String getMealString(PojoMeal meal)
    {
        String str="";
        str = meal.strMeal+"\n\n"+"Category : "+meal.strCategory+"\n\n"+"Country : "+meal.strArea+"\n\n"+
                "Instructions : "+"\n"+formatText(meal.strInstructions)+"\n\n"+"youtube Link : "+meal.strYoutube;
        return str;
    }

    private static String formatText(String strInstructions) {
        strInstructions = strInstructions.replace(". ", ".\n\n");
        return strInstructions;
    }
}
