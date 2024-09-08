package com.example.translator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.security.Policy;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    //variables

    static final float END_SCALE=0.7f;
    ImageView menuIcon;
    RelativeLayout contentView;

    //Drawer menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private CardView Translator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        //hooks
        menuIcon = findViewById(R.id.menu_icon);
        contentView=findViewById(R.id.content);

        //menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        Translator = findViewById(R.id.translatorView);


        //navigation drawers
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        navigationDrawer();
        Translator.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,FireTranslator.class);
            startActivity(intent);
        });


    }
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(
                view -> {
                    if (drawerLayout.isDrawerVisible(GravityCompat.START))
                        drawerLayout.closeDrawer(GravityCompat.START);
                    else drawerLayout.openDrawer(GravityCompat.START);

                });
        animateNavigationDrawer();
    }
    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.purple_200));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //SCALE THE VIEW BASED ON CURRENT SLIDE OFFSET
                final float diffScaledOffset=slideOffset*(1- END_SCALE);
                final float offsetScale=1- diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //TRANSLATE THE VIEW
                final float xOffset=drawerView.getWidth()*slideOffset;
                final float xOffsetDiff=contentView.getWidth()*diffScaledOffset/2;
                final float xTranslation=xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);

            }
        });
    }
    //navigation drawer function
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(getApplicationContext(), "Home already opened", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
                break;


            case R.id.nav_policy:
                Intent intent2 = new Intent(MainActivity.this, Privacy.class);
                startActivity(intent2);
                break;

            case R.id.nav_terms:
                Intent intent3 = new Intent(MainActivity.this, TermsandConditons.class);
                startActivity(intent3);
                break;

            case R.id.nav_share:
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "This is The best App \n For Language Translation \n By This App You Can easily learn any language \n This is The Free App Hurry up ! Download Now \n" +
                "https://play.google.com/store/apps/details?id=com.example.translator&hl=en";
                String shareSub = "Language Translator";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
                break;

            case R.id.nav_rate:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=" +
                            getPackageName())));
// google play

                } catch (Exception ex) {
                    startActivity(new
                            Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" +
                            getPackageName())));
// website google play

                }
                break;






            case R.id.nav_logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,Login.class));


        }
        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }
}