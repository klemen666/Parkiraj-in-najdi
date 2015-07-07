package si.uni_lj.fe.tnvu.parkirajinnajdi;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;


public class GoogleMapsActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    protected static final String TAG = GoogleMapsActivity.class.getName();

    // Keys for storing activity state in the Bundle.
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";


    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    protected GoogleApiClient mGoogleApiClient;
    protected Location mCurrentLocation;
    protected String mLastUpdateTime;
    protected LocationRequest mLocationRequest;

    private GoogleMap mMap;

    private Marker marker;

    public String latText;
    public String lonText;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        buildGoogleApiClient();

        Intent intent = getIntent();
        latText = intent.getStringExtra("latitude");
        lonText = intent.getStringExtra("longitude");

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_google_maps, menu);
        return true;
    }*/

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

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        /*Toast.makeText(this, getResources().getString(R.string.location_updated_message),
                Toast.LENGTH_SHORT).show();*/
        setLocation();
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

       /* if (mCurrentLocation != null) {
            mLatitudeText.setText(String.valueOf(mCurrentLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mCurrentLocation.getLongitude()));
        }*/
        setLocation();
    }



    protected void setLocation(){

        LatLng LatLngZemljevid = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLngZemljevid, 15));

        if(marker != null){
            marker.remove();
        }

        marker = mMap.addMarker(new MarkerOptions()
                .title("Lokacija")
                .snippet("Zapomnili smo si vaso lokacijo")
                .position(LatLngZemljevid));
                marker.showInfoWindow();
    }

    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    private void saveToFile(String latText, String lonText, String filename){
        try {

            // output flow
            FileOutputStream os = openFileOutput(filename, Context.MODE_PRIVATE);
            // join latitude and longitude
            String content = latText + "|" + lonText;
            // write content to file
            os.write(content.getBytes());
            // closing output flow
            os.close();

            Intent intentDirections = new Intent(this, GetDirections.class);
            startActivity(intentDirections);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    public void saveLocation(){

        SharedPreferences prevLoc = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prevLoc.edit();
        editor.putString("prevLat", Double.toString(mCurrentLocation.getLatitude()));
        editor.putString("prevLong", Double.toString(mCurrentLocation.getLongitude()));
        editor.commit();
    }

    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }


}
