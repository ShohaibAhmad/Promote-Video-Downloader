package com.promoteprovider.promotevideodownloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import com.facebook.ads.Ad;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import com.promoteprovider.promotevideodownloader.databinding.ActivityMainBinding;


import org.jetbrains.annotations.NotNull;



import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static com.facebook.ads.CacheFlag.ALL;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    private InterstitialAd adMobIntAd;
    private com.facebook.ads.InterstitialAd fbIntAd;
    String AdmobAdsShowValue="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
     //notification
        FirebaseMessaging.getInstance().subscribeToTopic("notification");

        AudienceNetworkAds.initialize(this);
//database
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("AdsController");
        dbRef.child("Admob").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()) {
                    AdmobAdsShowValue = snapshot.child("AdmobAdPermitted").getValue().toString();
                    //minor  changes here
                    if (AdmobAdsShowValue.equals("yes")){
                        LoadAdmobInterstitialAds();
                    }else {
                        LoadFbInterstitialAds();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        if (AdmobAdsShowValue.equals("yes")){
            LoadAdmobInterstitialAds();
        }
        else {
            LoadFbInterstitialAds();
        }

        binding.WhatsUp.setOnClickListener(view -> {
            if (adMobIntAd != null) {
                adMobIntAd.show(MainActivity.this);
                adMobIntAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Intent intent = new Intent(MainActivity.this, WhatsUpActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onAdShowedFullScreenContent() {
                        adMobIntAd = null;
                    }
                });
            } else{
                if (AdmobAdsShowValue.equals("no") && fbIntAd.isAdLoaded() && !fbIntAd.isAdInvalidated()) {
                    Intent intent = new Intent(MainActivity.this, WhatsUpActivity.class);
                    startActivity(intent);
                    fbIntAd.show();
                }else {
                    Intent intent = new Intent(MainActivity.this, WhatsUpActivity.class);

                    startActivity(intent);
                }
            }
        });

        binding.Facebook.setOnClickListener(view -> {
            if (adMobIntAd != null) {
                adMobIntAd.show(MainActivity.this);
                adMobIntAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Intent intent = new Intent(MainActivity.this, FacebookActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onAdShowedFullScreenContent() {
                        adMobIntAd = null;
                    }
                });
            } else{
                if (AdmobAdsShowValue.equals("no") && fbIntAd.isAdLoaded() && !fbIntAd.isAdInvalidated()) {
                    Intent intent = new Intent(MainActivity.this, FacebookActivity.class);
                    startActivity(intent);
                    fbIntAd.show();
                }else {
                    Intent intent = new Intent(MainActivity.this, FacebookActivity.class);

                    startActivity(intent);
                }
            }
        });

   binding.ShareChat.setOnClickListener(view -> {
       if (adMobIntAd != null) {
           adMobIntAd.show(MainActivity.this);
           adMobIntAd.setFullScreenContentCallback(new FullScreenContentCallback(){
               @Override
               public void onAdDismissedFullScreenContent() {
                   Intent intent = new Intent(MainActivity.this, ShareChatActivity.class);
                   startActivity(intent);
               }
               @Override
               public void onAdShowedFullScreenContent() {
                   adMobIntAd = null;
               }
           });
       } else{
           if (AdmobAdsShowValue.equals("no") && fbIntAd.isAdLoaded() && !fbIntAd.isAdInvalidated()) {
               Intent intent = new Intent(MainActivity.this, ShareChatActivity.class);
               startActivity(intent);
               fbIntAd.show();
           }else {
               Intent intent = new Intent(MainActivity.this, ShareChatActivity.class);

               startActivity(intent);
           }
       }
        });

   binding.File.setOnClickListener(view -> {
       if (adMobIntAd != null) {
           adMobIntAd.show(MainActivity.this);
           adMobIntAd.setFullScreenContentCallback(new FullScreenContentCallback(){
               @Override
               public void onAdDismissedFullScreenContent() {
                   Intent intent = new Intent(MainActivity.this, InstagramActivity.class);
                   startActivity(intent);
               }
               @Override
               public void onAdShowedFullScreenContent() {
                   adMobIntAd = null;
               }
           });
       } else{
           if (AdmobAdsShowValue.equals("no") && fbIntAd.isAdLoaded() && !fbIntAd.isAdInvalidated()) {
               Intent intent = new Intent(MainActivity.this, InstagramActivity.class);
               startActivity(intent);
               fbIntAd.show();
           }else {
               Intent intent = new Intent(MainActivity.this, InstagramActivity.class);

               startActivity(intent);
           }
       }
        });

   binding.About.setOnClickListener(view -> {
       if (adMobIntAd != null) {
           adMobIntAd.show(MainActivity.this);
           adMobIntAd.setFullScreenContentCallback(new FullScreenContentCallback(){
               @Override
               public void onAdDismissedFullScreenContent() {
                   Intent intent = new Intent(MainActivity.this, About.class);
                   startActivity(intent);
               }
               @Override
               public void onAdShowedFullScreenContent() {
                   adMobIntAd = null;
               }
           });
       } else{
           if (AdmobAdsShowValue.equals("no") && fbIntAd.isAdLoaded() && !fbIntAd.isAdInvalidated()) {
               Intent intent = new Intent(MainActivity.this, About.class);
               startActivity(intent);
               fbIntAd.show();
           }else {
               Intent intent = new Intent(MainActivity.this, About.class);

               startActivity(intent);
           }
       }
        });

   binding.Policy.setOnClickListener(view -> {

       if (adMobIntAd != null) {
           adMobIntAd.show(MainActivity.this);
           adMobIntAd.setFullScreenContentCallback(new FullScreenContentCallback(){
               @Override
               public void onAdDismissedFullScreenContent() {
                   Intent intent = new Intent(MainActivity.this, Policy.class);
                   startActivity(intent);
               }
               @Override
               public void onAdShowedFullScreenContent() {
                   adMobIntAd = null;
               }
           });
       } else{
           if (AdmobAdsShowValue.equals("no") && fbIntAd.isAdLoaded() && !fbIntAd.isAdInvalidated()) {
               Intent intent = new Intent(MainActivity.this, Policy.class);
               startActivity(intent);
               fbIntAd.show();
           }else {
               Intent intent = new Intent(MainActivity.this, Policy.class);

               startActivity(intent);
           }
       }
        });


        showPermissionDialog();
    }

    private void LoadFbInterstitialAds() {

        fbIntAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_int_ad));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                fbIntAd.loadAd();
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                fbIntAd.loadAd();

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        fbIntAd.loadAd(fbIntAd.buildLoadAdConfig()
                .withAdListener(interstitialAdListener)
                .withCacheFlags(ALL)
                .build());
    }
    @Override
    protected void onDestroy() {
        if (fbIntAd != null) {
            fbIntAd.destroy();
        }
        super.onDestroy();
    }

    private void LoadAdmobInterstitialAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.admob_int_ad), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                adMobIntAd = interstitialAd;
            }
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                adMobIntAd = null;
            }
        });
    }



    private void showPermissionDialog() {
        if (SDK_INT >= Build.VERSION_CODES.R) {

            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
                startActivityForResult(intent, 2000);
            } catch (Exception e) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2000);

            }

        } else
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 333);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(getApplicationContext(),
                    WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getApplicationContext(),
                    READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED &&
                    read == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 333) {
            if (grantResults.length > 0) {
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (read && write){

                }else {

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                } else {

                }
            }
        }
    }
}