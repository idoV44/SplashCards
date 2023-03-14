package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Road extends AppCompatActivity {

    SharedPreferences sp;
    int world;
    int i;

    ImageView npc1;
    ImageView npc2;
    ImageView npc3;
    ImageView npc4;
    ImageView cup2;
    ImageView cup3;
    ImageView cup4;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    boolean newGame;
    Boolean playMusic;
    Boolean english;
    MediaPlayer musicBcg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road);

        sp= getSharedPreferences("details", MODE_PRIVATE);
        // musicBcg = MediaPlayer.create(Road.this, R.raw.bcg);

        newGame= sp.getBoolean("newGame", true);
        if(newGame)
        {
            world=sp.getInt("worldNew",1); ;
        }
        else
        {
            world= sp.getInt("world",1);
        }


        btn1= findViewById(R.id.world1);
        btn2= findViewById(R.id.world2);
        btn3= findViewById(R.id.world3);
        btn4= findViewById(R.id.world4);

        cup2=findViewById(R.id.imageView16);
        cup3=findViewById(R.id.imageView17);
        cup4=findViewById(R.id.imageView18);

        npc1=findViewById(R.id.imageView4);
        npc2=findViewById(R.id.imageView5);
        npc3=findViewById(R.id.imageView6);
        npc4=findViewById(R.id.imageView8);
//        ImageView scoreWin1= findViewById(R.id.scoreLevel1);
//        ImageView scoreWin2= findViewById(R.id.scoreLevel2);
//        ImageView scoreWin3= findViewById(R.id.scoreLevel3);
//        ImageView scoreWin4= findViewById(R.id.scoreLevel4);

        Button [] btnWorlds= {btn1, btn2, btn3, btn4};


//        ImageView []scoreWorlds= {scoreWin1, scoreWin2, scoreWin3, scoreWin4};


        for(i=0; i<world; i++)
        {
            btnWorlds[i].setAlpha(1);
            btnWorlds[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btnNow= findViewById(v.getId());
                    int worldNow=  Integer.parseInt(btnNow.getText().toString());
                    Intent gonext = new Intent(Road.this, Heart.class);
                    // gonext.putExtra("numWorld",numWorld);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("worldNow", worldNow);
                    editor.putBoolean("music", playMusic);
                    MusicManger.player.pause();
                    editor.commit();
                    startActivity(gonext);
                    finish();


                }
            });
//
        }

        if(world==2){
            npc2.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            cup2.setVisibility(View.VISIBLE);
        }

        else if(world==3){
            npc2.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            npc3.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            cup2.setVisibility(View.VISIBLE);
            cup3.setVisibility(View.VISIBLE);
        }
        else if(world==4){
            npc2.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            npc3.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            npc4.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);
            cup2.setVisibility(View.VISIBLE);
            cup3.setVisibility(View.VISIBLE);
            cup4.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_menu,menu);
        MenuItem music= menu.findItem(R.id.music);
        MenuItem langh= menu.findItem(R.id.language);
        playMusic= sp.getBoolean("music", true);
        english= sp.getBoolean("english", true);
        if(playMusic)
        {
            music.setTitle("on");
            music.setIcon(R.drawable.speker);
            MusicManger.player.start();
        }
        else
        {
            music.setTitle("off");
            music.setIcon(R.drawable.mute);
            MusicManger.player.pause();
        }
        if(english)
        {
            //  setLocale("he");
            langh.setIcon(R.drawable.hebrew);
            langh.setTitle("Hebrew");
        }
        else
        {
            //  setLocale("hi");
            langh.setIcon(R.drawable.english);
            langh.setTitle("English");
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
                MusicManger.player.pause();
                item.setIcon(R.drawable.mute);
            }


        }
        else if(item.getItemId() == R.id.language)
        {
            if(!english)
            {
                english= true;
                item.setIcon(R.drawable.hebrew);
                //setLocale("he");
                item.setTitle("Hebrew");
            }
            else
            {
                english= false;
                item.setIcon(R.drawable.english);
                // setLocale("hi");
                item.setTitle("English");
            }

        }
        return super.onOptionsItemSelected(item);
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    protected void onPause() {
        super.onPause();
        MusicManger.player.pause();
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean("music", playMusic);
        editor.putBoolean("english", english);
        editor.commit();
    }

    @Override
    public void finish() {
        super.finish();
        MusicManger.player.pause();
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean("music", playMusic);
        editor.putBoolean("english", english);
        editor.commit();
    }

    public void score1(View view) {
        SharedPreferences.Editor editor = sp.edit();
        Intent tfd = new Intent(Road.this, ScoreBoard.class);
        tfd.putExtra("place", 1);
        editor.putInt("sumTime",0);
        editor.commit();
        startActivity(tfd);
    }

    public void score2(View view) {
        SharedPreferences.Editor editor = sp.edit();
        Intent tfd = new Intent(Road.this, ScoreBoard.class);
        tfd.putExtra("place", 2);
        editor.putInt("sumTime",0);
        editor.commit();
        startActivity(tfd);
    }

    public void score3(View view) {
        SharedPreferences.Editor editor = sp.edit();
        Intent tfd = new Intent(Road.this, ScoreBoard.class);
        tfd.putExtra("place", 3);
        editor.putInt("sumTime",0);
        editor.commit();
        startActivity(tfd);
    }

    public void score4(View view) {
        SharedPreferences.Editor editor = sp.edit();
        Intent tfd = new Intent(Road.this, ScoreBoard.class);
        tfd.putExtra("place", 4);
        editor.putInt("sumTime",0);
        editor.commit();
        startActivity(tfd);
    }
}