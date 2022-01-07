package com.ipi.filmquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ipi.filmquiz.pojos.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvquestion;
    private TextView tvscore;
    private Button trueBtn;
    private Button falseBtn;
    private List<Question> questions = new ArrayList<Question>();
    private int indexQuestion= 0;
    private int indexScore= 0;
    private Question question;
    private final String TAG = "QUIZEACTIVITY";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";
    private static final String KEY_REPLAY = "replay";
    private Button replayBtn;
    public static final String KEY_QUESTION= "question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate() called");

        tvquestion = findViewById(R.id.question);
        tvscore = findViewById(R.id.score);
        trueBtn = findViewById(R.id.button1);
        falseBtn = findViewById(R.id.button2);
        replayBtn = findViewById(R.id.replay);
        //getString(R.string.question);

        tvscore.setText("score= "+ indexScore);

        //add the questions
        questions.add(new Question(getString(R.string.question_ai), true));
        questions.add(new Question(getString(R.string.question_taxi_driver), true));
        questions.add(new Question(getString(R.string.question_2001), false));
        questions.add(new Question(getString(R.string.question_reservoir_dogs), true));
        questions.add(new Question(getString(R.string.question_citizen_kane), false));

        replayBtn.setVisibility(View.INVISIBLE);

        //get all the info in bundle
        if (savedInstanceState != null)
        {
            indexQuestion = savedInstanceState.getInt(KEY_INDEX);
            indexScore = savedInstanceState.getInt(KEY_SCORE);
        }

        question = questions.get(indexQuestion);

        tvquestion.setText(question.getText());


        trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the response is correct
                if(question.isAnswer())
                {
                    indexScore++;
                    Toast toast = Toast.makeText(getApplicationContext(),"TRUE",Toast.LENGTH_SHORT);
                    toast.show();
                }
                indexQuestion++;

                if (indexQuestion <= questions.size()-1) {

                    question = questions.get(indexQuestion);
                    tvquestion.setText(question.getText());
                    tvscore.setText("score= " + indexScore);
                }

                if (indexQuestion >= questions.size()) {
                    replayBtn.setVisibility(View.VISIBLE);
                    trueBtn.setEnabled(false);
                    falseBtn.setEnabled(false);
                }


            }
        });
        falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(indexQuestion <= questions.size() - 1 && !question.isAnswer())
                {
                    indexScore++;

                    Toast toast = Toast.makeText(getApplicationContext(),"FALSE",Toast.LENGTH_SHORT);
                    toast.show();
                }

                indexQuestion++;

                if (indexQuestion < questions.size()-1) {

                    question = questions.get(indexQuestion);
                    tvquestion.setText(question.getText());
                }

                if(indexQuestion >= questions.size()){
                    replayBtn.setVisibility(View.VISIBLE);
                    trueBtn.setEnabled(false);
                    falseBtn.setEnabled(false);
                }
                // affiche le score
                tvscore.setText(String.format("Score : %d", indexScore));

            }


        });

        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( indexQuestion >= questions.size()){
                    indexQuestion= 0;
                    indexScore = 0;
                    question = questions.get(indexQuestion);
                    tvquestion.setText(question.getText());
                    tvscore.setText(String.format("Score : %d", indexScore));

                }
                if (indexQuestion <= questions.size() -1){
                    trueBtn.setEnabled(true);
                    falseBtn.setEnabled(true);
                }
            }
        });


    }


    //save the index od score and questions
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState() called");
        outState.putInt(KEY_INDEX, indexQuestion);
        outState.putInt(KEY_SCORE, indexScore);

        outState.putBoolean(KEY_REPLAY, replayBtn.getVisibility() == View.VISIBLE ? true: false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // test iem id which was choosed
        switch (item.getItemId())
        {
            case R.id.cheat:
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);

                intent.putExtra(KEY_QUESTION, question);
                startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "onRestart() called");
    }
}