package athrow.rocks.android_news_app.data;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import athrow.rocks.android_news_app.BuildConfig;

/**
 * API
 * An API class to connect to the Guardian and get the results
 * Created by jose on 8/7/16.
 */
public final class API {
    private static final String API_KEY = BuildConfig.GUARDIAN_API_KEY;
    private static final String API_URL = "http://content.guardianapis.com/";
    private static final String apiSearchParam = "search?q=";
    private static final String apiOtherParams = "&show-tags=contributor&show-blocks=body:latest&show-elements=image";
    private static final String apikeyParam = "&api-key=";
    public static String callAPI(){
        String searchCriteria = "art%20history";
        String APIUrl = API_URL + apiSearchParam + searchCriteria + apiOtherParams + apikeyParam + API_KEY;
        Log.d("APIUrl", APIUrl);
        return httpConnect(APIUrl);
    }
    /**
     * httpConnect
     * This method handles communicating with the API and converting the input stream into a string
     * @return a json string to be used in a parsing method
     */
    private static String httpConnect(String APIurl) {
        String results = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            // Build the URL
            Uri builtUri = Uri.parse(APIurl).buildUpon().build();
            URL url = new URL(builtUri.toString());
            // Establish the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            //urlConnection.addRequestProperty("Authorization", apiKey);
            urlConnection.addRequestProperty("Content-Length", "0");
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                results = null;
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                results = null;
            }
            results = buffer.toString();
        } catch (IOException v) {
            results = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.i("json ", results);
        return results;
    }
}
