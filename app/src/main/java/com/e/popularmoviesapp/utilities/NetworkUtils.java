package com.e.popularmoviesapp.utilities;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.e.popularmoviesapp.Movie;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static ArrayList<Movie> fetchData(String url) throws IOException{
        ArrayList<Movie> movies = new ArrayList<Movie>();
        try {
            URL new_url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)new_url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            String results = IOUtils.toString(inputStream);

            parseJson(results,movies);
            inputStream.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        return movies;
    }

    private static void parseJson(String data, ArrayList<Movie> list) {

        try {
            JSONObject mainObject = new JSONObject(data);

            JSONArray resArray = mainObject.getJSONArray("results");
            //get the result object
            for (int i = 0; i<resArray.length(); i++){
                JSONObject jsonObject = resArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setId(jsonObject.getInt("id"));
                movie.setVoteAverage(jsonObject.getInt("vote_average"));
                movie.setVoteCount(jsonObject.getInt("vote_count"));

                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setTitle(jsonObject.getString("title"));
                movie.setPopularity(jsonObject.getDouble("popularity"));

                movie.setBackdropPath(jsonObject.getString("backdrop_path"));
                movie.setOverview(jsonObject.getString("overview"));

                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setPosterPath(jsonObject.getString("poster_path"));

                list.add(movie);
            }
        }catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG,"Error occured dusing JSON parsing",e);
        }
    }
    public static Boolean netWorkStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
