package com.example.android.what_error;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import static com.example.android.what_error.User_Details.LVarea;
import static com.loopj.android.http.AsyncHttpClient.log;

public class requests_list extends AppCompatActivity {
    public String name,name_to_extract;
    public String LVarea="Kuttralam";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_list);
        display();
    }

    public void display()
    {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username",MainActivity.dummy_username);
        params.add("password",MainActivity.dummy_password);
        client.post("https://quizkrieg.000webhostapp.com/area.php",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                LVarea=new String(responseBody);
                log.v("name",LVarea);
                displayall();
                // Toast.makeText(User_Page.this,new String(responseBody),Toast.LENGTH_LONG).show();
                //tv.setText("" + name + "!!!");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
    public void displayall()
    {


        //String[] names = {"Sriraam Ashok","Siddharth CV","Sai Sanjay","Mathana Kumar",
        //"WArun Vigesh","Shravan",LVarea};

        //ArrayAdapter adapter = new ArrayAdapter<String>(this,
        //   R.layout.listview_layout, names);

        //ListView listView = (ListView) findViewById(R.id.listview);
        //listView.setAdapter(adapter);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username",MainActivity.dummy_username);
        params.add("password",MainActivity.dummy_password);
        params.add("area",LVarea);
        client.post("https://quizkrieg.000webhostapp.com/requestees.php",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                name=new String(responseBody);
                log.v("name",LVarea);
                // Toast.makeText(Alldetails.this,new String(responseBody),Toast.LENGTH_LONG).show();
                String[] data = name.split("_");
                //setter();

                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.listview_layout,data );

                ListView listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        name_to_extract=parent.getItemAtPosition(position).toString();
                        display_alldetails(name_to_extract);
                    }


                });
                //tv.setText("" + name + "!!!");
//            try {
//                JSONArray jsonArray =new JSONArray(new String(responseBody));
//                size=jsonArray.length();
//                for(int i=0;i<size;i++) {
//                     Toast.makeText(Alldetails.this, jsonArray.get(i).toString(), Toast.LENGTH_LONG).show();
//                    list.add(jsonArray.get(i).toString());
//                    setter();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });



    }

    public void display_alldetails(final String name_to_extract)
    {
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        params.add("username",MainActivity.dummy_username);
        params.add("password",MainActivity.dummy_password);
        params.add("name",name_to_extract);
        client.post("https://quizkrieg.000webhostapp.com/request_details.php",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                name=new String(responseBody);
                log.v("name",name);
                final String[]  data2 = name.split("_");
                new AlertDialog.Builder(requests_list.this)
                        .setTitle("DETAILS")
                        .setMessage("NAME        " + data2[0] +"\n"   + "CONTACT     " + data2[1] +"\n" + "WATER AVL   " + data2[2] + " litres"
                        +"\n" + "WATER REQ   " + data2[3] + " litres"  +"\n" + "REASON      " + data2[4])
                        .setPositiveButton("APPROVE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                               int water_avl=Integer.parseInt(data2[2]);
                               int water_req=Integer.parseInt(data2[3]);
                               water_avl=water_avl+water_req;
                               String str_wateravl=Integer.toString(water_avl);
                               params.add("username",MainActivity.dummy_username);
                               params.add("password",MainActivity.dummy_password);
                               params.add("water_avl",str_wateravl);
                               params.add("name",name_to_extract);
                                client.post("https://quizkrieg.000webhostapp.com/approve_update.php",params, new AsyncHttpResponseHandler()
                                {

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        Toast.makeText(requests_list.this, new String(responseBody), Toast.LENGTH_LONG).show();
                                        String msg="Your request has been approved ! Please check your details  to know your water levels ";
                                        SmsManager sms=SmsManager.getDefault();
                                        sms.sendTextMessage(data2[1], null, msg, null,null);
                                        Intent i=new Intent(requests_list.this,requests_list.class);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                    }
                                });


                            }
                        })

                        .setNegativeButton("DECLINE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                int water_avl=Integer.parseInt(data2[2]);
                                int water_req=Integer.parseInt(data2[3]);
                                water_avl=water_avl;
                                String str_wateravl=Integer.toString(water_avl);
                                params.add("username",MainActivity.dummy_username);
                                params.add("password",MainActivity.dummy_password);
                                params.add("water_avl",str_wateravl);
                                params.add("name",name_to_extract);
                                client.post("https://quizkrieg.000webhostapp.com/approve_update.php",params, new AsyncHttpResponseHandler()
                                {

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        Toast.makeText(requests_list.this, new String(responseBody), Toast.LENGTH_LONG).show();
                                        String msg="Your request has been declined ! Please check your details  to know your water levels ";
                                        SmsManager sms=SmsManager.getDefault();
                                        sms.sendTextMessage(data2[1], null, msg, null,null);
                                        Intent i=new Intent(requests_list.this,requests_list.class);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                    }
                                });


                            }
                        })


                        .show();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });





    }



}
