package com.programa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity{
    private static final int QUESTIONNAIRE_REQUEST = 1;
    private static final int QUESTION_REQUEST = 2;
    public static final String TASK_MESSAGE = "taskComplete";
    Button jornada;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> userList = new ArrayList<>();
    private User u;
    private FirebaseAuth mAuth;
    private boolean quest;
    public static List<Ranking> listRanking;
    public static List<String> badges;
    private Button btnScan;
    private ImageButton currentTask;
    private ImageButton nextTask;
    private DrawerLayout mainDrawerLayout;
    private ActionBarDrawerToggle mainToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutNavigation);
        mainToggle = new ActionBarDrawerToggle(this, mainDrawerLayout, R.string.open, R.string.close);

        mainDrawerLayout.addDrawerListener(mainToggle);
        mainToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        ImageButton img = findViewById(R.id.imageButtonQuestionario);
        img.setClickable(true);
        img = findViewById(R.id.imageButtonTarefa1);
        img.setClickable(false);
        img = findViewById(R.id.imageButtonTarefa2);
        img.setClickable(false);
        currentTask = findViewById(R.id.imageButtonQuestionario);
        nextTask = findViewById(R.id.imageButtonTarefa1);
        listRanking = new ArrayList<>();
        badges = u.getBadgesList();
        addChildEventListener();
        final Activity activity = this;
        addEventFirebaseListener();
        addEventBadgesFirebaseListener();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mainToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void alertScan(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == QUESTIONNAIRE_REQUEST) {
            if (resultCode == RESULT_OK) {
                u.setTarefa();
                String x = data.getStringExtra("resposta");
                TextView text = findViewById(R.id.textCurso);
                text.setText("Curso: " + Questionario.curso);
                alertCurso(Questionario.curso);
                quest = true;
                currentTask.setImageResource(R.drawable.testecompleto);
                nextTask.setImageResource(R.drawable.quest);
                currentTask.setClickable(false);
                nextTask.setClickable(true);
            }
        }
        else if(requestCode == QUESTION_REQUEST){
            if(resultCode == RESULT_OK){
                u.setTarefa();
                currentTask.setClickable(false);
                currentTask.setImageResource(R.drawable.questcompleto);
                nextTask.setClickable(true);
                nextTask.setImageResource(R.drawable.quest);
            }

        }
        else{
            if(data !=null) {
                u.setTarefa();
                String x = data.getStringExtra("resposta");
                if(x.contains("DAI")){
                    currentTask.setClickable(false);
                    currentTask.setImageResource(R.drawable.questcompleto);
                }
                alertScan(x);
            }
        }

    }

    private void alertCurso(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Curso Sugerido");
        if(msg.contains("Nenhum")) msg = "Você respondeu não em todas a perguntas, nenhum curso sugerido :(";
        builder.setMessage(msg);
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public void iniciarJ(View view){
        Jornada j;
        myRef.child("jornada").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                j = dataSnapshot.getValue(Jornada.class);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(quest){
            j.iniciarTarefa(u.getTarefa(), QUESTION_REQUEST);
            myRef.child("jornada").child("tarefaAtual").setValue(j.getTarefaAtual()+1);
            if(j.tarefaAtual() == 1) {
                currentTask = findViewById(R.id.imageButtonTarefa1);
                nextTask = findViewById(R.id.imageButtonTarefa2);
            }

            if(j.tarefaAtual() >1){
                currentTask = findViewById(R.id.imageButtonTarefa2);
            }
        }
        else {
            Intent intent = new Intent(this, QuestionarioActivity.class);
            startActivityForResult(intent, QUESTIONNAIRE_REQUEST);
        }
    }

    public void iniciaMaps(View view){
        Intent intent = new Intent(this, MapsWebView.class);
        startActivity(intent);
    }

    public void mostraRanking(MenuItem item){
        Intent intent = new Intent(this, RankingActivity.class);
        startActivityForResult(intent, 9);
    }

    public void mostraBadges(MenuItem item){
        Intent intent = new Intent(this, BadgesActivity.class);
        startActivityForResult(intent, 9);
    }

    public void mostraTarefas(MenuItem item){
        Intent intent = new Intent(this, LogDeTarefasActivity.class);
        String message = u.getTarefa()+"";
        intent.putExtra(TASK_MESSAGE, message);
        startActivity(intent);
    }


    private void addEventFirebaseListener() {
        //Progressing

       myRef.child("users").child(u.getUid()).child("points").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               String c = (String)dataSnapshot.getValue();
               myRef.child("ranking").child(u.getEmail().replace(".", "")).setValue(c);
               TextView textView = findViewById(R.id.textPontos);
               textView.setText("Pontos:"+ c);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

          }
       });
    }

    private void addEventBadgesFirebaseListener() {
        //Progressing

        myRef.child("users").child(u.getUid()).child("badges").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String c = (String)dataSnapshot.getValue();
                u.setBadges(c);
                badges = u.getBadgesList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
