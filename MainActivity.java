package com.example.librarymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText user_ed, pwd_ed;
    private Button login_bt, register_bt;
    private Button im_bt;
    private CheckBox rember, auto_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private  void init()
    {
        user_ed = (EditText) findViewById(R.id.uname);
        pwd_ed = (EditText) findViewById(R.id.passwrd);
        //check box for rememe or auto loging
        rember = (CheckBox) findViewById(R.id.rmber_pwd);//remember password
        auto_login = (CheckBox) findViewById(R.id.auto_login);//auto login
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String Rusername = sp.getString("users", "");
        String Rpassword = sp.getString("passwords", "");
        boolean choseRemember = sp.getBoolean("remember", false);
        boolean choseAutoLogin = sp.getBoolean("autologin", false);


        //if remember the username and password, auto fill the fields
        if (choseRemember) {
            user_ed.setText(Rusername);
            pwd_ed.setText(Rpassword);
            rember.setChecked(true);
        }
        //if you choose auto login, select auto login
        if (choseAutoLogin) {
            auto_login.setChecked(true);
        }

        //register button
        register_bt = (Button) findViewById(R.id.register);
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to register activity
                Intent intent = new Intent(MainActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });
        //administrator login
        im_bt = (Button) findViewById(R.id.admin);
        im_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
        //login button click
        login_bt = (Button) findViewById(R.id.login);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String struser = user_ed.getText().toString();
                String strpwd = pwd_ed.getText().toString();
                databaseHelp help = new databaseHelp(getApplicationContext());
                SQLiteDatabase db = help.getWritableDatabase();
                boolean login_succ = false;
                Cursor cursor = db.query("admin", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("user"));
                        @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                        if (username.equals(struser) && password.equals(strpwd)) {
                            login_succ=true;
                            Intent intent = new Intent(MainActivity.this, contentActivity.class);
                            startActivity(intent);

                            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                            editor.putString("users", username);
                            editor.putString("passwords", password);
                            //remember or not
                            if (rember.isChecked()) {
                                editor.putBoolean("remember", true);
                            } else {
                                editor.putBoolean("remember", false);
                            }


                            //auto login
                            if (auto_login.isChecked()) {
                                editor.putBoolean("autologin", true);
                                Intent intent1 = new Intent(MainActivity.this, contentActivity.class);
                                startActivity(intent1);
                            } else {
                                editor.putBoolean("autologin", false);
                            }
                            editor.apply();
                        }

                    } while (cursor.moveToNext());

                }
                if(!login_succ){
                    Toast.makeText(MainActivity.this, "invali username or password, please try again", Toast.LENGTH_SHORT).show();
                }
                cursor.close();

            }

        });

    }
}