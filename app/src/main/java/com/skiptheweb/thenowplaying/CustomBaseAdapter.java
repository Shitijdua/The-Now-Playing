package com.skiptheweb.thenowplaying;

import static androidx.core.graphics.drawable.IconCompat.*;
import static com.skiptheweb.thenowplaying.MainActivity.backDropPath;
import static com.skiptheweb.thenowplaying.MainActivity.backDropPathList;
import static com.skiptheweb.thenowplaying.MainActivity.movieList;
import static com.skiptheweb.thenowplaying.MovieActivity.likedMovie;
import static com.skiptheweb.thenowplaying.MovieActivity.resource;
import static com.skiptheweb.thenowplaying.R.color.white;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> titles;
    ArrayList<String> backdropList;
    ArrayList<String> likeImageList;
    static ArrayList<String> likeMovies = new ArrayList<String>();
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, ArrayList<String> movieTitles, ArrayList<String> backdropImages, ArrayList<String> likeImages) {
        this.context = ctx;
        this.titles = movieTitles;
        this.backdropList = backdropImages;
        this.likeImageList = likeImages;
        inflater = LayoutInflater.from(ctx);

    }


    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        resource= "https://image.tmdb.org/t/p/w500" + backDropPathList.get(position);

        convertView = inflater.inflate(R.layout.list_view, null);
        TextView textView = (TextView) convertView.findViewById(R.id.movieTitle);
        ImageView movieImg = (ImageView) convertView.findViewById(R.id.movie_imageview);
        ImageView likeImg = (ImageView) convertView.findViewById(R.id.imageIcon);




//on clicking on heart for particular movie
        likeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context.getApplicationContext(), "Liked Movie" + position, Toast.LENGTH_SHORT).show();

                Log.i("moviedadded", movieList.get(position));

                //if liked movie is already in the list
                if (likeMovies.contains(movieList.get(position))) {

                    Toast.makeText(context.getApplicationContext(), "Already in the liked list", Toast.LENGTH_SHORT).show();
                    

                } else {
//add liked movie to the list
                    likeMovies.add(movieList.get(position));
                    Toast.makeText(context.getApplicationContext(), "Added to liked movies list", Toast.LENGTH_SHORT).show();

                }

                //retreive shared preference
                SharedPreferences sharedPreferences = context.getSharedPreferences("com.skiptheweb.thenowplaying", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(likeMovies);
                sharedPreferences.edit().putStringSet("likedmovies", set).apply();


                Log.i("movies psnd", String.valueOf(likeMovies));

                /*for (int i = 0; i < likeImageList.size(); i++) {

                    if (likeImageList.get(i).startsWith("/")) {

                        likeImageList.remove(likeImageList.get(i));


                    }
                } */


                notifyDataSetChanged();
                Log.i("likedmovielist", String.valueOf(likeImageList));

            }
        });


        


        textView.setText(titles.get(position));

        notifyDataSetChanged();

//to load image on custom adapter from API
        Picasso.with(context)
                .load(resource)
                .resize(50, 50) // here you resize your image to whatever width and height you like
                .into(movieImg);

        notifyDataSetChanged();



        //movieImg.setImageResource(Integer.parseInt(backdropList.get(position)));
        // likeImg.setImageResource(Integer.parseInt(backdropList.get(position)));

        return convertView;

    }





   /* public Bitmap downloadImage(View view) {
        MovieActivity.ImageDownloader task = new MovieActivity.ImageDownloader();
        Bitmap myImage = null;


        try {

            Log.i("res", resource);
            myImage = task.execute(resource).get();
            movieImg.setImageBitmap(myImage); //problem


        } catch (Exception e) {
            e.printStackTrace();
        }


        return myImage;
    }


int counter = 0;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View customView = layoutInflater.inflate(R.layout.my_row, parent, false);

        RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder();

        holder.name = (TextView)customView.findViewById(R.id.nameTextView);
        holder.picture = (ImageView)customView.findViewById(R.id.pictureImageView);

        holder.name.setText(allNames.get(position));
        holder.picture.setImageResource(allPictures.get(position));

        //This does not work ---> holder.name.setTag(counter);

        customView.setTag(Integer.valueOf(counter));

        counter ++;
        return customView;
    }

    */

}













