package com.loop.finalnews;
import android.text.TextUtils;
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

public class QueryUtils {
    private static final String log = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    private static List<Model> Json(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<Model> news = new ArrayList<>();

        try {

            JSONObject j1 = new JSONObject(newsJSON);
            JSONObject j2 = j1.getJSONObject("response");
            JSONArray j3 = j2.getJSONArray("results");

            for (int i = 0; i < j3.length(); i++) {
                JSONObject news2 = j3.getJSONObject(i);
                String title = news2.getString("webTitle");
                String category = news2.getString("sectionName");
                String date = news2.getString("webPublicationDate");
                String url = news2.getString("webUrl");
                JSONArray tags = news2.getJSONArray("tags");
                String author = "";
                if (tags.length() != 0) {
                    JSONObject currentTag = tags.getJSONObject(0);
                    author = currentTag.getString("webTitle");
                } else author = "Anonymous";
                Model News = new Model(title, category, date, url, author);
                news.add(News);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Error", e);
        }
        return news;
    }

    public static List<Model> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String Json1 = null;
        try {
            Json1 = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(log, "Error", e);
        }

        List<Model> news = Json(Json1);
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(log, "Error ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String Json2 = "";

        if (url == null)
            return Json2;


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                Json2 = readFromStream(inputStream);
            } else Log.e(log, "Error" + urlConnection.getResponseCode());
        } catch (IOException e) {
            Log.e(log, "Error", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return Json2;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader input = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(input);
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                line = reader.readLine();
            }
        }
        return result.toString();
    }
}