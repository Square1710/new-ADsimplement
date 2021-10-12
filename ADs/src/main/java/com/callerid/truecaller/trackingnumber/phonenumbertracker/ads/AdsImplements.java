package com.callerid.truecaller.trackingnumber.phonenumbertracker.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.ads.mediation.facebook.FacebookAdapter;
import com.google.ads.mediation.facebook.FacebookExtras;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.Mrec;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;

import java.util.ArrayList;
import java.util.Random;


public class AdsImplements {
    static OnAdListner onAdListner;
    static OnInitialSucess onInitialSucess;
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;
    static boolean mybennerisredy = false;
    static Activity activity;
    private static boolean myinternet;
    static int i;
    static Dialog dialog;
    static int mfil=0;
    static int myrecount;
    static NativeAd nativeAd;
    static int Intertstialcount;


    IronSource ironSource ;

    private static InterstitialAd mInterstitialAd;
    private static InterstitialAd fbmInterstitialAd;
    static boolean isInterstitialReady;


    static boolean admobInterstitialReady =false;
    static boolean fbadmobInterstitialReady =false;
    static boolean unityInterstitialReady =false;
    static boolean ironsourceInterstitialReady = false;
    static boolean stretaappInterstitialReady = false;
    private static RewardedAd mRewardedAd;
    private static RewardedAd fbmRewardedAd;


    public static ArrayList<Integer> nativepriorityarrer =new ArrayList<>();
    public static  ArrayList<Integer> rewardpriorityarrer =new ArrayList<>();
    public static  ArrayList<Integer> bannerpriorityarrer =new ArrayList<>();
    public static  ArrayList<Integer> interpriorityarrer =new ArrayList<>();






    AdView adView;



    public static boolean testmode = true;

    static OnRewardAdListner onRewardAdListner;

    public static int myperindex;
    public static int myperindexnative;
    public static int myperindexInterstitial;
    public static int myrewardindex;

    public static interface OnAdListner {
        void OnClose();
    }

    public static interface OnRewardAdListner {
        void Onsuccess();
        void Onunsuccess();
        void OnMyerror();
    }


    public AdsImplements(Activity activity ,SharedPreferences preferences) {
        Log.e("inductyalshow","enter in constor class   =   ");
        this.preferences =preferences;
        editor = preferences.edit();

        String id =preferences.getString("finid","finid");
        String id1 =preferences.getString("startappid","finid");
        String id2 =preferences.getString("ironappkey","finid");
        String id3 =preferences.getString("unityappid","finid");

        Log.e("inductyalshow","id == == == == == == "+id+"  =  "+id1+"  =  "+id2+"  =  "+id3);

        OnAdListner onAdListner = null;

        this.activity = activity;

       /* preferences = activity.getSharedPreferences("mysession", Context.MODE_PRIVATE);
        editor = preferences.edit();*/

        StartAppSDK.init(activity, preferences.getString("startappid", ""), false);

        IronSource.init(activity, preferences.getString("ironappkey", "ironappkey"), IronSource.AD_UNIT.OFFERWALL, IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.REWARDED_VIDEO, IronSource.AD_UNIT.BANNER);

        UnityAds.initialize(activity, preferences.getString("unityappid", ""), preferences.getBoolean("unitytestmode", false));
        loderewardads(onAdListner,onRewardAdListner);

        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

    }


    public interface OnInitialSucess {
        void OnInitialSucess();
    }


    public static void BanerLoad(Activity activity, RelativeLayout Adlayout) {
        myperindex = 0;

        if (!isConnectionAvailable(activity)) {
            Toast.makeText(activity, "Check your Internet connection", Toast.LENGTH_SHORT).show();
        }
        else if(bannerpriorityarrer.size()!=0)
        {

            if (myperindex < bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 1) {
                LoadAdBanner(activity, Adlayout);
            } else if (myperindex < bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 2) {
                FbmodualLoadAdBanner(activity, Adlayout);
            } else if (myperindex < bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 3) {
                Unitybennerload(activity, Adlayout);
            } else if (myperindex < bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 4) {
                Ironsourbennerload(activity, Adlayout);
            } else if (myperindex < bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 5) {
                Startappybennerload(activity, Adlayout);
            } else if (myperindex < bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 6) {
                Qrecabennerload(activity, Adlayout);
            }
        }

    }

    public static void chakemybenner(Activity activity, RelativeLayout Adlayout) {

        myperindex++;
        Log.e("mydasinif", "indext  = LoadAdBanner   " + myperindex);


        if (myperindex <  bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 1) {
            LoadAdBanner(activity, Adlayout);
        } else if (myperindex <  bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 2) {
            FbmodualLoadAdBanner(activity, Adlayout);
        } else if (myperindex <  bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 3) {

            Unitybennerload(activity, Adlayout);
        } else if (myperindex <  bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 4) {
            Ironsourbennerload(activity, Adlayout);
        } else if (myperindex <  bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 5) {
            Startappybennerload(activity, Adlayout);
        } else if (myperindex <  bannerpriorityarrer.size() && bannerpriorityarrer.get(myperindex) == 6) {
            Qrecabennerload(activity, Adlayout);
        }


    }

    public static void LoadAdBanner(Activity activity, RelativeLayout Adlayout) {

        AdView adView = new AdView(activity);
        adView.setAdUnitId(preferences.getString("bid", ""));
        Adlayout.removeAllViews();
        Adlayout.addView(adView);

        adView.setAdSize(AdSize.BANNER);

        // AdSize adSize = getAdSize(activity, Adlayout);
        // AdSize adSize1 =AdSize.getLandscapeInterscrollerAdSize(AdmobActivity2.this,10);
        // adView.setAdSize(adSize1);

        AdRequest adRequest = new AdRequest.Builder().build();


        // Start loading the ad in the background.
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mybennerisredy = true;
                Log.e("mydasinif", "loadadmob  " + mybennerisredy);

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Adlayout.removeAllViews();
                Log.e("myadsload", "loadadmob  " + adError.getMessage());

                chakemybenner(activity, Adlayout);


            }

            @Override
            public void onAdOpened() {

                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    public static void FbmodualLoadAdBanner(Activity activity, RelativeLayout Adlayout) {

        AdView adView = new AdView(activity);
        adView.setAdUnitId(preferences.getString("mbannerid", ""));
        Adlayout.removeAllViews();
        Adlayout.addView(adView);

        adView.setAdSize(AdSize.BANNER);

        // AdSize adSize = getAdSize(activity, Adlayout);
        // AdSize adSize1 =AdSize.getLandscapeInterscrollerAdSize(AdmobActivity2.this,10);
        // adView.setAdSize(adSize1);

        AdRequest adRequest = new AdRequest.Builder().build();


        // Start loading the ad in the background.
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mybennerisredy = true;
                Log.e("myadsload", "madiation  =  " + mybennerisredy);
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {

                chakemybenner(activity, Adlayout);
                Adlayout.removeAllViews();
                Log.e("myadsload", "madiation  ==  " + adError.getMessage());


            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    public static void Ironsourbennerload(Activity activity, RelativeLayout Adlayout) {

        IronSource.init(activity, preferences.getString("ironappkey", ""), IronSource.AD_UNIT.BANNER);

        IronSourceBannerLayout banner = IronSource.createBanner(activity, ISBannerSize.BANNER);

//        IronSourceBannerLayout banner = IronSource.createBanner(this, ISBannerSize.BANNER);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        Adlayout.addView(banner, 0, layoutParams);

        banner.setBannerListener(new BannerListener() {
            @Override
            public void onBannerAdLoaded() {
                mybennerisredy = true;
                Log.e("myadsload", "ironsource  " + mybennerisredy);
                // Called after a banner ad has been successfully loaded
            }

            @Override
            public void onBannerAdLoadFailed(IronSourceError error) {
                // Called after a banner has attempted to load an ad but failed.

                chakemybenner(activity, Adlayout);

                Log.w("AdsImplementsLog", "Iron Banner : " + error.getErrorMessage());
                Log.e("myadsload", "ironsource  " + error.getErrorMessage());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Adlayout.removeAllViews();
//                        Adlayout.removeAllViews();

                    }
                });
            }

            @Override
            public void onBannerAdClicked() {
                // Called after a banner has been clicked.
            }

            @Override
            public void onBannerAdScreenPresented() {
                // Called when a banner is about to present a full screen content.
            }

            @Override
            public void onBannerAdScreenDismissed() {
                // Called after a full screen content has been dismissed
            }

            @Override
            public void onBannerAdLeftApplication() {
                // Called when a user would be taken out of the application context.
            }
        });

        IronSource.loadBanner(banner);


    }

    public static void Unitybennerload(Activity activity, RelativeLayout Adlayout) {



        UnityAds.initialize(activity, preferences.getString("unityappid", ""), preferences.getBoolean("unitytestmode", false));

        UnityBanners.loadBanner(activity, preferences.getString("unitybanner", ""));
        UnityAds.show(activity);

        IUnityBannerListener iUnityAdsListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String s, View view) {

                Adlayout.removeView(view);
                Adlayout.addView(view);


            }

            @Override
            public void onUnityBannerUnloaded(String s) {

                Log.e("mydasinif", "unload   =   " + s);

            }

            @Override
            public void onUnityBannerShow(String s) {

                Log.e("mydasinif", "show   =   " + s);

            }

            @Override
            public void onUnityBannerClick(String s) {


            }

            @Override
            public void onUnityBannerHide(String s) {

            }

            @Override
            public void onUnityBannerError(String s) {


                Log.e("mydasinif", "show   =   " + s + "  ===   " + myperindex);
                chakemybenner(activity, Adlayout);

            }
        };

        UnityBanners.setBannerListener(iUnityAdsListener);
        UnityBanners.loadBanner(activity, preferences.getString("unitybanner", ""));
        UnityAds.show(activity);

    }

    public static void Startappybennerload(Activity activity, RelativeLayout Adlayout) {


        Banner banner3D = new Banner(activity);
        Adlayout.addView(banner3D);


        com.startapp.sdk.ads.banner.BannerListener banner = new com.startapp.sdk.ads.banner.BannerListener() {
            @Override
            public void onReceiveAd(View view) {

            }

            @Override
            public void onFailedToReceiveAd(View view) {

                Log.e("mydasinif", "adsfil   =   " + myperindex);
                chakemybenner(activity, Adlayout);
            }

            @Override
            public void onImpression(View view) {

            }

            @Override
            public void onClick(View view) {

            }
        };
        banner3D.setBannerListener(banner);


    }

    public static void Qrecabennerload(Activity activity, RelativeLayout Adlayout) {

        if (!isConnectionAvailable(activity)) {

            Log.e("mydasinif", "nointernet  --  ==  ");
            chakemybenner(activity, Adlayout);

        } else {
            mybennerisredy = true;
            Log.e("mydasinif", "qreca show ");
            int drawablebanner;
            drawablebanner = R.drawable.qbenner2;

            ImageView imageView = new ImageView(activity);
            imageView.setImageResource(drawablebanner);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            Adlayout.addView(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stratQureka(activity);
                }
            });

        }


    }


    public static void stratQureka(Activity activity) {


        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(preferences.getString("qreka", "https://335.win.qureka.com/intro/question")));
            activity.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }




    //nativeload    *******************************************************************************************



    public static void NativeLoad(Activity activity, FrameLayout dialog) {
        myperindexnative = 0;

        if (!isConnectionAvailable(activity)) {
            Toast.makeText(activity, "Check your Internet connection", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 1) {

                LoadAdNative(activity, dialog);
            } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 2) {

                FBmodualLoadAdNative(activity, dialog);
            } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 3) {

                UnityNativeLoad(activity, dialog);
            } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 4) {

                IronsourNativeLoad(activity, dialog);
            } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 5) {

                StartappNativeLoad(activity, dialog);
            } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 6) {

                QrecaNativeLoad(activity, dialog);
            }
        }

    }

    public static void chakemyNativce(Activity activity, FrameLayout dialog) {

        myperindexnative++;

        if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 1) {

            LoadAdNative(activity, dialog);
        } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 2) {

            FBmodualLoadAdNative(activity, dialog);
        } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 3) {

            IronsourNativeLoad(activity, dialog);
        } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 4) {

            UnityNativeLoad(activity, dialog);
        } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 5) {

            StartappNativeLoad(activity, dialog);
        } else if (myperindexnative < nativepriorityarrer.size() && nativepriorityarrer.get(myperindexnative) == 6) {

            QrecaNativeLoad(activity, dialog);

        }


    }


    public static void LoadAdNative(Activity activity, FrameLayout dialog) {


        AdLoader.Builder builder = new AdLoader.Builder(activity, preferences.getString("naid", ""));

        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAds) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = activity.isDestroyed();
                        }
                        if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                            nativeAds.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (nativeAd != null) {
                            nativeAd.destroy();
                        }
                        nativeAd = nativeAds;
                        FrameLayout frameLayout = dialog;
                        NativeAdView adView =
                                (NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ads_layout, null);
                        populateNativeAdView(nativeAds, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

//        VideoOptions videoOptions =
//                new VideoOptions.Builder().setStartMuted(startVideoAdsMuted.isChecked()).build();
//
//        NativeAdOptions adOptions =
//                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
//
//        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {

                chakemyNativce(activity, dialog);

            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }


    public static void FBmodualLoadAdNative(Activity activity, FrameLayout dialog) {
        AdLoader.Builder builder = new AdLoader.Builder(activity, preferences.getString("naid", ""));

        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    // OnLoadedListener implementation.
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAds) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.
                        boolean isDestroyed = false;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = activity.isDestroyed();
                        }
                        if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                            nativeAds.destroy();
                            return;
                        }
                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
                        if (nativeAd != null) {
                            nativeAd.destroy();
                        }
                        nativeAd = nativeAds;
                        FrameLayout frameLayout = dialog;
                        NativeAdView adView =
                                (NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ads_layout, null);
                        populateNativeAdView(nativeAds, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

//        VideoOptions videoOptions =
//                new VideoOptions.Builder().setStartMuted(startVideoAdsMuted.isChecked()).build();
//
//        NativeAdOptions adOptions =
//                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
//
//        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                chakemyNativce(activity, dialog);

            }
        }).build();

        Bundle extras = new FacebookExtras()
                .setNativeBanner(true)
                .build();

        AdRequest request = new AdRequest.Builder()
                .addNetworkExtrasBundle(FacebookAdapter.class, extras)
                .build();

        adLoader.loadAd(request);

    }

    public static void IronsourNativeLoad(Activity activity, FrameLayout dialog) {

        {
            AdLoader.Builder builder = new AdLoader.Builder(activity, preferences.getString("naid", ""));

            builder.forNativeAd(
                    new NativeAd.OnNativeAdLoadedListener() {
                        // OnLoadedListener implementation.
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAds) {
                            // If this callback occurs after the activity is destroyed, you must call
                            // destroy and return or you may get a memory leak.
                            boolean isDestroyed = false;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                isDestroyed = activity.isDestroyed();
                            }
                            if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                                nativeAds.destroy();
                                return;
                            }
                            // You must call destroy on old ads when you are done with them,
                            // otherwise you will have a memory leak.
                            if (nativeAd != null) {
                                nativeAd.destroy();
                            }
                            nativeAd = nativeAds;
                            FrameLayout frameLayout = dialog;
                            NativeAdView adView =
                                    (NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ads_layout, null);
                            populateNativeAdView(nativeAds, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);
                        }
                    });

//        VideoOptions videoOptions =
//                new VideoOptions.Builder().setStartMuted(startVideoAdsMuted.isChecked()).build();
//
//        NativeAdOptions adOptions =
//                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
//
//        builder.withNativeAdOptions(adOptions);

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    chakemyNativce(activity, dialog);

                }
            }).build();

            Bundle extras = new FacebookExtras()
                    .setNativeBanner(true)
                    .build();

            AdRequest request = new AdRequest.Builder()
                    .addNetworkExtrasBundle(FacebookAdapter.class, extras)
                    .build();

            adLoader.loadAd(request);

        }
    }

    public static void UnityNativeLoad(Activity activity, FrameLayout dialog) {
        {


            AdLoader.Builder builder = new AdLoader.Builder(activity, preferences.getString("naid", ""));

            builder.forNativeAd(
                    new NativeAd.OnNativeAdLoadedListener() {
                        // OnLoadedListener implementation.
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAds) {
                            // If this callback occurs after the activity is destroyed, you must call
                            // destroy and return or you may get a memory leak.
                            boolean isDestroyed = false;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                isDestroyed = activity.isDestroyed();
                            }
                            if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                                nativeAds.destroy();
                                return;
                            }
                            // You must call destroy on old ads when you are done with them,
                            // otherwise you will have a memory leak.
                            if (nativeAd != null) {
                                nativeAd.destroy();
                            }
                            nativeAd = nativeAds;
                            FrameLayout frameLayout = dialog;
                            NativeAdView adView =
                                    (NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ads_layout, null);
                            populateNativeAdView(nativeAds, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);
                        }
                    });

//        VideoOptions videoOptions =
//                new VideoOptions.Builder().setStartMuted(startVideoAdsMuted.isChecked()).build();
//
//        NativeAdOptions adOptions =
//                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
//
//        builder.withNativeAdOptions(adOptions);

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {

                    chakemyNativce(activity, dialog);

                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());

        }
    }

    public static void StartappNativeLoad(Activity activity, FrameLayout dialog) {

        Mrec banner3D = new Mrec(activity);
        dialog.addView(banner3D);

        com.startapp.sdk.ads.banner.BannerListener bannerListener = new com.startapp.sdk.ads.banner.BannerListener() {
            @Override
            public void onReceiveAd(View view) {

            }

            @Override
            public void onFailedToReceiveAd(View view) {
                chakemyNativce(activity, dialog);
            }

            @Override
            public void onImpression(View view) {

            }

            @Override
            public void onClick(View view) {

            }
        };
        banner3D.setBannerListener(bannerListener);


    }

    public static void QrecaNativeLoad(Activity activity, FrameLayout dialog) {

        if (!isConnectionAvailable(activity)) {

            Log.e("mydasinif", "nointernet  --  ==  ");
            chakemyNativce(activity, dialog);

        }
        else {

            final int min = 1;
            final int max = 5;
            final int random = new Random().nextInt((max - min) + 1) + min;
            int drawablebanner;
            if (random == 1) {
                drawablebanner = R.drawable.qnative1;
            } else if (random == 2) {
                drawablebanner = R.drawable.qnative2;
            } else if (random == 3) {
                drawablebanner = R.drawable.qnative3;
            } else if (random == 4) {
                drawablebanner = R.drawable.qnative4;
            } else {
                drawablebanner = R.drawable.qnative4;
            }


            ImageView imageView = new ImageView(activity);
            imageView.setImageResource(drawablebanner);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stratQureka(activity);
                }
            });


            dialog.addView(imageView);

        }



    }


    private static void populateNativeAdView (NativeAd nativeAd, NativeAdView adView){
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);


    }


    //indiatitionload   *************************************************************************


   /* public static void InterLoad(Activity activity, OnAdListner onAdListner1) {
        onAdListner = onAdListner1;
        boolean myindshow = false;

        Indiatialload();

        for (int i = 0; i < moindex.length; i++) {

            if (!myindshow) {
                if (moindex[i] == 1) {
                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(activity);
                    }

                } else if (moindex[i] == 2) {
                    if (fbmInterstitialAd != null) {
                        fbmInterstitialAd.show(activity);
                    }

                } else if (moindex[i] == 3) {
                    UnityAds.load(preferences.getString("unityinter", ""));

                } else if (moindex[i] == 4) {
                    if (isInterstitialReady) {
                        IronSource.showInterstitial();

                    }

                } else if (moindex[i] == 5) {

                    StartAppAd.showAd(activity);

                } else if (moindex[i] == 6) {

                    QrecaInterstitialLoad(activity);

                }
            }


            }


        }*/

        public static void InterLoad(Activity activity, OnAdListner onAdListner1)
        {
            if(preferences.getInt("adcounter", 1)==Intertstialcount)
            {
                    Intertstialcount=0;
                myperindexInterstitial =0;
                onAdListner = onAdListner1;

                //   Log.e("myidexininter","my index InterLoad  = =   "+moindex[myperindexInterstitial]);
                Log.e("myidexininter","my count InterLoad  = =   "+myperindexInterstitial);

                if (!isConnectionAvailable(activity)) {
                    Toast.makeText(activity, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                }
                else if(interpriorityarrer.size()!=0)
                {
                    if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 1)
                    {
                        AdmobInterstitial(activity, onAdListner1);

                    }

                    else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 2)
                    {
                        FbwithAdmobInterstitial(activity, onAdListner1);

                    }

                    else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 3)
                    {

                        UnityInterLoad(activity,onAdListner1);

                    }

                    else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 4)
                    {
                        IronSourceInterstitialLoad(activity, onAdListner1);

                    }

                    else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 5)
                    {

                        StartAppInterstitialLoad(activity,onAdListner1);

                    }

                    else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 6) {

                        // chkemyindshoen(activity,  onAdListner1);
                        QrecaInterstitialLoad(activity,onAdListner1);
                    }
                    else
                    {
                        onAdListner1.OnClose();
                    }

                }
                else
                {
                    onAdListner1.OnClose();
                }

            }
            else
            {
                Intertstialcount++;
                onAdListner1.OnClose();
            }




        }


        public static void chkemyindshoen(Activity activity, OnAdListner onAdListner1)
        {
            myperindexInterstitial ++;
            onAdListner = onAdListner1;



            Log.e("myidexininter","my count  = =   "+myperindexInterstitial);

            if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 1)
            {

               AdmobInterstitial(activity, onAdListner1);

            }

            else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 2)
            {
                FbwithAdmobInterstitial(activity, onAdListner1);

            }

            else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 3)
            {

                UnityInterLoad(activity,onAdListner1);

               // UnityAds.show(activity);


            }

            else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 4)
            {
                IronSourceInterstitialLoad(activity, onAdListner1);


            }

            else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 5)
            {

                StartAppInterstitialLoad(activity,onAdListner1);

            }

            else if (myperindexInterstitial < interpriorityarrer.size() && interpriorityarrer.get(myperindexInterstitial) == 6) {

              //  chkemyindshoen(activity,  onAdListner1);
                   QrecaInterstitialLoad(activity,onAdListner1);
            }

            else
            {
                onAdListner1.OnClose();
            }


        }



        // load calss

        public static void AdmobInterstitial (Activity activity,OnAdListner onAdListner1){

            if (mInterstitialAd != null) {
                mInterstitialAd.show(activity);

            }
            else
            {
                chkemyindshoen(activity,  onAdListner1);
            }

/*
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(activity, preferences.getString("inid", ""), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                            admobInterstitialReady =true;
                            mInterstitialAd.show(activity);
                            Log.i("inductyalshow", "onAdLoaded");

                            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when fullscreen content is dismissed.
                                    Log.d("inductyalshow", "The ad admob was dismissed.");

                                    onAdListner.OnClose();

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when fullscreen content failed to show.
                                    Log.d("inductyalshow", "The ad admob failed to show.");
                                    admobInterstitialReady =false;


                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    // Called when fullscreen content is shown.
                                    // Make sure to set your reference to null so you don't
                                    // show it a second time.
                                    admobInterstitialReady =false;
                                    mInterstitialAd = null;
                                    Log.d("inductyalshow", "The admob ad was shown.");
                                }


                            });


                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.i("TAG", loadAdError.getMessage());
                            mInterstitialAd = null;
                            admobInterstitialReady =false;
                            chkemyindshoen(activity,  onAdListner1);


                        }

                    });*/


        }


        public static void FbwithAdmobInterstitial (Activity activity,OnAdListner onAdListner1){


            if (fbmInterstitialAd != null) {
                fbmInterstitialAd.show(activity);

            }
            else
            {
                chkemyindshoen(activity,  onAdListner1);
            }

           /* AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(activity, preferences.getString("minterid", ""), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            fbmInterstitialAd = interstitialAd;
                            fbadmobInterstitialReady =true;
                            fbmInterstitialAd.show(activity);
                            Log.i("inductyalshow", "  fbmadiation   onAdLoaded");

                            fbmInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when fullscreen content is dismissed.
                                    Log.d("inductyalshow", "The ad fbmadiation  was dismissed.");
                                    fbadmobInterstitialReady =false;
                                    onAdListner.OnClose();

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when fullscreen content failed to show.
                                    Log.d("inductyalshow", "The ad  fbmadiation failed to show.");
                                    fbadmobInterstitialReady =false;
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    // Called when fullscreen content is shown.
                                    // Make sure to set your reference to null so you don't
                                    // show it a second time.
                                    fbmInterstitialAd = null;
                                    fbadmobInterstitialReady =false;
                                    Log.d("inductyalshow", "The fbmadiation  ad was shown.");
                                }


                            });


                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.i("TAG", loadAdError.getMessage());
                            fbmInterstitialAd = null;
                            fbadmobInterstitialReady =false;
                            chkemyindshoen(activity,  onAdListner1);

                        }

                    });

*/
        }


        public static void IronSourceInterstitialLoad (Activity activity,OnAdListner onAdListner1) {

            if(isInterstitialReady)
            {
                IronSource.showInterstitial();

            }
            else
            {
                chkemyindshoen(activity,  onAdListner1);
            }

         /*   isInterstitialReady = false;

            IronSource.init(activity,"e92b41a1", IronSource.AD_UNIT.INTERSTITIAL);


            IronSource.setInterstitialListener(new InterstitialListener() {
                *//**
                 * Invoked when Interstitial Ad is ready to be shown after load function was called.
                 *//*
                @Override
                public void onInterstitialAdReady() {

                    IronSource.showInterstitial();
                }
                *//**
                 * invoked when there is no Interstitial Ad available after calling load function.
                 *//*
                @Override
                public void onInterstitialAdLoadFailed(IronSourceError error) {

                    chkemyindshoen(activity,  onAdListner1);


                }
                *//**
                 * Invoked when the Interstitial Ad Unit is opened
                 *//*
                @Override
                public void onInterstitialAdOpened() {
                }
                *//*
                 * Invoked when the ad is closed and the user is about to return to the application.
                 *//*
                @Override
                public void onInterstitialAdClosed() {
                    onAdListner.OnClose();

                }
                *//**
                 * Invoked when Interstitial ad failed to show.
                 * @param error - An object which represents the reason of showInterstitial failure.
                 *//*
                @Override
                public void onInterstitialAdShowFailed(IronSourceError error) {

                }
                *//*
                 * Invoked when the end user clicked on the interstitial ad, for supported networks only.
                 *//*
                @Override
                public void onInterstitialAdClicked() {
                }
                *//** Invoked right before the Interstitial screen is about to open.
                 *  NOTE - This event is available only for some of the networks.
                 *  You should NOT treat this event as an interstitial impression, but rather use InterstitialAdOpenedEvent
                 *//*
                @Override
                public void onInterstitialAdShowSucceeded() {


                }
            });
            IronSource.loadInterstitial();
*/


        }


        public static void UnityInterLoad (Activity activity,OnAdListner onAdListner1){

            Log.e("inductyalshowu", "ind unity enter class   =  =  =  ");



            UnityAds.load(preferences.getString("unityinter", "Interstitial_Android"));
        //    UnityAds.load(preferences.getString("unityinter", ""));
            IUnityAdsListener iUnityAdsListener = new IUnityAdsListener() {
                @Override
                public void onUnityAdsReady(String s) {

                    unityInterstitialReady =true;

                    Log.e("inductyalshowu", "ind unity ads   is  loded   =  =  =  ");
                }

                @Override
                public void onUnityAdsStart(String s) {

                    unityInterstitialReady =false;
                    Log.e("inductyalshowu", "ind unity ads   is  start   =  =  =  ");

                }

                @Override
                public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                    onAdListner.OnClose();

                    unityInterstitialReady =false;
                    Log.e("inductyalshowu", "ind unity ads   is  finish   =  =  =  ");



                }

                @Override
                public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {

                    Log.e("inductyalshowu", "ind unity ads   is  erorr   =  =  =  "+s+"   ==  "+unityAdsError.toString());
                    unityInterstitialReady =false;
                    chkemyindshoen(activity,  onAdListner1);
                }
            };

            UnityAds.setListener(iUnityAdsListener);
            UnityAds.show(activity);
        }


        public static void StartAppInterstitialLoad (Activity activity ,OnAdListner onAdListner1){

            onAdListner = onAdListner1;

            StartAppAd startAppAd = new StartAppAd(activity);

            startAppAd.showAd();

            startAppAd.loadAd(new AdEventListener() {
                @Override
                public void onReceiveAd(Ad ad) {
                    Log.e("inductyalshow", "ind start  ads   is  ok   =  =  =  ");
                    stretaappInterstitialReady =true;

                }

                @Override
                public void onFailedToReceiveAd(Ad ad) {
                    Log.e("inductyalshow", "ind start  ads   is  error   =  =  =  ");
                    stretaappInterstitialReady =false;

                    chkemyindshoen(activity,  onAdListner1);

                }
            });


            startAppAd.showAd(new AdDisplayListener() {
                @Override
                public void adHidden(Ad ad) {

                    stretaappInterstitialReady =false;
                }

                @Override
                public void adDisplayed(Ad ad) {
                    stretaappInterstitialReady =false;

                    onAdListner.OnClose();



                }

                @Override
                public void adClicked(Ad ad) {

                }

                @Override
                public void adNotDisplayed(Ad ad) {
                //    chakemyIndiatial(activity);
                    stretaappInterstitialReady =false;



                }
            });

        }


        public static void QrecaInterstitialLoad (Activity activity,OnAdListner onAdListner1){

            onAdListner.OnClose();

            Intent intent = new Intent(activity, QurekaInterActivity.class);
            activity.startActivityForResult(intent, 523);
        }


        //rewardads   ***********************************************************************************


    public static void loderewardads(OnAdListner onAdListner1,OnRewardAdListner onRewardAdListner1)
    {
        admobrelode();

        fbmadiationreload();

        ironsourcerewerd(onRewardAdListner1);

        startapprload();
       AdmobInterstitial(onAdListner1);
        FbwithAdmobInterstitial(onAdListner1);
        IronSourceInterstitialLoad(onAdListner1);



    }

    private static void ironsourcerewerd(OnRewardAdListner onRewardAdListner1) {

            IronSource.loadISDemandOnlyRewardedVideo(activity,"DefaultReward");

        IronSource.setRewardedVideoListener(new RewardedVideoListener() {
            /**
             * Invoked when the RewardedVideo ad view has opened.
             * Your Activity will lose focus. Please avoid performing heavy
             * tasks till the video ad will be closed.
             */
            @Override
            public void onRewardedVideoAdOpened() {
                dialog.dismiss();
            }
            /*Invoked when the RewardedVideo ad view is about to be closed.
            Your activity will now regain its focus.*/
            @Override
            public void onRewardedVideoAdClosed() {
                onRewardAdListner1.Onunsuccess();

            }
            /**
             * Invoked when there is a change in the ad availability status.
             *
             * @param - available - value will change to true when rewarded videos are *available.
             *          You can then show the video by calling showRewardedVideo().
             *          Value will change to false when no videos are available.
             */
            @Override
            public void onRewardedVideoAvailabilityChanged(boolean available) {
                //Change the in-app 'Traffic Driver' state according to availability.
            }
            /**
             /**
             * Invoked when the user completed the video and should be rewarded.
             * If using server-to-server callbacks you may ignore this events and wait *for the callback from the ironSource server.
             *
             * @param - placement - the Placement the user completed a video from.
             */
            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {

                onRewardAdListner1.Onsuccess();
                /** here you can reward the user according to the given amount.
                 String rewardName = placement.getRewardName();
                 int rewardAmount = placement.getRewardAmount();
                 */
            }
            /* Invoked when RewardedVideo call to show a rewarded video has failed
             * IronSourceError contains the reason for the failure.
             */
            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError error) {

              //  ChakemyReward(activity,onRewardAdListner1);

            }
            /*Invoked when the end user clicked on the RewardedVideo ad
             */
            @Override
            public void onRewardedVideoAdClicked(Placement placement){
            }

            @Override
            public void onRewardedVideoAdStarted(){
                dialog.dismiss();
            }
            /* Invoked when the video ad finishes plating. */
            @Override
            public void onRewardedVideoAdEnded(){
            }
        });
    }

    public static void loderewardads(OnRewardAdListner onRewardAdListner1)
    {
        admobrelode();
        fbmadiationreload();
        startapprload();
        ironsourcerewerd(onRewardAdListner1);

    }

    public static void FbwithAdmobInterstitial (OnAdListner onAdListner1){
        onAdListner = onAdListner1;

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity, preferences.getString("minterid", ""), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        fbmInterstitialAd = interstitialAd;
                        fbadmobInterstitialReady =true;

                        Log.i("inductyalshow", "  fbmadiation   onAdLoaded");

                        fbmInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("inductyalshow", "The ad fbmadiation  was dismissed.");
                                fbadmobInterstitialReady =false;
                                onAdListner.OnClose();

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("inductyalshow", "The ad  fbmadiation failed to show.");
                                fbadmobInterstitialReady =false;
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                fbmInterstitialAd = null;
                                fbadmobInterstitialReady =false;
                                Log.d("inductyalshow", "The fbmadiation  ad was shown.");
                            }


                        });


                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        fbmInterstitialAd = null;
                        fbadmobInterstitialReady =false;


                    }

                });


    }

    public static void AdmobInterstitial (OnAdListner onAdListner1){
        onAdListner = onAdListner1;

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(activity, preferences.getString("inid", ""), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        admobInterstitialReady =true;

                        Log.i("inductyalshow", "onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("inductyalshow", "The ad admob was dismissed.");

                                onAdListner.OnClose();

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("inductyalshow", "The ad admob failed to show.");
                                admobInterstitialReady =false;


                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                admobInterstitialReady =false;
                                mInterstitialAd = null;
                                Log.d("inductyalshow", "The admob ad was shown.");
                            }


                        });


                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                        admobInterstitialReady =false;

                    }

                });


    }

    public static void IronSourceInterstitialLoad (OnAdListner onAdListner1) {
        onAdListner = onAdListner1;
        isInterstitialReady = false;

        IronSource.init(activity, preferences.getString("ironappkey", ""), IronSource.AD_UNIT.INTERSTITIAL);


        IronSource.setInterstitialListener(new InterstitialListener() {
            /**
             * Invoked when Interstitial Ad is ready to be shown after load function was called.
             */
            @Override
            public void onInterstitialAdReady() {
                isInterstitialReady=true;


            }
            /**
             * invoked when there is no Interstitial Ad available after calling load function.
             */
            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {




            }
            /**
             * Invoked when the Interstitial Ad Unit is opened
             */
            @Override
            public void onInterstitialAdOpened() {
            }
            /*
             * Invoked when the ad is closed and the user is about to return to the application.
             */
            @Override
            public void onInterstitialAdClosed() {
                onAdListner.OnClose();


            }
            /**
             * Invoked when Interstitial ad failed to show.
             * @param error - An object which represents the reason of showInterstitial failure.
             */
            @Override
            public void onInterstitialAdShowFailed(IronSourceError error) {

            }
            /*
             * Invoked when the end user clicked on the interstitial ad, for supported networks only.
             */
            @Override
            public void onInterstitialAdClicked() {
            }
            /** Invoked right before the Interstitial screen is about to open.
             *  NOTE - This event is available only for some of the networks.
             *  You should NOT treat this event as an interstitial impression, but rather use InterstitialAdOpenedEvent
             */
            @Override
            public void onInterstitialAdShowSucceeded() {


            }
        });
        IronSource.loadInterstitial();



    }

    private static void startapprload() {

        StartAppAd startAppAd = new StartAppAd(activity);
        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                mfil =0;
                //startAppAd.showAd();
            }


            @Override
            public void onFailedToReceiveAd(Ad ad) {
               // ChakemyReward(activity,onRewardAdListner);
                mfil =1;

            }
        });
    }



    private static void fbmadiationreload() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(activity, preferences.getString("mreward", ""),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("TAG", loadAdError.getMessage());
                        fbmRewardedAd = null;
                      //  ChakemyReward(activity,onRewardAdListner);
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        fbmRewardedAd = rewardedAd;
                        Log.d("TAG", "Ad was loaded.");

                        fbmRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d("TAG", "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d("TAG", "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d("TAG", "Ad was dismissed.");
                                fbmRewardedAd = null;
                            }
                        });
                    }
                });

    }

    private static void admobrelode() {


        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(activity,  preferences.getString("rwdid", ""),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("TAG", loadAdError.getMessage());
                        mRewardedAd = null;
                        Log.e("myreward","admob ads error    =   =   "+loadAdError.getMessage());

                        //ChakemyReward(activity,onRewardAdListner);
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.e("myreward","admob ads load    =   =   ");

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.e("myreward","admob ads shown    =   =   ");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e("myreward","admob ads fali to show     =   =   ");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.e("myreward","admob ads  desmiss     =   =   ");
                                mRewardedAd = null;
                            }
                        });
                    }
                });

    }

    public static void RewardAdLoad(Activity activity,OnRewardAdListner onRewardAdListner1)

    {

        if (!isConnectionAvailable(activity)) {
            Toast.makeText(activity, "Check your Internet connection", Toast.LENGTH_SHORT).show();
        }
        else if(rewardpriorityarrer.size()!=0)
        {

            dialog = new Dialog(activity);
            dialog.setContentView(R.layout.witdilog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
            myrewardwait(activity,onRewardAdListner1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();

                }
            },6000);




        }
        else
        {
            Toast.makeText(activity, "Sorry reward Not available", Toast.LENGTH_SHORT).show();
        }

    }


    public static void myrewardwait(Activity activity,OnRewardAdListner onRewardAdListner1)

        {


            onRewardAdListner = onRewardAdListner1;

            myrewardindex =0;

           /* Log.e("myreward","count   =   =   "+myrewardindex);
            Log.e("myreward","indext  =   =   "+moindex[myrewardindex]);*/


            if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 1)
            {
                AdmobRewardAdLoad(activity,onRewardAdListner1);

            }

            else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 2)
            {
                FBWithAdmobRewardAdLoad(activity,onRewardAdListner1);

            }

            else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 3)
            {

                UnityRewardAdLoad(activity,onRewardAdListner1);

            }

            else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 4)
            {
                IronsourceRewardAdLoad(activity,onRewardAdListner1);

            }

            else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 5)
            {

                Log.e("myreward","enter in start app colom  =   =   ");
                StartappRewardAdLoad(activity,onRewardAdListner1);

            }

            else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 6) {

                ChakemyReward(activity,onRewardAdListner);

            }
            else 
            {
                Toast.makeText(activity, "reward are not available in start .... ", Toast.LENGTH_SHORT).show();
            }

        }



    public static void ChakemyReward(Activity activity,OnRewardAdListner onRewardAdListner1)
    {
        myrewardindex ++;



        if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 1)
        {

            AdmobRewardAdLoad(activity,onRewardAdListner1);

        }

        else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 2)
        {
            FBWithAdmobRewardAdLoad(activity,onRewardAdListner1);

        }

        else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 3)
        {

            UnityRewardAdLoad(activity,onRewardAdListner1);

            // UnityAds.show(activity);

        }

        else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 4)
        {
            IronsourceRewardAdLoad(activity,onRewardAdListner1);


        }

        else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 5)
        {

            StartappRewardAdLoad(activity,onRewardAdListner1);

        }

        else if (myrewardindex < rewardpriorityarrer.size() && rewardpriorityarrer.get(myrewardindex) == 6) {

            //  chkemyindshoen(activity,  onAdListner1);
            ChakemyReward(activity,onRewardAdListner);
        }
        else
        {
            Toast.makeText(activity, "reward are not available .... ", Toast.LENGTH_SHORT).show();
        }

    }



    // load calss

    public static void AdmobRewardAdLoad (Activity activity,OnRewardAdListner onRewardAdListner){

            

                if (mRewardedAd != null) {
                    dialog.dismiss();
                    Activity activityContext = activity;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d("TAG", "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                            onRewardAdListner.Onsuccess();
                            loderewardads(onRewardAdListner);
                            

                        }
                    });
                } else {
                    ChakemyReward(activity,onRewardAdListner);


                    Log.d("TAG", "The rewarded ad wasn't ready yet.");
                }




    }


    public static void FBWithAdmobRewardAdLoad (Activity activity,OnRewardAdListner onRewardAdListner){




            if (fbmRewardedAd != null) {
                dialog.dismiss();
                Activity activityContext = activity;
                fbmRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        Log.d("TAG", "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                        onRewardAdListner.Onsuccess();
                        loderewardads(onRewardAdListner);

                    }
                });
            } else {
                ChakemyReward(activity,onRewardAdListner);


                Log.d("TAG", "The rewarded ad wasn't ready yet.");
            }



    }


    public static void IronsourceRewardAdLoad (Activity activity ,OnRewardAdListner onRewardAdListner) {

        IronSource.loadISDemandOnlyRewardedVideo(activity,"DefaultReward");


        if(IronSource.isRewardedVideoAvailable())
        {
            IronSource.showRewardedVideo();
            ironsourcerewerd(onRewardAdListner);

            loderewardads(onRewardAdListner);
        }

        else
        {
            ironsourcerewerd(onRewardAdListner);
            ChakemyReward(activity,onRewardAdListner);
        }

        }


    public static void UnityRewardAdLoad (Activity activity,OnRewardAdListner onRewardAdListner){

       UnityAdsListener myAdsListener = new UnityAdsListener(onRewardAdListner);
        UnityAds.addListener(myAdsListener);

        IUnityAdsListener iUnityAdsListener =new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {

            }

            @Override
            public void onUnityAdsStart(String s) {
                dialog.dismiss();
                loderewardads(onRewardAdListner);

            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {

            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                ChakemyReward(activity,onRewardAdListner);

            }
        };
        UnityAds.addListener(iUnityAdsListener);

        if (UnityAds.isReady (preferences.getString("unityrwd", ""))) {
            UnityAds.show (activity, (preferences.getString("unityrwd", "")));
        }


    }

    public static void StartappRewardAdLoad (Activity activity,OnRewardAdListner onRewardAdListner){


            if(myrecount==1)
            {
                ChakemyReward(activity,onRewardAdListner);
               // UnityRewardAdLoad(activity,onRewardAdListner);
            }
            else
            {

                myrecount=1;
                StartAppAd startAppAd = new StartAppAd(activity);

                startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
                        Log.e("myreward"," to startapp reward  ==  "+ad.getAdId());
                        dialog.dismiss();
                        startAppAd.showAd();
                        loderewardads(onRewardAdListner);
                        mfil =0;
                        //startAppAd.showAd();
                    }




                    @Override
                    public void onFailedToReceiveAd(Ad ad) {
                        Log.e("myreward","Faile to startapp reward  ==  "+ad.getErrorMessage());
                        ChakemyReward(activity,onRewardAdListner);
                        mfil =1;

                       // onRewardAdListner.Onunsuccess();

                    }
                });


                startAppAd.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoCompleted() {

                        onRewardAdListner.Onsuccess();
                        Log.e("myreward"," video complet start load  =   =   ");


                    }

                });

            }


    }

    private static class UnityAdsListener implements IUnityAdsListener {

        public UnityAdsListener(OnRewardAdListner onRewardAdListner) {

        }

        public void onUnityAdsReady (String adUnitId) {
            // Implement functionality for an ad being ready to show.
            Log.e("rewardads","redy   ==  ==  "+adUnitId);
        }

        @Override
        public void onUnityAdsStart (String adUnitId) {
            // Implement functionality for a user starting to watch an ad.
            Log.e("rewardads","start   ==  ==  "+adUnitId);
        }

        @Override
        public void onUnityAdsFinish (String adUnitId, UnityAds.FinishState finishState) {
            // Implement conditional logic for each ad completion status:
            if (finishState.equals(UnityAds.FinishState.COMPLETED)) {

                onRewardAdListner.Onsuccess();

                // Reward the user for watching the ad to completion.
            } else if (finishState.equals(UnityAds.FinishState.SKIPPED)) {
                onRewardAdListner.Onunsuccess();
                // Do not reward the user for skipping the ad.
            } else if (finishState.equals(UnityAds.FinishState.ERROR)) {

                // Log an error.
            }
        }

        @Override
        public void onUnityAdsError (UnityAds.UnityAdsError error, String message) {

            Log.e("rewardads","erorr  ==  ==  "+message);
            // Implement functionality for a Unity Ads service error occurring.
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    }




