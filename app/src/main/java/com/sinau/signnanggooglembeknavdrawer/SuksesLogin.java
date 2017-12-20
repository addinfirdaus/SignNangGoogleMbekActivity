package com.sinau.signnanggooglembeknavdrawer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableBadgeDrawerItem;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.MiniProfileDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SuksesLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button btlogout;
    ImageView img_profile_pic;
    private GoogleApiClient mGoogleApiClient;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukses_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img_profile_pic=(ImageView)findViewById(R.id.img_profile_pic);
        Uri myUri = Uri.parse(getIntent().getStringExtra("imageUri"));
        Picasso.with(this)
                .load(myUri)
                .fit()
                .error(R.mipmap.ic_launcher_round)
                .into(img_profile_pic);
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }
            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });

        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(myUri);

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

        //MEMBUAT DRAWER
        new DrawerBuilder().withActivity(this).build();

        PrimaryDrawerItem beranda = new PrimaryDrawerItem().withIcon(R.mipmap.ic_launcher_round).withIdentifier(1).withName("Beranda");
        PrimaryDrawerItem kategori = new PrimaryDrawerItem().withIdentifier(2).withName("Kategori");
        PrimaryDrawerItem masuk = new PrimaryDrawerItem().withIdentifier(3).withName("Masuk");
        PrimaryDrawerItem daftar = new PrimaryDrawerItem().withIdentifier(4).withName("Daftar");


//if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("home");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("   atur");
//create the drawer and remember the `Drawer` result object
//        Drawer result = new DrawerBuilder()
//                .withActivity(this)
//                .withToolbar(toolbar)
//                .addDrawerItems(
//                        item1,
//                        new SecondaryDrawerItem()
////                                .withTypeface (yourTypeface)//jika menggunakan font
//                        ,
//                        item2,
//                        new SecondaryDrawerItem().withName("setting")
//                )
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        // do something with the clicked item :D
//                        return false;
//                    }
//                })
//                .build(); //ini membuild default tanpa nav header

// Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.accent)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        //Now create your drawer and pass the AccountHeader.Result
        Drawer result = new DrawerBuilder()
                .withActivity(this)

                .withToolbar (toolbar)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggleAnimated (true)
                .withAccountHeader(headerResult)

                .addDrawerItems(
                        beranda,kategori,masuk,daftar
//                        item1,
//                        new DividerDrawerItem(),//menggunakan garis
//                        item2,
//                        new SecondaryDrawerItem().withName("setting"),
//                        new ExpandableBadgeDrawerItem().withSubItems(item1),
//                        new ExpandableDrawerItem().withName("list").withSubItems(item2)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                Toast.makeText(SuksesLogin.this, "1", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(SuksesLogin.this, "2", Toast.LENGTH_SHORT).show();
                            default:
                                break;
                        }
                        return false;
                    }
                })
                .build();




    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}

