package com.example.addandsearchbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class delete_book extends AppCompatActivity {
    private ListView listView;
    private ImageButton back_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_book);
        init();
    }

    private void init() {
        back_bt=(ImageButton)findViewById(R.id.deletebook_back);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(delete_book.this,manager_book.class);
                startActivity(intent);
            }
        });

        listView=(ListView)findViewById(R.id.delete_book_list);
        final databaseHelp help = new databaseHelp(getApplicationContext());
        Cursor cursor = help.querybookinfo();
        String from[] = {"_id","img", "bookid", "name"};
//        int to[] = {R.id.book_id, R.id.book_name, R.id.book_author,R.id.book_page,R.id.book_price,R.id.book_publish,R.id.book_intime};
        int to[] = {R.id.book_info_img, R.id.book_name};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.admin_book_item, cursor, from, to);
        listView.setAdapter(adapter);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final long temp = l;
                builder.setMessage("comfirm to delete？").setPositiveButton("comfirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        help.delbookinfo((int) temp);

                        Cursor cursor = help.querybookinfo();
                        String from[] = {"_id","img", "bookid", "name"};
                        int to[] = {R.id.book_info_img,R.id.book_name};
                        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.admin_book_item, cursor, from, to);
                        listView.setAdapter(adapter);

                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        help.close();
    }
}
