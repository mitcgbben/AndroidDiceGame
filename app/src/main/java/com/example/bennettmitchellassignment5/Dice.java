package com.example.bennettmitchellassignment5;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Dice implements Parcelable{
    // constructor
    private int[] numRolled = new int[3];
    private int total;
    private int DICECOUNT;
    private int scoreBonus;
    private int rollTotal;

    // multipliers
    private boolean doubles;
    private final int DOUBLEBONUS = 50;
    private boolean triples;
    private final int TRIPLEBONUS = 100;
    private String bonusType = "No Bonus";

    private String playerName;
    private int doublesRolled;
    private int triplesRolled;

    public Dice(){
        this(0,0,0);
    }

    public Dice(int num1, int num2, int num3){
        numRolled = new int[]{num1, num2, num3};
        total = 0;
        DICECOUNT = 3;
        scoreBonus = 0;
        rollTotal = 0;
        doubles = true;
        triples = true;
        doublesRolled = 0;
        triplesRolled = 0;
        playerName = "";
    }


    protected Dice(Parcel in) {
        numRolled = in.createIntArray();
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(numRolled);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dice> CREATOR = new Creator<Dice>() {
        @Override
        public Dice createFromParcel(Parcel in) {
            return new Dice(in);
        }

        @Override
        public Dice[] newArray(int size) {
            return new Dice[size];
        }
    };

    public int[] getNums(){
        return numRolled;
    }

    public int[] roll(){
        rollTotal = 0;
        for (int i = 0; i < DICECOUNT; i++){
            int roll = (int)(Math.random() * 6) + 1; // 1-6
            numRolled[i] = roll;
            rollTotal += roll;
        }
        // doubles
        if ((numRolled[0] == numRolled[1] && numRolled[1] == numRolled[2]) && triples){
            scoreBonus = TRIPLEBONUS;
            bonusType = "Triples";
            triplesRolled++;
        }
        else if ((numRolled[0] == numRolled[1] || numRolled[0] == numRolled[2] || numRolled[1] == numRolled[2])
        && doubles){
            scoreBonus = DOUBLEBONUS;
            bonusType = "Doubles";
            doublesRolled++;
        }
        else{
            scoreBonus = 0;
            bonusType = "None";
        }
        rollTotal += scoreBonus;
        total += rollTotal;
        return getNums();
    }
    // return the new player back to 0
    public void updatePlayerName(String name){
        if (!name.equals(this.playerName)){
            this.playerName = name;
            this.total = 0;
            this.doublesRolled = 0;
            this.triplesRolled = 0;
        }
    }

    public int getTotal(){return total;}
    public int getBonus(){return scoreBonus;}
    public String getBonusType(){return bonusType;}
    public int getRollTotal(){return rollTotal;}
    public String getPlayerName(){return playerName;}
    public int getDoublesRolled(){return this.doublesRolled;}
    public int getTriplesRolled(){return this.triplesRolled;}

    public boolean getDoubleMult(){return doubles;}
    public void setDoubleMult(boolean b){doubles = b;}
    public boolean getTripleMult(){return triples;}
    public void setTripleMult(boolean b){triples = b;}
}
