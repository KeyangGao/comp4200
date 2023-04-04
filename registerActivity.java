package com.example.librarymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class registerActivity extends AppCompatActivity {

    EditText user_ed, username ,pwd_ed, phone, sex;
    Button back_bt, register_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        user_ed = (EditText) findViewById(R.id.r_name);
        pwd_ed = (EditText) findViewById(R.id.r_password);
        username = findViewById(R.id.user_name);
        phone = findViewById(R.id.r_phone);
        sex = findViewById(R.id.r_sex);
        //back button click
        back_bt = (Button) findViewById(R.id.r_back);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(registerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //register on click
        register_bt = (Button) findViewById(R.id.r_register);
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String struser = user_ed.getText().toString();
                String strpwd = pwd_ed.getText().toString();
                String uname = username.getText().toString();
                String phonenum = phone.getText().toString();
                String usersex = sex.getText().toString();

                databaseHelp help = new databaseHelp(getApplicationContext());
                ContentValues values = new ContentValues();
                values.put("user", struser);
                values.put("name", uname);
                values.put("password", strpwd);
                values.put("sex", usersex);
                values.put("phone", phonenum);
                SQLiteDatabase db = help.getWritableDatabase();
                //check if user already exist
                Cursor cusror = db.query("admin", null, null, null, null, null, null);
                if (cusror.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String username = cusror.getString(cusror.getColumnIndex("user"));
                        if (username.equals(user_ed.getText().toString())) {
                            Toast.makeText(registerActivity.this, "user exists", Toast.LENGTH_LONG).show();
                            ((EditText) findViewById(R.id.r_name)).setText("");
                            return;
                        }

                    } while (cusror.moveToNext());

                }
                cusror.close();
                //check register information, length 6
                boolean testid=true,testnum=true;
                if (user_ed.getText().length()!=6) {
                    Toast.makeText(registerActivity.this,"please input 6 digits",Toast.LENGTH_SHORT).show();
                    testid=false;

                }

                if(phone.getText().length()!=11){
                    Toast.makeText(registerActivity.this,"please input 11 digits phone number",Toast.LENGTH_SHORT).show();
                    testnum=false;
                }
                if(testid && testnum){
                    help.insert(values);
                    Toast.makeText(registerActivity.this, "register successfully", Toast.LENGTH_LONG).show();
                    //back to main
                    Intent intent = new Intent(registerActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(registerActivity.this, "please try again", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(registerActivity.this, registerActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}