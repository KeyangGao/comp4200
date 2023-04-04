package com.example.librarymanager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class databaseHelp extends SQLiteOpenHelper {
    private static final String DB_NAME = "librarymanager.db";
    private static final String Table_Name1 = "admin";//user
    private static final String Table_Name2 = "bookinfo";//book
    private static final String Table_Name3 = "borrow";//borrow book
    private static final String Table_Name4 = "return";//return book


    public static final String id="_id";
    public static final String Table_Book="bookinfo";
    public static final String Book_Id="bookid";//isbn number
    public static final String Book_Name="name";
    public static final String Book_Writer="writer";
    public static final String Book_Comment="comment";
    public static final String Book_Img="img";
    public static final String Book_Publicer="publicer";//

    private static final String Creat_table = "create table admin(_id integer primary key autoincrement,user text, " +
            "name text, password text,sex text, phone text)";


    public static final String Creat_table2="create table "+Table_Book+"("
            +id+" integer primary key autoincrement,"+Book_Id+","+Book_Name+","
            +Book_Writer+","+Book_Publicer+","+Book_Comment+")";


    private static final String Creat_table3 = "create table borrow(_Bid integer primary key autoincrement," +
            "Borname text,Bookid integer,bookname text,bookauthor text,nowtime text)";

    private static final String Creat_table4 = "create table pay(_Rid integer primary key autoincrement," +
            "Borname text,Bookid integer,bookname text,bookauthor text,nowtime text)";
    public databaseHelp(Context context) {
        super(context, DB_NAME, null, 2);
    }

    SQLiteDatabase db;

    //add user
    public void insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Table_Name1, null, values);
        db.close();
    }

    //add book
    public boolean inserbooktdata(String bookid,String name,String writer,
                                  String publicer,String comment){
        db=this.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(Book_Id,bookid);
        values.put(Book_Name,name);
        values.put(Book_Writer,writer);
        values.put(Book_Publicer,publicer);
        values.put(Book_Comment,comment);
        long line = db.insert("bookinfo",null,values);
        db.close();
        if(line!=-1){
            return true;
        }else{
            return false;
        }
    }

    //check user information
    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name1, null, null, null, null, null, null);
        return cursor;

    }

    //delete user
    public void del(int id) {
        if (db == null) {
            db = getWritableDatabase();
            db.delete(Table_Name1, "_id=?", new String[]{String.valueOf(id)});
        }
        db.close();

    }

    //search user by id
    public Cursor queryid(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name1, null, "_id=" + id, null, null, null, null);
        return cursor;
    }

    //search user by username
    public Cursor queryname(String name) {
        Log.i("inut:", "queryname: " + name);
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name1, null, "user=" + name, null, null, null, null);
        return cursor;
    }

    //insert table to database
    public void insertbookinfo(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Table_Name2, null, values);
        db.close();

    }

    //list all books
    public Cursor querybookinfo() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name2, null, null, null, null, null, null);
        return cursor;
    }

    //search book by id
    public Cursor querybookinfoid(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name2, null, "_id=" + id, null, null, null, null);
        return cursor;
    }

    //search book by name
    public Cursor querybookinfoname(String name) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name2, null, "name like ?", new String[]{"%"+name+"%"}, null, null, null, null);
        return cursor;
    }

    //delete book
    public void delbookinfo(int id) {
        if (db == null) {
            db = getWritableDatabase();
            db.delete(Table_Name2, "_id=?", new String[]{String.valueOf(id)});
        }
        db.close();
    }

    //insert borrow table to database
    public void insertorrowo(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Table_Name3, null, values);
        db.close();

    }

    //list borrow books information
    public Cursor queryborrowinfo() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name3, null, null, null, null, null, null);
        return cursor;

    }
    //check all borrow
    @SuppressLint("Range")
    public List<Map<String, Object>> queryborrow(){
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(Table_Name3, null,null, null, null, null, null);
        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_Bid",cursor.getString(cursor.getColumnIndex("_Bid")));
            map.put("Borname",cursor.getString(cursor.getColumnIndex("Borname")));
            map.put("Bookid",cursor.getString(cursor.getColumnIndex("Bookid")));
            map.put("bookname",cursor.getString(cursor.getColumnIndex("bookname")));
            map.put("bookauthor",cursor.getString(cursor.getColumnIndex("bookauthor")));
            map.put("nowtime",cursor.getString(cursor.getColumnIndex("nowtime")));
            data.add(map);
        }
        return data;
    }
    //check borrow books by name
    @SuppressLint("Range")
    public List<Map<String, Object>> queryborrow(String str){
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(Table_Name3, null,"Borname=?", new String[]{str}, null, null, null, null);
        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_Bid",cursor.getString(cursor.getColumnIndex("_Bid")));
            map.put("Borname",cursor.getString(cursor.getColumnIndex("Borname")));
            map.put("Bookid",cursor.getString(cursor.getColumnIndex("Bookid")));
            map.put("bookname",cursor.getString(cursor.getColumnIndex("bookname")));
            map.put("bookauthor",cursor.getString(cursor.getColumnIndex("bookauthor")));
            map.put("nowtime",cursor.getString(cursor.getColumnIndex("nowtime")));
            data.add(map);
        }
        return data;
    }

    //delete borrow books by id
    public void delborrow(int id) {
        if (db == null) {
            db = getWritableDatabase();
            db.delete(Table_Name3, "_Bid=?", new String[]{String.valueOf(id)});
        }
        db.close();
    }

//delete borrow by name
    public void delborrowbyname(String name) {
        if (db == null) {
            db = getWritableDatabase();
            db.delete(Table_Name3, "bookname=?", new String[]{String.valueOf(name)});
        }
        db.close();
    }

    //通过id查询borrow表里的信息
    public Cursor queryborrowinforname(String name) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(Table_Name3, null, "bookname=" + name, null, null, null, null);
        return cursor;
    }


    //add data to return table
    public void insertreturn(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(Table_Name4, null, values);
        db.close();

    }
    //check return table
    @SuppressLint("Range")
    public List<Map<String, Object>> queryreturn(){
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(Table_Name4, null,null, null, null, null, null);
        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_Rid",cursor.getString(cursor.getColumnIndex("_Rid")));
            map.put("Borname",cursor.getString(cursor.getColumnIndex("Borname")));
            map.put("Bookid",cursor.getString(cursor.getColumnIndex("Bookid")));
            map.put("bookname",cursor.getString(cursor.getColumnIndex("bookname")));
            map.put("bookauthor",cursor.getString(cursor.getColumnIndex("bookauthor")));
            map.put("nowtime",cursor.getString(cursor.getColumnIndex("nowtime")));
            data.add(map);
        }
        return data;
    }
    //在pay表中根据用户查询

    @SuppressLint("Range")
    public List<Map<String, Object>> queryreturnuser(String str){
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(Table_Name4, null,"Borname=?", new String[]{str}, null, null, null);
        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_Rid",cursor.getString(cursor.getColumnIndex("_Rid")));
            map.put("Borname",cursor.getString(cursor.getColumnIndex("Borname")));
            map.put("Bookid",cursor.getString(cursor.getColumnIndex("Bookid")));
            map.put("bookname",cursor.getString(cursor.getColumnIndex("bookname")));
            map.put("bookauthor",cursor.getString(cursor.getColumnIndex("bookauthor")));
            map.put("nowtime",cursor.getString(cursor.getColumnIndex("nowtime")));
            data.add(map);
        }
        return data;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;
        db.execSQL(Creat_table);
        db.execSQL(Creat_table2);
        db.execSQL(Creat_table3);
        db.execSQL(Creat_table4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(Creat_table);
                break;
            case 2:
                db.execSQL(Creat_table2);
                break;
            case 3:
                db.execSQL(Creat_table3);
                break;
            case 4:
                db.execSQL(Creat_table4);
                break;
            default:

        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}

