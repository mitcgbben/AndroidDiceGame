package com.example.bennettmitchellassignment5;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreboardAdapter extends ArrayAdapter<Player> {
    private Context context;
    private List<Player> items;
    // constructor for the adapter
    public ScoreboardAdapter(Context context, List<Player> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        // get the information from the location
        Player playerItem = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.scoreboard_list_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView bonus = convertView.findViewById(R.id.bonuses);
        TextView score = convertView.findViewById(R.id.totalScore);

        name.setText(playerItem.getName());
        bonus.setText(convertView.getResources().getString(R.string.doubleCount) + " " + playerItem.getDoubles() + " " + convertView.getResources().getString(R.string.tripleCount) + " " + playerItem.getTriples());
        score.setText(playerItem.getScore());

        return convertView;
    }
}
