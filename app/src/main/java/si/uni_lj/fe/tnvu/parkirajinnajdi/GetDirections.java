package si.uni_lj.fe.tnvu.parkirajinnajdi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class GetDirections extends ActionBarActivity {
    public String locationFile = "lokacija";

    public String latitude;
    public String longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);


        String lokacija = readFromfile(locationFile);
        String[] loca = lokacija.split("-");

        latitude = loca[0];
        longitude = loca[1];


        TextView izpisLat = (TextView) findViewById(R.id.latText);
        izpisLat.setText("Latitude: " + latitude);

        TextView izpisLon = (TextView) findViewById(R.id.lonText);
        izpisLon.setText("Longitude: " + longitude);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_walking_direction, menu);
        return true;
    }

/*
    @Override
    public void onStart() {
        super.onStart();

        String lokacija = readFromfile(locationFile);
        String[] loca = lokacija.split("-");

        String latitude = loca[0];
        String longitude = loca[1];


        TextView izpisLat = (TextView) findViewById(R.id.latText);
        izpisLat.setText("Latitude: " + latitude);

        TextView izpisLon = (TextView) findViewById(R.id.lonText);
        izpisLon.setText("Longitude: " + longitude);
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
        Log.d("comments", "Get Dir: String content = " + content);
        return content;
    }


    public void izbrisiLokacijo(View view){
        try {
            // output flow
            FileOutputStream os = openFileOutput(locationFile, Context.MODE_PRIVATE);
            // join latitude and longitude
            String content = "";
            // write content to file
            os.write(content.getBytes());
            // closing output flow
            os.close();

            Log.d("comments", "Get Dir: izbrisiLokacijo");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } catch (Exception e) {
            Log.d("comments", "Get Dir: exception");
            e.printStackTrace();
        }
    }

    public void walkNavigation(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
        Uri.parse(String.format("google.navigation:q=" + latitude +"," + longitude + "&mode=w")));
        startActivity(intent);
    }

    public void busNavigation(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(String.format("google.navigation:q=" + latitude +"," + longitude + "&mode=b")));
        startActivity(intent);
    }

    public void driveNavigation(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(String.format("google.navigation:q=" + latitude +"," + longitude + "&mode=d")));
        startActivity(intent);
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
