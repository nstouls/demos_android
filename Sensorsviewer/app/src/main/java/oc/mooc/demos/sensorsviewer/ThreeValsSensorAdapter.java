package oc.mooc.demos.sensorsviewer;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ThreeValsSensorAdapter extends ArrayAdapter<Sensor> {
    private int layoutName;
    private HashMap<Integer, float[]> vals;

    java.text.DecimalFormat df;

    public ThreeValsSensorAdapter(Context context, int layoutName, List<Sensor> items, HashMap<Integer, float[]> vals) {
        super(context, layoutName, items);
        this.layoutName = layoutName;
        this.vals=vals;
        df = new java.text.DecimalFormat("0.0");
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Sensor item = getItem(position);
        float[] values = vals.get(new Integer(item.getType()));
        if (item != null) {
            if(convertView==null) {
                convertView = LayoutInflater.from(this.getContext()).inflate(layoutName, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);

            name.setText("Type: "+ sensorTypeToString(item.getType())+" ("+item.getName()+")");


            if(values!=null) {
                if(values.length>0) {
                    ((TextView) convertView.findViewById(R.id.xValue)).setText("" + df.format(values[0]));
                    ((TextView) convertView.findViewById(R.id.xUnit)).setText(sensorTypeToUnit(item.getType()));
                }

                if(values.length>1) {
                    ((TextView) convertView.findViewById(R.id.yUnit)).setText(sensorTypeToUnit(item.getType()));
                    ((TextView) convertView.findViewById(R.id.yValue)).setText(""+df.format(values[1]));
                }


                if(values.length>2) {
                    ((TextView) convertView.findViewById(R.id.zUnit)).setText(sensorTypeToUnit(item.getType()));
                    ((TextView) convertView.findViewById(R.id.zValue)).setText(""+df.format(values[2]));
                }

            }
        }

        return convertView;
    }




    public static String sensorTypeToString(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "Accelerometer";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_TEMPERATURE:
                return "Ambient Temperature";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "Game Rotation Vector";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "Geomagnetic Rotation Vector";
            case Sensor.TYPE_GRAVITY:
                return "Gravity";
            case Sensor.TYPE_GYROSCOPE:
                return "Gyroscope";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "Gyroscope Uncalibrated";
            case Sensor.TYPE_HEART_RATE:
                return "Heart Rate Monitor";
            case Sensor.TYPE_LIGHT:
                return "Light";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "Linear Acceleration";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "Magnetic Field";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "Magnetic Field Uncalibrated";
            case Sensor.TYPE_ORIENTATION:
                return "Orientation";
            case Sensor.TYPE_PRESSURE:
                return "Pressure";
            case Sensor.TYPE_PROXIMITY:
                return "Proximity";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "Relative Humidity";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "Rotation Vector";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "Significant Motion";
            case Sensor.TYPE_STEP_COUNTER:
                return "Step Counter";
            case Sensor.TYPE_STEP_DETECTOR:
                return "Step Detector";

            case 65558:
                return "Screen Orientation Sensor";
            default:
                return "Unknown ("+sensorType+")";
        }
    }





    public static String sensorTypeToUnit(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "m/s^2";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_TEMPERATURE:
                return "°C";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "rad/s";
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                return "rad/s";
            case Sensor.TYPE_GRAVITY:
                return "m/s^2";
            case Sensor.TYPE_GYROSCOPE:
                return "rad/s";
            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                return "rad/s";
            case Sensor.TYPE_HEART_RATE:
                return ""; // [0..1]
            case Sensor.TYPE_LIGHT:
                return "lux";
            case Sensor.TYPE_LINEAR_ACCELERATION:
                return "m/s^2";
            case Sensor.TYPE_MAGNETIC_FIELD:
                return "µT";
            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                return "µT";
            case Sensor.TYPE_ORIENTATION:
                return "°";
            case Sensor.TYPE_PRESSURE:
                return "hPa";
            case Sensor.TYPE_PROXIMITY:
                return "cm";
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return "%";
            case Sensor.TYPE_ROTATION_VECTOR:
                return "";
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return "";
            case Sensor.TYPE_STEP_COUNTER:
                return "";
            case Sensor.TYPE_STEP_DETECTOR:
                return "";
            default:
                return "";
        }
    }
}
