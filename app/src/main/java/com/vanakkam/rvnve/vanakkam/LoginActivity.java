package com.vanakkam.rvnve.vanakkam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {

    EditText input_email, input_password;
    TextView link_register;
    Button btn_login;
    DataBaseHandler db;
    String username, password, StoredPassword;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        link_register = (TextView) findViewById(R.id.link_register);

        //Login button click event
        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //Validate the data
                if (validate()) {
                    btn_login.setEnabled(false);

                    // create and display a new ProgressBarDialog
                    progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {


                                    db = new DataBaseHandler(LoginActivity.this, null, null, 2);
                                    username = input_email.getText().toString();
                                    password = input_password.getText().toString();
                                    StoredPassword = db.getregister(username);


                                    if (password.equals(StoredPassword))
                                    {
                                        Toast.makeText(getApplicationContext(), username +" "+ "Login Successfull!!", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Username/Password incorrect", Toast.LENGTH_LONG).show();
                                        input_email.setText("");
                                        input_password.setText("");
                                        progressDialog.dismiss();
                                        btn_login.setEnabled(true);
                                    }
                                }
                            }, 3000);


                } else {
                    Toast.makeText(getBaseContext(), "Login Information Not Valid", Toast.LENGTH_LONG).show();
                    btn_login.setEnabled(true);
                }
            }
        });

        //Not Registered link click event
        link_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();

    }

    //Validate the input data
    public boolean validate() {
        boolean valid = true;

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

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
