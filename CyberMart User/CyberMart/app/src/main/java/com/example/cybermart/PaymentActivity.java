package com.example.cybermart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class PaymentActivity extends AppCompatActivity {
String billid;
    private ScrollView llPdf;
    private Bitmap bitmap;
    String[][] table = new String[10000][4];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bills = database.getReference("cybermart/bill");
        DatabaseReference trolley = database.getReference("cybermart/trolleys");

///
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String bill = extras.getString("bill");
        String tid = extras.getString("tid");
        String bid = extras.getString("billid");


        billid=bid;
        table = (String[][]) extras.getSerializable("table");
///




        TextView mid = findViewById(R.id.textView2);
        TextView left = findViewById(R.id.textView3);
        TextView right = findViewById(R.id.textView4);
        TextView tv5 = findViewById(R.id.textView5);
        TextView tv6 = findViewById(R.id.textView6);
        TextView tv7 = findViewById(R.id.textView7);
        TextView oid = findViewById(R.id.textView8);
        oid.setText("Order ID: " + bid);

        TextView tv9 = findViewById(R.id.textView9);
        TextView pkr = findViewById(R.id.textView10);
        pkr.setText("PKR " + bill + ".00");

        TextView head = findViewById(R.id.textView11);
        TextView mobAc = findViewById(R.id.textView12);
        TextView cardNo = findViewById(R.id.textView13);
        TextView tcvv = findViewById(R.id.textView14);
        TextView texp = findViewById(R.id.textView15);
        Button pay = findViewById(R.id.button);
        EditText mcAccount = findViewById(R.id.editTextText2);
        EditText cvv = findViewById(R.id.editTextText5);
        EditText exp = findViewById(R.id.editTextText6);


        Spinner drop = findViewById(R.id.spinner2);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner2, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drop.setAdapter(adapter);


        drop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String item = (String) drop.getSelectedItem();

                switch (item) {
                    case "Mobile Account":
                        pay.setVisibility(View.VISIBLE);
                        head.setText("Please enter walet details");
                        mobAc.setVisibility(View.VISIBLE);
                        mcAccount.setVisibility(View.VISIBLE);
                        drop.setVisibility(View.GONE);
                        mid.setBackgroundColor(Color.rgb(176, 0, 32));
                        mid.setTextColor(Color.rgb(255, 255, 255));
                        left.setBackgroundColor(Color.rgb(211, 211, 211));
                        left.setTextColor(Color.rgb(0, 0, 0));
                        break;
                    case "Credit Card":
                        pay.setVisibility(View.VISIBLE);
                        head.setText("Please enter card details");
                        cardNo.setVisibility(View.VISIBLE);
                        tcvv.setVisibility(View.VISIBLE);
                        cvv.setVisibility(View.VISIBLE);
                        texp.setVisibility(View.VISIBLE);
                        exp.setVisibility(View.VISIBLE);
                        mcAccount.setVisibility(View.VISIBLE);
                        drop.setVisibility(View.GONE);
                        mid.setBackgroundColor(Color.rgb(176, 0, 32));
                        mid.setTextColor(Color.rgb(255, 255, 255));
                        left.setBackgroundColor(Color.rgb(211, 211, 211));
                        left.setTextColor(Color.rgb(0, 0, 0));

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        pay.setOnClickListener(new View.OnClickListener() {

                                   @Override
                                   public void onClick(View view) {

                                       pay.setVisibility(View.GONE);
                                       head.setText("Payment processing please wait...\nBill ID: ");
                                       cardNo.setVisibility(View.GONE);
                                       tcvv.setVisibility(View.GONE);
                                       cvv.setVisibility(View.GONE);
                                       texp.setVisibility(View.GONE);
                                       exp.setVisibility(View.GONE);
                                       mcAccount.setVisibility(View.GONE);
                                       drop.setVisibility(View.GONE);
                                       mobAc.setVisibility(View.GONE);
                                       mid.setBackgroundColor(Color.rgb(211, 211, 211));
                                       mid.setTextColor(Color.rgb(0, 0, 0));
                                       right.setBackgroundColor(Color.rgb(176, 0, 32));
                                       right.setTextColor(Color.rgb(255, 255, 255));

                                       Toast.makeText(PaymentActivity.this, "Resolving request " + bid, Toast.LENGTH_SHORT).show();
                                       Toast.makeText(PaymentActivity.this, "Verifying " + mcAccount.getText(), Toast.LENGTH_SHORT).show();

                                       GifImageView gif = findViewById(R.id.gif);
                                       gif.setVisibility(View.VISIBLE);

                                       new Handler().postDelayed(new Runnable() {
                                           @Override
                                           public void run() {
                                               setContentView(R.layout.activity_end);


                                               Button menu = findViewById(R.id.menu);
                                               menu.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       startActivity(new Intent(PaymentActivity.this, LogedIn.class));
                                                   }
                                               });
                                               Toast.makeText(PaymentActivity.this, "Payment complete!", Toast.LENGTH_SHORT).show();

                                               String prod = "";
                                               String prices = "";

                                               ///\
                                               for(int i =0;i < table.length;i++) {
                                                   if(table[i][0]!=null) {
                                                       TableLayout tl = findViewById(R.id.table);
                                                       tl.setColumnStretchable(0, true);
                                                       tl.setColumnStretchable(1, true);
                                                       TableRow tr = new TableRow(PaymentActivity.this);
                                                       TextView tw1 = new TextView(PaymentActivity.this);
                                                       tw1.setText(table[i][0]);
                                                       tw1.setGravity(Gravity.CENTER);

                                                       if(i==0){
                                                           prod += table[i][0];
                                                           prices += table[i][3];
                                                       }else{
                                                           prod += ","+table[i][0];
                                                           prices += ","+table[i][3];
                                                       }

                                                       TextView tw2 = new TextView(PaymentActivity.this);
                                                       tw2.setText(table[i][1]);
                                                       tw2.setGravity(Gravity.CENTER);
                                                       TextView tw3 = new TextView(PaymentActivity.this);
                                                       tw3.setText(table[i][2]);
                                                       tw3.setGravity(Gravity.CENTER);
                                                       TextView tw4 = new TextView(PaymentActivity.this);
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
                                               ///

                                               bills.child(bid).child("bid").setValue(bid);
                                               bills.child(bid).child("total").setValue(bill);
                                               bills.child(bid).child("tid").setValue(tid);

                                               bills.child(bid).child("prod").setValue(prod);

                                               bills.child(bid).child("prices").setValue(prices);

                                               DateFormat df = new SimpleDateFormat("HH:mm:ss a, dd/MM/yyyy", Locale.getDefault());
                                               String currentDateAndTime = df.format(new Date());

                                               bills.child(bid).child("date").setValue(currentDateAndTime);

                                               trolley.child(tid).child("bid").setValue(bid);
                                               trolley.child(tid).child("status").setValue("paid");

                                               TextView Bid = findViewById(R.id.info);
                                               Bid.setText("BILL: "+bid+" | Total: Rs."+bill+" [Paid!]");

                                               Button save = findViewById(R.id.save);
                                               save.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {

                                                       llPdf = findViewById(R.id.scrollView3);

                                                       bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());

                                                       createPdf();
                                                   }
                                               });

                                           }
                                       }, 5000);

                                   }
                               }
        );



    }



    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = Environment.getExternalStorageDirectory()+"/Download/"+billid+".pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Bill saved in "+targetPdf, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();




    }



}
