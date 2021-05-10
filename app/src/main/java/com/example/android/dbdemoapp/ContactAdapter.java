package com.example.android.dbdemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.dbdemoapp.data.MyDbHandler;
import com.example.android.dbdemoapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    Context context;
    MyDbHandler myDbHandler;
    ArrayList<Contact> contacts;

    public ContactAdapter(Context context, MyDbHandler myDbHandler){
        this.context = context;
        this.myDbHandler = myDbHandler;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_row, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        try {
            contacts = myDbHandler.getAllContact();
        } catch (Error e){
            contacts = new ArrayList<>();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.contact_id.setText(contacts.get(position).getId() + ")");
        holder.contact_name.setText(contacts.get(position).getName());
        holder.contact_number.setText(contacts.get(position).getPhone_number());
    }

    @Override
    public int getItemCount() {
        return myDbHandler.getCount();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView contact_name, contact_number, contact_id;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contact_id = itemView.findViewById(R.id.contact_id);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
        }
    }
}
