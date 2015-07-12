package si.uni_lj.fe.tnvu.parkirajinnajdi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GetDirections extends ActionBarActivity {
    public String locationFile = "lokacija";

    public String latitude;
    public String longitude;
    public String parkingTime;
    public String locationAddress;
    public long parkingTimeLong;
    public long currentTime;
    public String timeDiff;
    TextView timePrint;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_directions);


        String lokacija = readFromfile(locationFile);
        String[] loca = lokacija.split("-");

        latitude = loca[0];
        longitude = loca[1];
        parkingTime = loca[2];
        locationAddress = loca[3];

        timeDiff = getTimeDiff(parkingTime);



        TextView locationPrint = (TextView) findViewById(R.id.locationPrintOut);
        locationPrint.setText(getResources().getString(R.string.naslov) + locationAddress);

        timePrint = (TextView) findViewById(R.id.timePrintOut);
        handler.post(showTimeDiff);
        //timePrint.setText("Cas parkiranja " + timeDiff);

/*
        TextView izpisLat = (TextView) findViewById(R.id.latText);
        izpisLat.setText("Latitude: " + latitude);

        TextView izpisLon = (TextView) findViewById(R.id.lonText);
        izpisLon.setText("Longitude: " + longitude);
*/
    }


    private Runnable showTimeDiff = new Runnable() {
        @Override
        public void run() {
            timeDiff = getTimeDiff(parkingTime);
            timePrint.setText(getResources().getString(R.string.casParkiranja) + " " + timeDiff);
            handler.postDelayed(showTimeDiff, 1000);
        }
    };

    /*
    Get current time and time difference between current and "parking time".
     */
    public String getTimeDiff(String parkingTime) {
        parkingTimeLong = Long.valueOf(parkingTime);
        currentTime = System.currentTimeMillis();
        Long timeDiffLong = currentTime - parkingTimeLong;

        long diffSeconds = timeDiffLong / 1000 % 60;
        long diffMinutes = timeDiffLong / (60 * 1000) % 60;
        long diffHours = timeDiffLong / (60 * 60 * 1000) % 24;

        String minutes = String.format("%02d", diffMinutes);
        String hours = String.format("%02d", diffHours);
        return hours + ":" + minutes;
        // return (new SimpleDateFormat("hh:mm")).format(timeDiffLong);
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



    /*
    Save empty string to file.
    Also used in FirstActivity to check if location is saved.
     */

    public void izbrisiLokacijo(){
        try {
            Log.d("comments", "izbrisiLokacijo");
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



    /*
    Alert dialog.
     */

    public void confirmDelete(View view) {
        Log.d("comments", "Confirm Delete");
        AlertDialog.Builder dialog = new AlertDialog.Builder(GetDirections.this);
        // dialog text
        dialog.setMessage(getApplicationContext().getResources().getString(R.string.confirmDelete));
        // Add the buttons
        // dialog Positive Button
        dialog.setPositiveButton(getApplicationContext().getResources().getString(R.string.resetFlag), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked OK button
                        izbrisiLokacijo();
                    }
                }
        );
        // dialog Negative Button
        dialog.setNegativeButton(getApplicationContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Set other dialog properties

        // Create the AlertDialog
        // AlertDialog dialog = builder.create();
        dialog.show();

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
    Exit application on back key press.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(this, FirstActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
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
