package com.example.fengzi113.mynewsapp;

import android.support.design.widget.TabLayout;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String TAG = "QueryUtils";

    private QueryUtils() {
    }

    public static ArrayList<News> extractBooks(String jsonResponse) {

        ArrayList<News> news = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject currentNew = results.getJSONObject(i);
                String title = currentNew.getString("webTitle");
                String time = currentNew.getString("webPublicationDate");
                String url = currentNew.getString("webUrl");

                News new1 = new News(title, time, url);
                news.add(new1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

    private static URL creatUrl(String stringurl) {

        URL url = null;

        try {
            url = new URL(stringurl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "problem - creatUrl", e);
        }
        return url;

    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(50000);
            urlConnection.setConnectTimeout(55000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFormStream(inputStream);

            } else {

                Log.e(TAG, "Error response code" + urlConnection.getResponseCode());

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {

                urlConnection.disconnect();

            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFormStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {

                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }

    public static List<News> fetchBookDate(String requestUrl) {

        URL url = creatUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractBooks(jsonResponse);

    }
}
