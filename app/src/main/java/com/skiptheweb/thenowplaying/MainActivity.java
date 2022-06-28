package com.skiptheweb.thenowplaying;

import static com.skiptheweb.thenowplaying.CustomBaseAdapter.likeMovies;
import static com.skiptheweb.thenowplaying.LikedMovieActivity.new_liked_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

   public static ArrayList<String> movieList = new ArrayList<>();
   public static ArrayList<String> overviewList = new ArrayList<>();
    public static ArrayList<String> popularityList = new ArrayList<>();
    public static ArrayList<String> voteAverageList = new ArrayList<>();
    public static ArrayList<String> backDropPathList = new ArrayList<>();
    public ArrayList<String> likedMoviesList = new ArrayList<>();
    public static ArrayList<String> popularMovieList = new ArrayList<>();
    ArrayList<String> popularMovies = new ArrayList<>();
    ArrayList<Double> pops = new ArrayList<>();





    TextView nextPage;

    ArrayAdapter arrayAdapter;
    int pageNumber = 1;
    static String result = "";
    static String title = "";
    static String popularity;
    static String overview = "";
    static String voteAverage = "";
    static String backDropPath = "";
    static CustomBaseAdapter customBaseAdapter;
    CustomBaseAdapter popularCustomBaseAdapter;
    ListView movieListView;
    ImageView imageIcon;
    ImageView heart;

    public static class DownloadTask extends AsyncTask<String, Void, String > {

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection  = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data!= -1) {
                    char current  = (char) data;
                    result+= current;
                    data = reader.read();
                }

                return result;


            } catch (Exception e) {
                e.printStackTrace();

                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String newReleases = jsonObject.getString("results");

                JSONArray arr = new JSONArray(newReleases);


                for (int i = 0; i<arr.length();i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    title = jsonPart.getString("title");
                    overview = jsonPart.getString("overview");
                    popularity = jsonPart.getString("popularity");
                    voteAverage = jsonPart.getString("vote_average");
                    backDropPath = jsonPart.getString("backdrop_path");



                    Log.i("title", title);
                    Log.i("overview", overview);
                    Log.i("backdroppath", backDropPath);
                    movieList.add(title);
                    overviewList.add(overview);
                    popularityList.add(popularity);
                    voteAverageList.add(voteAverage);
                    backDropPathList.add(backDropPath);
                    popularMovieList.add(title);
                    customBaseAdapter.notifyDataSetChanged();

                }

            } catch (Exception e){
                e.printStackTrace();
            }



            Log.i("popularitylist", String.valueOf(popularityList));



        }


    }



    public void nextPage(View view) {

        customBaseAdapter.notifyDataSetChanged();

        result = "";

        ++pageNumber;
        DownloadTask nextPageTask = new DownloadTask();

        try {

            result = nextPageTask.execute("https://api.themoviedb.org/3/movie/now_playing?api_key=9d0e3e33437b228d3184927838d32b9b&language=en-US&page=" + pageNumber).get();
            Log.i("newresult", result);

            customBaseAdapter.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sortByPopularity(View view){


        Double highestPop = 0.0;
        int positionIndex = 0;

        ArrayList<Integer> popularMoviesIndex = new ArrayList<>();


        Log.i("popularitylistt", String.valueOf(MainActivity.popularityList));

        for (int i = 0; i < popularityList.size(); i++) {
            pops.add(Double.parseDouble(popularityList.get(i)));
            Log.i("pops", String.valueOf(pops.get(i)));


        }

        for (int i = 0; i < pops.size(); i++) {

                highestPop = pops.get(i);
                Collections.sort(pops, Collections.reverseOrder());


                Log.i("popsDesc", String.valueOf(pops.get(i)));


        }

        for (int i = 0; i< popularityList.size(); i++) {
            for (int y = 0; y < pops.size(); y++) {
                if (popularityList.get(y).equals(String.valueOf(pops.get(i)))) {

                    positionIndex = i;
                    Log.i("positionIndex", String.valueOf(positionIndex));
                }
            }
        }


        Toast.makeText(this, "Movies sorted based on popularity", Toast.LENGTH_SHORT).show();

        int highestPosition = 0;
        Log.i("button pressed", "popular");

        for (int i = 0; i < movieList.size(); i++) {
            movieList.set(0, Collections.max(movieList));
        }
        customBaseAdapter.notifyDataSetChanged();

        // if (Integer.parseInt(popularityList.get(0)) > Integer.parseInt(popularityList.get(1))){
        //Log.i("popular movie", popularityList.get(0));


        // popularMovieList.add(popularityList.get(0));
        // customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),popularMovieList, backDropPathList, backDropPathList );
    movieListView.setAdapter(popularCustomBaseAdapter);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // testing listview
      //  movieList.add("The Jurrasic Park");
      //  movieList.add("Kal Ho Na Ho");

        movieListView = findViewById(R.id.movieListView);

        customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),movieList, backDropPathList, backDropPathList );
        popularCustomBaseAdapter = new CustomBaseAdapter(getApplicationContext(),movieList,backDropPathList,backDropPathList);
        customBaseAdapter.notifyDataSetChanged();

        nextPage = findViewById(R.id.nextPage);
        imageIcon = findViewById(R.id.imageIcon);
        heart = findViewById(R.id.heart);



       /* nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("Nextpg", "clicked");


                ++pageNumber;
                Log.i("Page nuMBER", String.valueOf(pageNumber));
                DownloadTask nextPageTask = new DownloadTask();
                try {

                    result = nextPageTask.execute("https://api.themoviedb.org/3/movie/now_playing?api_key=9d0e3e33437b228d3184927838d32b9b&language=en-US&page=" + pageNumber).get();
                    customBaseAdapter.notifyDataSetChanged();
                    Log.i("newresult", result);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }); */


        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieList);
       //movieListView.setAdapter(arrayAdapter);





        DownloadTask task = new DownloadTask();
        String result = null;
        movieListView.setAdapter(customBaseAdapter);



        try {
            result = task.execute("https://api.themoviedb.org/3/movie/now_playing?api_key=9d0e3e33437b228d3184927838d32b9b&language=en-US&page=" + pageNumber).get();
            Log.i("Result", result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.i("displaylikedmovies", "yes");

                Intent intent = new Intent(getApplicationContext(), LikedMovieActivity.class);

                intent.putExtra("new_liked_movies", new_liked_movies);
                    startActivity(intent);

            }

            });

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), MovieActivity.class );
                intent.putExtra("MovieTitleIndex", position);
                intent.putExtra("title", movieList.get(position));
                intent.putExtra("overview",overviewList.get(position));
                intent.putExtra("popularity", popularityList.get(position));
                intent.putExtra("vote_average", voteAverageList.get(position));
                intent.putExtra("backdrop_path", backDropPathList.get(position));
                startActivity(intent);


                }

        });


    }

}