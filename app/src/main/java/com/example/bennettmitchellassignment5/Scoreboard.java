package com.example.bennettmitchellassignment5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Scoreboard extends AppCompatActivity {

    private TextView rollsLabel;
    private TextView thanksLabel;

    // listview
    private ListView LVscoreboard;
    private ScoreboardAdapter scoreboardAdapter;

    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard); //xml

        // assign variables
        rollsLabel = findViewById(R.id.totalRolls);
        //scores = findViewById(R.id.scores);
        thanksLabel = findViewById(R.id.thankMsg);

        // get data
        Bundle bundle = getIntent().getExtras(); // get the extras passed
        String playerName = bundle.getString("com.bennett.PlayerName"); // get the player name
        sp = getSharedPreferences("Stats", MODE_PRIVATE);
        Map<String, ?> stats = sp.getAll(); // get all the sharedpreferences

        int totalRolls = sp.getInt("totalRolls", 0);
        String scoreMsg = "";

        //iterate through each entry
//        for (Map.Entry<String, ?> player: stats.entrySet()){
            // i saw the opportunity for using this conditional statement so here we are
            // ignores total rolls
//            scoreMsg += (!player.getKey().equals("totalRolls")) ? player.getKey() + ": " + player.getValue().toString() + "\n" : "";
//        }
        // show the data
        thanksLabel.setText(getText(R.string.thankYou) + " " + playerName + "!");
        rollsLabel.setText(getText(R.string.totalDiceRolls) + ": " + Integer.toString(totalRolls));

        // list view
        scoreboardAdapter = new ScoreboardAdapter(this, getPlayers());
        LVscoreboard = findViewById(R.id.scoreDisplay);

        LVscoreboard.setAdapter(scoreboardAdapter);
        //scores.setText(scoreMsg);
        Button exit = findViewById(R.id.returnButton);
        exit.setOnClickListener((View v) -> {
            this.finish(); // close
        });


    }

    private List<Player> getPlayers(){
        ArrayList<Player> playerList = new ArrayList<>();
        Map<String, ?> playerMap = sp.getAll();

        for (Map.Entry<String, ?> playerInfo : playerMap.entrySet()){
            String name = playerInfo.getKey();
            if (!name.equals("totalRolls")){
                String nums = playerInfo.getValue().toString();
                String[] stats = nums.split(",");
                String totalScore, doubles, triples;
                try {
                    totalScore = stats[0];
                    doubles = stats[1];
                    triples = stats[2];
                } catch (IndexOutOfBoundsException e){ // allow old data to be displayed
                    totalScore = nums;
                    doubles = "0";
                    triples = "0";
                }
                Player player = new Player(name, totalScore, doubles, triples);

                playerList.add(player);
            }
        }

        return playerList;
    }


}