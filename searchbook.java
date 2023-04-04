package com.example.librarymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class searchbook extends AppCompatActivity {


    private Button search;
    private EditText search_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbook);
        search = findViewById(R.id.search_btn);
        search_name = findViewById(R.id.search_name);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(searchbook.this, admin_search_bookinfo.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", search_name.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}