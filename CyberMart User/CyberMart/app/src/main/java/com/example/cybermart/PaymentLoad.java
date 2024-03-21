package com.example.cybermart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentLoad extends AppCompatActivity {

    String[][] table = new String[10000][4];

    protected void onCreate(Bundle savedInstanceState){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentloading);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String bill = extras.getString("bill");
        String bid = extras.getString("billid");
        String tid = extras.getString("tid");
        table = (String[][]) extras.getSerializable("table");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(PaymentLoad.this,PaymentActivity.class);
                Bundle extras = new Bundle();
                extras.putString("bill",bill);
                extras.putString("billid",bid);
                extras.putString("tid",tid);
                extras.putSerializable("table",table);
                intent.putExtras(extras);
                startActivity(intent);
            }
        },3000);

    }
}
