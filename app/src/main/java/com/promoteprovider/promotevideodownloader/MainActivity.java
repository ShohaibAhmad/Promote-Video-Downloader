package com.promoteprovider.promotevideodownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.promoteprovider.promotevideodownloader.databinding.ActivityMainBinding;


import org.jetbrains.annotations.NotNull;

import java.util.List;
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


        checkPermission();
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
}