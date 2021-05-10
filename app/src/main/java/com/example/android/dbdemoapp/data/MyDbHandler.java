package com.example.android.dbdemoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.dbdemoapp.model.Contact;
import com.example.android.dbdemoapp.params.Params;

import java.util.ArrayList;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context){
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Params.KEY_NAME + " TEXT, "
                + Params.KEY_PHONE_NUMBER + " TEXT" + ")";
        Log.d("dbTushar" , "Query: " + create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_PHONE_NUMBER, contact.getPhone_number());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbTushar" , "Successfully inserted");
        db.close();
    }

    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;

        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone_number(cursor.getString(2));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }

        return contactList;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_PHONE_NUMBER, contact.getPhone_number());

        return db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public void deleteAllContact() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Params.TABLE_NAME);
        db.close();
    }
}
