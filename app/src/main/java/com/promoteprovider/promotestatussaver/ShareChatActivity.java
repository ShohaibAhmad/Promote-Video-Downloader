package com.promoteprovider.promotestatussaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.promoteprovider.promotestatussaver.databinding.ActivityShareChatBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShareChatActivity extends AppCompatActivity {
    private ActivityShareChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_share_chat);
        MobileAds.initialize(this, initializationStatus -> {
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        binding.Download.setOnClickListener(view -> {
            getShareChatData();
        });
    }

    private void getShareChatData() {
        URL url = null;
        try {
            url = new URL(binding.ShareChatUrl.getText().toString());
            String host = url.getHost();
            if (host.contains("sharechat"))
                new callGetChareChatData().execute(binding.ShareChatUrl.getText().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    class callGetChareChatData extends AsyncTask<String,Void, Document>{
        Document scDocument;

        @Override
        protected Document doInBackground(String... strings) {
            try {
                scDocument = Jsoup.connect(strings[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return scDocument;
        }

        @Override
        protected void onPostExecute(Document document) {
            String videoUrl = document.select("meta[property=\"og:video:secure_url\"]")
                    .last().attr("content");
            if (!videoUrl.equals(""))
                Util.download(videoUrl, Util.RootDirectoryShareChat,ShareChatActivity.this,"ShareChat "+ System.currentTimeMillis()+".mp4");
        }
    }
}