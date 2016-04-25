package com.vanakkam.rvnve.vanakkam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    EditText input_name, input_email, input_password;
    Button btn_signup;
    TextView link_login;
    DataBaseHandler db;
    ProgressDialog progressDialog;
    String strName, strEmail, strPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        input_name = (EditText) findViewById(R.id.input_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);

        btn_signup = (Button) findViewById(R.id.btn_signup);
        link_login = (TextView) findViewById(R.id.link_login);

        btn_signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (validate()) {
                    btn_signup.setEnabled(false);

                     strName = input_name.getText().toString();
                     strEmail = input_email.getText().toString();
                     strPass = input_password.getText().toString();

                    // create and display a new ProgressBarDialog
                    progressDialog = new ProgressDialog(RegisterActivity.this, R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Creating Account...");
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    db = new DataBaseHandler(RegisterActivity.this, null, null, 2);
                                    RegisterData reg = new RegisterData();
                                    reg.setName(strName);
                                    reg.setEmailId(strEmail);
                                    reg.setPassword(strPass);
                                    db.addregister(reg);
                                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                } }, 3000);

                } else {
                    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                    btn_signup.setEnabled(true);
                }
            }
        });

        link_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
       super.onDestroy();
    }
    //Validate the input data
    public boolean validate() {
        boolean valid = true;

        String name = input_name.getText().toString();
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            input_name.setError("at least 3 characters");
            valid = false;
        } else {
            input_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("enter a valid email address");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);
        }


        return valid;
    }
}