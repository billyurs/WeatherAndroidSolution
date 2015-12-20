package com.example.tharunkrishna.grocessory;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * Created by Tharun Krishna on 12/19/2015.
 */
public class weatherreportactivity extends ListActivity {
   // TextView welcomename;

    private ProgressDialog pDialog;
String weathermessage="";
    // JSON Node names
    private static final String TAG_CONTACTS = "weather";
    private static final String MIN_TEMP = "Min Temperature";
    private static final String EXACT_DESC = "Exact Description";
    private static final String EXACT_TEMP = "Exact Temperature";
    private static final String MAX_TEMP = "Max Temperature";
    private static final String DATE = "Date";


    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    String url;
    String smintemp="",sexdesc="",sextemp="",sexmaxtemp="",sexdate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.weatherlayout);
       // welcomename = (TextView)findViewById(R.id.txtwelcome);
       /* Bundle extras = getIntent().getExtras();
        String name =  extras.getString("fname");*/

        Intent intent = getIntent();
        String name = intent.getStringExtra("fname");
        String loc = intent.getStringExtra("flocation");
        if(loc.equalsIgnoreCase("bengaluru"))
            loc="bangalore";
        // URL to get contacts JSON
          url = "http://sparkapp.pythonanywhere.com/?details=place:"+loc+"%7Cdaysflag:true";

        contactList = new ArrayList<HashMap<String, String>>();
//        welcomename.setText("Hi, Welcome "+name);
        Toast.makeText(this,"Hi, Welcome "+name,Toast.LENGTH_SHORT).show();
        ListView lv = getListView();
       // ListView lv = (ListView)findViewById(R.id.list);
        // Listview on item click listener
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                 smintemp = ((TextView) view.findViewById(R.id.mintemp))
                        .getText().toString();
                 sexdesc = ((TextView) view.findViewById(R.id.edesc))
                        .getText().toString();
                 sextemp = ((TextView) view.findViewById(R.id.etemp))
                        .getText().toString();
                 sexmaxtemp = ((TextView) view.findViewById(R.id.maxtemp))
                        .getText().toString();
                 sexdate = ((TextView) view.findViewById(R.id.date))
                        .getText().toString();

                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(),
                        SingleweatherActivity.class);
                in.putExtra(DATE,sexdate);
                in.putExtra(MIN_TEMP, smintemp);
                in.putExtra(EXACT_DESC, sexdesc);
                in.putExtra(EXACT_TEMP, sextemp);
                in.putExtra(MAX_TEMP, sexmaxtemp);

                startActivity(in);

            }
        });

        // Calling async task to get json
        new GetWeather().execute();
}

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetWeather extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(weatherreportactivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    contacts = jsonObj.getJSONArray(TAG_CONTACTS);

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String mintemp = c.getString(MIN_TEMP);
                        String exactdesc = c.getString(EXACT_DESC);
                        String exacttemp = c.getString(EXACT_TEMP);
                        String maxtemp = c.getString(MAX_TEMP);
                        String dateofw = c.getString(DATE);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put(DATE,"DATE: " + dateofw);
                        contact.put(MIN_TEMP,"MIN TEMP: "+ mintemp);
                        contact.put(EXACT_DESC,"EXACT DESC: "+ exactdesc);
                        contact.put(EXACT_TEMP,"EXACT TEMP: "+ exacttemp);
                        contact.put(MAX_TEMP, "MAX TEMP: "+ maxtemp);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                    weathermessage = contactList.get(1).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    weatherreportactivity.this, contactList,
                    R.layout.list_item, new String[] { DATE,MIN_TEMP, EXACT_DESC,
                    EXACT_TEMP,MAX_TEMP }, new int[] { R.id.date,R.id.mintemp,
                    R.id.edesc, R.id.etemp,R.id.maxtemp });

            setListAdapter(adapter);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // do something based on first item click
                // Starting single contact activity

                Intent ggeett = getIntent();

                Intent in = new Intent(getApplicationContext(),
                        Packageselection.class);
                in.putExtra("phoneno",ggeett.getStringExtra("fname"));
                in.putExtra("namef", ggeett.getStringExtra("fphone"));
                in.putExtra("weather", weathermessage);

                startActivity(in);
                break;
            case R.id.logout:
                // do something based on second item
                Intent i2 = new Intent(this,MainActivity.class);
                startActivity(i2);
                finish();
                break;
            case R.id.exit:
                // do something based on second item
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
