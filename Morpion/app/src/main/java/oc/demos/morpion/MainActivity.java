package oc.demos.morpion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startBtn;
    private SeekBar choixTaille;
    private TextView tvTaille;

    private final int MIN_TAILLE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTaille = (TextView)findViewById(R.id.taille);
        choixTaille = (SeekBar) findViewById(R.id.choixTaille);
        startBtn = (Button)findViewById(R.id.startBtn);

        tvTaille.setText((10+ MIN_TAILLE)+"x"+(10+ MIN_TAILLE));
        choixTaille.setProgress(10);

        choixTaille.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvTaille.setText((MIN_TAILLE +i)+"x"+(MIN_TAILLE +i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int val = choixTaille.getProgress()+ MIN_TAILLE;
                go(val);
            }
        });
    }


    public void go(int taille) {
        Intent caller = new Intent(this, oc.demos.morpion.Morpion.class);
        caller.putExtra(Morpion.EXTRA_SIZE, taille);
        startActivity(caller);
    }
}
