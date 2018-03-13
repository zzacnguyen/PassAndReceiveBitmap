package com.example.zzacn.imagetest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static com.example.zzacn.imagetest.Main2Activity.bitmapArrayList;


public class MainActivity extends AppCompatActivity {
    Button btnActivity2, btnShowTextView;
    TextView textView;
    ImageView imageBitmap, imageBitmap2, imageBitmap3;
    MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

    public static final String URL_HOST = "http://192.168.1.114/doan3_canthotour/public/";
    public static final String URL_POST_IMAGE = "upload-image/";
    public static final ArrayList<String> JSON_ADD_IMAGE = new ArrayList<>(Arrays.asList("\"banner\"", "\"details1\"", "\"details2\""));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActivity2 = findViewById(R.id.btnActivity2);
        btnShowTextView = findViewById(R.id.btnShowTextView);
        imageBitmap = findViewById(R.id.imageViewBitmap);
        imageBitmap2 = findViewById(R.id.imageBitmap2);
        imageBitmap3 = findViewById(R.id.imageBitmap3);

        btnActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        btnShowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream ban = new ByteArrayOutputStream();
                bitmapArrayList.get(0).compress(Bitmap.CompressFormat.JPEG, 100, ban);
                ContentBody contentBanner = new ByteArrayBody(ban.toByteArray(), "");

                ByteArrayOutputStream de1 = new ByteArrayOutputStream();
                bitmapArrayList.get(1).compress(Bitmap.CompressFormat.JPEG, 100, de1);
                ContentBody contentDetails1 = new ByteArrayBody(de1.toByteArray(), "");

                ByteArrayOutputStream de2 = new ByteArrayOutputStream();
                bitmapArrayList.get(2).compress(Bitmap.CompressFormat.JPEG, 100, de2);
                ContentBody contentDetails2 = new ByteArrayBody(de2.toByteArray(), "");

                reqEntity.addPart("banner", contentBanner);
                reqEntity.addPart("details1", contentDetails1);
                reqEntity.addPart("đetails2", contentDetails2);

                try {
                    String s = new PostImage().execute(URL_HOST + URL_POST_IMAGE + 1).get();
                    System.out.println(s);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                imageBitmap.setImageBitmap(bitmapArrayList.get(0));
                imageBitmap2.setImageBitmap(bitmapArrayList.get(1));
                imageBitmap3.setImageBitmap(bitmapArrayList.get(2));

            }
        });
    }

    private class PostImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpRequestAdapter.httpPostImage(strings[0], reqEntity);
        }
    }
}
