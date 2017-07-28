package oc.mooc.demos.sensorsviewer;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.androidplot.Plot;
import com.androidplot.util.Redrawer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;

import java.text.DecimalFormat;
import java.util.Arrays;

public class AccelerometersComparison extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;

    private XYPlot plots[];
    private SimpleXYSeries series[];
    private final int NB_PLOTS =3;
    private final int ACC_SENSOR=0;
    private final int LINEAR_SENSOR =1;
    private final int GRAVITY_SENSOR =2;


    private Redrawer redrawer;

    private static final int HISTORY_SIZE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_sensor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        String title= "Comparaison des accéléromètres (axe Z)";

        plots = new XYPlot[NB_PLOTS];
        series = new SimpleXYSeries[NB_PLOTS];

        // initialize our XYPlot references :
        plots[ACC_SENSOR] =(XYPlot) findViewById(R.id.plotA);
        plots[LINEAR_SENSOR] =(XYPlot) findViewById(R.id.plotB);
        plots[GRAVITY_SENSOR] =(XYPlot) findViewById(R.id.plotC);
        findViewById(R.id.plotD).setVisibility(View.GONE);
        findViewById(R.id.plotE).setVisibility(View.GONE);


        plots[0].setTitle(title);

        for(int i = 0; i< NB_PLOTS; i++) {
            series[i]=new SimpleXYSeries(Arrays.asList(new Number[]{0}), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");
            series[i].useImplicitXVals();

            plots[i].setRangeBoundaries(-20, 20, BoundaryMode.FIXED);
            plots[i].setDomainStepMode(StepMode.INCREMENT_BY_VAL);
            plots[i].setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);
            plots[i].setDomainStepValue(HISTORY_SIZE/10);
            plots[i].setRangeLabel("m/s^2");
            plots[i].getRangeTitle().pack();
            plots[i].getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));
            plots[i].getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("#"));
        }

        plots[ACC_SENSOR].setDomainLabel("TYPE_ACCELEROMETER");
        plots[ACC_SENSOR].getDomainTitle().pack();
        LineAndPointFormatter f = new LineAndPointFormatter(Color.rgb(100, 100, 200), null, null, null);
        f.setLegendIconEnabled(false);
        plots[ACC_SENSOR].addSeries(series[ACC_SENSOR], f);

        plots[LINEAR_SENSOR].setDomainLabel("TYPE_LINEAR_ACCELERATION");
        plots[LINEAR_SENSOR].getDomainTitle().pack();
        f = new LineAndPointFormatter(Color.rgb(200, 100, 100), null, null, null);
        f.setLegendIconEnabled(false);
        plots[LINEAR_SENSOR].addSeries(series[LINEAR_SENSOR], f);

        plots[GRAVITY_SENSOR].setDomainLabel("TYPE_GRAVITY");
        plots[GRAVITY_SENSOR].getDomainTitle().pack();
        f = new LineAndPointFormatter(Color.rgb(200, 100, 100), null, null, null);
        f.setLegendIconEnabled(false);
        plots[GRAVITY_SENSOR].addSeries(series[GRAVITY_SENSOR], f);

        redrawer = new Redrawer(Arrays.asList((Plot[])plots), 100, false);
    }


    @Override
    protected void onResume() {
        super.onResume();

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_FASTEST);

        redrawer.start();
    }


    @Override
    protected void onPause() {
        redrawer.pause();
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroy() {
        redrawer.finish();
        super.onDestroy();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float val=event.values[2];
        int sensorID;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER       : sensorID=ACC_SENSOR;     break;
            case Sensor.TYPE_LINEAR_ACCELERATION : sensorID=LINEAR_SENSOR;  break;
            case Sensor.TYPE_GRAVITY             : sensorID=GRAVITY_SENSOR; break;
            default: return;

        }

        if (series[sensorID].size() >= HISTORY_SIZE) {
            series[sensorID].removeFirst();
        }

        series[sensorID].addLast(null, val);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
