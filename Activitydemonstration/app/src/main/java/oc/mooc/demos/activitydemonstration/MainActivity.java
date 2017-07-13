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
    private EditText paramIn;
    private TextView result;
    private Button btnParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btnExample = (Button)findViewById(R.id.btnExample);
        btnExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.openclassrooms.fr"));
                startActivity(i);
            }
        });



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
