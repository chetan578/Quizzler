package com.example.chetan578.quizzler;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //Prevent keyboard from popping up when starting
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    //Start Quiz Button
    public void startBtn(View view){
        EditText mNameView = findViewById(R.id.name_edittext);
        String mName = mNameView.getText().toString().trim();

        if (mName.equals("")){
            //If empty string, make a toast message
            String enter_name = getString(R.string.txt_pls_enter_ur_name);
            Toast.makeText(FormActivity.this,
                    enter_name,
                    Toast.LENGTH_SHORT).show();
        }else{
            //Go to quiz
            goToQuiz(mName);
        }
    }

    //Go to Quiz Intent
    private void goToQuiz(String mname) {
        Intent intent_main = new Intent(FormActivity.this, CategoryActivity.class);
        intent_main.putExtra("mName", mname);
        startActivity(intent_main);
    }

    //Prevent Back Button
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}

