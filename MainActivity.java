package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText input_name;
    Button startNewGame;
    Button startUserName;
    SharedPreferences sp;
    boolean playMusic= true;
    boolean english;
    boolean newGame;
    MediaPlayer musicBcg;
    boolean changelangh= false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp= getSharedPreferences("details", MODE_PRIVATE);

        // musicBcg = MediaPlayer.create(MainActivity.this, R.raw.bcg);
        english= sp.getBoolean("english", false);

        if(english)
        {
            setLocale("hi");
        }
        else {
            setLocale("he");
        }
        setContentView(R.layout.activity_main);
        MusicManger.SoundPlayer(MainActivity.this,R.raw.bcg);
        //  MusicManger.player.pause();
        //input_name=findViewById(R.id.text_name);
        startNewGame=findViewById(R.id.start_new);
        startUserName= findViewById(R.id.start_user);



        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  String user_name=input_name.getText().toString();
                Intent gonext = new Intent(MainActivity.this, Road.class);
                //   gonext.putExtra("user_name",user_name);
                newGame= true;
                startActivity(gonext);
                finish();
            }
        });
        startUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gonext = new Intent(MainActivity.this, Login.class);
                //   gonext.putExtra("user_name",user_name);
                newGame= false;
                startActivity(gonext);
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_menu,menu);
        MenuItem music= menu.findItem(R.id.music);
        MenuItem langh= menu.findItem(R.id.language);
        playMusic= sp.getBoolean("music", true);
        english= sp.getBoolean("english", true);
        //Toast.makeText(this, ""+playMusic, Toast.LENGTH_SHORT).show();
        if(playMusic)
        {
            music.setTitle("on");
            music.setIcon(R.drawable.speker);
            MusicManger.player.start();
            // musicBcg.start();
        }
        else
        {
            music.setTitle("off");
            music.setIcon(R.drawable.mute);
            MusicManger.player.pause();
        }
        if(english)
        {
            setLocale("he");
            langh.setTitle("Hebrew");
            langh.setIcon(R.drawable.hebrew);
        }
        else
        {
            setLocale("hi");
            langh.setTitle("English");
            langh.setIcon(R.drawable.english);
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
                item.setIcon(R.drawable.speker);
                item.setTitle("on");
            }
            else
            {
                playMusic= false;
                //musicBcg.stop();
                item.setIcon(R.drawable.mute);
                item.setTitle("off");
                MusicManger.player.pause();
            }
            // Toast.makeText(this, ""+music, Toast.LENGTH_SHORT).show();

        }
        else if(item.getItemId() == R.id.language)
        {
            changelangh= true;
            // musicBcg.stop();
            if(!english)
            {
                english= true;
                item.setIcon(R.drawable.hebrew);
                setLocale("he");
                Intent refresh = new Intent(this, MainActivity.class);
                finish();
                startActivity(refresh);
                item.setTitle("Hebrew");
            }
            else
            {
                english= false;
                item.setIcon(R.drawable.english);
                setLocale("hi");
                Intent refresh = new Intent(this, MainActivity.class);
                finish();
                startActivity(refresh);
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
        SharedPreferences.Editor editor= sp.edit();
        // editor.putString("user_name",input_name.getText().toString());
        editor.putBoolean("music", playMusic);
        editor.putBoolean("english", english);
        editor.putBoolean("newGame", newGame);
        MusicManger.player.pause();
        if(newGame)
        {
            editor.putInt("worldNew",1);
        }
        editor.commit();
    }

}