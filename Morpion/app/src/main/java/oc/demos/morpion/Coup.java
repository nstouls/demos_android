package oc.demos.morpion;

/**
 * Created by nstouls on 04/07/2017.
 */

public class Coup {
    public int x;
    public int y;
    public boolean estCroix;

    public Coup(int x, int y, boolean estCroix) {
        this.x=x;
        this.y=y;
        this.estCroix=estCroix;
    }
}
