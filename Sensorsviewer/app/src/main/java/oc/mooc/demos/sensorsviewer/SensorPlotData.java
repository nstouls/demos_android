package oc.mooc.demos.sensorsviewer;

public class SensorPlotData {
    public final int NB_VALS;
    public final String NAME;
    public final String UNIT;
    public final int[] MINS;
    public final int[] MAXS;
    public SensorPlotData(String name, String unit, int ... minMax){
        NAME=name;
        UNIT=unit;
        NB_VALS=minMax.length/2;
        MINS=new int[NB_VALS];
        MAXS=new int[NB_VALS];

        for(int i=0; i<NB_VALS ; i++) {
            MINS[i] = minMax[i*2];
            MAXS[i] = minMax[i*2+1];
        }
    }

    public String toString(){
        String res= "Capteur : "+NAME+" "+UNIT+" "+NB_VALS+"D : ";
        for(int i=0; i<NB_VALS ; i++) {
            res+="["+MINS[i]+".."+MAXS[i]+"] ";
        }
        return res;
    }
}
