package oc.mooc.demos.sensorsviewer;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class FrisbeeActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;

    private XYPlot plots[];
    private SimpleXYSeries series[];
    private final int NB_PLOTS =2;
    private final int ACC_MODULE=0;
    private final int LIGHT_GYRO =1;
    private final int LIGHT_FIELD =0;
    private final int GYRO_FIELD =2;
    private boolean isGyro=true;


    private Redrawer redrawer;

    private static final int HISTORY_SIZE = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frisbee);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        String title= "Mode frisbee aquatique";

        plots = new XYPlot[NB_PLOTS];
        series = new SimpleXYSeries[NB_PLOTS];

        // initialize our XYPlot references :
        plots[ACC_MODULE] =(XYPlot) findViewById(R.id.plotA);
        plots[LIGHT_GYRO] =(XYPlot) findViewById(R.id.plotB);


        plots[0].setTitle(title);

        for(int i = 0; i< NB_PLOTS; i++) {
            series[i]=new SimpleXYSeries(Arrays.asList(new Number[]{0}), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");
            series[i].useImplicitXVals();

            plots[i].setRangeBoundaries(-20, 20, BoundaryMode.GROW);
            plots[i].setDomainStepMode(StepMode.INCREMENT_BY_VAL);
            plots[i].setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);
            plots[i].setDomainStepValue(HISTORY_SIZE/10);
            plots[i].getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));
            plots[i].getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("#"));
        }

        plots[ACC_MODULE].setDomainLabel("Module du vecteur accélération");
        plots[ACC_MODULE].getDomainTitle().pack();
        plots[ACC_MODULE].setRangeLabel("m/s^2");
        plots[ACC_MODULE].getRangeTitle().pack();
        LineAndPointFormatter f = new LineAndPointFormatter(Color.rgb(100, 100, 200), null, null, null);
        f.setLegendIconEnabled(false);
        plots[ACC_MODULE].addSeries(series[ACC_MODULE], f);

        plots[LIGHT_GYRO].setDomainLabel("Gyroscope");
        plots[LIGHT_GYRO].getDomainTitle().pack();
        plots[LIGHT_GYRO].setRangeLabel("rad/s");
        plots[LIGHT_GYRO].getRangeTitle().pack();
        f = new LineAndPointFormatter(Color.rgb(200, 100, 100), null, null, null);
        f.setLegendIconEnabled(false);
        plots[LIGHT_GYRO].addSeries(series[LIGHT_GYRO], f);


        redrawer = new Redrawer(Arrays.asList((Plot[])plots), 100, false);
    }


    @Override
    protected void onResume() {
        super.onResume();

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        // En second capteur, si on ne trouve pas de gyroscope, alors on prend un capteur de luminosité.
        Sensor s = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(s==null) {
            isGyro=false;
            s=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            plots[LIGHT_GYRO].setDomainLabel("Luminosité");
            plots[LIGHT_GYRO].getDomainTitle().pack();
            plots[LIGHT_GYRO].setRangeLabel("lux");
            plots[LIGHT_GYRO].getRangeTitle().pack();
        }
        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);

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
        int sensorID = LIGHT_GYRO;
        double val = event.values[GYRO_FIELD];
        if(!isGyro)  {
            val = event.values[LIGHT_FIELD];
        }

        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            sensorID=ACC_MODULE;
            val = Math.sqrt(
                        event.values[0]*event.values[0]+
                        event.values[1]*event.values[1]+
                        event.values[2]*event.values[2]
            );
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
