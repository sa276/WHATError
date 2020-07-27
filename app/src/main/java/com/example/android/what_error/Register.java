package com.example.android.what_error;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ProgressDialog progressDialog;
    public static String number_temp;
    String str_billnum="CHE27";
    private static Random rnd = new Random();
    String str_city,str_state,str_area,str_contact;
    EditText family_name, head, address1,address2, contact, consumption, user_name, password, age, confpass,pincode;
    RadioButton r1,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView iv1=findViewById(R.id.logo_register);
        iv1.setVisibility(View. GONE);
        Spinner spinner1=findViewById(R.id.state_spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.state,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);


        Spinner spinner2=findViewById(R.id.city_spinner);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        Spinner spinner3=findViewById(R.id.area_spinner);
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,R.array.area,android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);

        progressDialog = new ProgressDialog(this);
        family_name = (EditText) findViewById(R.id.fn);
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
         r1 = (RadioButton) findViewById(R.id.r1);
         r2 = (RadioButton) findViewById(R.id.r2);

    }


    public void register(View view) {


        String str_fn = family_name.getText().toString();
        String str_head = head.getText().toString();
        String str_addr = address1.getText().toString() +"," + address2.getText().toString();
        String str_consumption = consumption.getText().toString();
        String str_un = user_name.getText().toString();
        String str_password = password.getText().toString();
        String str_age = age.getText().toString();
        String str_confpassword = confpass.getText().toString();
        str_contact= contact.getText().toString();
        String str_pincode=pincode.getText().toString();

        if (TextUtils.isEmpty(str_fn)) {
            Toast.makeText(this, "Please enter Family Nmae", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_head)) {
            Toast.makeText(this, "Please enter name of the head", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_age)) {
            Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_addr)) {
            Toast.makeText(this, "Please enter Address", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_contact)) {
            Toast.makeText(this, "Please enter Contact number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_consumption)) {
            Toast.makeText(this, "Please enter monthly consumption", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_un)) {
            Toast.makeText(this, "Please enter Username", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_pincode)) {
            Toast.makeText(this, "Please enter Pincode", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(str_password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_confpassword)) {
            Toast.makeText(this, "Please Confirm your password", Toast.LENGTH_SHORT).show();
        } else if (str_password.equals(str_confpassword)) {
            progressDialog.setMessage("Registering...");
            progressDialog.show();
            //String type = "register";
            //BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            //backgroundWorker.execute(type,str_fn,str_contact,str_head,str_addr,str_consumption,str_un,str_password,str_age);
            final AsyncHttpClient client = new AsyncHttpClient();
            final RequestParams params = new RequestParams();

            number_temp = getRandomNumber(4);

            char ca=str_city.charAt(0);
            char cb=str_city.charAt(1);
            char cc=str_city.charAt(2);
            char aa=str_area.charAt(0);
            char ab=str_area.charAt(1);
            char ac=str_area.charAt(2);

            str_billnum=ca+cb+cc+"-"+aa+ab+ac+number_temp;

            params.add("fn", str_fn);
            params.add("head", str_head);
            params.add("address", str_addr);
            params.add("consumption", str_consumption);
            params.add("contact", str_contact);
            //params.add("age", str_age);
            params.add("username", str_un);
            params.add("password", str_password);
            params.add("bill_no",str_billnum);
            params.add("state",str_state);
            params.add("city", str_city);
            params.add("area", str_area);
            params.add("pincode",str_pincode);
            if(r1.isChecked())
            {
                params.add("type","COMMERCIAL");
            }
            else
            {
                params.add("type","DOMESTIC");
            }

            client.post("https://quizkrieg.000webhostapp.com/register.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    //progressDialog.dismiss();
                    Toast.makeText(Register.this, new String(responseBody), Toast.LENGTH_LONG).show();
                    progressDialog.setMessage("A few moments...");
                    //Intent i = new Intent(Register.this, MainActivity.class);
                    //startActivity(i);

                    client.post("https://quizkrieg.000webhostapp.com/backup.php", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            progressDialog.dismiss();
                            Toast.makeText(Register.this, new String(responseBody), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(Register.this, MainActivity.class);
                            startActivity(i);





                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });



                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
            String no=contact.getText().toString();
            String msg="Thank you for registering with us!!Your bill number is: " + str_billnum + ". Looking forward to serving you soon!";
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(no, null, msg, null,null);




        }
        else
        {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId()==R.id.city_spinner)
            {
                str_city=parent.getItemAtPosition(position).toString();
            }
            else if(parent.getId()==R.id.state_spinner)
            {
                str_state=parent.getItemAtPosition(position).toString();
            }
            else if(parent.getId()==R.id.area_spinner)
            {
                str_area=parent.getItemAtPosition(position).toString();
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public static String getRandomNumber(int digCount) {
        StringBuilder sb = new StringBuilder(digCount);
       int count=0;
        for(int i=0; i < digCount; i++)
            sb.append((char)('0' + rnd.nextInt(10)));
        String num=sb.toString();
        String newnum="";
        for(int i=0;i< num.length();i++)
        {
            count=count+1;
            newnum=newnum+num.charAt(i);
            if(count==4)
            {
                newnum=newnum + " ";
                count=0;
            }
        }


        return newnum;
    }

}
