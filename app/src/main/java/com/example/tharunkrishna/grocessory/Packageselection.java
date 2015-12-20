package com.example.tharunkrishna.grocessory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

/**
 * Created by Tharun Krishna on 12/20/2015.
 */
public class Packageselection extends Activity {
Button paymon;
    CheckBox r1 ,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selpackage);
        paymon = (Button)findViewById(R.id.btnpaymon);
        r1 = (CheckBox)findViewById(R.id.checkBox);
        r2 = (CheckBox)findViewById(R.id.checkBox2);


    }

    public void gotopaymoney(View v){
        if(r1.isChecked() || r2.isChecked()) {
            Intent input = getIntent();
            Intent i = new Intent(this, paymentmoney.class);
            i.putExtra("phoneno",input.getStringExtra("phoneno"));
            i.putExtra("name",input.getStringExtra("namef"));
            i.putExtra("weath",input.getStringExtra("weather"));
            startActivity(i);
            finish();
        }
    }
}
