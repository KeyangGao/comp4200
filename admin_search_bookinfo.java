package com.example.librarymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class admin_search_bookinfo extends AppCompatActivity {

    ListView listView;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search_bookinfo);
        init();
    }


    private void init() {
        listView = (ListView) findViewById(R.id.select_book_list);
        final databaseHelp help = new databaseHelp(getApplicationContext());
        Bundle bundle=this.getIntent().getExtras();
        name=bundle.getString("name");
        Cursor cursor = help.querybookinfoname(name);
        String from[] = {"name", "writer","publicer"};
        int to[] = {R.id.book_name, R.id.book_author, R.id.book_publish};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.admin_book_item, cursor, from, to);


        listView.setAdapter(adapter);
    }
}