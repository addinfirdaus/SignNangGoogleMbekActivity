package com.sinau.signnanggooglembeknavdrawer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class SuksesLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button btlogout;
    ImageView img_profile_pic;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN=0;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukses_login);

        img_profile_pic=(ImageView)findViewById(R.id.img_profile_pic);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        img_profile_pic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(this,icon, 200, 200, 200, false, false, false, false));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        btlogout=(Button)findViewById(R.id.button2);
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Log.w("", "onResult: "+status.toString());
//                                Toast.makeText(SuksesLogin.this, ""+status.toString(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SuksesLogin.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

    }

        });

        textView=(TextView)findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("nama"));

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

