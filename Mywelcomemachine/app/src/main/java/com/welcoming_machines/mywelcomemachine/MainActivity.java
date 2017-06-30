package com.welcoming_machines.mywelcomemachine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvTexte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTexte = (TextView) findViewById(R.id.le_texte);
        tvTexte.setText("Texte change-toi et danse avec la vie\n" +
                "L'écho de ta voix est venu jusqu'à moi !");
    }
}
