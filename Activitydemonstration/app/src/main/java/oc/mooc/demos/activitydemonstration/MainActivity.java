package oc.mooc.demos.activitydemonstration;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnScdActivity;
    private Button btnExample;
    private Button btnMaps;
    private EditText paramIn;
    private TextView result;
    private Button btnParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Premier bouton : appel explicite
        btnScdActivity = (Button)findViewById(R.id.btnScdActivity);
        btnScdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Comme on se trouve dans une sous-classe, le this est un objet de type OnClickListener.
                // Pour faire référence au this qui est une activité, alors on utilise la notation : TYPE.this.
                // Ici : MainActivity.this
                Intent i = new Intent(MainActivity.this,
                                      oc.mooc.demos.activitydemonstration.AnotherActivity.class);
                startActivity(i);
            }
        });


        // Second bouton : appel implicite vers un browser
        btnExample = (Button)findViewById(R.id.btnExample);
        btnExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri myURI = Uri.parse("http://www.openclassrooms.fr");
                Intent i = new Intent(Intent.ACTION_VIEW, myURI);
                startActivity(i);
            }
        });

        // Appel implicite vers un carte
        btnMaps = (Button)findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri myURI = Uri.parse("geo:0,0?q=20 avenue Albert Einstein, 69100 Villeurbanne, france");
                Intent i = new Intent(Intent.ACTION_VIEW, myURI);
                startActivity(i);
            }
        });



        // Troisième bouton : appel explicite avec paramètre et résultat
        paramIn = (EditText) findViewById(R.id.paramIn);
        result = (TextView) findViewById(R.id.result);

        btnParams = (Button)findViewById(R.id.btnParams);
        btnParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,
                        oc.mooc.demos.activitydemonstration.ParametrizedActivity.class);
                i.putExtra(ParametrizedActivity.EXTRA_INPUT,paramIn.getText().toString());
                startActivityForResult(i,1);
            }
        });
    }


    // Méthode appelée en callback suite au retour de l'activité 3.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra(ParametrizedActivity.EXTRA_RESULT);
                result.setText(s);
            } else {
                result.setText("Action annulée");
            }
        } else {
            result.setText("Code de requête inconnu.");
        }
    }
}
