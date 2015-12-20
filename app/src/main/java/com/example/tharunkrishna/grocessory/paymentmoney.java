package com.example.tharunkrishna.grocessory;

/**
 * Created by Tharun Krishna on 12/20/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class paymentmoney extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
    }

    public void paynowtobank(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Payment");

        // set dialog message
        alertDialogBuilder
                .setMessage("Data Paid successfully!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent getin = getIntent();
                        SMSbean sm = new SMSbean();
                        sm.setNumber(getin.getStringExtra("phoneno"));
                        sm.setMessage("hi" + getin.getStringExtra("name") + "todays weather is :" + getin.getStringExtra("weather"));
                        sm.send();

                        //paymentmoney.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        Toast.makeText(this,"SMS send to USER",Toast.LENGTH_SHORT).show();
    }
}