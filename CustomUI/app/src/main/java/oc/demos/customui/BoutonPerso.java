package oc.demos.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import java.util.Random;

public class BoutonPerso extends AppCompatButton {
    private String sousTitre="Beta";
    public void setSousTitre(String s) {sousTitre=s;}
    public void changeCouleurPoint() {
        Random random= new Random();
        // Tirage aléatoire d'une couleur et d'une transparence
        int a = random.nextInt(256);
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        pCercle.setARGB(a, r, g, b) ;
    }

    // 3 constructeurs, qui appellent tous la méthode init().
    public BoutonPerso(Context context) {
        super(context);
        init();
    }

    public BoutonPerso(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoutonPerso(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private Paint pCercle;
    private Paint pBeta;

    private void init(){
        pCercle = new Paint(); // Créer un objet de type Paint
        pCercle.setColor(Color.GREEN); // Utiliser la couleur rouge
        pCercle.setStyle(Paint.Style.FILL); // Si la forme dessinée est fermée, alors la remplir
        pCercle.setAntiAlias(true); // Faire de l'anti-aliasing

        pBeta = new Paint();
        pBeta.setTextAlign(Paint.Align.RIGHT);  // Le texte sera centré par rapport au point d'ancrage.
        pBeta.setTextSize(12); // Taille de la police de caractères
        pBeta.setColor(Color.RED); // Couleur du texte
    }


    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        Drawable d = getResources().getDrawable(android.R.drawable.btn_star_big_on);
        d.setBounds(0,getHeight()-30,30,getHeight());
        d.draw(c);

        c.drawCircle(8, 8, 4, pCercle);
        c.drawText(sousTitre, getWidth(), getHeight()-pBeta.getTextSize(), pBeta);
    }
}
