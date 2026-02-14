package com.example.nasaapodapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle, tvDate, tvExplanation;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvExplanation = findViewById(R.id.tvExplanation);
        imageView = findViewById(R.id.imageView);

        new LoadApodTask().execute();
    }

    private class LoadApodTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                NasaApodClient client = new NasaApodClient();
                return client.getApodData();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject apodData) {

            if (apodData == null) {
                tvTitle.setText("Error cargando datos");
                return;
            }

            try {
                String imageUrl = apodData.getString("url");
                String title = apodData.getString("title");
                String date = apodData.getString("date");
                String explanation = apodData.getString("explanation");

                tvTitle.setText(title);
                tvDate.setText(date);
                tvExplanation.setText(explanation);

                Glide.with(MainActivity.this)
                        .load(imageUrl)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
