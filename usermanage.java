package com.example.librarymanager;

import androidx.appcompat.app.AlertDialog;
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

public class usermanage extends AppCompatActivity {

    Button view, delete, add;
    databaseHelp db;
    EditText userRealname, username, password, phone, sex;
    EditText deleteReader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermanage);
        view = findViewById(R.id.searchreader);
        delete = findViewById(R.id.delreader);
        add = findViewById(R.id.addreader);
        db = new databaseHelp(this);
        addinit();
        deleteinit();
        viewinit();

    }

    private void viewinit() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer buffer;
                try (Cursor res = db.getdata()) {
                    if (res.getCount() == 0) {
                        Toast.makeText(usermanage.this, "No User Reader Exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("User :" + res.getString(0) + "\n");
                        buffer.append("Name :" + res.getString(1) + "\n");
                        buffer.append("Password :" + res.getString(2) + "\n");
                        buffer.append("Phone :" + res.getString(3) + "\n");
                        buffer.append("Sex :" + res.getString(4) + "\n\n");
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(usermanage.this);
                builder.setCancelable(true);
                builder.setTitle("User Reader Entries(User Table");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });


    }


    private void deleteinit() {
        deleteReader = (EditText) findViewById(R.id.r_name);
        delete = findViewById(R.id.delreader);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = deleteReader.getText().toString();
                Boolean checkDelete = db.delete(name);
                if(checkDelete)
                    Toast.makeText(usermanage.this, "User Reader Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(usermanage.this, "User Reader Not Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addinit() {
        userRealname = (EditText) findViewById(R.id.r_name);
        password = (EditText) findViewById(R.id.r_password);
        username = findViewById(R.id.user_name);
        phone = findViewById(R.id.r_phone);
        sex = findViewById(R.id.r_sex);

        //add a reader on click
        add = findViewById(R.id.addreader);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String struser = userRealname.getText().toString();
                String strpwd = password.getText().toString();
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
                        if (username.equals(userRealname.getText().toString())) {
                            Toast.makeText(usermanage.this, "user exists", Toast.LENGTH_LONG).show();
                            ((EditText) findViewById(R.id.r_name)).setText("");
                            return;
                        }

                    } while (cusror.moveToNext());

                }
                cusror.close();
                //check register information, length 6
                boolean testid = true, testnum = true;
                if (userRealname.getText().length() != 6) {
                    Toast.makeText(usermanage.this, "input 6 digits", Toast.LENGTH_SHORT).show();
                    testid = false;

                }

                if (phone.getText().length() != 11) {
                    Toast.makeText(usermanage.this, "input 11 digits phone number", Toast.LENGTH_SHORT).show();
                    testnum = false;
                }
                if (testid && testnum) {
                    help.insert(values);
                    Toast.makeText(usermanage.this, "Reader added!", Toast.LENGTH_LONG).show();
                    //back to main
                    Intent intent = new Intent(usermanage.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(usermanage.this, "please try again", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(usermanage.this, usermanage.class);
                    startActivity(intent);
                }
            }
        });

    }
}