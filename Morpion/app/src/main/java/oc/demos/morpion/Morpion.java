package oc.demos.morpion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class Morpion extends AppCompatActivity {

    private Plateau plateau;
    private int taille;
    private List<Coup> historique = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        taille = Integer.parseInt(b.get("oc.demos.morpion.taille").toString());
        Log.d("GO", "Taille reçue : "+b.get("oc.demos.morpion.taille"));

        plateau = new Plateau(this, taille, historique);
        setContentView(plateau);

        plateau.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                play(view, motionEvent);
                return true;
            }
        });
    }


    public boolean addCoup(int x, int y, boolean b) {
        // On ne peut pas jouer dans une place occupée
        for(Coup c : historique){
            if(c.x==x && c.y==y) return false;
        }

        // On mémorise le coup
        historique.add(new Coup(x, y, b));
        plateau.postInvalidate();
        return true;
    }


    private boolean next=false;
    public void play(View v, MotionEvent motionEvent) {
        if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
            int x = (int)(motionEvent.getX()*taille/plateau.getWidth());
            int y = (int)(motionEvent.getY()*taille/plateau.getHeight());
            if(addCoup(x, y, next)) {

                if(!estFini(x,y)) {
                    next = !next;
                } else {
                    plateau.setVainqueur(next);
                    plateau.setOnTouchListener(null);
                }
            }
        }
    }

    private boolean estMemeQueCourant(int x, int y) {
        for(Coup c:historique) {
            if (c.x==x && c.y==y & c.estCroix==next) return true;
        }
        return false;
    }

    private int compteAlignementDeCourants(int x, int y, int dx, int dy) {
        int c = 0;
        x+=dx;
        y+=dy;
        while(estMemeQueCourant(x,y)){
            x+=dx;
            y+=dy;
            c++;
        }
        return c;
    }
    private boolean estFini(int x, int y) {
        int[][] directions = {{1,0},{0,1}, {1,1}, {-1,1}};

        for(int d = 0;d<directions.length; d++) {
            int dx=directions[d][0];
            int dy=directions[d][1];
            int c=1+compteAlignementDeCourants(x,y,dx,dy)+compteAlignementDeCourants(x,y,-dx,-dy);
            Log.d("estFini","Direction "+d+" : "+c);
            if(c>=5) return true;
        }

        return false;
    }
}
