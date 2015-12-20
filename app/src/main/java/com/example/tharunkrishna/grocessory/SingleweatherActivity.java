package com.example.tharunkrishna.grocessory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Tharun Krishna on 12/20/2015.
 */
public class SingleweatherActivity extends Activity {

    // JSON Node names
    private static final String TAG_CONTACTS = "weather";
    private static final String MIN_TEMP = "Min Temperature";
    private static final String EXACT_DESC = "Exact Description";
    private static final String EXACT_TEMP = "Exact Temperature";
    private static final String MAX_TEMP = "Max Temperature";
    private static final String DATE = "Date";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleweatherdisplay);


        //img declare
        String uri= "@drawable/noimage";
        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String date = in.getStringExtra(DATE);
        String min = in.getStringExtra(MIN_TEMP);
        String max = in.getStringExtra(MAX_TEMP);
        String etemp = in.getStringExtra(EXACT_TEMP);
        String desc = in.getStringExtra(EXACT_DESC);

        // Displaying all values on the screen
        TextView lbldate = (TextView) findViewById(R.id.date);
        TextView lblmintemp = (TextView) findViewById(R.id.mintemp);
        TextView lblmaxtemp = (TextView) findViewById(R.id.maxtemp);
        TextView lbletemp = (TextView) findViewById(R.id.etemp);
        TextView lbledesc = (TextView) findViewById(R.id.edesc);

        //Image view
        ImageView wimage = (ImageView)findViewById(R.id.imageButton);

        lbldate.setText("DATE: "+date);
        lblmintemp.setText("MIN TEMP: "+ min);
        lblmaxtemp.setText("MAX TEMP: "+max);
        lbletemp.setText("EXACT TEMP: "+etemp);
        lbledesc.setText("DESC OF WEATHER: "+desc);

        if(desc.contains("rain")){
            uri = "@drawable/rainimg";
        }else if(desc.equals("few clouds")){
             uri = "@drawable/clearimg";
        }else if(desc.contains("clear")){
             uri = "@drawable/cloud";
        }


        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        wimage.setImageDrawable(res);
    }
}
