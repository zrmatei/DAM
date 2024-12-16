package com.example.finala.network;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {
    private HttpURLConnection urlConnection;
    private String url;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    public HttpManager(String url) {
        this.url = url;
    }

    public String call(){
        try {
            return getURL();
        } catch (IOException e) {
            Log.e("HttpManager", "Connection failed");
        }finally {
            closeConnection();
        }
        return null;
    }

    @NonNull
    private String getURL() throws IOException {
        urlConnection = (HttpURLConnection) new URL(url).openConnection();
        inputStream = urlConnection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder result = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            result.append(line);
        }
        return result.toString();
    }

    private void closeConnection() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            Log.e("HttpManager", "bufferedReader failed");
        }
        try {
            inputStreamReader.close();
        } catch (IOException e) {
            Log.e("HttpManager", "inputStreamReader failed");
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            Log.e("HttpManager", "inputStream failed");
        }
        urlConnection.disconnect();
    }
}
