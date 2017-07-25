package oc.mooc.demos.niveaueau;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvDeltaX;
    private TextView tvDeltaY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDeltaX = (TextView) findViewById(R.id.deltax);
        tvDeltaY = (TextView) findViewById(R.id.deltay);
    }



    private SensorManager sensorManager;
    @Override
    protected void onResume() {
        super.onResume();

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        tvDeltaX.setText((int)(event.values[0]*100/SensorManager.GRAVITY_EARTH)+"%");
        tvDeltaY.setText((int)(event.values[1]*100/SensorManager.GRAVITY_EARTH)+"%");
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
