package oc.mooc.demos.sensorsviewer;

import android.content.Intent;
import android.graphics.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.androidplot.Plot;
import com.androidplot.util.Redrawer;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.*;

import java.text.DecimalFormat;
import java.util.Arrays;

public class OneSensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private XYPlot plots[];
    private SimpleXYSeries series[];
    private int nbPlots;
    private int SensorType;

    private Redrawer redrawer;

    public final static String EXTRA_SENSOR_TYPE = "oc.mooc.demos.sensorsviewer.OneSensorActivity.EXTRA_SENSOR_TYPE";

    private static final int HISTORY_SIZE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_sensor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Lecture du paramètre reçu
        // Input attendu : SensorType
        // Si pas de paramètre, usage de l'accéléromètre par défaut.
        Intent intent = getIntent();
        SensorType = intent.getIntExtra(EXTRA_SENSOR_TYPE,Sensor.TYPE_ACCELEROMETER);

        GenericSensorsData sData = new GenericSensorsData();
        SensorPlotData data = sData.getSensorData(SensorType);

        Log.d("Used sensor",data.toString());

        nbPlots=data.NB_VALS;
        String title= data.NAME;

        plots = new XYPlot[nbPlots];
        series = new SimpleXYSeries[nbPlots];

        // initialize our XYPlot references :
        XYPlot p = (XYPlot) findViewById(R.id.plotA);
        plots[0] =p;

        p = (XYPlot) findViewById(R.id.plotB);
        if(nbPlots>1){ plots[1] = p; } else { p.setVisibility(View.GONE); }

        p = (XYPlot) findViewById(R.id.plotC);
        if(nbPlots>2){ plots[2] = p; } else { p.setVisibility(View.GONE); }

        p = (XYPlot) findViewById(R.id.plotD);
        if(nbPlots>3){ plots[3] = p; } else { p.setVisibility(View.GONE); }

        p = (XYPlot) findViewById(R.id.plotE);
        if(nbPlots>4){ plots[4] = p; } else { p.setVisibility(View.GONE); }


        plots[0].setTitle(title);

        for(int i=0 ; i<nbPlots ;i++) {
            series[i]=new SimpleXYSeries(Arrays.asList(new Number[]{0}), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");
            series[i].useImplicitXVals();

            plots[i].setRangeBoundaries(data.MINS[i], data.MAXS[i], BoundaryMode.FIXED);
            plots[i].setDomainStepMode(StepMode.INCREMENT_BY_VAL);
            plots[i].setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);
            plots[i].setDomainStepValue(HISTORY_SIZE/10);
            plots[i].setDomainLabel("");
            plots[i].getDomainTitle().pack();
            plots[i].setRangeLabel(data.UNIT);
            plots[i].getRangeTitle().pack();
            plots[i].getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));
            plots[i].getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("#"));

            LineAndPointFormatter fX = new LineAndPointFormatter(Color.rgb(100, 100, 200), null, null, null);
            fX.setLegendIconEnabled(false);
            plots[i].addSeries(series[i], fX);
        }


        redrawer = new Redrawer(Arrays.asList((Plot[])plots), 100, false);
    }


    @Override
    protected void onResume() {
        super.onResume();

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(SensorType), SensorManager.SENSOR_DELAY_FASTEST);


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
    public void onSensorChanged(SensorEvent sensorEvent) {
//        Log.d("OneSensor", sensorEvent.sensor.getName()+ " -> "+Arrays.toString(sensorEvent.values));

//        Log.d("xValues.size : ", ""+series[0].size());
        if (series[0].size() >= HISTORY_SIZE) {
            for(int i=0;i<nbPlots ;i++) {
                if(sensorEvent.values.length>i) {
                    series[i].removeFirst();
                }
            }
        }

        for(int i=0;i<nbPlots ;i++) {
            if(sensorEvent.values.length>i) {
                series[i].addLast(null, sensorEvent.values[i]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
