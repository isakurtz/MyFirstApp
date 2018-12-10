package com.programa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TarefaQuestActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_tarefa_quest);
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
        TextView text = findViewById(R.id.textView2);
        text.setText(questao);
        text.setMovementMethod(new ScrollingMovementMethod());
        Button b = findViewById(R.id.button2);
        b.setVisibility(View.GONE);

    }


    public void Selected( View view){

        boolean checked = ((RadioButton) view).isChecked();
        Button b = findViewById(R.id.button2);
        switch(view.getId()) {
            case R.id.radioButton6:
                if (checked){
                   b.setVisibility(View.VISIBLE);
                   acerto = true;
                }
                break;
            default:
                b.setVisibility(View.VISIBLE);
                acerto = false;
                break;
        }
    }

    private void alertScan(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {

                Intent devolve = new Intent();
                devolve.putExtra("resposta", "Resposta");
                setResult(RESULT_OK, devolve);
                finish();
            }
        });

        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public void butoonclicked(View view){
        if(acerto) {
            myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints()) + 30 + "");
            if(!u.hasBadge("1")){
                myRef.child("users").child(u.getUid()).child("badges").setValue(u.getBadgesToString()+"1;");
            }
            alertScan("Parabéns + 30 pontos!");
        }
        else{
            alertScan("Resposta Errada");
        }

    }
}
