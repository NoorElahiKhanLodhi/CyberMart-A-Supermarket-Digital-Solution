package com.example.cybermart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class review extends AppCompatActivity {

    String[][] table = new String[10000][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_bill);





        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String bid = extras.getString("billid");

        String tid = extras.getString("tid");

        String bill = extras.getString("bill");

        TextView Bid = findViewById(R.id.info);
        Bid.setText("BILL: "+bid+" | Total: Rs."+bill);



        table = (String[][]) extras.getSerializable("table");



        ///Table assigning
        for(int i =0;i < table.length;i++) {
            if(table[i][0]!=null) {
                TableLayout tl = findViewById(R.id.table);
                tl.setColumnStretchable(0, true);
                tl.setColumnStretchable(1, true);
                TableRow tr = new TableRow(this);
                TextView tw1 = new TextView(this);
                tw1.setText(table[i][0]);
                tw1.setGravity(Gravity.CENTER);




                TextView tw2 = new TextView(this);
                tw2.setText(table[i][1]);
                tw2.setGravity(Gravity.CENTER);
                TextView tw3 = new TextView(this);
                tw3.setText(table[i][2]);
                tw3.setGravity(Gravity.CENTER);
                TextView tw4 = new TextView(this);
                tw4.setText(table[i][3]);
                tw4.setGravity(Gravity.CENTER);
                int size = 6;
                tw1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tw2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tw3.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tw4.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tw1.setTextSize(size);
                tw2.setTextSize(size);
                tw3.setTextSize(size);
                tw4.setTextSize(size);
                tr.addView(tw1);
                tr.addView(tw2);
                tr.addView(tw3);
                tr.addView(tw4);
                tl.addView(tr);
            }
        }



        ///Table assigning

        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(review.this,LogedIn.class));
            }
        });

        Button pay = findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
Intent intent = new Intent(review.this,PaymentLoad.class);
///
                Bundle extras = new Bundle();

                extras.putString("bill",bill);
                extras.putString("billid",bid);
                extras.putString("tid",tid);
                extras.putSerializable("table",table);
                intent.putExtras(extras);
                ///

                startActivity(intent);
            }
        });

    }
}