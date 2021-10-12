package com.callerid.truecaller.trackingnumber.phonenumbertracker.adslibry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.callerid.truecaller.trackingnumber.phonenumbertracker.ads.AdsImplements;
import com.callerid.truecaller.trackingnumber.phonenumbertracker.ads.Tost;
import com.startapp.sdk.adsbase.StartAppAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.callerid.truecaller.trackingnumber.phonenumbertracker.ads.AdsImplements.bannerpriorityarrer;
import static com.callerid.truecaller.trackingnumber.phonenumbertracker.ads.AdsImplements.interpriorityarrer;
import static com.callerid.truecaller.trackingnumber.phonenumbertracker.ads.AdsImplements.nativepriorityarrer;
import static com.callerid.truecaller.trackingnumber.phonenumbertracker.ads.AdsImplements.rewardpriorityarrer;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor ;
    public static  String openid;
    AdsImplements adsImplements;
    TextView mynext;


    TextView interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Tost.simpleost(this,"ok");
        StartAppAd.disableSplash();

        preferences = getSharedPreferences("mysession", MODE_PRIVATE);
        editor = preferences.edit();


        interstitial=findViewById(R.id.interstitial);




        if (!isConnectionAvailable(this)) {
            SweetAlertDialog sd = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
            sd.setTitleText("Network Error")
                    .setContentText("Turn on data or WIFI")
                    .setConfirmButton(R.string.dialog_ok, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            finish();
                        }
                    }).show();
            sd.setCancelable(false);
            return;
        }

        getmayadsid();



        interstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdsImplements.InterLoad(MainActivity.this, new AdsImplements.OnAdListner() {
                    @Override
                    public void OnClose() {
                        Toast.makeText(MainActivity.this, "    ok    ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




    }

    private void loadads() {

        AdsImplements.BanerLoad(MainActivity.this,(RelativeLayout)findViewById(R.id.bennerads));
        AdsImplements.NativeLoad(MainActivity.this,(FrameLayout)findViewById(R.id.nativeads));

    }

    private void getmayadsid() {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://squareupinfotech.com/bus_simulation/adid/test.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            Log.e("inductyalshow","myresponce == ===   "+response);

                            JSONObject jobj2 = jsonArray.getJSONObject(0);
                            editor.putInt("version", jobj2.getInt("version"));
                            editor.putString("type", jobj2.getString("type").toUpperCase());



                            editor.putString("admobappid", jobj2.getString("admobappid"));
                            editor.putString("bid", jobj2.getString("bid"));
                            editor.putString("inid", jobj2.getString("inid"));
                            editor.putString("opid", jobj2.getString("opid"));
                            openid =jobj2.getString("opid");
                            editor.putString("naid", jobj2.getString("naid"));
                            editor.putString("rwdid", jobj2.getString("rwdid"));
                            editor.putString("mbannerid", jobj2.getString("mbannerid"));
                            editor.putString("minterid", jobj2.getString("minterid"));
                            editor.putString("mreward", jobj2.getString("mreward"));
                            editor.putString("mnaid", jobj2.getString("mnaid"));
                            editor.putString("fbid", jobj2.getString("fbid"));

                            editor.putString("finid", jobj2.getString("finid"));
                            editor.putString("fnaid", jobj2.getString("fnaid"));
                            editor.putString("ironappkey", jobj2.getString("ironappkey"));
                            editor.putString("unityappid", jobj2.getString("unityappid"));
                            editor.putString("unityinter", jobj2.getString("unityinter"));
                            editor.putString("unitybanner", jobj2.getString("unitybanner"));

                            editor.putInt("adcounter", jobj2.getInt("adcounter"));

                            editor.putBoolean("unitytestmode", jobj2.getBoolean("unitytestmode"));

                            editor.putString("unityrwd", jobj2.getString("unityrwd"));
                            editor.putString("startappid", jobj2.getString("startappid"));
                            editor.putString("qreka", jobj2.getString("qreka"));
                            editor.putBoolean("isqrekashow",jobj2.getBoolean("isqrekashow"));
                            editor.putBoolean("isMove",jobj2.getBoolean("isMove"));
                            editor.putBoolean("isAppopen",jobj2.getBoolean("isAppopen"));
                            editor.putString("moveurl", jobj2.getString("moveurl"));
                            JSONArray nativepriority =jobj2.getJSONArray("nativepriority");
                            JSONArray rewardpriority =jobj2.getJSONArray("rewardpriority");
                            JSONArray bannerpriority =jobj2.getJSONArray("bannerpriority");
                            JSONArray interpriority =jobj2.getJSONArray("interpriority");


                           /* nativepriorityarrer.clear();
                            rewardpriorityarrer.clear();
                            bannerpriorityarrer.clear();
                            interpriorityarrer.clear();

                            nativepriorityarrer.add(3);
                            rewardpriorityarrer.add(4);
                            bannerpriorityarrer.add(4);
                            interpriorityarrer.add(5);*/

                            for(int i=0; i<nativepriority.length(); i++){

                                int index = (int) nativepriority.get(i);

                                nativepriorityarrer.add(index);

                                Log.e("inductyalshow","index  =  "+index+"arrey    "+ nativepriorityarrer);

                            }
                            for(int i=0; i<rewardpriority.length(); i++){

                                int index = (int) rewardpriority.get(i);

                                rewardpriorityarrer.add(index);

                                Log.e("inductyalshow","index  =  "+index+"arrey    "+rewardpriorityarrer);

                            }
                            for(int i=0; i<bannerpriority.length(); i++){

                                int index = (int) bannerpriority.get(i);

                                bannerpriorityarrer.add(index);

                                Log.e("inductyalshow","index  =  "+index+"arrey    "+bannerpriorityarrer);

                            }
                            for(int i=0; i<interpriority.length(); i++){

                                int index = (int) interpriority.get(i);

                                interpriorityarrer.add(index);

                                Log.e("inductyalshow","index  =  "+index+"arrey    "+interpriorityarrer);

                            }
                            editor.apply();


                            adsImplements=new AdsImplements(MainActivity.this,preferences);

                            loadads();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("inductyalshow","myresponce == ===   "+error.getMessage());
                progressDialog.dismiss();

                SweetAlertDialog sd = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE);
                sd.setTitleText("Network Error")
                        .setContentText("Turn on data or WIFI")
                        .setConfirmButton(R.string.dialog_ok, new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(MainActivity.this, MainActivity.class));
                                finish();
                            }
                        }).show();
                sd.setCancelable(false);

            }
        });

        queue.add(stringRequest);


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