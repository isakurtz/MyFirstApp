package com.programa;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    private List<Ranking> listRanking;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ranking);
        ListView listaRanking = (ListView) findViewById(R.id.listRanking);
        listRanking = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        addChildEventListener();
        ArrayAdapter<Ranking> adapter = new ArrayAdapter<Ranking>(this,
                android.R.layout.simple_list_item_1, listRanking);
        listaRanking.setAdapter(adapter);
    }



    public void alertRanking(View view){
        //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ranking");
        for(int i = 0; i < listRanking.size();i++){
            for(int j = 0; j< listRanking.size()-1;j++){
                if(listRanking.get(j).getPontos() < listRanking.get(j+1).getPontos()){
                    Ranking aux = listRanking.get(j);
                    listRanking.set(j,listRanking.get(j+1));
                    listRanking.set(j+1, aux);
                }
            }
        }
        String msg ="";
        for (Ranking s:listRanking
                ) {
            msg += s.getEmail() +" "+ s.getPontos() +System.getProperty ("line.separator");
        }

        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    private void addChildEventListener(){
        myRef.child("ranking").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listRanking.add(new Ranking((String)dataSnapshot.getKey(),Integer.parseInt((String)dataSnapshot.getValue())));
                for(int i = 0; i < listRanking.size();i++){
                    for(int j = 0; j< listRanking.size()-1;j++){
                        if(listRanking.get(j).getPontos() < listRanking.get(j+1).getPontos()){
                            Ranking aux = listRanking.get(j);
                            listRanking.set(j,listRanking.get(j+1));
                            listRanking.set(j+1, aux);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (int i =0; i<listRanking.size();i++){
                    if(listRanking.get(i).getEmail().equals(dataSnapshot.getKey())) listRanking.set(i,new Ranking((String)dataSnapshot.getKey(),Integer.parseInt((String)dataSnapshot.getValue())) );
                }
                for(int i = 0; i < listRanking.size();i++){
                    for(int j = 0; j< listRanking.size()-1;j++){
                        if(listRanking.get(j).getPontos() < listRanking.get(j+1).getPontos()){
                            Ranking aux = listRanking.get(j);
                            listRanking.set(j,listRanking.get(j+1));
                            listRanking.set(j+1, aux);
                        }
                    }
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
