package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class JornadaActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> userList = new ArrayList<>();
    private User u;
    private FirebaseAuth mAuth;

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
        Button b = findViewById(R.id.button2);
        b.setVisibility(View.GONE);

    }


    public void Selected( View view){

        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton7:
                if (checked){
                    TextView editText = findViewById(R.id.resultado);
                    editText.setText("Parabéns + 30 pontos!");
                    myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints())+30 +"");
                    Button b = findViewById(R.id.button2);
                    b.setVisibility(View.VISIBLE);
                }
                break;
            default: TextView editText = findViewById(R.id.resultado);
                editText.setText("Resposta Errada");
        }
    }

    public void butoonclicked(View view){
        Intent intent = new Intent(this, jornada2.class);
        //String message = editText.getText().toString();
        startActivity(intent);
    }
}
