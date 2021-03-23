package com.example.myapplication.OmdbApi;

import android.util.Log;

import org.json.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OmdbApiSearch {

    private static final String URL_S = "http://www.omdbapi.com/?apikey=KEY&s=TITLE&type=movie";
    private static final String URL_T = "http://www.omdbapi.com/?apikey=KEY&i=TITLE&type=movie";

    private final String title;
    private final String apiKey;


    private JSONObject httpGetRequest(String Url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Url).build();

        try {
            Response response = client.newCall(request).execute();
            String filmResponse = response.body().string();
            Log.d("MSG_OKHTTPTEST", filmResponse);
            return new JSONObject(filmResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getMovies() throws JSONException {
        String request = URL_S.replaceAll("TITLE", title);
        request = request.replaceAll("KEY", apiKey);

        JSONObject jsonFilm = httpGetRequest(request);

        return jsonFilm.getJSONArray("Search");

    }

    public JSONObject getMovie() {
        String request = URL_T.replaceAll("TITLE", title);
        request = request.replaceAll("KEY", apiKey);

        return httpGetRequest(request);

    }

    public OmdbApiSearch(String title, String apiKey) {
        this.title = title.trim();
        this.apiKey = apiKey;
    }
}
