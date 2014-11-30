package com.example.contact;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<Contact> {
	public ContactsAdapter(Context context, ArrayList<Contact> contacts){
		super(context, 0, contacts);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		Contact contact = getItem(position);
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, parent, false);	
		}
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
		TextView tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);
		TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
		tvName.setText(contact.getName());
		tvPhone.setText(contact.getPhone());
		tvEmail.setText(contact.getEmail());
		tvAddress.setText(contact.getAddress());
		return convertView;
	}

}
