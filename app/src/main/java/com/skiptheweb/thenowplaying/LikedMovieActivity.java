package com.skiptheweb.thenowplaying;

import static com.skiptheweb.thenowplaying.CustomBaseAdapter.likeMovies;
import static com.skiptheweb.thenowplaying.MainActivity.customBaseAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class LikedMovieActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    String liked_movies = "";

    ListView likedMovieList;

    ArrayAdapter arrayAdapter;


    static ArrayList<String> new_liked_movies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_movie);



        likedMovieList = findViewById(R.id.likedMovieList);

        //to save liked movie list in shared preference using Hash Set
        sharedPreferences = getApplicationContext().getSharedPreferences("com.skiptheweb.thenowplaying", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("likedmovies", null);

        if (set == null) {
            return;

        } else  {

            likeMovies = new ArrayList(set);

        }

            Intent intent = getIntent();
            liked_movies = intent.getStringExtra("liked_movies");
            Log.i("Likeds", String.valueOf(CustomBaseAdapter.likeMovies));




            //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new_liked_movies);

            //customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), MainActivity.movieList, MainActivity.backDropPathList, MainActivity.backDropPathList );
            //customBaseAdapter.notifyDataSetChanged();
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, likeMovies);

            likedMovieList.setAdapter(arrayAdapter);
        }

    }
