package oc.mooc.demos.sensorsviewer;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ListView list;
    private List<Sensor> sensorList;
    private SensorManager sensorManager ;
    private HashMap<Integer, float[]> vals;
    private FiveValsSensorAdapter adapter;
    private Timer refresher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();

        vals = new HashMap<>();
        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);


        list = (ListView) findViewById(R.id.list);
        adapter=new FiveValsSensorAdapter(this, R.layout.five_vals_sensor, sensorList, vals);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Intent i = new Intent(MainActivity.this, OneSensorActivity.class);
                i.putExtra(OneSensorActivity.EXTRA_SENSOR_TYPE,sensorList.get(position).getType());
                startActivity(i);
            }
        });


        for(Sensor s: sensorList) {
            sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }

        refresher = new Timer();
        refresher.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      if(adapter!=null && vals!=null) adapter.notifyDataSetChanged();
                                  }
                              }
                );
          }
        }, 333, 333);
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        vals=null;
        adapter=null;
        refresher.cancel();
        refresher=null;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        vals.put(sensorEvent.sensor.getType(),sensorEvent.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
