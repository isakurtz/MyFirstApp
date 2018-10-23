package com.programa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity{
    private static final int QUESTIONNAIRE_REQUEST = 1;
    private static final int QUESTION_REQUEST = 2;
    Button jornada;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> userList = new ArrayList<>();
    private User u;
    private FirebaseAuth mAuth;
    private boolean quest;
    //private ZXingScannerView mScannerView;
    private Button btnScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
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
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        TextView textNome = findViewById(R.id.texNome);
        textNome.setText("Email: " + currentUser.getEmail());
        TextView textCurso = findViewById(R.id.textCurso);
        textCurso.setText("Curso: ");
        TextView textPontos = findViewById(R.id.textPontos);
        textPontos.setText("Pontos: "+ u.getPoints());

        //jornada = findViewById(R.id.buttonIn);
        //jornada.setText("Iniciar Teste");

        btnScan = (Button) findViewById(R.id.scanQR);
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





       //myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints())+10 +"");


       addEventFirebaseListener();

    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if (result.getContents() !=  null){
                alertCurso(result.getContents());
            }else{
                alertCurso("Scan cancelado");
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
**/
    private void alertScan(String msg){
        //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
        TextView textView = findViewById(R.id.textScan);
        textView.setText(msg);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == QUESTIONNAIRE_REQUEST) {
            if (resultCode == RESULT_OK) {
               // super.onActivityResult(requestCode, resultCode, data);
                String x = data.getStringExtra("resposta");
                TextView text = findViewById(R.id.textCurso);
                text.setText("Curso: " + Questionario.curso);
                alertCurso(Questionario.curso);
                quest = true;
            }
        }
        else if(requestCode == QUESTION_REQUEST){
            if(resultCode == RESULT_OK){
                u.setTarefa();
                ImageView image = findViewById(R.id.imageView3);
                image.setVisibility(View.VISIBLE);
            }

        }
        else{
            if(data !=null) {
                ImageView image = findViewById(R.id.imageView4);
                image.setVisibility(View.VISIBLE);
                String x = data.getStringExtra("resposta");
                alertScan(x);
            }
        }

    }

    private void alertCurso(String msg){
        //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Curso Sugerido");
        if(msg.contains("Nenhum")) msg = "Você respondeu não em todas a perguntas, nenhum curso sugerido :(";
        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public void iniciarJ(View view){

        if(quest){
            if(u.getTarefa() == 0) {
                ImageButton b = findViewById(R.id.imageButton3);
                b.setImageResource(R.drawable.questico);
                b = findViewById(R.id.imageButton4);
                b.setClickable(false);
                Intent intent = new Intent(this, JornadaActivity.class);
                startActivityForResult(intent, QUESTION_REQUEST);
            }
           // if(u.getTarefa() == 1){
              //  Intent intent = new Intent(this, jornada2.class);
               // startActivityForResult(intent, QUESTION_REQUEST);
            //}
            if(u.getTarefa() >0){
                Intent intent = new Intent(this, ScanActivity.class);
                startActivityForResult(intent, 6);

            }
        }
        //Intent intent = new Intent(this, JornadaActivity.class);
        //startActivity(intent);

        else {
            //jornada.setText("Próxima QUEST");
            ImageButton b = findViewById(R.id.imageButton4);
            b.setImageResource(R.drawable.questico);
            b = findViewById(R.id.imageButton2);
            b.setClickable(false);
            Intent intent = new Intent(this, QuestionarioActivity.class);
            //intent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
            startActivityForResult(intent, QUESTIONNAIRE_REQUEST);
        }

    }

    public void iniciaMaps(View view){
        Intent intent = new Intent(this, MapsWebView.class);
        startActivity(intent);
    }

    private void addEventFirebaseListener() {
        //Progressing
       myRef.child("users").child(u.getUid()).child("points").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               String c = (String)dataSnapshot.getValue();
               TextView textView = findViewById(R.id.textPontos);
               textView.setText("Pontos:"+ c);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

          }
       });
    }

}
