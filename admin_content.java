package com.example.librarymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_content extends AppCompatActivity {

    Button searchbtn,bookmanagebtn,usermanagebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_content);

        searchbtn=findViewById(R.id.ad_bookinfor);
        bookmanagebtn=findViewById(R.id.ad_borrowinfor);
        usermanagebtn=findViewById(R.id.ad_returninfor);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin_content.this,searchmessage.class);
                startActivity(intent);
            }
        });

        bookmanagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin_content.this,bookmanage.class);
                startActivity(intent);
            }
        });

        usermanagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin_content.this,usermanage.class);
                startActivity(intent);
            }
        });




    }
}