package com.programa;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Jornada {
    private String nome;
    private List<Integer> tarefas;
    private int tarefaAtual;


    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getTarefaAtual() {
        return tarefaAtual;
    }

    public void setTarefaAtual(int tarefaAtual) {
        this.tarefaAtual = tarefaAtual;
    }

    public void iniciarTarefa(int tarefa, int questionRequest){
        String params;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Tarefas").child(tarefa+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                params = (String)dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(params.contains("quest")) {
            Intent intent = new Intent(this, TarefaQuestActivity.class);
            intent.putExtra("Params", params);
            startActivityForResult(intent, questionRequest);
        }

        if(params.contains("scan")){
            Intent intent = new Intent(this, TarefaScanActivity.class);
            intent.putExtra("Params", params);
            startActivityForResult(intent, questionRequest);
        }
    }

}
