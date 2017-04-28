package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView LVcontacts;
    ArrayList<Contacts> listContacts = new ArrayList<Contacts>();
    ContactAdapter adapter = new ContactAdapter(this, listContacts);
    //FileOutputStream fos = openFileOutput("contacts", Context.MODE_WORLD_READABLE);
    //

    public MainActivity() throws FileNotFoundException {
    }

    public void setLVcontacts(ListView LVcontacts) {
        this.LVcontacts = LVcontacts;
    }
    public void setAdapter(ContactAdapter adapter) {
        this.adapter = adapter;
    }
    public void setListContacts(ArrayList<Contacts> listContacts) {
        this.listContacts = listContacts;
    }

    public List<Contacts> getListContacts() {
        return listContacts;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        try {
            populateContacts();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        LVcontacts = (ListView)findViewById(R.id.listContacts);

        //listContacts.add(new Contacts("Austin", "16366924186747"));
        //listContacts.add(new Contacts("Lauro", "174816471624917"));

        LVcontacts.setAdapter(adapter);

        LVcontacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Messaging.class);
                startActivity(intent);
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                // User chose the "Settings" item, show the app settings UI...
                //System.out.println("settings clicked \n");
                //startActivity(new Intent(MainActivity.this, ContactsEdit.class));
                Intent i = new Intent(MainActivity.this, ContactsEdit.class);
                i.putParcelableArrayListExtra("listContacts",listContacts);
                startActivity(i);
                return true;

            case R.id.logout:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                //System.out.println("logout clicked\n");
                startActivity(new Intent(MainActivity.this, Login.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void populateContacts(){
        FileInputStream fis = null;
        try {
            fis = openFileInput("contacts");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            listContacts = (ArrayList) ois.readObject();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        try{
            ois.close();
        } catch (Exception e){e.printStackTrace();}
        adapter = new ContactAdapter(this, listContacts);
    }

    public ListView getLVcontacts() {
        return LVcontacts;
    }
}
