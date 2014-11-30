package com.example.contact;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class AndContactsTester extends Activity {
	
    private int SHOW_SUB_ACTIVITY;
	private int PICK_CONTACT;
	public static final int REQUEST_CODE = 1;
	
	ArrayAdapter<Contact> contactsAdapter = null;
	ListView lvContacts;
	private ArrayList<Contact> contacts;
	
	//private TextView tvName;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_and_contacts_tester);
        contacts = new ArrayList<Contact>();
		contactsAdapter = new ContactsAdapter(this, contacts);
		lvContacts = (ListView) findViewById(R.id.lvContacts);
		lvContacts.setAdapter(contactsAdapter);
    }


	private void populate() {
		 // Retrieve student records
		  contacts.clear();
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
	      contactsAdapter.notifyDataSetChanged();
	   }

	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.and_contacts_tester, menu);
        return true;
    }
    public void AndContact(View v){
    	Intent intent = new Intent(AndContactsTester.this, AndContacts.class);
        startActivity(intent);
    	
    }
    
    public void ContactPicker(View v){
    	populate();
    	
    }
    
    public void onClear(View v){
    	contacts.clear();
    	contactsAdapter.notifyDataSetChanged();
    }
    
    public void ContactPickerOld(View v){
    	Intent intent1 = new Intent (Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
        //startActivity(intent1);
        startActivityForResult(intent1, REQUEST_CODE ); 
        
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
