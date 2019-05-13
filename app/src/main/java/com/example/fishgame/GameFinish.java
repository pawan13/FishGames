package com.example.fishgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameFinish extends AppCompatActivity {
    private Button PlayAgain;
    private TextView ScoreScored;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finish);
        PlayAgain = findViewById(R.id.btn_playAgain);
        PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameFinish.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ScoreScored = findViewById(R.id.txtScore);
        score = getIntent().getExtras().get("Score").toString();
        ScoreScored.setText("Score:" + score);
    }
}
