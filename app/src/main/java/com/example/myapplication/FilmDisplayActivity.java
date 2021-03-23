package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class FilmDisplayActivity extends AppCompatActivity {

    ImageView imageViewFilmLayout;
    TextView textFilmLayoutTitle;
    TextView textFilmLayoutRuntime;
    TextView textFilmLayoutYear;
    TextView textFilmLayoutCast;
    TextView textFilmLayoutPlot;
    TextView textFilmLayoutGenre;
    TextView textFilmLayoutCountry;
    TextView textFilmLayoutDirector;
    TextView textFilmLayoutWriter;
    TextView textFilmLayoutLanguage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_layout);
        imageViewFilmLayout = findViewById(R.id.imageViewFilmLayoutPoster);
        textFilmLayoutCast = findViewById(R.id.textFilmLayoutCast);
        textFilmLayoutCountry = findViewById(R.id.textFilmLayoutCountry);
        textFilmLayoutDirector = findViewById(R.id.textFilmLayoutDirector);
        textFilmLayoutTitle = findViewById(R.id.textFilmLayoutTitle);
        textFilmLayoutRuntime = findViewById(R.id.textFilmLayoutRuntime);
        textFilmLayoutYear = findViewById(R.id.textFilmLayoutYear);
        textFilmLayoutPlot = findViewById(R.id.textFilmLayoutPlot);
        textFilmLayoutGenre = findViewById(R.id.textFilmLayoutGenre);
        textFilmLayoutWriter = findViewById(R.id.textFilmLayoutWriter);
        textFilmLayoutLanguage = findViewById(R.id.textFilmLayoutLanguage);

        Intent intent = this.getIntent();

        try {

            JSONObject json = new JSONObject(intent.getStringExtra("json"));
            /*if (intent.getByteArrayExtra("ByteArray") != null) {
                byte[] array = intent.getByteArrayExtra("ByteArray");
                Bitmap poster = BitmapFactory.decodeByteArray(array, 0, array.length);
                imageViewFilmLayout.setImageBitmap(poster);
            }*/

            Glide.with(this).load(json.get("Poster").toString()).into(imageViewFilmLayout);


            textFilmLayoutDirector.setText(json.get("Director").toString());
            textFilmLayoutCast.setText(json.get("Actors").toString());
            textFilmLayoutGenre.setText(json.get("Genre").toString());
            textFilmLayoutLanguage.setText(json.get("Language").toString());
            textFilmLayoutYear.setText(json.get("Year").toString());
            textFilmLayoutPlot.setText(json.get("Plot").toString());
            textFilmLayoutCountry.setText(json.get("Country").toString());
            textFilmLayoutRuntime.setText(json.get("Runtime").toString());
            textFilmLayoutWriter.setText(json.get("Writer").toString());
            textFilmLayoutTitle.setText(json.get("Title").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}