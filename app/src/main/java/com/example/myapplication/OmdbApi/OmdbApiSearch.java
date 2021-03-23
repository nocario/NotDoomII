package com.example.myapplication.OmdbApi;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OmdbApiSearch {

    private static final String URL_S = "http://www.omdbapi.com/?apikey=KEY&s=TITLE&type=movie";
    private static final String URL_T = "http://www.omdbapi.com/?apikey=KEY&i=TITLE&type=movie";

    private final String title;
    private final String apiKey;
    private HttpURLConnection connection;

    BufferedReader buffer;

    private void sendGetRequest(String requestUrl) {
        try {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            Log.d("MSG", requestUrl);

            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);

            buffer = new BufferedReader(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getMovies() throws IOException, JSONException {
        String request = URL_S.replaceAll("TITLE", title);
        request = request.replaceAll("KEY", apiKey);

        OkHttpClient client = new OkHttpClient();
        Request request1 = new Request.Builder().url(request).build();

        try {
            Response response = client.newCall(request1).execute();
            //Log.d("MSG_OKHTTP", response.body().string());
            String filmResponse = response.body().string();
            Log.d("MSG_OKHTTP", filmResponse);
            JSONObject jsonFilm = new JSONObject(filmResponse);
            JSONArray jsonFilmArray = jsonFilm.getJSONArray("Search");
            return jsonFilmArray;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        /*
        sendGetRequest(request);

        while ((line = buffer.readLine()) != null) {
            res.append(line);
        }

        Log.i("MSG_FILM", res.toString());

        JSONObject jsonTxt = new JSONObject(res.toString());

        JSONArray jsonArray = jsonTxt.getJSONArray("Search");

        ArrayList<String> list = new ArrayList<>();
        ArrayList<JSONObject> listJson = new ArrayList<>();

        for (int i = 0; i<jsonArray.length(); i++) {
            JSONObject j = (JSONObject) jsonArray.get(i);
            listJson.add(j);
            list.add(j.get("Title").toString());
            Log.i("msg", "Title: " + j.get("Title").toString());
        }

        buffer.close();
        connection.disconnect();

        //return listJson;*/

    }

    public JSONObject getMovie() throws IOException, JSONException {
        String line;
        StringBuilder res = new StringBuilder();
        String request = URL_T.replaceAll("TITLE", title);
        request = request.replaceAll("KEY", apiKey);

        sendGetRequest(request);

        while ((line = buffer.readLine()) != null) {
            res.append(line);
        }

        Log.i("MSG_FILM", res.toString());

        JSONObject jsonTxt = new JSONObject(res.toString());

        buffer.close();
        connection.disconnect();

        return jsonTxt;

    }

    public OmdbApiSearch(String title, String apiKey) {
        this.title = title;
        this.apiKey = apiKey;
    }
}
