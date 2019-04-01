package com.example.chetan578.quizzler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView Question,score,textView;
    RelativeLayout startLayout,endLayout;
ArrayList<String>answers=new ArrayList<>();
String [] ans=new String[4];
int locofCorrectAnswer=0;
Button b1,b2,b3,b4,start;
ProgressBar progressBar,probar;
final int PROGRESS_BAR_INC= 10;
int current_score=0;
int total_ques=0;
    public class ProgressBarAnimation extends Animation {
        private ProgressBar probar;
        private TextView textView;
        private float from;
        private float to;


        public ProgressBarAnimation( ProgressBar probar, float from, float to, TextView textView) {
            this.probar = probar;
            this.textView = textView;
            this.from = from;
            this.to = to;

        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value= from + (to-from)*interpolatedTime;
            probar.setProgress((int) value);
            textView.setText((int) value + "%");
            if(value==to)
            {
                startLayout.setVisibility(View.INVISIBLE);
                endLayout.setVisibility(View.VISIBLE);
            }
        }
    }

public void selectedOption(View view)
{
    if(view.getTag().toString().equals(Integer.toString(locofCorrectAnswer)))
    {
        Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT).show();
        progressBar.incrementProgressBy(PROGRESS_BAR_INC);
        current_score++;
    }
    else{
        Toast.makeText(this, "Wrong , it was "+answers.get(0), Toast.LENGTH_SHORT).show();
        progressBar.incrementProgressBy(PROGRESS_BAR_INC);

    }
    total_ques++;
    score.setText("Score is " + current_score +" / "+ total_ques);
    start();
    if(total_ques==10)
    {
        new AlertDialog.Builder(this)
                .setTitle("Game over ")
                .setCancelable(false)
                .setMessage("Your Final score is "+ current_score + "!")
                .setPositiveButton("Play Again?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        start();
                        current_score=0;
                        total_ques=0;
                        score.setText("Score is " + current_score +" / "+ total_ques);
                        progressBar.setProgress(0);

                    }
                }).setNegativeButton("Close Application", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).show();

    }
}

public void start(){
        DownloadTask task = new DownloadTask();
        Intent intent =getIntent();
        String x=intent.getStringExtra("same");
        if(x.equals("television"))
        task.execute("https://raw.githubusercontent.com/chetan578/JSONData/master/television.json");
        else if(x.equals("science"))
            task.execute("https://raw.githubusercontent.com/chetan578/JSONData/master/science.json");
        else if(x.equals("sports"))
            task.execute("https://raw.githubusercontent.com/chetan578/JSONData/master/sports.json");
        else if(x.equals("gk"))
            task.execute("https://raw.githubusercontent.com/chetan578/JSONData/master/gk.json");
        else if(x.equals("music"))
            task.execute("https://raw.githubusercontent.com/chetan578/JSONData/master/music.json");
        else if(x.equals("computers"))
            task.execute("https://raw.githubusercontent.com/chetan578/JSONData/master/computers.json");
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();

                }
                return result;

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "could not find the question", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.i("information", result);
            try {
                JSONObject jsonObject=new JSONObject(result);
                String obj=jsonObject.getString("results");
                JSONArray array =new JSONArray(obj);
                JSONObject part = null;
                Random num =new Random();
                int random=num.nextInt(18);
                try {
                    part = array.getJSONObject(random);
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "could not load a question!", Toast.LENGTH_SHORT).show();
                }
                String question=part.getString("question");
                Question.setText(question);
                answers.clear();
                String correctAnswer=part.getString("correct_answer");
                answers.add(correctAnswer);
                String wrongAnswers=part.getString("incorrect_answers");
                JSONArray incorrectAnswers=new JSONArray(wrongAnswers);
                for(int i=0;i<3;i++) {
                    String x = (String) incorrectAnswers.get(i);
                    answers.add(x);
                }
                locofCorrectAnswer=num.nextInt(4);
                int i=0;
                int j=0;
                while(i<4)
                {
                    if (locofCorrectAnswer == i) {
                        ans[i] = answers.get(0);
                    }
                    else {
                        ans[i] = (String) incorrectAnswers.get(j);
                        j++;
                    }
                    i++;
                    }
                b1.setText(ans[0]);
                b2.setText(ans[1]);
                b3.setText(ans[2]);
                b4.setText(ans[3]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
            @Override
            protected void onCreate (Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                start =findViewById(R.id.start);
                b1=findViewById(R.id.button1);
                b2=findViewById(R.id.button2);
                b3=findViewById(R.id.button3);
                b4=findViewById(R.id.button4);
                startLayout=findViewById(R.id.startLayout);
                endLayout=findViewById(R.id.endLayout);
                Question=findViewById(R.id.question);
                score=findViewById(R.id.score);
                progressBar=findViewById(R.id.progressBar);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                probar=findViewById(R.id.proBar);
                textView=findViewById(R.id.text_view);
                probar.setMax(100);
                probar.setScaleY(3f);
                progressAnimation();
                start();
            }
    public void progressAnimation()
    {
        ProgressBarAnimation progressBarAnimation=new ProgressBarAnimation(probar,0f,100f,textView);
        progressBarAnimation.setDuration(4000);
        probar.setAnimation(progressBarAnimation);
    }
    }