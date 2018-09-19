package com.programa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    private Button btnScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        btnScan = (Button) findViewById(R.id.button4);
        final Activity activity = this;

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Camera Scan");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });
    }

    private void alertScan(String msg){
        //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(result != null){
                if(result.getContents()!=null) {
                    Intent devolve = new Intent();
                    devolve.putExtra("resposta", result.getContents());
                    setResult(RESULT_OK, devolve);
                }
                finish();
            }else{
                super.onActivityResult(requestCode, resultCode, data);
               finish();
           }
        }
}
