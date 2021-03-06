package com.example.android.what_error;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class request extends AppCompatActivity {
EditText qty,reason;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        qty=(EditText) findViewById(R.id.waterreqd_input);
        reason=(EditText) findViewById(R.id.reason_input);

        ImageView iv1=findViewById(R.id.user_logo);
        iv1.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);

        TextView tv1=findViewById(R.id.name_input);
        tv1.setText("" + MainActivity.dummy_username);


    }

    public void request (View view)
    {
        final String str_qty=qty.getText().toString();
        String str_reason=reason.getText().toString();
        //int int_qty=Integer.parseInt(str_qty);
        if(TextUtils.isEmpty(str_qty))
        {
            Toast.makeText(this, "Please enter required quantity", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(str_reason))
        {
            Toast.makeText(this, "Please enter valid reason", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog.setMessage("Requesting...");
            progressDialog.show();
            //String type = "register";
            //BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            //backgroundWorker.execute(type,str_fn,str_contact,str_head,str_addr,str_consumption,str_un,str_password,str_age);
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("username", MainActivity.dummy_username);
            params.add("password", MainActivity.dummy_password);
            params.add("qty", str_qty);
            params.add("reason", str_reason);
            client.post("https://quizkrieg.000webhostapp.com/request.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    progressDialog.dismiss();
                    //Toast.makeText(request.this, new String(responseBody), Toast.LENGTH_LONG).show();

                    new AlertDialog.Builder(request.this)
                            .setTitle("ALERT")
                            .setMessage("You have requested for " + str_qty + " litres of water and your request will be addressed shortly" )

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    Intent i = new Intent(request.this, User_Page.class);
                                    startActivity(i);

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)

                            .show();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
    }

}
