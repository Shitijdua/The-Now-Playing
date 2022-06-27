package com.skiptheweb.thenowplaying;

import static com.skiptheweb.thenowplaying.MainActivity.customBaseAdapter;
import static com.skiptheweb.thenowplaying.MainActivity.movieList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

public class MovieActivity extends AppCompatActivity {

    int MovieTitleIndex;
    TextView titleTextView;
    TextView overviewTextView;
    TextView popularityTextView;
    TextView voteAverageTextView;
    ImageView movieImage;
    String result = "";
    String title = "";
    String overview = "";
    static String popularity = "";
    String voteAverage = "";
    Double finalVoteAverage;
    String backDropPath = "";
    static String resource = "";
    ImageView movie_imageview;
    Bitmap res;
    static boolean likedMovie = false;

    public Bitmap downloadImage(View view) {
        ImageDownloader task = new ImageDownloader();
        Bitmap myImage = null;


        try {
            myImage = task.execute(resource).get();
            Log.i("myimg", String.valueOf(myImage));
            movieImage.setImageBitmap(myImage);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return myImage;



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        titleTextView = findViewById(R.id.titleTextView);
        overviewTextView = findViewById(R.id.overviewTextView);
        popularityTextView = findViewById(R.id.popularityTextView);
        voteAverageTextView = findViewById(R.id.voteAverageTextView);
        movieImage = findViewById(R.id.movieImage);
        movie_imageview = findViewById(R.id.movie_imageview);

        Intent intent = getIntent();
        MovieTitleIndex = intent.getIntExtra("MovieTitleIndex", -1);
        title = intent.getStringExtra("title");
        overview = intent.getStringExtra("overview");
        popularity = intent.getStringExtra("popularity");
        voteAverage = intent.getStringExtra("vote_average");
        backDropPath = intent.getStringExtra("backdrop_path");
        Log.i("item clicked", String.valueOf(MovieTitleIndex));
        Log.i("Title clicked", title);
        Log.i("Overview present", overview);
        Log.i("Popularity", popularity);
        Log.i("vote average", voteAverage);
        Log.i("BackdropPath is", backDropPath);
        titleTextView.setText(title);
        overviewTextView.setText(overview);
        popularityTextView.setText("Popularity: " + popularity);
        //finalVoteAverage = Double.parseDouble(voteAverage)*10;
        finalVoteAverage = Double.parseDouble(voteAverage);
        finalVoteAverage = finalVoteAverage*10;

        voteAverageTextView.setText(finalVoteAverage + "%");
        resource = "https://image.tmdb.org/t/p/w500" + backDropPath;
        res = downloadImage(movieImage);



        //movie_imageview.setImageBitmap(res);



        }



        public static class ImageDownloader extends AsyncTask<String, Void, Bitmap> { //problem

            @Override
            protected Bitmap doInBackground(String... urls) {

                try {
                    URL url = new URL(urls[0]); //problem

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.connect();

                    InputStream in = connection.getInputStream();

                    Bitmap myBitmap = BitmapFactory.decodeStream(in);

                    return myBitmap;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
}