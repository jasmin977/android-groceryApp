package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText username,password,repassword;
    Button signin;

    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username= (EditText) findViewById(R.id.username_text);
        password= (EditText) findViewById(R.id.password_text);
        repassword= (EditText) findViewById(R.id.password2_text);
        signin= (Button) findViewById(R.id.resiter_btn);



        DB = new DBhelper(this);


    }

    public void register(View view) {

        String user =username.getText().toString();
        String pass =password.getText().toString();
        String repass =repassword.getText().toString();

          if (user.equals("") || pass.equals("") || repass.equals(""))
              Toast.makeText(Register.this, "Please enter all the fields" ,Toast.LENGTH_SHORT).show();
          else {
              if (pass.equals(repass)) {
                  Boolean checkuser = DB.check_username(user);

                  if(checkuser==false){
                      Boolean insert = DB.insertData(user,pass);
                             if (insert==true){
                                 Toast.makeText(Register.this, "Registered succesfully" ,Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getApplicationContext(),Home.class);
                                 startActivity(intent);
                             }
                             else {
                                 Toast.makeText(Register.this, "Registered failed" ,Toast.LENGTH_SHORT).show();

                             }
                  }
                  else {
                      Toast.makeText(Register.this, "user already exists" ,Toast.LENGTH_SHORT).show();

                  }


              }
              else {
                  Toast.makeText(Register.this, "passwords dont matching" ,Toast.LENGTH_SHORT).show();

              }
          }
    }

    public void go_login(View view) {
        Intent intent = new Intent(getApplicationContext(),Login_activity.class);
        startActivity(intent);
    }
}