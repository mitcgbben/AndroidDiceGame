package com.example.bennettmitchellassignment5;

import static java.lang.Math.abs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private Dice dice;


    private ImageView diceImg1;
    private ImageView diceImg2;
    private ImageView diceImg3;
    private TextView totalText;
    private TextView bonusText;
    private TextView rollText;
//    private int total = 0;
    private TextView enhanceText;

    private SharedPreferences sharedPref;
    private EditText nameField;


    private MediaPlayer rollSfx;
    private Animation bounceAnim;

    private GestureDetector gDetect;
    private static int MIN_Y_SWIPE = 100;
    private static int MAX_Y_SWIPE = 1000;

    private static int MIN_X_SWIPE = 0;
    private static int MAX_X_SWIPE = 400; // make it more narrow


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("plink", "app started");

        sharedPref = getSharedPreferences("Stats", MODE_PRIVATE);
        // create the dice
        if (dice == null){
            dice = new Dice(6,2,1);
        }
//        // image views
        diceImg1 = findViewById(R.id.dice1Img);
        diceImg2 = findViewById(R.id.dice2Img);
        diceImg3 = findViewById(R.id.dice3Img);
        Log.i("plink", "dice assigned");
        totalText = findViewById(R.id.totalText);
        bonusText = findViewById(R.id.bonusText);
        rollText = findViewById(R.id.rollScoreText);

        enhanceText = findViewById(R.id.enhancersSelected);
        nameField = findViewById(R.id.nameField);
//        diceImg1.setImageResource(R.drawable.kitty);
        // define animation and sound
        rollSfx = MediaPlayer.create(this, R.raw.dice);
        bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        bounceAnim.setDuration(200);

        this.gDetect = new GestureDetector(this, this);
//        Button rollButton = findViewById(R.id.rollButton);
//        rollButton.setOnClickListener((View v) -> rollDie());

        Button scoreboardButton = findViewById(R.id.scoreboardButton);
        scoreboardButton.setOnClickListener((View v) -> {
            Intent sb = new Intent(getApplicationContext(), Scoreboard.class);
            sb.putExtra("com.bennett.PlayerName", nameField.getText().toString());
            startActivity(sb);
        });
        displayNums();
    }
    // creates the option menu and displays it
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // get the option menu option that is selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId(); // get the id of the option

        if (id == R.id.settingsOption){
                SettingsMenu dialog = new SettingsMenu();
                dialog.sendDice(dice);
                // create dialog
                dialog.show(getSupportFragmentManager(), "123");
                return true;
        }
        else if (id == R.id.aboutOption){
            AlertDialog.Builder builder = new AlertDialog.Builder(this); // context is here
            builder.setMessage(R.string.aboutMsg);
            builder.setTitle(R.string.aboutTitle);

            AlertDialog alert = builder.create();
            alert.show();
            // i could do more with this but i want to keep it simple
        }
        return super.onOptionsItemSelected(item);
    }



    public void updateEnhancers(){
        bonusText.setVisibility(View.VISIBLE);
        String enhanceMsg = "";
        if (dice.getDoubleMult() && dice.getTripleMult()){
            enhanceMsg = getString(R.string.bothSelect);
        }
        else if (dice.getDoubleMult()){
            enhanceMsg = getString(R.string.doubleSelect);
        }
        else if (dice.getTripleMult()){
            enhanceMsg = getString(R.string.tripleSelect);
        }
        else{
            enhanceMsg = getString(R.string.noMult);
            bonusText.setVisibility(View.INVISIBLE); // hides it instead of gone
        }
        enhanceText.setText(enhanceMsg);
    }
    public void rollDie(){
        // update player
        dice.updatePlayerName(nameField.getText().toString());

        rollSfx.start();
        rollSfx.setVolume(100, 100);

        // roll the dice
        dice.roll();
//        Log.i("meowmrrp", "dice roled");
        displayNums();


        SharedPreferences.Editor spEdit = sharedPref.edit(); // create editor
        String name = nameField.getText().toString();
        String playerValues = dice.getTotal() + "," + dice.getDoublesRolled() + "," + dice.getTriplesRolled();
        spEdit.putString(name, playerValues);
        spEdit.putInt("totalRolls", sharedPref.getInt("totalRolls", 0) + 1); // default value of 0
        spEdit.commit();

    }



    private void displayNums(){
        //Log.i("plink", "displayNums " + dice1.getNum());
        int[] results = dice.getNums();
        diceImg1.setImageResource(DiceUtils.getImage(results[0]));
        diceImg2.setImageResource(DiceUtils.getImage(results[1]));
        diceImg3.setImageResource(DiceUtils.getImage(results[2]));
        diceImg1.startAnimation(bounceAnim);
        diceImg2.startAnimation(bounceAnim);
        diceImg3.startAnimation(bounceAnim);
        totalText.setText(getString(R.string.totalLabel) + " " + Integer.toString(dice.getTotal()));
        bonusText.setText(getBonusString());
        rollText.setText(getString(R.string.rollTotalLabel) + " " + Integer.toString(dice.getRollTotal()));
        updateEnhancers();

    }

    private String getBonusString(){
        String msg = "";
        int bonus = dice.getBonus();
        String bonusType = dice.getBonusType();

        switch (bonusType){
            case "Doubles":
                msg = getString(R.string.doubleMult)  + bonus;
                break;
            case "Triples":
                msg = getString(R.string.tripleMult) + bonus;
                break;
            default:
                msg = getString(R.string.noMult);
        }
        return msg;
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState); // push the rest of the function

        // save the die
        outState.putParcelable("dice", dice);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle SavedInstanceState){
        super.onRestoreInstanceState(SavedInstanceState);

        // get the die
        dice = SavedInstanceState.getParcelable("dice");
        displayNums();
    }

    // gesture implementation
    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        float deltaY = e1.getY() - e2.getY();

        float deltaX = abs(e1.getX() - e2.getX());

        Log.d("plink", "onFling: X:" + deltaX + " Y:" + deltaY);
        if ((deltaY >= MIN_Y_SWIPE && deltaY <= MAX_Y_SWIPE) && (deltaX >= MIN_X_SWIPE && deltaX <= MAX_X_SWIPE)){
            rollDie();;
        }
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gDetect.onTouchEvent(event);
        return super.onTouchEvent(event); // actually detect the inputs
    }
}