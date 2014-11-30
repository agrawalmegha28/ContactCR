package com.example.contact;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowContactsActivity extends Activity {
	ArrayAdapter<Contact> contactsAdapter = null;
	ListView lvContacts;
	private ArrayList<Contact> contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_contacts);
		contacts = new ArrayList<Contact>();
		populate();
		contactsAdapter = new ContactsAdapter(this, contacts);
		lvContacts = (ListView) findViewById(R.id.lvContacts);
		lvContacts.setAdapter(contactsAdapter);
	}

	private void populate() {
		 // Retrieve student records
	      String URL = "content://com.example.contact/contacts";
	      Uri students = Uri.parse(URL);
	      Cursor c = managedQuery(students, null, null, null, "name");
	      if (c.moveToFirst()) {
	         do{
	            Contact newContact = new Contact();
	            newContact.setName(c.getString(c.getColumnIndex( ContactProvider.NAME)));
	            newContact.setPhone(c.getString(c.getColumnIndex( ContactProvider.PHONE)));
	            newContact.setEmail(c.getString(c.getColumnIndex( ContactProvider.EMAIL)));
	            newContact.setAddress(c.getString(c.getColumnIndex( ContactProvider.ADDRESS)));
	            contacts.add(newContact);
	         } while (c.moveToNext());
	      }
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
