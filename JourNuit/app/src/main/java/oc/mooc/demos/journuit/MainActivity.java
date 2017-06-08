package oc.mooc.demos.journuit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnDay;
    private Button btnNight;
    private TextView tvStatus;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDay = (Button) findViewById(R.id.btnDay);
        btnNight = (Button) findViewById(R.id.btnNight);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        ivImage = (ImageView) findViewById(R.id.image);

        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStatus.setText("Jour !");
                 ivImage.setImageResource(R.drawable.day);
            }
        });
        btnNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStatus.setText("Nuit !");
                 ivImage.setImageResource(R.drawable.night);
            }
        });
    }
}
