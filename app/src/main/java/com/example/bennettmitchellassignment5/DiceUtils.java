package com.example.bennettmitchellassignment5;

import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;


public class DiceUtils {


    //public static Drawable getImage(int id){
    public static int getImage(int id){
        final int[] diceImgs = new int[]{R.drawable.die_1, R.drawable.die_2, R.drawable.die_3,
                R.drawable.die_4, R.drawable.die_5, R.drawable.die_6};
        return diceImgs[id - 1];
    }
}
