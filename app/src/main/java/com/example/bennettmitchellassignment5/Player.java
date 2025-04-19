package com.example.bennettmitchellassignment5;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Player {
    private String name;
    private String score;
    private String doubles;
    private String triples;

    public Player(String name, String score, String doubles, String triples){
        this.name = name;
        this.score = score;
        this.doubles = doubles;
        this.triples = triples;
    }

    public String getName(){return this.name;}
    public String getScore(){return this.score;}
    public String getDoubles(){return this.doubles;}
    public String getTriples(){return this.triples;}

}
