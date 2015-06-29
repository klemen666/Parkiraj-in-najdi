package si.uni_lj.fe.tnvu.parkirajinnajdi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;


public class GetWalkingDirection extends ActionBarActivity {
    String locationLatitudeKey = "locLatitude";
    String locationLongitudeKey = "locLongitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_walking_direction);

//        locationLatitudeKey = getResources().getString(R.string.locationLatitudeKey);

        /*
        locationLatitudeKey = getResources().getString(R.string.locationLatitudeKey);
        locationLongitudeKey = getResources().getString(R.string.locationLongitudeKey);

        TextView latitudeTV = (TextView) findViewById(R.id.latitudeTV);
        latitudeTV.setText("Latitude: ");

        TextView longitudeTV = (TextView) findViewById(R.id.longitudeTV);
        longitudeTV.setText("Longitude: ");
        */
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
        // read location and pass it to GetWalkingDirection

        TextView izpis = (TextView) findViewById(R.id.izpisFlag);

//        SharedPreferences latitudeSharedPref = this.getSharedPreferences(locationLatitudeKey,0);
//        double latitude = Double.longBitsToDouble(latitudeSharedPref.getLong(locationLatitudeKey, 0));
        izpis.setText("Latitude: ");

        /*
        if (locationFlag == true) {
            izpis.setText("Flag = true");
        }
        else {
            izpis.setText("Flag = false");
        } */

    /*
        SharedPreferences latitudeSharedPrefKey = getApplicationContext().getSharedPreferences(locationLatitudeKey,0);
        float latitude = latitudeSharedPrefKey.getFloat(locationLatitudeKey, 0);

        SharedPreferences longitudeSharedPrefKey = getApplicationContext().getSharedPreferences(locationLongitudeKey,0);
        float longitude = longitudeSharedPrefKey.getFloat(locationLongitudeKey, 0);


        TextView latitudeTV = (TextView) findViewById(R.id.latitudeTV);
        latitudeTV.setText("Latitude: " + latitude);

        TextView longitudeTV = (TextView) findViewById(R.id.longitudeTV);
        longitudeTV.setText("Longitude: " + longitude);
    */
    }

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
