package com.example.librarymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class bookmanage extends AppCompatActivity {

    Button addbtn, seabtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmanage);
        addbtn=findViewById(R.id.book_add);
        seabtn=findViewById(R.id.book_search);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(bookmanage.this,admin_add_book.class);
                startActivity(intent);
            }
        });

        seabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(bookmanage.this, searchbook.class);
                startActivity(intent);
            }
        });

    }
}