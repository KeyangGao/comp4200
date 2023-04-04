package com.example.librarymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin_add_book extends BaseActivity implements View.OnClickListener {

    private databaseHelp helper;
    Uri uri;
    private EditText bookid,bookname,bookwriter,bookpublicer,bookdecription;
    private Button bookadd,bookback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);


        initdata();
        helper = new databaseHelp(getApplicationContext());


    }


    private void initdata() {


        bookid=findViewById(R.id.et_bookid);
        bookname=findViewById(R.id.et_bookname);
        bookwriter=findViewById(R.id.et_bookwriter);
        bookpublicer=findViewById(R.id.et_bookPublisher);
        bookdecription=findViewById(R.id.et_bookdescription);


        bookadd=findViewById(R.id.btn_bookcommit);
        bookback=findViewById(R.id.btn_bookback);

        bookadd.setOnClickListener((View.OnClickListener) this);
        bookback.setOnClickListener((View.OnClickListener) this);

    }

    boolean testid=true,testother=true;
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_bookcommit:
                if (bookid.getText().length()!=13) {
                    Toast.makeText(admin_add_book.this,"Please enter 13 ISBN !",Toast.LENGTH_SHORT).show();
                    testid=false;
                    break;
                }

                if(bookname.getText().length()==0){
                    Toast.makeText(admin_add_book.this,"Please enter full book imformation!",Toast.LENGTH_SHORT).show();
                    testother=false;
                    break;
                }
                if(testid==true&&testother==true){
                    helper.inserbooktdata(bookid.getText().toString(),bookname.getText().toString(),
                            bookwriter.getText().toString(),bookpublicer.getText().toString(),
                            bookdecription.getText().toString());
                    Toast.makeText(admin_add_book.this,"add success！",Toast.LENGTH_SHORT).show();
                    break;
                }


            case R.id.btn_bookback:
                Intent intentback=new Intent();
                intentback.setClass(admin_add_book.this,bookmanage.class);
                startActivity(intentback);
        }
    }
}