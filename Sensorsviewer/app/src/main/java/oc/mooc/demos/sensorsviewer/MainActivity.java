package oc.mooc.demos.sensorsviewer;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView tvSensors;

    private SensorManager sm ;

//    private HashMap<Sensor,View>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);

        sm =  (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> msensorList = sm.getSensorList(Sensor.TYPE_ALL);

        tvSensors = (TextView) findViewById(R.id.sensors);
        String s = msensorList.size()+" capteurs !";

        // Print each Sensor available using sSensList as the String to be printed
        Sensor tmp;
        int x,i;
        for (i=0;i<msensorList.size();i++){
            tmp = msensorList.get(i);
            s+= "\n  "+tmp.getName(); // Add the sensor name to the string of sensors available
        }
        tvSensors.setText(s);
    }




}
