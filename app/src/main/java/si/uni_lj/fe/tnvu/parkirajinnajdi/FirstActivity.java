package si.uni_lj.fe.tnvu.parkirajinnajdi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class FirstActivity extends ActionBarActivity {
    String firstRunKey = "checkIfFirstRun";
    String locationFlagKey = "LocationFlag";
    String locationLatitudeKey = "locLatitude";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        // keys
        firstRunKey = getResources().getString(R.string.firstRunKey);
        locationFlagKey = getResources().getString(R.string.locationFlagKey);
        locationLatitudeKey = getResources().getString(R.string.locationLatitudeKey);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // read from SharedPreferences
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(firstRunKey, 0);
        int isFirstRun = sharedPref.getInt(firstRunKey, 1);

//        SharedPreferences locationFlagSharedPref = getApplicationContext().getSharedPreferences(locationFlagKey, 0);
//        boolean locationFlag = locationFlagSharedPref.getBoolean(locationFlagKey,false);

        // check if first run
        if (isFirstRun == 1){

            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }
        else {
            Intent intent = new Intent(this,GetWalkingDirection.class);
            startActivity(intent);
        }
//        else {
//            // check location flag
//
//            if (locationFlag == false) {
//                // start MainActivity
//                Intent intent1f = new Intent(this, MainActivity.class);
//                startActivity(intent1f);
//            }
//            if (locationFlag == true) {
//                // TO-DO: append location data (latitude and longitude)
//                Intent intent2 = new Intent(this, GetWalkingDirection.class);
//                startActivity(intent2);
//
//            }
//        }

        // set isFirstRun to true
        SharedPreferences.Editor editor = sharedPref.edit();
        isFirstRun = 0;
        editor.putInt(firstRunKey, isFirstRun);
        editor.commit();
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
            Intent intent2 = new Intent(this, GetWalkingDirection.class);
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
