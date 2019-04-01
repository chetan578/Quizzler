package com.example.chetan578.quizzler;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {
Button television,music,computers,sports,vehicles,gk;
    Intent intent;
    TextView text;

public void openTele(View view)
{
    intent=new Intent(CategoryActivity.this,MainActivity.class);
    intent.putExtra("same","television");
      startActivity(intent);
}

    public void openScience(View view)
    {
        intent=new Intent(CategoryActivity.this,MainActivity.class);
        intent.putExtra("same","science");
        startActivity(intent);

    }
    public void openSports(View view)
    {
        intent=new Intent(CategoryActivity.this,MainActivity.class);
        intent.putExtra("same","sports");
        startActivity(intent);
    }
    public void openGk(View view)
    {
        intent=new Intent(CategoryActivity.this,MainActivity.class);
        intent.putExtra("same","gk");
        startActivity(intent);
    }
    public void openMusic(View view) {

        intent = new Intent(CategoryActivity.this, MainActivity.class);
        intent.putExtra("same", "music");
        startActivity(intent);
    }
    public void openComputers(View view)
    {
        intent=new Intent(CategoryActivity.this,MainActivity.class);
        intent.putExtra("same","computers");
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
     television=findViewById(R.id.television);
     music=findViewById(R.id.music);
     computers=findViewById(R.id.computers);
     sports=findViewById(R.id.sports);
     vehicles=findViewById(R.id.vehicles);
     gk=findViewById(R.id.gk);
     text=findViewById(R.id.text);
     Intent intent= getIntent();
     String name =intent.getStringExtra("mName");
     text.setText("Hi "+ name + " !");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
