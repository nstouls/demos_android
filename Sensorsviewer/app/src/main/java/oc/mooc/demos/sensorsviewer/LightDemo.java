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
import android.widget.TextView;

import com.androidplot.Plot;
import com.androidplot.util.Redrawer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LightDemo extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;

    private XYPlot plots[];
    private SimpleXYSeries series[];
    private final int NB_PLOTS =1;
    private TextView tvSpeech;
    private int SEUIL = 2000;


    private Redrawer redrawer;

    private static final int HISTORY_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_demo);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        tvSpeech = (TextView)findViewById(R.id.speech);

        String title= "Démo luminosité)";

        plots = new XYPlot[1];
        series = new SimpleXYSeries[1];

        // initialize our XYPlot references :
        plots[0] =(XYPlot) findViewById(R.id.plotA);


        plots[0].setTitle(title);

        series[0]=new SimpleXYSeries(Arrays.asList(new Number[]{0}), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");
        series[0].useImplicitXVals();

        plots[0].setRangeBoundaries(0, 200, BoundaryMode.AUTO);
        plots[0].setDomainStepMode(StepMode.INCREMENT_BY_VAL);
        plots[0].setDomainBoundaries(0, HISTORY_SIZE, BoundaryMode.FIXED);
        plots[0].setDomainStepValue(HISTORY_SIZE/10);
        plots[0].setRangeLabel("lux");
        plots[0].getRangeTitle().pack();
        plots[0].getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("#"));
        plots[0].getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("#"));

        plots[0].setDomainLabel("Luminosité");
        plots[0].getDomainTitle().pack();
        LineAndPointFormatter f = new LineAndPointFormatter(Color.rgb(100, 100, 200), null, null, null);
        f.setLegendIconEnabled(false);
        plots[0].addSeries(series[0], f);

        redrawer = new Redrawer(Arrays.asList((Plot[])plots), 100, false);
    }


    @Override
    protected void onResume() {
        super.onResume();

        sensorManager =  (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_FASTEST);

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




    private int count=0;
    private int message=0;
    private String[] coupables = {
            "Oui c'est moi !",
            "J'avoue tout !",
            "C'est Jean-François Lalande qui m'a obligé :'(",
            "Je ne recommencerai plus !",
            "Je te fais à manger et on oublie tout ?"
    };
    private String[] innoncents={
            "Même pas vrai c'est pas moi !",
            "Tortionnaire ! Je le dirai à Jean-François !",
            "Tu ne le vois pas mais je te tire la langue :p",
            "Bisque bisque rage >:D",
            "Hey t'es miro quand il fait sombre ?",
            "Quand la nuit tombe les tablettes dansent >:D"
    };
    private boolean wasCoupable=true;
    private LinkedList<String> messages = new LinkedList<>();

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (series[0].size() >= HISTORY_SIZE) {
            series[0].removeFirst();
        }

        series[0].addLast(null, event.values[0]);

        count= (count+1)%11;
        if(count==0) {
            if(event.values[0]>SEUIL) {
                if(!wasCoupable) {
                    messages=new LinkedList<>();
                }
                if(message>=coupables.length) {
                    message=0;
                }
                messages.addLast(coupables[message]+"\n");
                wasCoupable=true;
                tvSpeech.setTextColor(Color.argb(255,0,150,0));
            } else {
                if(wasCoupable) {
                    messages=new LinkedList<>();
                }
                if(message>=innoncents.length) {
                    message=0;
                }
                messages.addLast(innoncents[message]+"\n");
                wasCoupable=false;
                tvSpeech.setTextColor(Color.RED);
            }
            message++;
            if(messages.size()>3) messages.removeFirst();
            tvSpeech.setText(messages.toString().replaceAll(", " ,"").replaceAll("\\[","").replaceAll("\\]",""));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
