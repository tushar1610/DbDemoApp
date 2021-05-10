package com.example.android.dbdemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.android.dbdemoapp.data.MyDbHandler;
import com.example.android.dbdemoapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyDbHandler myDbHandler;
    RecyclerView recyclerView;
    ContactAdapter contactAdapter;
    EditText enter_contact_name, enter_contact_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_contact);

        myDbHandler = new MyDbHandler(MainActivity.this);
        contactAdapter = new ContactAdapter(MainActivity.this, myDbHandler);
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_contact :
                addContact();
                return true;
            case R.id.delete_all_contact :
                deleteAllContact();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllContact() {
        myDbHandler.deleteAllContact();
        contactAdapter.notifyDataSetChanged();
    }

    private void addContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        enter_contact_name = view.findViewById(R.id.enter_contact_name);
        enter_contact_number = view.findViewById(R.id.enter_contact_number);
        builder.setView(view);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = enter_contact_name.getEditableText().toString().trim();
                        String number = enter_contact_number.getEditableText().toString().trim();
                        Contact contact = new Contact();
                        contact.setName(name);
                        contact.setPhone_number(number);
                        myDbHandler.addContact(contact);
                        contactAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}