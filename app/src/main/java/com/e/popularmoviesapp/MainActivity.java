package com.e.popularmoviesapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.popularmoviesapp.utilities.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.indeterminateBar)
    ProgressBar mProgressBar;

    String popularMovies;
    String topRatedMovies;

    ArrayList<Movie> mPopularList;
    ArrayList<Movie> mTopRatedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public class FetchMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            new FetchMovies().execute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            popularMovies = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key"+
                    "65e9d14557406c4d0bed5311f2ffa2d";
            topRatedMovies = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key" +
                    "65e9d14557406c4d0bed5311f2ffa2d";

            mPopularList = new ArrayList<>();
            mTopRatedList = new ArrayList<>();

            try {
                if (NetworkUtils.netWorkStatus(MainActivity.this)){
                    mPopularList = NetworkUtils.fetchData(popularMovies);
                    mTopRatedList = NetworkUtils.fetchData(topRatedMovies);
                }else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }


}