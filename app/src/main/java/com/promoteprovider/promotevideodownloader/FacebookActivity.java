package com.promoteprovider.promotevideodownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.promoteprovider.promotevideodownloader.databinding.ActivityFacebookBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FacebookActivity extends AppCompatActivity {
    private ActivityFacebookBinding binding;
    private FacebookActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_facebook);
        activity = this;

        binding.Download.setOnClickListener(view -> {
            getFaceBookData();
        });

    }

    private void getFaceBookData() {
        URL url = null;
        try {
            url = new URL(binding.fbUrl.getText().toString());
            String host = url.getHost();
            if (host.contains("facebook.com")){
                new callGetFbData().execute(binding.fbUrl.getText().toString());

            }
            else Toast.makeText(activity, "url is invalid", Toast.LENGTH_SHORT).show();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    class callGetFbData extends AsyncTask<String,Void, Document>{
        Document fbDoc;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                fbDoc = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fbDoc;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video\"]")
                    .last().attr("content");
            if (!videoUrl.equals(""))
                Util.download(videoUrl,Util.RootDirectoryFacebook,activity,"Facebook " + System.currentTimeMillis()+".mp4");
        }
    }
}