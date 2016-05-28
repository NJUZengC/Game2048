package com.example.zengc.game2048;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GridLayout s;
    GameView gameView;
    private TextView score;
    static MainActivity mainActivity = null;

    private Button restart;
    public MainActivity(){
        mainActivity = this;
    }

    public static MainActivity getMainActivity()
    {
        return mainActivity;
    }

    private int Scores = 0;
    public void clearScore(){
        Scores = 0;
        showScore();

    }

    public  void showScore()
    {
        score.setText(this.Scores+"");
    }

    public void addScore(int num){
        Scores += num;
        showScore();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score = (TextView)findViewById(R.id.Score);
        restart = (Button)findViewById(R.id.RESTART);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearScore();
                GameView gameView = (GameView)findViewById(R.id.Grid);
                gameView.startGame();
            }
        });


    }
}
