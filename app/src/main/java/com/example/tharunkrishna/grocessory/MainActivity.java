package com.example.tharunkrishna.grocessory;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    Button btnlogin,btnsignup;
    EditText edtusername,edtpass;
    final String LOGTAG ="Loginactivity";

    //variables
    String gname="",gemail="",gphone="",gpassword="",glocation="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        btnlogin = (Button)findViewById(R.id.btnlogin);
        btnsignup = (Button)findViewById(R.id.btnsign);
        edtusername = (EditText)findViewById(R.id.edtusername);
        edtpass = (EditText)findViewById(R.id.edtpassword);


    }

    public void signupactivity(View v) {
        Intent i  = new Intent(this,signupactivity.class);
        startActivity(i);
    }


    public void loginsuccess(View v) {
        boolean result = validateuser();
        if(result) {
            Intent i = new Intent(this, weatherreportactivity.class);
            /*Bundle bundle = new Bundle();
            bundle.putString("fname",gname);
            bundle.putString("femail",gemail);
            bundle.putString("fphone",gphone);
            bundle.putString("flocation",glocation);

            i.putExtra("farmerdata", bundle);*/
            i.putExtra("fname", gname);
            i.putExtra("femail", gemail);
            i.putExtra("fphone", gphone);
            i.putExtra("flocation",glocation);
            startActivity(i);
            finish();
        }
    }

    public boolean validateuser(){
        boolean flag = false;
        //if(edtusername.getText().toString().equals("") ||edtpass.getText().toString().equals("")) {
            // Show all the birthdays sorted by friend's name
            String whereclause = Userdatabase.EMAIL + "=?";
            String[] whereArgs = new String[]{edtusername.getText().toString()};
            String[] tableColumns = new String[]{Userdatabase.NAME, Userdatabase.PASSWORD, Userdatabase.EMAIL};
            String URL = "content://com.example.tharunkrishna.grocessory.usertable/friends";
            Uri friends = Uri.parse(URL);
            Cursor c = getContentResolver().query(friends, null, whereclause, whereArgs, null);
            if (c != null) {

                while (c.moveToNext()) {
                    gname = c.getString(1);
                    gemail = c.getString(3);
                    gphone = c.getString(2);
                    gpassword = c.getString(4);
                    glocation = c.getString(5);

                }
                Log.d(LOGTAG, gname + "" + gemail + "" + gpassword + "" + gphone + "" + gpassword);
                Toast.makeText(this, gname + gemail + gpassword + gphone + gpassword + glocation, Toast.LENGTH_SHORT).show();

                if ((edtusername.getText().toString()).equals(gemail) && (edtpass.getText().toString()).equals(gpassword)) {
                    Log.d(LOGTAG, "Login success");
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                    flag = true;
                } else {
                    //edtusername.findFocus();
                    Log.d(LOGTAG, "Login Failed, Please enter credentials properly");
                    Toast.makeText(this, "Login FAILED" + gemail + gpassword, Toast.LENGTH_SHORT).show();
                }


            } else {
                Log.d(LOGTAG, "error in inserting row.");
                Toast.makeText(this, c.toString(), Toast.LENGTH_SHORT).show();
            }
            c.close();
       // }

        return flag;
    }


}
