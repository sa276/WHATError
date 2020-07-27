package com.example.android.what_error;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class User_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user__page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.first=='U') {
                    Intent i = new Intent(User_Page.this, request.class);
                    startActivity(i);
                }
                else
                {
                    Snackbar.make(view,"Sorry ! Manager can't raise water request !",Snackbar.LENGTH_LONG).setAction("ACTION",null).show();
                }
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

         Select();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user__page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String[] TO = {"what_error@gmail.com"};

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, TO);
            intent.putExtra(Intent.EXTRA_SUBJECT, "ISSUE WITH RESPECT TO SERVICE " );
            intent.putExtra(Intent.EXTRA_TEXT,"Sir/Madam,");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_details) {
            Intent i=new Intent(this,User_Details.class);
            startActivity(i);
        } else if (id == R.id.nav_update) {

                Intent i = new Intent(this, User_Update.class);
                startActivity(i);

        } //else if (id == R.id.nav_share) {

        //}
        else if (id == R.id.nav_recycle) {
            Intent i=new Intent(this,recycle.class);
            startActivity(i);
        }

       else if (id==R.id.nav_alldetails)
        {
            if(MainActivity.first=='M')
            {
                //Toast.makeText(User_Page.this,"App in Progress !!",Toast.LENGTH_LONG).show();
                Intent i=new Intent(this,Alldetails.class);
                startActivity(i);
           }
            else {
                Toast.makeText(User_Page.this,"Sorry, only for Managers !!",Toast.LENGTH_LONG).show();
            }
        }

        else if (id==R.id.nav_request)
        {
            if(MainActivity.first=='M')
            {
                Intent i=new Intent(this,requests_list.class);
                startActivity(i);
            }
            else {
                Toast.makeText(User_Page.this,"Sorry, only for Managers !!",Toast.LENGTH_LONG).show();
            }
        }




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void Select()
    {
        final TextView tv=findViewById(R.id.user);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username",MainActivity.dummy_username);
        params.add("password",MainActivity.dummy_password);
        client.post("https://quizkrieg.000webhostapp.com/selection.php",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String name=new String(responseBody);
                log.v("name",name);
               // Toast.makeText(User_Page.this,new String(responseBody),Toast.LENGTH_LONG).show();
                tv.setText("" + name + "!!!");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

}
