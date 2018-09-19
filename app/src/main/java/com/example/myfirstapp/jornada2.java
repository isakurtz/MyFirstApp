package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class jornada2 extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> userList = new ArrayList<>();
    private User u;
    private FirebaseAuth mAuth;
    private boolean acerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String text = "\n" +
                "Uma das áreas bases da computação é programação. Um programa ou script é uma série de instruções que são interpretadas por uma máquina, essas instruções podem ser escritas em ínumeras linguagens de programação diferentes, que são adequadas para diferentes propositos. Independentemente da linguagem a base lógica é muito similar na maioria delas, e uma das instruções lógicas mais básicas é o 'if' (se). Por exemplo na instrução 'Se x é par então some 4' o computador receberia uma entrada x, que pode assumir qualquer valor númerico, avaliaria a condição (x é par) e somaria 4 ao resultado apenas se for verdadeira, se a condição for falsa (o x recebido é ímpar) a instrução seria ignorada. Baseando-se na lógica de programação do if, tente resolver o problema:\n" +
                "\n" +
                "Considere que b é a palavra ZAPING e x é igual à 5, qual seria o valor de b após a execução das instruções abaixo:\n" +
                "\t\t\n" +
                "\t\tse x²/2 > 10 então faça:\n" +
                "\t\t\t\t\n" +
                "\t\t\t\tem b substitua Z por T\n" +
                "\t\t\t\tem b substitua A por U\n" +
                "\t\t\t\tem b substitua P por R\n" +
                "\t\t\n" +
                "\t\tse 7-x*6 < 20 \n" +
                "\t\t\t\tem b substitua Z por S\n" +
                "\t\t\t\tem b substitua T por S\n" +
                "\t\t\t\tem b substitua U por A\n" +
                "\t\t\t\tem b substitua P por Y\n" +
                "\t\t\t\tem b substitua R por Y\n" +
                "\t\t\t\t";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jornada2);
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
        TextView text2 = findViewById(R.id.textView4);
        text2.setText(text);
        text2.setMovementMethod(new ScrollingMovementMethod());
    }

        public void okClicked(View view){
            boolean checked = ((RadioButton) view).isChecked();
            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.radioButton:
                    if (checked){
                        acerto = true;
                        TextView editText = findViewById(R.id.textView5);
                        editText.setText("Parabéns + 30 pontos!");
                        //myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints())+30 +"");
                        Button b = findViewById(R.id.button3);
                        b.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    //TextView editText = findViewById(R.id.resultado);
                    //acerto = false;
                   // editText.setText("Resposta Errada");
            }


        }

        public void clickk(View view){
            if(acerto){
                myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints())+30 +"");
            }
            else{

            }
            Intent devolve = new Intent();
            devolve.putExtra("resposta", "Resposta");
            setResult(RESULT_OK, devolve);
            finish();
        }

}
