package com.programa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class TarefaScanActivity extends AppCompatActivity {

    private Button btnScan;
    private boolean acerto;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> userList = new ArrayList<>();
    private User u;
    private FirebaseAuth mAuth;
    private String questao ;
    private String resposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle params = getIntent().getExtras();
        String[] p = params.getString("params").split(":");
        questao = p[4];
        resposta = p[5];
        setContentView(R.layout.activity_tarefa_scan);
        btnScan = (Button) findViewById(R.id.button4);
        final Activity activity = this;

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        for ( User a: MainActivity.userList
                ) {
            if(a.getUid().equals(currentUser.getUid())){
                u= a;
            }
        }

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
                    if(result.getContents().equals(resposta)){
                        myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints()) + 100 + "");
                        if(!u.hasBadge("2")){
                            myRef.child("users").child(u.getUid()).child("badges").setValue(u.getBadgesToString()+"2;");
                        }
                        Intent devolve = new Intent();
                        devolve.putExtra("resposta", result.getContents() + " Parabéns + 100 pontos! você ganhou o badge Explorador");
                        setResult(RESULT_OK, devolve);
                    }
                    else{
                        Intent devolve = new Intent();
                        devolve.putExtra("resposta", result.getContents() + " Não é QR Code correto");
                        setResult(RESULT_OK, devolve);
                    }

                }
                finish();
            }else{
                super.onActivityResult(requestCode, resultCode, data);
               finish();
           }
        }
}
