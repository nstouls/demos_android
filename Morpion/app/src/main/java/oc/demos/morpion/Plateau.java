package oc.demos.morpion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.List;

public class Plateau extends View {

    private int taille;
    private Paint crayon;
    private Paint pLignes;
    private Paint pCroix;
    private Paint pRond;
    private Paint pVainqueur;
    private List<Pion> historique;

    private int[] solution = null;
    private boolean vainqueur;

    public void setVainqueur(boolean v, int[] solution) {
        vainqueur=v;
        this.solution=solution;
    }

    public Plateau(Context context, int taille, List<Pion> historique) {
        super(context);

        this.taille= taille;

        crayon = new Paint();
        crayon.setColor(Color.BLACK);

        pLignes = new Paint();
        pLignes.setColor(Color.BLACK);

        pCroix = new Paint();
        pCroix.setColor(Color.BLUE);
        pCroix.setStrokeWidth(5);

        pRond = new Paint();
        pRond.setColor(Color.GREEN);
        pRond.setStyle(Paint.Style.STROKE);
        pRond.setStrokeWidth(5);

        pVainqueur = new Paint();
        pVainqueur.setColor(Color.RED);
        pVainqueur.setTextSize(40);
        pVainqueur.setTextAlign(Paint.Align.CENTER);
        pVainqueur.setStrokeWidth(5);

        this.historique = historique;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int rayon = Math.min(w,h)/taille/2;

        // Cadrillage
        for(int i=1 ; i< taille ; i++) {
            canvas.drawLine(i*w/taille, 0, i*w/taille, h, pLignes);
        }

        for(int j=1 ; j<= taille ; j++) {
            canvas.drawLine(0,j*h/taille, w, j*h/taille, pLignes);
        }

        // placement de l'historique de jeu
        for(Pion c:historique) {
            if(c.estCroix) {
                canvas.drawLine(
                        c.x*w/taille, c.y*h/taille,
                        (c.x+1)*w/taille, (c.y+1)*h/taille,
                        pCroix);
                canvas.drawLine(
                        (c.x+1)*w/taille, c.y*h/taille,
                        c.x*w/taille, (c.y+1)*h/taille,
                        pCroix);
            } else {
                canvas.drawCircle(
                        (int)((c.x+0.5)*w/taille),
                        (int)((c.y+0.5)*h/taille),
                        rayon,
                        pRond);
            }
        }

        //Si jeu fini, affichage du statut
        if(solution!=null) {
            canvas.drawLine(
                    (int)((solution[0]+0.5)*w/taille),
                    (int)((solution[1]+0.5)*h/taille),
                    (int)((solution[2]+0.5)*w/taille),
                    (int)((solution[3]+0.5)*h/taille),
                    pVainqueur);

            canvas.drawText("Vainqueur : "+((vainqueur)?"croix":"ronds"), w/2, h/2, pVainqueur);
        }
    }

}
