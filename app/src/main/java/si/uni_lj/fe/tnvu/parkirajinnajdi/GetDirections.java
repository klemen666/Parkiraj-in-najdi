package si.uni_lj.fe.tnvu.parkirajinnajdi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class GetDirections extends ActionBarActivity {
    String locationLatitudeKey = "locLatitude";
    String locationLongitudeKey = "locLongitude";
    String locationFlagKey = "LocationFlag";
    String sharedPref = "sharedPref";
    String locationPref = "locationPref";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);

        String locationFlagKey = getResources().getString(R.string.locationFlagKey);
        String locationLatitudeKey = getResources().getString(R.string.locationLatitudeKey);
        String locationLongitudeKey = getResources().getString(R.string.locationLongitudeKey);
        String sharedPref = getResources().getString(R.string.sharedPref);
        String locationPref = getResources().getString(R.string.locationPref);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_walking_direction, menu);
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
        /**
         * Read from shared preferences.
         * locationFlagKey
         * locationLatitudeKey
         * locationLongitudeKey
         */
/*
        SharedPreferences locationFlagSharedPref = getApplicationContext().getSharedPreferences(locationFlagKey, Context.MODE_PRIVATE);
        String locationFlag = locationFlagSharedPref.getString(locationFlagKey, "false");

        SharedPreferences locationLatitudeSharedPref = getApplicationContext().getSharedPreferences(locationLatitudeKey, MODE_PRIVATE);
        double latitude = Double.longBitsToDouble(locationLatitudeSharedPref.getLong(locationLatitudeKey, 0));

        SharedPreferences locationLongitudeSharedPref = getApplicationContext().getSharedPreferences(locationLongitudeKey, Context.MODE_PRIVATE);
        //double longitude = Double.longBitsToDouble(locationLongitudeSharedPref.getLong(locationLongitudeKey, 0));
        String longitude = locationLongitudeSharedPref.getString(locationLongitudeKey, "nekbrezvezennapis");
*/

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(sharedPref, MODE_MULTI_PROCESS);
//        String longitude = sharedPreferences.getString(locationLongitudeKey, "lonVal");
//        String latitude = sharedPreferences.getString(locationLatitudeKey, "latVal");
        boolean locationFlag = sharedPreferences.getBoolean(locationFlagKey, true);



        SharedPreferences locationSharedPref = getApplicationContext().getSharedPreferences(locationPref, MODE_PRIVATE);
        String latitude = locationSharedPref.getString(locationLatitudeKey, "0");
        String longitude = locationSharedPref.getString(locationLongitudeKey, "0");

        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");

        TextView izpisLat = (TextView) findViewById(R.id.latText);
        izpisLat.setText("Latitude: " + latitude);

        TextView izpisLon = (TextView) findViewById(R.id.lonText);
        izpisLon.setText("Longitude: " + longitude);

        TextView izpisLoc = (TextView) findViewById(R.id.izpisLocFlag);
        izpisLoc.setText("locationFlag (def F): " + locationFlag);
        Log.i("Comments", "GetDirections - prebrano: flag=" + locationFlag);

        saveLocation(latitude, longitude);

    }

    private void saveLocation(String latitude, String longitude) {
        SharedPreferences locationSharedPref = getApplicationContext().getSharedPreferences(locationPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = locationSharedPref.edit();
        editor.putString(locationLatitudeKey, latitude);
        editor.putString(locationLongitudeKey, longitude);
        editor.commit();
    }

    public void setLocationFlag(View view) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(sharedPref, MODE_MULTI_PROCESS);
        SharedPreferences.Editor editorG = sharedPreferences.edit();
        editorG.putBoolean(locationFlagKey, false);
        editorG.commit();
        Log.i("Comments", "GetDirections: locationFlag set to false");

        Intent intent = new Intent(this,FirstActivity.class);
        intent.putExtra("locationFlag", false);
//        startActivity(intent);
    }

/*
    public void showWalkNav(View view) {
        // vstavi intent na google maps navigation
        String dLat = "14.493945";
        String dLong = "46.046988";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + dLat + "," + dLong + "&mode=w"));
        startActivity(intent);
    }
    */

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

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
