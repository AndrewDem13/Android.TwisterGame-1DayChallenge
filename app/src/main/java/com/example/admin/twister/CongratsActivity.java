package com.example.admin.twister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class CongratsActivity extends AppCompatActivity implements View.OnClickListener {

    Button quit, newGame;
    TextView congratulations, winnerSteps, statistics;
    ArrayList<User> losers;
    User winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congrats);

        quit = (Button) findViewById(R.id.exit);
        quit.setOnClickListener(this);

        newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(this);

        congratulations = (TextView) findViewById(R.id.congratsText);
        winnerSteps = (TextView) findViewById(R.id.winnerSteps);
        statistics = (TextView) findViewById(R.id.statistics);

        winner = (User) getIntent().getSerializableExtra("winner");
        losers = (ArrayList<User>) getIntent().getSerializableExtra("losers");

        congratulations.setText(String.format(Locale.getDefault(), "Игрок %d", winner.getId()));
        winnerSteps.setText(String.format(Locale.getDefault(), "%d %s",
                winner.getMoves(), getWord(winner.getMoves())));

        String stats = "";
        for (int i = losers.size()-1; i >= 0; i--) {
            stats += String.format(Locale.getDefault(), "\nИгрок %d - %d %s",
                    losers.get(i).getId(), losers.get(i).getMoves(), getWord(losers.get(i).getMoves()));
        }

        statistics.setText(stats);
    }

    private String getWord(int i) {
        switch (i) {
            case 1:
                return "раунд";
            case 2:
                return "раунда";
            case 3:
                return "раунда";
            case 4:
                return "раунда";
            default:
                return "раундов";
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newGame:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;
            case R.id.exit:
                finishAffinity();
                break;
        }
    }
}
