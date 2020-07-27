package com.example.android.what_error;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class User_Update extends AppCompatActivity {
    private ProgressDialog progressDialog;
    String str_billnum="CHE27";
    String str_city,str_state,str_area;
    EditText family_name, head, address1,address2, contact, consumption, user_name, password, age, confpass,pincode,state,city,area;
    RadioButton r1,r2;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__update);
        progressDialog = new ProgressDialog(this);
        ImageView iv1=findViewById(R.id.logo_register);
        iv1.setVisibility(View.GONE);
        display();

        //family_name = (EditText) findViewById(R.id.fn);
        head = (EditText) findViewById(R.id.head);
        age = (EditText) findViewById(R.id.age);
        address1 = (EditText) findViewById(R.id.addr1);
        address2=(EditText)findViewById(R.id.addr2);
        contact = (EditText) findViewById(R.id.contact);
        consumption = (EditText) findViewById(R.id.consum);
        user_name = (EditText) findViewById(R.id.un);
        password = (EditText) findViewById(R.id.password);
        confpass = (EditText) findViewById(R.id.confpassword);
        pincode =(EditText) findViewById(R.id.pincode);
        state=(EditText) findViewById(R.id.state_input);
        city=(EditText) findViewById(R.id.city_input);
        area=  (EditText) findViewById(R.id.area_input);
        r1 = (RadioButton) findViewById(R.id.r1);
        r2 = (RadioButton) findViewById(R.id.r2);

    }

    public void display() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username", MainActivity.dummy_username);
        params.add("password", MainActivity.dummy_password);
        client.post("https://quizkrieg.000webhostapp.com/set_update.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String name = new String(responseBody);
                log.v("name", name);
                String[] data = name.split("_", 9);
                TextView tv1 = findViewById(R.id.head);
                tv1.setText("" + data[0]);

                TextView tv2 = findViewById(R.id.state_input);
                tv2.setText("" + data[1]);

                TextView tv3 = findViewById(R.id.city_input);
                tv3.setText("" + data[2]);

                TextView tv4 = findViewById(R.id.area_input);
                tv4.setText("" + data[3]);

                TextView tv5 = findViewById(R.id.contact);
                tv5.setText("" + data[4]);

                TextView tv6 = findViewById(R.id.pincode);
                tv6.setText("" + data[7]);

                TextView tv7 = findViewById(R.id.consum);
                tv7.setText("" + data[8]);

                TextView tv8 = findViewById(R.id.addr1);
                tv8.setText(""+ data[6]);

               // TextView tv9 = findViewById(R.id.fn);
                //tv9.setText("" + data[9]);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    public void update(View view) {
        log.v("update","updating");
        //Toast.makeText(this, "App in progress...", Toast.LENGTH_SHORT).show();
        //String str_fn = family_name.getText().toString();
        String str_head = head.getText().toString();
        String str_addr = address1.getText().toString() + address2.getText().toString();
        String str_consumption = consumption.getText().toString();
        //String str_un = user_name.getText().toString();
        //String str_password = password.getText().toString();
        String str_age = age.getText().toString();
        //String str_confpassword = confpass.getText().toString();
        String str_contact = contact.getText().toString();
        String str_pincode = pincode.getText().toString();
        String str_state=state.getText().toString();
        String str_city=city.getText().toString();
        String str_area=area.getText().toString();
       // if (TextUtils.isEmpty(str_fn)) {
            //Toast.makeText(this, "Please enter Family Name", Toast.LENGTH_SHORT).show();
        //}

        if (TextUtils.isEmpty(str_head)) {
            Toast.makeText(this, "Please enter name of the head", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_age)) {
            Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_addr)) {
            Toast.makeText(this, "Please enter Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_contact)) {
            Toast.makeText(this, "Please enter Contact number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_consumption)) {
            Toast.makeText(this, "Please enter monthly consumption", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(str_pincode)) {
            Toast.makeText(this, "Please enter Pincode", Toast.LENGTH_SHORT).show();

        } else {
            progressDialog.setMessage("Updating...");
            progressDialog.show();
            //final LottieAnimationView animationview;
            //animationview = findViewById(R.id.loader);
            //animationview.setAnimation("loader.json");
            //animationview.playAnimation();

            //String type = "register";
            //BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            //backgroundWorker.execute(type,str_fn,str_contact,str_head,str_addr,str_consumption,str_un,str_password,str_age);
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            //params.add("family_name", str_fn);
            params.add("head", str_head);
            params.add("address", str_addr);
            params.add("consumption", str_consumption);
            params.add("contact", str_contact);
            //params.add("age", str_age);
            params.add("username", MainActivity.dummy_username);
            params.add("password", MainActivity.dummy_password);
            params.add("bill_no", str_billnum);
            params.add("state", str_state);
            params.add("city", str_city);
            params.add("area", str_area);
            params.add("pincode", str_pincode);
            log.v("params",params.toString());
            if (r1.isChecked()) {
                params.add("type", "COMMERCIAL");
            } else {
                params.add("type", "DOMESTIC");
            }

            client.post("https://quizkrieg.000webhostapp.com/update.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    progressDialog.dismiss();
                    Toast.makeText(User_Update.this, new String(responseBody), Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), User_Page.class);
                    //animationview.pauseAnimation();
                    startActivity(i);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
            String no=contact.getText().toString();
            String msg="The requested changes have been made. Thank you for your constant support !";
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(no, null, msg, null,null);


        }



    }

}