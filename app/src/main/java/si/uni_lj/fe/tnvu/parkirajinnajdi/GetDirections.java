package si.uni_lj.fe.tnvu.parkirajinnajdi;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);

        locationFlagKey = getResources().getString(R.string.locationFlagKey);
        locationLatitudeKey = getResources().getString(R.string.locationLatitudeKey);
        locationLongitudeKey = getResources().getString(R.string.locationLongitudeKey);

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

        SharedPreferences locationFlagSharedPref = getApplicationContext().getSharedPreferences(locationFlagKey, MODE_PRIVATE);
        boolean locationFlag = locationFlagSharedPref.getBoolean(locationFlagKey, false);
/*

        SharedPreferences locationLatitudeSharedPref = getApplicationContext().getSharedPreferences(locationLatitudeKey, MODE_PRIVATE);
        double latitude = Double.longBitsToDouble(locationLatitudeSharedPref.getLong(locationLatitudeKey, 0));

        TextView izpisLat = (TextView) findViewById(R.id.latText);
        izpisLat.setText("Latitude: " + latitude);
*/
        SharedPreferences locationLongitudeSharedPref = getApplicationContext().getSharedPreferences(locationLongitudeKey, MODE_PRIVATE);
        //double longitude = Double.longBitsToDouble(locationLongitudeSharedPref.getLong(locationLongitudeKey, 0));
        String longitude = locationLongitudeSharedPref.getString(locationLongitudeKey, "false");


        TextView izpisLon = (TextView) findViewById(R.id.lonText);
        izpisLon.setText("Longitude: " + longitude);


        TextView izpisLoc = (TextView) findViewById(R.id.izpisLocFlag);
        izpisLoc.setText("locationFlag (def F): " + locationFlag);

    }

    public void setLocationFlag(View view) {
        SharedPreferences locationFlagSharedPref = getApplicationContext().getSharedPreferences(locationFlagKey, 0);
        SharedPreferences.Editor editor = locationFlagSharedPref.edit();
        editor.putBoolean(locationFlagKey, false);
        editor.commit();
        Log.i("Comments", "GetDirections: locationFlag set to false");
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
