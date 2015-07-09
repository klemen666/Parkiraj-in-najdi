package si.uni_lj.fe.tnvu.parkirajinnajdi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;


public class FirstActivity extends ActionBarActivity {
    // ime datoteke
    public String locationFile = "lokacija";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        /**
        * Intent on back key press.
         * Exit app.
        */

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

                String lokacija = readFromfile(locationFile);
        Log.d("comments", "First: String content = " + lokacija);
        if (lokacija.isEmpty()) {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(this,GetDirections.class);
            startActivity(intent2);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();

    }
    */

    private String readFromfile(String filename){

        // create input stream
        FileInputStream inputStream;

        //ugotovimo, koliko je velika datoteka
        File file = new File(getFilesDir(), filename);
        int length = (int) file.length();

        // initializing temp var
        byte[] bytes = new byte[length];

        // read data
        try {
            inputStream = openFileInput(filename);
            inputStream.read(bytes);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // convert from bytes to string
        String content = new String(bytes);
        return content;
    }

/*
    @Override
    protected void onResume() {
        super.onResume();

        // check location flag
        SharedPreferences locationFlagSharedPref = getApplicationContext().getSharedPreferences(locationFlagKey, 0);
        boolean locationFlag = locationFlagSharedPref.getBoolean(locationFlagKey, false);

        if (locationFlag == false) {
            // start MainActivity
            Intent intent1f = new Intent(this, MainActivity.class);
            startActivity(intent1f);
        }
        if (locationFlag == true) {
            // TO-DO: append location data (latitude and longitude)
            Intent intent2 = new Intent(this, GetDirections.class);
            startActivity(intent2);

        }
    }
*/
/*
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
