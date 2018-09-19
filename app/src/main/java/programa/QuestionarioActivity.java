package programa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionarioActivity extends AppCompatActivity {

    private Questionario quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);
        quest = new Questionario();
    }

    public void clickIniciar(View view){
        Button iniciar = findViewById(R.id.buttonIniciarQuestion);
        iniciar.setVisibility(View.INVISIBLE);
        TextView text = findViewById(R.id.textPergunta);
        text.setText(quest.getPergunta());
        Button sim = findViewById(R.id.buttonSim);
        sim.setVisibility(View.VISIBLE);
        Button nao = findViewById(R.id.buttonnao);
        nao.setVisibility(View.VISIBLE);
    }

    public void clickSim(View view){
            quest.daPonto(true);
            TextView per = findViewById(R.id.textPergunta);
            String msg = quest.getPergunta();
            if(msg !=null){
                per.setText(msg);
            }
            else{
                Questionario.curso = quest.getCurso();
                Intent devolve = new Intent();
                devolve.putExtra("resposta", "Resposta");
                setResult(RESULT_OK, devolve);
                finish();
            }
    }

    public void clicknao(View view){
        quest.daPonto(false);
        TextView per = findViewById(R.id.textPergunta);
        String msg = quest.getPergunta();
        if(msg !=null){
            per.setText(msg);
        }
        else{
            Questionario.curso = quest.getCurso();
            Intent devolve = new Intent();
            devolve.putExtra("resposta", "Resposta");
            setResult(RESULT_OK, devolve);
            finish();
        }
    }


}
