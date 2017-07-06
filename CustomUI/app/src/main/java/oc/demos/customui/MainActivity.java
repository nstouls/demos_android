package oc.demos.customui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private BoutonPerso btn;
    private EditText etValue;
    private Button btnChgColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (BoutonPerso)findViewById(R.id.button);
        etValue = (EditText)findViewById(R.id.etValue);
        btnChgColor = (Button)findViewById(R.id.btnChgColor);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setSousTitre(etValue.getText().toString());
            }
        });


        btnChgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.changeCouleurPoint();

                // Il faut forcer la mise à jour de l'affichage.
                btn.postInvalidate();
            }
        });
    }
}
