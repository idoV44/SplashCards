package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class ScoreBoard extends AppCompatActivity {
    SharedPreferences sp;
    int score [][]=new int [4][10];
    ImageView logo;
    int firstplace=0;
    ConstraintLayout bgElement;
    int dar=1;
    int k=0;

    MediaPlayer musicBcg;
    boolean playMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        //  musicBcg = MediaPlayer.create(ScoreBoard.this, R.raw.bcg);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linegod);
        logo = findViewById(R.id.logo);
        bgElement = findViewById(R.id.scoresbcg);
        sp = getSharedPreferences("details", MODE_PRIVATE);
        int sumTime = sp.getInt("sumTime", 0);
        int world = sp.getInt("worldNow", 1);
        if (sumTime == 0) {
            Intent mIntent = getIntent();
            int intValue = mIntent.getIntExtra("place", world);
            world = intValue;
        } else {
            world = world - 1;
        }
        int place = 0;
        boolean enough = false;
        String savedString="";

        if(world==1) {
            savedString = sp.getString("string1", "");
        }
        else if(world==2){
            savedString = sp.getString("string2", "");
        }
        else if(world==3){
            savedString = sp.getString("string3", "");
        }
        else if (world==4){
            savedString = sp.getString("string4", "");
        }
        StringTokenizer st = new StringTokenizer(savedString, ",");
        if(savedString!="") {
            for (int i = 0; i < 10; i++) {
                score[world - 1][i] = Integer.parseInt(st.nextToken());
            }
        }

        if (sumTime > 0) {



            if (sumTime > score[world - 1][9]) {
                for (int i = 1; i <= 10 && enough == false; i++) {
                    if (sumTime > score[world - 1][i]) {
                        place = i - 1;
                        firstplace = i - 1;
                        enough = true;
                    }
                }

                int archive[] = new int[10 - place];

                for (place = place; place < 10; place = place + 1) {
                    if (place == firstplace) {
                        archive[k] = score[world - 1][place];
                        score[world - 1][place] = sumTime;

                    } else {
                        archive[k + 1] = score[world - 1][place];
                        score[world - 1][place] = archive[k++];
                    }
                }
            }



        }
        for (int i = 0; i < 10; i++) {
            if(score[world - 1][9-i]!=0) {
                int d = i + 1;
                TextView dynamicTextView = new TextView(this);
                dynamicTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                dynamicTextView.setText(""+score[world - 1][9 - i]);
                dynamicTextView.setTextSize(80);
                layout.addView(dynamicTextView);
            }
        }
        if (world == 1) {
            logo.setImageResource(R.drawable.score1);
            bgElement.setBackground(ContextCompat.getDrawable(this, R.drawable.blue));

        } else if (world == 2) {
            logo.setImageResource(R.drawable.score2);
            bgElement.setBackground(ContextCompat.getDrawable(this, R.drawable.pink));

        } else if (world == 3) {
            logo.setImageResource(R.drawable.score3);
            bgElement.setBackground(ContextCompat.getDrawable(this, R.drawable.tibet));
        } else if (world == 4) {
            logo.setImageResource(R.drawable.score4);
            bgElement.setBackground(ContextCompat.getDrawable(this, R.drawable.black));
        }
        sumTime = 0;

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("sumTime", sumTime);

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            str.append(score[world-1][i]).append(",");
        }
        String result=str.toString();
        if(world==1){
            editor.putString("string1", result);
        }
        else if(world==2){
            editor.putString("string2", result);
        }
        else if(world==3){
            editor.putString("string3", result);
        }
        else if(world==4){
            editor.putString("string4", result);
        }
        editor.commit();


    }


    public void back(View view) {
        Intent tfd = new Intent(ScoreBoard.this, Road.class);
        startActivity(tfd);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        MenuItem item= menu.findItem(R.id.music);
        playMusic= sp.getBoolean("music", true);
        Toast.makeText(this, ""+playMusic, Toast.LENGTH_SHORT).show();
        if(playMusic)
        {
            item.setTitle("on");
            item.setIcon(R.drawable.speker);
            MusicManger.player.start();
        }
        else
        {
            item.setTitle("off");
            item.setIcon(R.drawable.mute);
            MusicManger.player.pause();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.music)
        {
            if(!playMusic)
            {
                playMusic= true;
                MusicManger.player.start();
                item.setTitle("on");
                item.setIcon(R.drawable.speker);
            }
            else
            {
                playMusic= false;
                item.setTitle("off");
                item.setIcon(R.drawable.mute);
                MusicManger.player.pause();
            }
            // Toast.makeText(this, ""+music, Toast.LENGTH_SHORT).show();

        }
        else  if(item.getItemId() == R.id.world)
        {
            Intent gonext = new Intent(ScoreBoard.this, Road.class);
            startActivity(gonext);
            finish();

        }
        else  if(item.getItemId() == R.id.user)
        {
            Intent gonext = new Intent(ScoreBoard.this, Login.class);
            startActivity(gonext);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean("music", playMusic);
        editor.commit();
        MusicManger.player.pause();
    }
}