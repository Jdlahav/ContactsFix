package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ContactsEdit extends AppCompatActivity {
    public String userID;
    public String Alias;
    final Context c = this;
    public ListView LVcontacts;
    ArrayList<Contacts> listContacts;
    ContactAdapter adapter2;

    public ContactsEdit() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_edit);
        Toolbar myToolbarRename = (Toolbar) findViewById(R.id.my_toolbar_rename);
        setSupportActionBar(myToolbarRename);
        getSupportActionBar().setTitle("Manage Contacts");
        System.out.println("Before");
        listContacts = getIntent().getParcelableArrayListExtra("listContacts");
        LVcontacts = (ListView)findViewById(R.id.listContacts2);
        adapter2 = new ContactAdapter(this, listContacts);
        System.out.println("During");
        LVcontacts.setAdapter(adapter2);
        System.out.println("After");
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        final Button add = (Button)findViewById(R.id.ADD);
        Button remove = (Button)findViewById(R.id.REMOVE);
        Button edit = (Button)findViewById(R.id.EDIT);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText usernameContact = (EditText) mView.findViewById(R.id.userID);
                final EditText aliasContact = (EditText) mView.findViewById(R.id.Alias);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                userID = usernameContact.getText().toString();
                                Alias = aliasContact.getText().toString();
                                Contacts contact =  new Contacts(Alias, userID);
                                //listContacts = getIntent().getParcelableArrayListExtra("listContacts");
                                listContacts.add(contact);
                                FileOutputStream fos = null;
                                try {
                                    fos = openFileOutput("contacts", Context.MODE_PRIVATE);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                ObjectOutputStream oos = null;
                                try {
                                    oos = new ObjectOutputStream(fos);
                                } catch (IOException e) {e.printStackTrace();

                                }
                                try {
                                    oos.writeObject(listContacts);
                                } catch(IOException exception) {
                                    exception.printStackTrace();
                                }
                                try {
                                    oos.close();
                                }catch (Exception e){e.printStackTrace();}

                                recreate();




                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.items_edit, menu);
        return true;
    }
}
