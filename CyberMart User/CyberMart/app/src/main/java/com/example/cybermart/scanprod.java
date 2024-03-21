package com.example.cybermart;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;


public class scanprod extends AppCompatActivity {
    String[][] table = new String[10000][4];
    int i =0;
    int total =0;
    public static final String bill = "";
   public static final String billid = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_trolley);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String tid = extras.getString("tid");
        String bid = extras.getString(LogedIn.bill_id);
        TextView Bid = findViewById(R.id.billid);
        Bid.setText("BILL ID: "+bid);

        //Exit Button
        Button exb = findViewById(R.id.exb);
        exb.setOnClickListener(v -> {
            Intent intent12 = new Intent(scanprod.this, LogedIn.class);
            startActivity(intent12);
        });
        //Exit Button
        //add
        ImageButton add = findViewById(R.id.imageButton);
        add.setOnClickListener(v -> scan());
        //add


        Button complete = findViewById(R.id.complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scanprod.this, review.class);
                Bundle extras = new Bundle();

                extras.putString("bill",String.valueOf(total));
                extras.putString("billid",bid);
                extras.putString("tid",tid);
                extras.putSerializable("table",table);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }
    //QR
    private void scan(){
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(capture.class);
        barLauncher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if(result.getContents()!=null){
                    Toast.makeText(scanprod.this,result.getContents(),Toast.LENGTH_SHORT).show();

                    String text = result.getContents();
                    String[] productInfo = text.split(",");
////{
                    table[i][0] = productInfo[0];
                    table[i][1] = productInfo[1];
                    table[i][2] = productInfo[2];
                    table[i][3] = productInfo[3];
                    i++;
////}

                    TableLayout tl = findViewById(R.id.table);
                    tl.setColumnStretchable(0, true);
                    tl.setColumnStretchable(1, true);
                    TableRow tr = new TableRow(this);
                    TextView tw1 = new TextView(this);
                    tw1.setText(productInfo[0]);
                    tw1.setGravity(Gravity.CENTER);
                    TextView tw2 = new TextView(this);
                    tw2.setText(productInfo[1]);
                    tw2.setGravity(Gravity.CENTER);
                    TextView tw3 = new TextView(this);
                    tw3.setText(productInfo[2]);
                    tw3.setGravity(Gravity.CENTER);
                    TextView tw4 = new TextView(this);
                    tw4.setText(productInfo[3]);
                    int price = Integer.parseInt(productInfo[3]);
                    total = total + price;
                    TextView tot = findViewById(R.id.total);
tot.setText("Total:" + total);
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
            });

    //QR

}
