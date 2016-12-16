package com.example.admin.twister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        int numberOfPlayers;
        switch (view.getId()) {
            case R.id.button1:
                numberOfPlayers = 2;
                intent.putExtra("numberOfPlayers", numberOfPlayers);
                startActivity(intent);
                break;
            case R.id.button2:
                numberOfPlayers = 3;
                intent.putExtra("numberOfPlayers", numberOfPlayers);
                startActivity(intent);
                break;
            case R.id.button3:
                numberOfPlayers = 4;
                intent.putExtra("numberOfPlayers", numberOfPlayers);
                startActivity(intent);
                break;
            case R.id.button4:
                numberOfPlayers = 5;
                intent.putExtra("numberOfPlayers", numberOfPlayers);
                startActivity(intent);
                break;

        }
    }
}
