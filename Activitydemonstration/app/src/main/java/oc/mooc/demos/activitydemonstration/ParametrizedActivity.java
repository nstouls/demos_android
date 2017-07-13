package oc.mooc.demos.activitydemonstration;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ParametrizedActivity extends AppCompatActivity {

    public final static String EXTRA_INPUT = "oc.mooc.demos.activitydemonstration.input";
    public final static String EXTRA_RESULT = "oc.mooc.demos.activitydemonstration.result";

    private String param;
    private TextView paramIn;
    private EditText result;
    private Button btnFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrized);

        paramIn = (TextView)findViewById(R.id.paramIn);

        Intent intent = getIntent();
        param = intent.getStringExtra(EXTRA_INPUT);
        paramIn.setText(param);


        result = (EditText) findViewById(R.id.result);


        btnFin = (Button)findViewById(R.id.btnFin);
        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent res = new Intent();
                res.putExtra(EXTRA_RESULT, result.getText().toString());
                setResult(Activity.RESULT_OK, res);
                finish();
            }
        });
    }



}
