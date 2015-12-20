package com.example.tharunkrishna.grocessory;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Tharun Krishna on 12/19/2015.
 */
public class signupactivity extends Activity {
    static final int PICK_CURRENT_LOCATION = 1;
    EditText edtloc,ename,ephone,eemail,epswd,erepswd;
    String sname,semail,sphone,spassword,slocation;
    final String LOGTAG ="signupactivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuppage);
        edtloc = (EditText)findViewById(R.id.edtlocation);
        eemail = (EditText)findViewById(R.id.edtemail);
        ename = (EditText)findViewById(R.id.edtname);
        ephone = (EditText)findViewById(R.id.edtphone);
        epswd = (EditText)findViewById(R.id.edtpswd);
        erepswd = (EditText)findViewById(R.id.edtrepswd);
    }

    public void getthelocation(View v){
        Intent i = new Intent(this,locationoffarmer.class);
        startActivityForResult(i, PICK_CURRENT_LOCATION);

    }

    public void signupcomplete(View V){
        boolean flag = validatefields();
        if(flag) {
            boolean success =saverowtodatabase();
            if(success) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            }else{
                Log.d(LOGTAG,"error in inserting to database");
            }
        }
    }
    public boolean validatefields(){
        boolean result =false;
        if(ename.getText().toString()== ""){
            Toast.makeText(this,"Name field is empty",Toast.LENGTH_SHORT).show();
            ename.setSelected(true);
        }else if(eemail.getText().toString()==""){
            Toast.makeText(this,"email field is empty",Toast.LENGTH_SHORT).show();
            eemail.setSelected(true);
        }else if(ephone.getText().toString()=="" || ephone.getText().toString().length()!=10){
            Toast.makeText(this,"phone field is empty",Toast.LENGTH_SHORT).show();
            ephone.setSelected(true);
        }else if(eemail.getText().toString()==""){
            Toast.makeText(this,"email field is empty",Toast.LENGTH_SHORT).show();
            eemail.setSelected(true);
        }else if(epswd.getText().toString()=="" || erepswd.getText().toString()==""){
            Toast.makeText(this,"password field is empty",Toast.LENGTH_SHORT).show();
            epswd.setSelected(true);
        }else if(!epswd.getText().toString().equals(erepswd.getText().toString())){
            Toast.makeText(this,"password is not matching",Toast.LENGTH_SHORT).show();
            epswd.setSelected(true);
        }else if(edtloc.getText().toString()==""){
            Toast.makeText(this,"location is empty",Toast.LENGTH_SHORT).show();
            edtloc.setSelected(true);
        }else {
            sname =ename.getText().toString();
            sphone = ephone.getText().toString();
            semail = eemail.getText().toString();
            spassword = epswd.getText().toString();
            slocation = edtloc.getText().toString();
            result=true;
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        Toast.makeText(this,"result came",Toast.LENGTH_LONG).show();
        if(requestCode==PICK_CURRENT_LOCATION && resultCode == 2)
        {
            String message=data.getStringExtra("MESSAGE");
            edtloc.setText(message);
        }
    }

    public boolean saverowtodatabase(){
        // Add a new birthday record
        ContentValues values = new ContentValues();
        values.put(Userdatabase.NAME,sname);
        values.put(Userdatabase.PHONE,sphone);
        values.put(Userdatabase.EMAIL,semail);
        values.put(Userdatabase.PASSWORD,spassword);
        values.put(Userdatabase.LOCATION,slocation);

        Uri uri = getContentResolver().insert(Userdatabase.CONTENT_URI, values);

        Log.d(LOGTAG,"data inserted = "+uri.toString());
        return true;

    }
}
