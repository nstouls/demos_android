package oc.mooc.demos.ou_suis_je;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    // Widgets and attributes for Android API (Android)
    private Button btnAndroidGetLastLocation;
    private Button btnAndroidGetLocation;
    private Button btnAndroidUpdateLocation;
    private Button btnAndroidGetGeo;
    private TextView tvAndroidLastLocation;
    private TextView tvAndroidGetLocation;
    private TextView tvAndroidUpdateLocation;
    private Location AndroidLocation = null;
    final static int REQUEST_CODE_ANDROID_LAST_LOCATION=1;
    final static int REQUEST_CODE_ANDROID_GET_LOCATION=2;
    final static int REQUEST_CODE_ANDROID_UPDATE_LOCATION=3;


    // Widgets and attributes for Google Play Services API (GPS)
    private Button btnGPSGetLastLocation;
    private Button btnGPSGetLocation;
    private Button btnGPSUpdateLocation;
    private Button btnGPSGetGeo;
    private TextView tvGPSLastLocation;
    private TextView tvGPSGetLocation;
    private TextView tvGPSUpdateLocation;
    private Location GPSLocation = null;
    final static int REQUEST_CODE_GPS_LAST_LOCATION=4;
    final static int REQUEST_CODE_GPS_GET_LOCATION=5;
    final static int REQUEST_CODE_GPS_UPDATE_LOCATION=6;



    // Shared attributes
    final static String MY_PERMISSION=Manifest.permission.ACCESS_COARSE_LOCATION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAndroidGetLastLocation = (Button)   findViewById(R.id.btnAndroidGetLastLocation);
        btnAndroidGetLocation     = (Button)   findViewById(R.id.btnAndroidGetLocation);
        btnAndroidUpdateLocation  = (Button)   findViewById(R.id.btnAndroidUpdateLocation);
        btnAndroidGetGeo          = (Button)   findViewById(R.id.btnAndroidGetGeo);
        tvAndroidLastLocation     = (TextView) findViewById(R.id.tvAndroidLastLocation);
        tvAndroidGetLocation      = (TextView) findViewById(R.id.tvAndroidGetLocation);
        tvAndroidUpdateLocation   = (TextView) findViewById(R.id.tvAndroidUpdateLocation);



        btnAndroidGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeActionWithPermission();
            }
        });


        btnAndroidGetGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AndroidLocation !=null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + AndroidLocation.getLatitude()+","+ AndroidLocation.getLongitude())));
                } else {
                    Toast.makeText(MainActivity.this,"Commence par te localiser.", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Google Play Service initialization

        btnGPSGetLastLocation     = (Button)   findViewById(R.id.btnGPSGetLastLocation);
        btnGPSGetLocation         = (Button)   findViewById(R.id.btnGPSGetLocation);
        btnGPSUpdateLocation      = (Button)   findViewById(R.id.btnGPSUpdateLocation);
        btnGPSGetGeo              = (Button)   findViewById(R.id.btnGPSGetGeo);
        tvGPSLastLocation         = (TextView) findViewById(R.id.tvGPSLastLocation);
        tvGPSGetLocation          = (TextView) findViewById(R.id.tvGPSGetLocation);
        tvGPSUpdateLocation       = (TextView) findViewById(R.id.tvGPSUpdateLocation);


        btnGPSGetGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GPSLocation !=null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + GPSLocation.getLatitude()+","+ GPSLocation.getLongitude())));
                } else {
                    Toast.makeText(MainActivity.this,"Commence par te localiser.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public void makeActionWithPermission(){
        // A-t-on la permission de le faire ?
        if (ActivityCompat.checkSelfPermission(MainActivity.this, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            // Si "non", alors il faut demander la ou les permissions

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{MY_PERMISSION},
                    REQUEST_CODE_GPS_LAST_LOCATION);

            // Finir ici le traitement : ne pas bloquer.
        } else {
            FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
            Task<Location> t = locationClient.getLastLocation();
            t.addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Toast.makeText(MainActivity.this,"Et hop ! Une géoloc ! :D", Toast.LENGTH_LONG).show();
                        tvAndroidLastLocation.setText("Lat : "+location.getLatitude()+" / Long : "+location.getLongitude()+" (Précision : "+location.getAccuracy()+")");
                        AndroidLocation =location;
                    }
                }
            });
            t.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,"Désolé, la localisation n'a pas été trouvée", Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_GPS_LAST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this,"Réponse de l'utlisateur : oui", Toast.LENGTH_LONG).show();

                    // On relancer la méthode de réalisation de l'action. Cette fois, on a l'autorisation.
                    makeActionWithPermission();

                } else {
                    Toast.makeText(MainActivity.this,"Réponse de l'utlisateur : non", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}

