package com.example.user.elevate.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;

import com.example.user.elevate.CustomTypefaceSpan;
import com.example.user.elevate.fragment.Dashboard;
import com.example.user.elevate.fragment.Scan;
import com.example.user.elevate.fragment.Scan2;
import com.example.user.elevate.fragment.Scan3;
import com.example.user.elevate.sql.DatabaseHelper;

import java.util.Date;

import com.example.user.elevate.R;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * Created by User on 09/01/2018.
 */

public class UserActivity extends AppCompatActivity{
    private TextView textViewName;
    String scannedData;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public String urlX="https://script.google.com/macros/s/AKfycbw8v-BARGXPK0kdGKbCFnuMiLiZOo9pyn_q7QBXfBlR-wIAkjJU/exec";
    private  NavigationView navigationView;
    private View HeaderView;
    private Fragment fragment;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                fragment = (Fragment) Dashboard.class.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }


            setContentView(R.layout.activity_user);

            setTitle("Dashboard");
            drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

            actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();
            navigationView = (NavigationView)findViewById(R.id.navi);
            HeaderView=navigationView.getHeaderView(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setupDrawerContent(navigationView);
            textViewName=(TextView) HeaderView.findViewById(R.id.textemail);
        textViewName.setTypeface(Typeface.createFromAsset(getAssets(),"American_Typewriter_Regular.ttf"));

        String nameFromIntent = getIntent().getStringExtra("Email");
            if(nameFromIntent.length()>33)
            {
                nameFromIntent=nameFromIntent.substring(0,32)+"...";
            }
            textViewName.setText(nameFromIntent);
            final Activity activity = this;
            databaseHelper =new DatabaseHelper(activity);
            textViewName=(TextView) HeaderView.findViewById(R.id.text2);
        textViewName.setTypeface(Typeface.createFromAsset(getAssets(),"American_Typewriter_Regular.ttf"));
        nameFromIntent = databaseHelper.getNama(getIntent().getStringExtra("Email")).toUpperCase();
            if(nameFromIntent.length()>20)
            {
                nameFromIntent=nameFromIntent.substring(0,19)+"...";
            }
            textViewName.setText(nameFromIntent);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this,"American_Typewriter_Regular.ttf",true);

        Menu m = navigationView.getMenu();

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "American_Typewriter_Regular.ttf");

        for (int i=0;i<m.size();i++) {

            MenuItem mi = m.getItem(i);

            SpannableString s = new SpannableString(mi.getTitle());
            s.setSpan(new CustomTypefaceSpan("", tf1), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mi.setTitle(s);

        }


        //time
        Thread myThread = null;

        Runnable myRunnableThread = new CountDownRunner();
        myThread= new Thread(myRunnableThread);
        myThread.start();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //dowork

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    TextView txtCurrentTime= (TextView)findViewById(R.id.time);
                    Date dt = new Date();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    int seconds = dt.getSeconds();
                    String curTime = String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds) ;
                    txtCurrentTime.setText(curTime);

                    if(dt.getHours()==11&&dt.getMinutes()==30) {
                        //.setVisibility(View.GONE);
                    }
                    else{
                        //scanBtn.setVisibility(View.VISIBLE);
                    }

                }catch (Exception e) {}
            }
        });
    }

    class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000); // Pause of 1 Second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

    //enddowork


    public void selectItemDrawer(MenuItem item)
    {
        Fragment fragment=null;
        Class fragmentclass = Dashboard.class;
        switch (item.getItemId())
        {
            case R.id.db_btn:
                fragmentclass= Dashboard.class;

                break;
            case R.id.scan :

                fragmentclass= Scan.class;

                break;
            case R.id.scan2 :

                fragmentclass= Scan2.class;

                break;
            case R.id.scan3 :

                fragmentclass= Scan3.class;

                break;
            default:
                fragmentclass= Dashboard.class;
                finish();
           break;

        }
        try {
            fragment =(Fragment) fragmentclass.newInstance();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        FragmentManager fragmentManager =getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flayout,fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }
}
