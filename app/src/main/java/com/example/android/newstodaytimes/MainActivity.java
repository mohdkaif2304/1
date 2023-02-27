package com.example.android.newstodaytimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.newstodaytimes.Weather.WeatherActivity;
import com.example.android.newstodaytimes.adapter.PagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView ;
    private SearchView searchView ;
    SliderView sliderView ;
    int[] images = {R.drawable.two,
            R.drawable.wall,
            R.drawable.two,
            R.drawable.two,
            R.drawable.wall,
            R.drawable.wall
    };
 private TabLayout mTabLayout ;
private DrawerLayout drawerLayout ;
private Toolbar mToolbar ;
private TabItem mHome , mSports , mHealth , mEntertainment , mScience , mTechnology ;
// A PagerAdapter may implement a form of View recycling if desired or use a more sophisticated method of
// managing page Views such as Fragment transactions where each page is represented by its own Fragment.
PagerAdapter pagerAdapter ;
CardView cardView ;
Button languageChange ;
String api = "29deb8a6f44c42f38803869979a4f3c3" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadLocale();

        mToolbar = findViewById(R.id.toolBar) ;
        setSupportActionBar(mToolbar);

        mHome = findViewById(R.id.home);
        mHealth = findViewById(R.id.health) ;
        mSports  =findViewById(R.id.sports);
        mEntertainment = findViewById(R.id.entertainment);
        mScience = findViewById(R.id.science);
        mTechnology = findViewById(R.id.technology);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        searchView = findViewById(R.id.searchView) ;
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        ViewPager viewPager = findViewById(R.id.fragmentContainer);
        mTabLayout = findViewById(R.id.TabLayout);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager() , 6);
        viewPager.setAdapter(pagerAdapter);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    //    mTabLayout.setupWithViewPager(viewPager);

          mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
          @Override
            public void onTabSelected(TabLayout.Tab tab) {
              viewPager.setCurrentItem(tab.getPosition());
              if ( tab.getPosition() == 0 || tab.getPosition() == 1|| tab.getPosition()== 2 || tab.getPosition() == 3 || tab.getPosition() ==4 || tab.getPosition() ==5 ){
                  pagerAdapter.notifyDataSetChanged();
              }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if (id == R.id.item_drawer_weather){
                    Intent newIntent = new Intent(MainActivity.this, WeatherActivity.class);
                    startActivity(newIntent);
                }
                else if (id == R.id.item_drawer_language) {
                    changeLanguage() ;
                }
                else if (id == R.id.item_drawer_share){
                    try{
                        Intent shareIntent =  new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"news Feed");
                        shareIntent.putExtra (Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id="+ getApplicationContext().getPackageName());
                        startActivity(Intent.createChooser(shareIntent,"Sharing via")); 
                    }
                    catch (Exception e){
                        Toast.makeText(MainActivity.this, "Unable to share this app", Toast.LENGTH_SHORT).show();
                    }
                  
                }
                else if (id == R.id.item_drawer_aboutUs){
                    startActivity(new Intent(MainActivity.this , AboutUs.class));
                }
                return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeLanguage() {
        final String languages[] = {"English", "Hindi"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Languages");
        builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("");
                    recreate();
                } else if (which == 1) {
                    setLocale("hi");
                    recreate();
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void setLocale(String language) {
        Locale locale = new Locale(language) ;
        Locale.setDefault(locale);
        Configuration configuration = new Configuration() ;
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration , getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings"  , MODE_PRIVATE).edit();
        editor.putString("app_lang" , language);
        editor.apply();
    }
    private void loadLocale(){
        SharedPreferences sharedPreferences = getSharedPreferences("Settings" , MODE_PRIVATE);
        String language = sharedPreferences.getString("app_lang" , "");
        setLocale(language);
    }


}