package com.callerid.truecaller.trackingnumber.phonenumbertracker.ads;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import static com.callerid.truecaller.trackingnumber.phonenumbertracker.ads.AdsImplements.onAdListner;


public class QurekaInterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qureka_inter);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}




        final int min = 1;
        final int max = 5;
        final int random = new Random().nextInt((max - min) + 1) + min;
        int drawablebanner;
        if (random == 1) {
            drawablebanner = R.drawable.qinter1;
        } else if (random == 2) {
            drawablebanner = R.drawable.qinter1;
        } else if (random == 3) {
            drawablebanner = R.drawable.qinter1;
        } else if (random == 4) {
            drawablebanner = R.drawable.qinter1;
        } else {
            drawablebanner = R.drawable.qinter1;
        }

        findViewById(R.id.qInter).setBackgroundResource(drawablebanner);

    }

    public void ShowQureka(View view) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://335.win.qureka.com/intro/question"));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(QurekaInterActivity.this, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void CloseIcon(View view) {
        finish();
        onAdListner.OnClose();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
        onAdListner.OnClose();

    }
}