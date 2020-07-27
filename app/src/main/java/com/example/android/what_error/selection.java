package com.example.android.what_error;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class selection extends AppCompatActivity {
TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
         tv1=findViewById(R.id.result);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("user_name",MainActivity.dummy_username);
        client.post("https://d5f352d8.ngrok.io/select.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                tv1.setText(new String(responseBody));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }


}
