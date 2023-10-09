package com.example.androedik2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyList extends Activity {
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list);

        Bundle arguments = getIntent().getExtras();
        String str = arguments.get("hello").toString();
        Toast.makeText(MyList.this, str, Toast.LENGTH_SHORT).show();

        Button delete = findViewById(R.id.DeleteButton);
        Button add = findViewById(R.id.AddButton);

        ArrayList<String> myStringArray = new ArrayList<String>();
        ArrayAdapter<String> TextAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);
        ListView textList = findViewById(R.id.textList);

        textList.setAdapter(TextAdapter);
        EditText editText = findViewById(R.id.EditText);

        textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!myStringArray.isEmpty())
                {
                    myStringArray.remove(index);
                }
                index = 0;

                TextAdapter.notifyDataSetChanged();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStringArray.add(editText.getText().toString());
                editText.setText("");
                TextAdapter.notifyDataSetChanged();
            }
        });
    }
}