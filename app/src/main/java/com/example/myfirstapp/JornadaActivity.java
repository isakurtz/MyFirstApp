package com.example.myfirstapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class JornadaActivity extends AppCompatActivity {
    private boolean acerto;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> userList = new ArrayList<>();
    private User u;
    private FirebaseAuth mAuth;
    private String questao = "O estudo da lógica é muito importante na computação. Na lógica clássica a partir de uma série de proposições verdadeiras ou falsas podemos deduzir alguns resultados. Abaixo " +
            "serão apresentadas algumas proposições verdadeiras, a partir delas tente responder a pergunta apresentada. "+ System.getProperty ("line.separator") + System.getProperty ("line.separator") +"Ada, Alan e Ed entram em um bar, cada " +
            "um pediu uma bebida. descubra quem pediu suco: "+ System.getProperty ("line.separator") + System.getProperty ("line.separator") + "\t\t\t\t\tAda pediu refrigerante" + System.getProperty ("line.separator") +
            "\t\t\t\t\tAlan não pediu cerveja" + System.getProperty ("line.separator") +"\t\t\t\t\tEd não pediu suco";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jornada);
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
        // Check which radio button was clicked
        Button b = findViewById(R.id.button2);
        switch(view.getId()) {
            case R.id.radioButton6:
                if (checked){
                    //TextView editText = findViewById(R.id.resultado);
                    //editText.setText("Parabéns + 30 pontos!");
                    //myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints())+30 +"");
                    b.setVisibility(View.VISIBLE);
                    acerto = true;
                }
                break;
            default:
                //TextView editText = findViewById(R.id.resultado);
                //editText.setText("Resposta Errada");
                b.setVisibility(View.VISIBLE);
                acerto = false;
                break;
        }
    }

    private void alertScan(String msg){
        //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
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
        //Intent intent = new Intent(this, jornada2.class);
        //String message = editText.getText().toString();
        //startActivity(intent);
        if(acerto) {
            myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints()) + 30 + "");
            alertScan("Parabéns + 30 pontos!");
        }
        else{
            alertScan("Resposta Errada");
        }

    }
}
