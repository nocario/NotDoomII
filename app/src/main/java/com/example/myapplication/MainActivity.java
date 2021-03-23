package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.OmdbApi.OmdbApiSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Request;


public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    LinearLayout l;
    RecyclerView recyclerView;
    private ArrayList<MovieData> movieData;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_film);

        l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);

        ImageButton btn = findViewById(R.id.searchButton);

        EditText txt = findViewById(R.id.editText);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layout);
        Log.d("testmsg", "juste pour voir");

        btn.setOnClickListener(v -> {
            String title = txt.getText().toString();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            ProgressDialog p = new ProgressDialog(this);
            p.setMessage("please wait");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("MSF", "this a thread message");
                    OmdbApiSearch o = new OmdbApiSearch(title, getResources().getString(R.string.ApiKey));
                    try {
                        JSONArray listJson = o.getMovies();
                        movieData = new ArrayList<>();

                        for (int i = 0; i<listJson.length(); i++) {
                            movieData.add(new MovieData((JSONObject) listJson.get(i)));
                        }

                        MovieAdapter adapter = new MovieAdapter(MainActivity.this, movieData, MainActivity.this);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                recyclerView.setAdapter(adapter);

                                p.hide();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            //String title = txt.getText().toString();
            //AsyncTaskOmdb asyncTaskOmdb = new AsyncTaskOmdb();
            //asyncTaskOmdb.execute(title);

        });
    }

    @Override
    public void onItemClick(int position) {
        Log.d("MSGTHREAD","clicked on: " + movieData.get(position).title);
        /*OmdbApiSearch o = new OmdbApiSearch(movieData.get(position).imdbID, "63f3e471");
        AsyncTaskOmdbFilm asyncTaskOmdbFilm = new AsyncTaskOmdbFilm();
        asyncTaskOmdbFilm.execute(o);*/
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                OmdbApiSearch o = new OmdbApiSearch(movieData.get(position).imdbID, getResources().getString(R.string.ApiKey));
                JSONObject json = o.getMovie();
                intent = new Intent(MainActivity.this, FilmDisplayActivity.class);
                intent.putExtra("json", json.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                });

            }
        });
    }
/*
    private class AsyncTaskOmdbFilm extends AsyncTask<OmdbApiSearch, String, String> {

        ProgressDialog p;

        protected void onPreExecute() {
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("please wait");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(OmdbApiSearch... omdb) {
            JSONObject json = omdb[0].getMovie();
            intent = new Intent(MainActivity.this, FilmDisplayActivity.class);
            intent.putExtra("json", json.toString());

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            //intent = new Intent(MainActivity.this, FilmDisplayActivity.class);
            //p.hide();
            startActivity(intent);
            //startActivity(intent);
        }
    }

    private class AsyncTaskOmdb extends AsyncTask<String, String, MovieAdapter> {

        ProgressDialog p;

        @Override
        protected MovieAdapter doInBackground(String... strings) {
            OmdbApiSearch o = new OmdbApiSearch(strings[0], "63f3e471");
            try {
                JSONArray listJson = o.getMovies();
                movieData = new ArrayList<>();

                for (int i = 0; i<listJson.length(); i++) {
                    movieData.add(new MovieData((JSONObject) listJson.get(i)));
                }

                return new MovieAdapter(MainActivity.this, movieData, MainActivity.this);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("please wait");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected void onPostExecute(MovieAdapter adapter) {
            super.onPostExecute(adapter);

            if (adapter==null) {
                //Toast.makeText(MainActivity.this, "no movie found", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_LONG).show();
                p.hide();
                return;
            }
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
            LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(adapter);

            p.hide();
            Log.d("MSG_FROM_ASYNC", "fonctionne");

        }
    }
*/
}
