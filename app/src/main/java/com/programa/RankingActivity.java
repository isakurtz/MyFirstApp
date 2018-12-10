package com.programa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class RankingActivity extends AppCompatActivity {
    private List<Ranking> listRanking;
    ListView listaRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        listaRanking = (ListView) findViewById(R.id.listRanking);
        listRanking = MainMenuActivity.listRanking;

        ArrayAdapter<Ranking> adapter = new ArrayAdapter<Ranking>(this,
                android.R.layout.simple_list_item_1, listRanking);
        listaRanking.setAdapter(adapter);
    }
  }
