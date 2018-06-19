package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

        public void okClicked(View view){
            EditText edit = findViewById(R.id.editText2);
            String res = edit.getText().toString();
            res = res.toLowerCase().replaceAll("\\.|,|0|","").trim();
            TextView editText = findViewById(R.id.textView5);
            if(res.equals("73")){
                editText.setText("Parabéns + 50 pontos!");
                myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints())+50 +"");
            }
            else{
                editText.setText("Resposta Errada");
            }


        }

}
