package oc.demos.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class MaClasseGraphique extends View {
    private Paint crayonTrait;
    private Paint crayonRect;
    private Paint crayonTexte;

    // Code visant une API 17, donc avec seulement 3 constructeurs définis
    public MaClasseGraphique(Context context) {
        super(context);
        init();
    }

    public MaClasseGraphique(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaClasseGraphique(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        // Exemple de crayon si on voulait dessiner un trait jaune
        crayonTrait = new Paint();
        crayonTrait.setColor(Color.RED);
        crayonTrait.setAntiAlias(true);  // Faire de l'anti-aliasing

        // Exemple de crayon si on voulait dessiner un rectangle noir plein
        crayonRect = new Paint();
        crayonRect.setColor(Color.BLACK);
        crayonRect.setStyle(Paint.Style.FILL);

        // Chargement de l'image et configuration de sa position
        etoile = getResources().getDrawable(android.R.drawable.btn_star_big_on);
        etoile.setBounds(60,10,90,40);
    }

    private Drawable etoile;
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawRect(0,0,20,20,crayonRect);
        canvas.drawLine(0,0,getWidth(), getHeight(),crayonTrait);

        etoile.draw(canvas);
    }
}
