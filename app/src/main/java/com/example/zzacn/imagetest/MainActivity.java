package com.example.zzacn.imagetest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.zzacn.imagetest.Main2Activity.bitmapArrayList;


public class MainActivity extends AppCompatActivity {
    Button btnActivity2, btnShowTextView;
    TextView textView;
    ImageView imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActivity2 = findViewById(R.id.btnActivity2);
        btnShowTextView = findViewById(R.id.btnShowTextView);
        textView = findViewById(R.id.textView);
        imageBitmap = findViewById(R.id.imageViewBitmap);

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

                textView.setText(contentBanner.toString());
                imageBitmap.setImageBitmap(bitmapArrayList.get(0));
            }
        });
    }
}
