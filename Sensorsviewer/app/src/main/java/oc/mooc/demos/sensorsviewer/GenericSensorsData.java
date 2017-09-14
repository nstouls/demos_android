package oc.mooc.demos.sensorsviewer;

import android.hardware.Sensor;

import java.util.HashMap;

public class GenericSensorsData {

    private static HashMap<Integer, SensorPlotData> sensors=null;

    public GenericSensorsData () {
        if(sensors==null) {
            sensors = new HashMap<>();
            sensors.put(Sensor.TYPE_ACCELEROMETER, new SensorPlotData(typeToStr(Sensor.TYPE_ACCELEROMETER), typeToUnit(Sensor.TYPE_ACCELEROMETER), -20, 20, -20, 20, -20, 20));
            sensors.put(Sensor.TYPE_LINEAR_ACCELERATION, new SensorPlotData(typeToStr(Sensor.TYPE_LINEAR_ACCELERATION), typeToUnit(Sensor.TYPE_LINEAR_ACCELERATION), -20, 20, -20, 20, -20, 20));
            sensors.put(Sensor.TYPE_GRAVITY, new SensorPlotData(typeToStr(Sensor.TYPE_GRAVITY), typeToUnit(Sensor.TYPE_GRAVITY), -13, 13, -13, 13, -13, 13));
            sensors.put(Sensor.TYPE_GYROSCOPE, new SensorPlotData(typeToStr(Sensor.TYPE_GYROSCOPE), typeToUnit(Sensor.TYPE_GYROSCOPE), -6, 6, -6, 6, -6, 6));
            sensors.put(Sensor.TYPE_ORIENTATION, new SensorPlotData(typeToStr(Sensor.TYPE_ORIENTATION), typeToUnit(Sensor.TYPE_ORIENTATION), -180, 359, -180, 359, -180, 359));
            sensors.put(Sensor.TYPE_LIGHT, new SensorPlotData(typeToStr(Sensor.TYPE_LIGHT), typeToUnit(Sensor.TYPE_LIGHT), 0, 300));
        }
    }

    public SensorPlotData getSensorData(int sensorType){
        if(sensors.containsKey(sensorType)) return sensors.get(sensorType);
        return new SensorPlotData(typeToStr(sensorType), typeToUnit(sensorType), -20, 20, -20, 20, -20, 20, -20, 20, -20, 20);
    }

    public static String typeToStr(int sensorType) {
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





    public static String typeToUnit(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                return "m/s^2";
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
            case Sensor.TYPE_TEMPERATURE:
                return "°C";
            case Sensor.TYPE_GAME_ROTATION_VECTOR:
                return "";
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
