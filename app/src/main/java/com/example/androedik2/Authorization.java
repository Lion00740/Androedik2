package com.example.androedik2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

public class Authorization extends AppCompatActivity {
    String email = "nikitapap";
    String password = "12345";
    SharedPreferences sharedPref;
    Boolean switchStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RelativeLayout relativeLayout = findViewById(R.id.authorization);
        Switch switchColor = findViewById(R.id.switchColor);
        Button buttonAuthorization = findViewById(R.id.buttonAuthorization);
        EditText editEmail = findViewById(R.id.editTextEmail);
        EditText editPassword = findViewById(R.id.editTextPassword);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switchStatus = sharedPref.getBoolean("switchStatus", false);

        if (switchStatus)
        {
            int back = Color.BLACK;
            relativeLayout.setBackgroundColor(back);
        }

        Toast mytoast = new Toast(this);
        Toast.makeText(Authorization.this, "Create", Toast.LENGTH_SHORT).show();
        switchColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int back;
                if (switchColor.isChecked())
                {
                    back = Color.BLACK;
                    switchStatus = true;
                    relativeLayout.setBackgroundColor(back);
                }
                else
                {
                    back = Color.WHITE;
                    switchStatus = false;
                    relativeLayout.setBackgroundColor(back);
                }
            }
        });
        buttonAuthorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (editEmail.getText().toString().equals(email)
//                        && editPassword.getText().toString().equals(password))
                if(true)
                {
                    Intent intent = new Intent(Authorization.this, MyList.class);
                    intent.putExtra("hello", "Hello from FirstActivity");
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onStart()
    {
        Toast mytoast = new Toast(this);
        Toast.makeText(Authorization.this, "Start", Toast.LENGTH_SHORT).show();
        super.onStart();
    }
    @Override
    protected void onStop() {
        Toast.makeText(Authorization.this, "Stop", Toast.LENGTH_SHORT).show();
        super.onStop();
    }
    @Override
    protected void onPause() {
        Toast.makeText(Authorization.this, "Pause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }
    @Override
    protected void onResume() {
        Toast.makeText(Authorization.this, "Resume", Toast.LENGTH_SHORT).show();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean("switchStatus", switchStatus);
        editor.apply();

        Log.i("State", "Destroy");
        Toast.makeText(Authorization.this, "Destroy", Toast.LENGTH_SHORT).show();
    }
}