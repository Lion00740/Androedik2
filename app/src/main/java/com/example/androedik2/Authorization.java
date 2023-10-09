package com.example.androedik2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Authorization extends AppCompatActivity {
    String email = "nikitapap";
    String password = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast mytoast = new Toast(this);
        Toast.makeText(Authorization.this, "Create", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        Button buttonAuthorization = findViewById(R.id.buttonAuthorization);
        EditText editEmail = findViewById(R.id.editTextEmail);
        EditText editPassword = findViewById(R.id.editTextPassword);

        buttonAuthorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editEmail.getText().toString().equals(email)
                        && editPassword.getText().toString().equals(password))
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
        Log.i("State", "Destroy");
        Toast.makeText(Authorization.this, "Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}