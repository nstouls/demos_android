package oc.mooc.demos.sensorsviewer;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class FiveValsSensorAdapter extends ArrayAdapter<Sensor> {
    private int layoutName;
    private HashMap<Integer, float[]> vals;

    java.text.DecimalFormat df;

    public FiveValsSensorAdapter(Context context, int layoutName, List<Sensor> items, HashMap<Integer, float[]> vals) {
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

            name.setText("Type: "+ GenericSensorsData.typeToStr(item.getType())+" ("+item.getName()+")");


            if(values!=null) {
                if(values.length>0) {
                    ((TextView) convertView.findViewById(R.id.xValue)).setText("" + df.format(values[0]));
                    ((TextView) convertView.findViewById(R.id.xUnit)).setText(GenericSensorsData.typeToUnit(item.getType()));
                }

                if(values.length>1) {
                    ((TextView) convertView.findViewById(R.id.yUnit)).setText(GenericSensorsData.typeToUnit(item.getType()));
                    ((TextView) convertView.findViewById(R.id.yValue)).setText(""+df.format(values[1]));
                    convertView.findViewById(R.id.y).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.yUnit).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.yValue).setVisibility(View.VISIBLE);
                }


                if(values.length>2) {
                    ((TextView) convertView.findViewById(R.id.zUnit)).setText(GenericSensorsData.typeToUnit(item.getType()));
                    ((TextView) convertView.findViewById(R.id.zValue)).setText(""+df.format(values[2]));
                    convertView.findViewById(R.id.z).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.zUnit).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.zValue).setVisibility(View.VISIBLE);
                }

                if(values.length>3) {
                    ((TextView) convertView.findViewById(R.id.dUnit)).setText(GenericSensorsData.typeToUnit(item.getType()));
                    ((TextView) convertView.findViewById(R.id.dValue)).setText(""+df.format(values[3]));
                    convertView.findViewById(R.id.d).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.dUnit).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.dValue).setVisibility(View.VISIBLE);
                }

                if(values.length>4) {
                    ((TextView) convertView.findViewById(R.id.eUnit)).setText(GenericSensorsData.typeToUnit(item.getType()));
                    ((TextView) convertView.findViewById(R.id.eValue)).setText(""+df.format(values[4]));
                    convertView.findViewById(R.id.e).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.eUnit).setVisibility(View.VISIBLE);
                    convertView.findViewById(R.id.eValue).setVisibility(View.VISIBLE);
                }
            }
        }

        return convertView;
    }

}
