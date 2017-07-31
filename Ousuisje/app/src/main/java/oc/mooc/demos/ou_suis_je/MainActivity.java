package oc.mooc.demos.ou_suis_je;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
    private boolean isAndroidUpdating=false;
    LocationManager androidLocationManager;
    LocationListener androidLocationListener;

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
    private boolean isGPSUpdating=false;
    private FusedLocationProviderClient GPSLocationClient;
    private LocationCallback GPSLocationCallback;

    final static int REQUEST_CODE_GPS_LAST_LOCATION=4;
    final static int REQUEST_CODE_GPS_GET_LOCATION=5;
    final static int REQUEST_CODE_GPS_UPDATE_LOCATION=6;

    // Shared attributes
    private String MY_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ================================================================================
        // Android location demo initialization

        btnAndroidGetLastLocation = (Button)   findViewById(R.id.btnAndroidGetLastLocation);
        btnAndroidGetLocation     = (Button)   findViewById(R.id.btnAndroidGetLocation);
        btnAndroidUpdateLocation  = (Button)   findViewById(R.id.btnAndroidUpdateLocation);
        btnAndroidGetGeo          = (Button)   findViewById(R.id.btnAndroidGetGeo);
        tvAndroidLastLocation     = (TextView) findViewById(R.id.tvAndroidLastLocation);
        tvAndroidGetLocation      = (TextView) findViewById(R.id.tvAndroidGetLocation);
        tvAndroidUpdateLocation   = (TextView) findViewById(R.id.tvAndroidUpdateLocation);


        btnAndroidGetLastLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidGetLastLocation();
            }
        });
        btnAndroidGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidGetLocation();
            }
        });
        btnAndroidUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidUpdateLocation();
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





        // ================================================================================
        // Google Play Service demo initialization

        btnGPSGetLastLocation     = (Button)   findViewById(R.id.btnGPSGetLastLocation);
        btnGPSGetLocation         = (Button)   findViewById(R.id.btnGPSGetLocation);
        btnGPSUpdateLocation      = (Button)   findViewById(R.id.btnGPSUpdateLocation);
        btnGPSGetGeo              = (Button)   findViewById(R.id.btnGPSGetGeo);
        tvGPSLastLocation         = (TextView) findViewById(R.id.tvGPSLastLocation);
        tvGPSGetLocation          = (TextView) findViewById(R.id.tvGPSGetLocation);
        tvGPSUpdateLocation       = (TextView) findViewById(R.id.tvGPSUpdateLocation);

        GPSLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnGPSGetLastLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSGetLastLocation();
            }
        });
        btnGPSGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSGetLocation();
            }
        });
        btnGPSUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSUpdateLocation();
            }
        });

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

        // ================================ end of onCreate ================================
    }











    // ============================ Android location API methods ===========================



    public void androidGetLastLocation(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,MY_PERMISSION)) {
                Toast.makeText(MainActivity.this, "Permission nécessaire avait déjà été refusée.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Demande de permission lancée.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{MY_PERMISSION},
                        REQUEST_CODE_ANDROID_LAST_LOCATION);
            }
        } else {
            androidLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Location loc = androidLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(loc==null) {
                Toast.makeText(MainActivity.this, "Pas de localisation disponible", Toast.LENGTH_LONG).show();
            } else {
                AndroidLocation = publishLocation(loc, tvAndroidLastLocation, "Et hop ! Une géoloc gratuite ! :D");
            }
        }
    }


    public void androidGetLocation(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,MY_PERMISSION)) {
                Toast.makeText(MainActivity.this, "Permission nécessaire avait déjà été refusée.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Demande de permission lancée.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{MY_PERMISSION},
                        REQUEST_CODE_ANDROID_GET_LOCATION);
            }
        } else {
            androidLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            androidLocationListener = new LocationListener() {
                public void onLocationChanged(Location locationResult) {
                    publishLocation(locationResult, tvAndroidGetLocation, null);
                }
                public void onStatusChanged(String provider, int status, Bundle extras) {}
                public void onProviderEnabled(String provider) {}
                public void onProviderDisabled(String provider) {}
            };

            // Register the listener with the Location Manager to receive location updates
            androidLocationManager.requestSingleUpdate(
                    LocationManager.NETWORK_PROVIDER,
                    androidLocationListener,
                    null);
        }
    }
    public void androidUpdateLocation(){
        if(isAndroidUpdating) {
            androidUpdateLocationStop();
        } else {

            if (ActivityCompat.checkSelfPermission(MainActivity.this, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,MY_PERMISSION)) {
                    Toast.makeText(MainActivity.this, "Permission nécessaire avait déjà été refusée.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Demande de permission lancée.", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{MY_PERMISSION},
                            REQUEST_CODE_ANDROID_UPDATE_LOCATION);
                }
            } else {

                androidLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

                androidLocationListener = new LocationListener() {
                    public void onLocationChanged(Location locationResult) {
                        publishLocation(locationResult, tvAndroidUpdateLocation, null);
                    }
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    public void onProviderEnabled(String provider) {}
                    public void onProviderDisabled(String provider) {}
                };

                // Register the listener with the Location Manager to receive location updates
                androidLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000, // in milliseconds
                        50, // in meters
                        androidLocationListener);
                btnAndroidUpdateLocation.setText("Stop");
                isAndroidUpdating=true;
            }
        }
    }


    public void androidUpdateLocationStop(){
        isAndroidUpdating=false;
        btnAndroidUpdateLocation.setText("Suis moi.");
        if(androidLocationListener!=null) {
            if (androidLocationManager == null) {
                androidLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            }
            androidLocationManager.removeUpdates(androidLocationListener);
            androidLocationManager=null;
            androidLocationListener=null;
        }
    }








    // ========================== Google Play Services API methods =========================

    public void GPSGetLastLocation(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,MY_PERMISSION)) {
                Toast.makeText(MainActivity.this, "Permission nécessaire avait déjà été refusée.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Demande de permission lancée.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{MY_PERMISSION},
                        REQUEST_CODE_GPS_LAST_LOCATION);
            }
        } else {
            FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
            Task<Location> t = locationClient.getLastLocation();
            t.addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    GPSLocation =publishLocation(location, tvGPSLastLocation, "Et hop ! Une géoloc gratuite ! :D");
                }
            });
            t.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,"Désolé, la localisation n'a pas abouti", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    public void GPSGetLocation(){
        Toast.makeText(MainActivity.this,"Fonctionnalité non supportée par Google Play Services", Toast.LENGTH_LONG).show();
    }


    public void GPSUpdateLocation(){
        if(isGPSUpdating) {
            GPSUpdateLocationStop();
        } else {

            if (ActivityCompat.checkSelfPermission(MainActivity.this, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
/*                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,MY_PERMISSION)) {
                    Toast.makeText(MainActivity.this, "Permission nécessaire avait déjà été refusée.", Toast.LENGTH_LONG).show();
                } else {
*/
                    Toast.makeText(MainActivity.this, "Demande de permission lancée.", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{MY_PERMISSION},
                            REQUEST_CODE_GPS_UPDATE_LOCATION);
//                }
            } else {
                GPSLocationCallback= new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        publishLocation(locationResult.getLastLocation(), tvGPSUpdateLocation, "Nb locs : "+locationResult.getLocations().size());
                    }
                };

                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(10000);
                locationRequest.setFastestInterval(5000);
                locationRequest.setSmallestDisplacement(50); // en mètres
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                GPSLocationClient.requestLocationUpdates(
                        locationRequest,
                        GPSLocationCallback,
                        null /* Looper */);

                isGPSUpdating=true;
                btnGPSUpdateLocation.setText("Stop");
            }
        }
    }

    public void GPSUpdateLocationStop(){
        isGPSUpdating=false;
        btnGPSUpdateLocation.setText("Suis moi.");
        if(GPSLocationClient!=null ) {
            if(GPSLocationCallback!=null) {
                GPSLocationClient.removeLocationUpdates(GPSLocationCallback);
            }
            GPSLocationCallback=null;
        }
    }








    // ================================= Shared methods =================================

    protected void onPause() {
        super.onPause();
        GPSUpdateLocationStop();
        androidUpdateLocationStop();
    }


    private Location publishLocation(Location loc, TextView tv, String msg) {
        if(loc != null ){
            tv.setText("Lat : "+loc.getLatitude()+" / Long : "+loc.getLongitude()+" (Provider: "+loc.getProvider()+")");
            if(msg!=null) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        }
        return loc;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {

        if (grantResults.length > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            switch (requestCode) {
                case REQUEST_CODE_ANDROID_LAST_LOCATION:   androidGetLastLocation(); return;
                case REQUEST_CODE_ANDROID_GET_LOCATION:    androidGetLocation();     return;
                case REQUEST_CODE_ANDROID_UPDATE_LOCATION: androidUpdateLocation();  return;
                case REQUEST_CODE_GPS_LAST_LOCATION:   GPSGetLastLocation(); return;
                case REQUEST_CODE_GPS_GET_LOCATION:    GPSGetLocation();     return;// Cas impossible
                case REQUEST_CODE_GPS_UPDATE_LOCATION: GPSUpdateLocation();  return;

            }
        } else {
            Toast.makeText(MainActivity.this, "Permission refusée.", Toast.LENGTH_LONG).show();
        }
    }


}

