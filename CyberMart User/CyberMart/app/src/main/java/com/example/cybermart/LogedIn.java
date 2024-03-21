package com.example.cybermart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Calendar;

public class LogedIn extends AppCompatActivity {
static String bid, tid;
public static final String bill_id = "bid";
String e;
    Button logout;
    GoogleSignInClient mGoogleSignInClient;
    TextView name,email;
    ImageView profilePic;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_in);

        Button proceed = findViewById(R.id.proceed);
        proceed.setVisibility(View.INVISIBLE);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogedIn.this, scanprod.class);
                Bundle extras = new Bundle();
                extras.putString(bill_id,bid);
                extras.putString("tid",tid);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });


        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        profilePic=(ImageView) findViewById(R.id.profilePic);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Button logout=findViewById(R.id.logout);
       logout.setOnClickListener((new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            signOut();
            startActivity(new Intent(LogedIn.this, MainActivity.class));
        }
    }));
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            name.setText(personName);
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            email.setText(personEmail);
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            String pic = String.valueOf(personPhoto);
            Glide.with(this).load(pic).circleCrop().error(R.drawable.profilepic).into(profilePic);
            Log.d("PICTURE URL",pic);
e=personEmail;


        }
    ImageButton scanTrolley = findViewById(R.id.scanTrolley);
        scanTrolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanTrolley.setVisibility(View.INVISIBLE);
                Toast.makeText(LogedIn.this,"Scan QR Code At The Front Of The Trolley ",Toast.LENGTH_SHORT).show();
                scan();
            }
        });

    }

    //xxx
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
                    Toast.makeText(LogedIn.this,result.getContents(),Toast.LENGTH_SHORT).show();
                    Button proceed = findViewById(R.id.proceed);
                    proceed.setVisibility(View.VISIBLE);
                    Calendar now = Calendar.getInstance();
                    bid= result.getContents() + String.valueOf(now.get(Calendar.HOUR)) + String.valueOf(now.get(Calendar.SECOND)) + e.substring(0,5);
                    proceed.setText("Proceed With Trolley: "+result.getContents());
                    tid = result.getContents();
                }
            });
    //xxx

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LogedIn.this,"signout",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

}