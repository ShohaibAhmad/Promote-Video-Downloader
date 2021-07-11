package com.promoteprovider.promotestatussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.promoteprovider.promotestatussaver.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {
        private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        binding.WhatsUp.setOnClickListener(view -> {
            showAds();
            startActivity(new Intent(this,WhatsUpActivity.class));
        });

        binding.Facebook.setOnClickListener(view -> {
            showAds();
            startActivity(new Intent(this,FacebookActivity.class));
        });

   binding.ShareChat.setOnClickListener(view -> {
       showAds();
            startActivity(new Intent(this,ShareChatActivity.class));
        });

   binding.Instagram.setOnClickListener(view -> {
       showAds();
            startActivity(new Intent(this,InstagramActivity.class));
        });







        checkPermission();
    }
        private void checkPermission(){
            Dexter.withContext(this)
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).withListener(new MultiplePermissionsListener() {
                @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (!report.areAllPermissionsGranted()){
                        checkPermission();
                    }
                }
                @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
            }).check();

        }
        private void showAds(){
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull @NotNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    interstitialAd.show(MainActivity.this);
                    Toast.makeText(MainActivity.this, "Banner ad is Loaded", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Toast.makeText(MainActivity.this, "Banner ad Load Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
}