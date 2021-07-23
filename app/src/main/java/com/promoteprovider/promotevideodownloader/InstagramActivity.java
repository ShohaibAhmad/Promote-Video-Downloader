package com.promoteprovider.promotevideodownloader;



import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.promoteprovider.promotevideodownloader.databinding.ActivityInstagramBinding;


public class InstagramActivity extends AppCompatActivity {
    private ActivityInstagramBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_instagram);
        binding.Download.setOnClickListener(view -> {
            String getUrl = binding.InstaUrl.getText().toString();
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getUrl));
            String title = URLUtil.guessFileName(getUrl,null,null);
            request.setTitle(title);
            request.setDescription("Downloading File Please wait...");
            String cookie = CookieManager.getInstance().getCookie(getUrl);
            request.addRequestHeader("cookie",cookie);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
            DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(InstagramActivity.this, "Downloading Started", Toast.LENGTH_SHORT).show();
//            Toast.makeText(InstagramActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
        });
    }

}