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
    ListView listaRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        listaRanking = (ListView) findViewById(R.id.listRanking);
        listRanking = DisplayMessageActivity.listRanking;

        ArrayAdapter<Ranking> adapter = new ArrayAdapter<Ranking>(this,
                android.R.layout.simple_list_item_1, listRanking);
        listaRanking.setAdapter(adapter);
    }
  }
