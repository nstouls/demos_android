package oc.demos.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

public class BoutonPerso extends AppCompatButton {

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
        c.drawCircle(8, 8, 4, pCercle);
        c.drawText("Beta", getWidth(), getHeight()-pBeta.getTextSize(), pBeta);
    }
}
