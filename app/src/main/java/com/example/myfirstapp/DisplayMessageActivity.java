package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayMessageActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> userList = new ArrayList<>();
    private User u;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
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

        message= currentUser.getEmail() + " Pontos: " + u.getPoints() ;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

       //myRef.child("users").child(u.getUid()).child("points").setValue(Integer.parseInt(u.getPoints())+10 +"");

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
      // addEventFirebaseListener();

    }


    public void iniciarJ( View view){

        Intent intent = new Intent(this, JornadaActivity.class);
        startActivity(intent);
    }

//    private void addEventFirebaseListener() {
//        //Progressing
//
//
//       myRef.child("users").addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(DataSnapshot dataSnapshot) {
//              // currentUser = dataSnapshot.getValue(User.class);
//               //TextView textView = findViewById(R.id.testPoints);
//               //textView.setText(currentUser.getPoints());
//               System.out.println("");
//           }
//
//           @Override
//           public void onCancelled(DatabaseError databaseError) {
//
//           }
//        });
//    }

}
