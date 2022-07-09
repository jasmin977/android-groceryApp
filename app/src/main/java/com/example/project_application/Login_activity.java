package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

public class Login_activity extends AppCompatActivity {

    EditText username,password;
    Button login;
    DBhelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login_activity);

        username= (EditText) findViewById(R.id.username_text);
        password= (EditText) findViewById(R.id.password_text);

        login= (Button) findViewById(R.id.login_btn);
        DB = new DBhelper(this);

    }

    @SuppressLint("WrongConstant")
    public void login_func(View view) {

        String user =username.getText().toString();
        String pass =password.getText().toString();

        if (user.equals("") || pass.equals(""))
            Toast.makeText(Login_activity.this, "Please enter all the fields" ,Toast.LENGTH_SHORT).show();
        else {
            Boolean insert_user_password =DB.check_username_password(user,pass);
             if (insert_user_password==true){

                 if((user.equals("yasmin"))&&(pass.equals("1999"))){
                     StyleableToast.makeText(Login_activity.this, "Welcome BOSS" ,R.style.boss).show();
                     Intent intent = new Intent(Login_activity.this,Product_items.class);
                     startActivity(intent);
                 }


                else {
                     StyleableToast.makeText(Login_activity.this, "Welcome Back",R.style.boss).show();
                     Intent intent = new Intent(Login_activity.this, Home.class);
                     startActivity(intent);
                 }
            }
            else {
                Toast.makeText(Login_activity.this, "Invalid" ,Toast.LENGTH_SHORT).show();

            }

        }



    }

    public void go_to_register(View view) {
        Intent intent = new Intent(Login_activity.this,Register.class);
        startActivity(intent);
    }
}