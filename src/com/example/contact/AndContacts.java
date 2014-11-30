package com.example.contact;

import java.util.ArrayList;

import com.example.contact.Contact;
import android.app.Activity;
import android.app.TabActivity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

public class AndContacts extends TabActivity {
	private ArrayList<AndContacts> todoItems;
	ToDoDBAdapter toDoDBAdapter;
	Cursor toDoListCursor;
	private static final String TAG = "AndContacts";
	TabHost mTabHost = null;
	private EditText txtname;
	private EditText txtphone;
	private EditText txtemail;
	private EditText txtpostaladdress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_and_contacts);
		toDoDBAdapter = new ToDoDBAdapter(this);
		todoItems = new ArrayList<AndContacts>();

		   // Open or create the database
		   toDoDBAdapter.open();
		   populateTodoList();
		mTabHost = getTabHost(); 
		mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("Contacts", getResources().getDrawable(R.drawable.contact)).setContent(R.id.contactsLayout)); 
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Music", getResources().getDrawable(R.drawable.music)).setContent(R.id.musicLayout)); 
        mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("Video", getResources().getDrawable(R.drawable.video)).setContent(R.id.videoLayout)); 
         
        mTabHost.setCurrentTab(0);
        
       txtname = (EditText) findViewById(R.id.txtname );
 	   txtphone = (EditText) findViewById(R.id.txtphone);
 	   txtemail = (EditText) findViewById(R.id.txtemail);
 	   txtpostaladdress = (EditText) findViewById(R.id.txtpostaladdress);
        

	}
	private void populateTodoList() {
		// Get all the todo list items from the database.
		   toDoListCursor = toDoDBAdapter. getAllToDoItemsCursor();
		   startManagingCursor(toDoListCursor);

		   // Update the array.
		   updateArray();
		
	}

	private void updateArray() {
		toDoListCursor.requery();
		   todoItems.clear();
		   if (toDoListCursor.moveToFirst())
		      do {
		         //String task = toDoListCursor.getString(ToDoDBAdapter.KEY_TASK);
		         //long created = toDoListCursor.getString(ToDoDBAdapter.KEY_CREATION_DATE);
		         AndContacts newItem = new AndContacts();
		         todoItems.add(0, newItem);
		      } while(toDoListCursor.moveToNext());

		   //aa.notifyDataSetChanged();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.and_contacts, menu);
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
	
	public void onClick(View view){
		// Add a new student record
	      ContentValues values = new ContentValues();

	      values.put(ContactProvider.NAME, 
	      txtname.getText().toString());
	      
	      values.put(ContactProvider.PHONE, 
	      txtphone.getText().toString());
	      
	      values.put(ContactProvider.EMAIL, txtemail.getText().toString());
	      values.put(ContactProvider.ADDRESS, txtpostaladdress.getText().toString());

	      Uri uri = getContentResolver().insert(
	    		  ContactProvider.CONTENT_URI, values);
	      
	      //Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
		Toast.makeText(this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
		finish();
		
	}
	public void onClickOld(View arg0)
	{
	   txtname = (EditText) findViewById(R.id.txtname );
	   txtphone = (EditText) findViewById(R.id.txtphone);
	   txtemail = (EditText) findViewById(R.id.txtemail);
	   txtpostaladdress = (EditText) findViewById(R.id.txtpostaladdress);
	     
	   ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
	   int rawContactInsertIndex = ops.size();

	   ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
	      .withValue(RawContacts.ACCOUNT_TYPE, null)
	      .withValue(RawContacts.ACCOUNT_NAME,null )
	      .build());
	   ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	      .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 
	                              rawContactInsertIndex)
	      .withValue(Data.MIMETYPE,Phone.CONTENT_ITEM_TYPE)
	      .withValue(Phone.NUMBER, txtphone.getText().toString())
	      .build());
	   ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
	      .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
	      .withValue(Data.MIMETYPE,StructuredName.CONTENT_ITEM_TYPE)
	      .withValue(StructuredName.DISPLAY_NAME, txtname.getText().toString())
	      .build());  
	               
	   try {
	      ContentProviderResult[] res = getContentResolver().
	                applyBatch(ContactsContract.AUTHORITY, ops);
	   } catch (RemoteException e) {
	     Log.d(TAG, "Remote Exception" ,e);
	     
	   } catch (OperationApplicationException e) {
		   Log.d(TAG, "Operation Exception" ,e);
	   }
	               
	   Uri outURI = Uri.parse(txtname.getText().toString());
	   Intent outData = new Intent();
	   outData.setData(outURI);
	   setResult(Activity.RESULT_OK, outData);
	   Toast.makeText(this, "Contact saved sucessfully",Toast.LENGTH_SHORT).show();
	   finish();
	}
	    

}
