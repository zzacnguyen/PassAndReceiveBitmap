package com.example.zzacn.imagetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.entity.mime.MultipartEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpRequestAdapter {
    private static HttpURLConnection urlConnection;

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String httpPostImage(String urlString, MultipartEntity reqEntity) {
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.addRequestProperty("Content-length", reqEntity.getContentLength() + "");
            urlConnection.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());

            OutputStream os = urlConnection.getOutputStream();
            reqEntity.writeTo(urlConnection.getOutputStream());
            os.close();
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                in.close();

                return result.toString();
            } else {
                return "failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
