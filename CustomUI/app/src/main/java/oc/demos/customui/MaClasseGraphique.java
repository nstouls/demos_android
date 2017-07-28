package oc.demos.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nstouls on 28/07/2017.
 */

public class MaClasseGraphique extends View {
    public MaClasseGraphique(Context context) {
        super(context);
        init();
    }

    public MaClasseGraphique(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaClasseGraphique(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private Paint crayonRect = new Paint();
    private Paint crayonDisque= new Paint();
    Drawable etoile;
    public void init(){
        crayonRect.setColor(Color.BLACK);
        crayonRect.setStyle(Paint.Style.FILL);
        crayonDisque.setColor(Color.RED);
        crayonDisque.setStyle(Paint.Style.FILL);
        etoile = getResources().getDrawable(android.R.drawable.btn_star_big_on);
        etoile.setBounds(200,200,300,300);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,0,100,50,crayonRect);
        canvas.drawCircle(100,100,60,crayonDisque);
        etoile.draw(canvas);
    }
}

