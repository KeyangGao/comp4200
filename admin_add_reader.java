package com.example.librarymanager;

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

/**
 * add reader
 */
public class admin_add_reader extends BaseActivity {
    private EditText user_ed, username ,pwd_ed,  birthday, phone, sex;
    private Button addreader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_reader);
        init();
    }

    private void init() {
        user_ed = (EditText) findViewById(R.id.r_name);
        pwd_ed = (EditText) findViewById(R.id.r_password);
        username = findViewById(R.id.user_name);

        phone = findViewById(R.id.r_phone);
        sex = findViewById(R.id.r_sex);
        addreader = (Button) findViewById(R.id.r_register);
        addreader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String struser = user_ed.getText().toString();
                String strpwd = pwd_ed.getText().toString();
                String uname = username.getText().toString();
                //String birth = birthday.getText().toString();
                String phonenum = phone.getText().toString();
                String usersex = sex.getText().toString();
                //验证用户名是否存在
                databaseHelp help = new databaseHelp(getApplicationContext());
                SQLiteDatabase db = help.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("user", struser);
                values.put("name", uname);
                values.put("password", strpwd);
                values.put("sex", usersex);
                values.put("phone", phonenum);
                //values.put("birthday", birth);
                Cursor cursor = db.query("admin", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("user"));
                        if (username.equals(user_ed.getText().toString())) {
                            Toast.makeText(admin_add_reader.this, "reader exist", Toast.LENGTH_LONG).show();
                            ((EditText) findViewById(R.id.r_name)).setText("");
                            return;
                        }

                    } while (cursor.moveToNext());

                }
                cursor.close();
                help.insert(values);
                Toast.makeText(admin_add_reader.this, "reader added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(admin_add_reader.this, usermanage.class);
                startActivity(intent);
                ActivityCollector.finishAll();
            }
        });
    }
}
