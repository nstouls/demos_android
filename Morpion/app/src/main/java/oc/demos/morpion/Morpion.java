package oc.demos.morpion;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.LinkedList;
import java.util.List;


public class Morpion extends AppCompatActivity implements View.OnTouchListener, SurfaceHolder.Callback{

    private Plateau plateau;
    private int taille;
    private List<Pion> historique = new LinkedList<>();
    private boolean joueurCourant =false;
    private SurfaceHolder plateauHolder;

    public static final String EXTRA_SIZE = "oc.demos.morpion.taille";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Récupération du paramètre d'appel
        Intent intent = getIntent();
        taille = intent.getIntExtra(EXTRA_SIZE, 20);

        plateau = new Plateau(this, taille, historique);
        setContentView(plateau);

        // Demande à être notifié du changement d'état de la surface
        plateau.getHolder().addCallback(this);

        plateau.setOnTouchListener(this);
    }


    /** Ajoute un pion en coordonnées (x,y) pour le joueur j.
     *  retourne vrai ssi cet ajout a pu être fait (la case était libre).
     */
    public boolean addPion(int x, int y, boolean j) {
        // On ne peut pas jouer dans une place occupée
        for(Pion c : historique){
            if(c.x==x && c.y==y) return false;
        }

        // On mémorise le coup
        historique.add(new Pion(x, y, j));
        return true;
    }

    /** Lorsqu'une touche de l'écran est détectée, un coup est joué si c'est possible.
     *  Cette méthode provoque également le raffraichissement de l'affichage du plateau
     *  et le passage au joueur suivant si le coup proposé est valide et que le jeu
     *  n'est pas fini.
     * */
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
            int x = (int)(motionEvent.getX()*taille/plateau.getWidth());
            int y = (int)(motionEvent.getY()*taille/plateau.getHeight());
            if(addPion(x, y, joueurCourant)) {
                if (!estFini(x, y)) {
                    joueurCourant = !joueurCourant;
                }
            }
        }

        //Pour raffraichir la surface, on lock le canvas, on y dessine, puis on déverrouille.
        Canvas drawingCanvas = plateauHolder.lockCanvas();
        plateau.draw(drawingCanvas);
        plateauHolder.unlockCanvasAndPost(drawingCanvas);

        return true;
    }



    /** retourne vrai ssi il existe un pion en (x,y) et qu'il est
     *  du même symbole que celui du joueur courant. */
    private boolean estMemeQueCourant(int x, int y) {
        for(Pion c:historique) {
            if (c.x==x && c.y==y & c.estCroix==joueurCourant) return true;
        }
        return false;
    }

    /** Retourne le nombre de pions du joueur courant qui sont alignés
     *  par rapport à une direction donnée (dx, dy).
     *  La valeur renvoyée considère les deux sens d'une même direction.
     */
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

    /** Retourne vrai ssi un alignement de 5 existe pour le joueur courant.
        Si le jeu est fini, cette méthode désactive l'écouteur de touche (bloque le jeu)
        et transmet la solution trouvée au plateau.
        */
    private boolean estFini(int x, int y) {
        int[][] directions = {
                {1,0}, // Horizontal
                {0,1}, // Vertical
                {1,1}, // Diagonale \
                {-1,1} // Diagonale /
        };

        for(int d = 0;d<directions.length; d++) {
            int dx=directions[d][0];
            int dy=directions[d][1];
            int c1 = compteAlignementDeCourants(x,y,dx,dy);
            int c2 = compteAlignementDeCourants(x,y,-dx,-dy);
            int c=1+c1+c2;
            Log.d("estFini","Direction "+d+" : "+c);
            if(c>=5) {
                int[] solution = {x+dx*c1, y+dy*c1,
                                  x-dx*c2, y-dy*c2};

                plateau.setVainqueur(joueurCourant, solution);
                plateau.setOnTouchListener(null);
                return true;
            }
        }

        return false;
    }


    // ==============================================
    // Méthodes nécessaire pour le SurfaceHolder.Callback
    // Les définir est inutile dans cet exemple, car il n'y a pas de processus à lancer/arrêter,
    // mais on peut exploiter le surfaceCreated pour initialiser l'affichage.

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        plateauHolder = surfaceHolder;

        // On fait un premier affichage
        Canvas drawingCanvas = plateauHolder.lockCanvas();
        plateau.draw(drawingCanvas);
        plateauHolder.unlockCanvasAndPost(drawingCanvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        plateauHolder=null;
    }
}
