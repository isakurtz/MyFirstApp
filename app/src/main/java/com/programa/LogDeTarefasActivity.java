package com.programa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LogDeTarefasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_de_tarefas);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainMenuActivity.TASK_MESSAGE);
        if(message.equals("2")){
            TextView text = findViewById(R.id.textView);
            text.setVisibility(View.VISIBLE);
        }
        if(message.equals("3")){
            TextView text = findViewById(R.id.textView);
            text.setVisibility(View.VISIBLE);

            text = findViewById(R.id.textView3);
            text.setVisibility(View.VISIBLE);
        }
    }
}
