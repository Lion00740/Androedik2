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
import java.util.List;

public class MyList extends Activity {
    int index = 0;
    DatabaseHandler db = new DatabaseHandler(this);
    User currentUser = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list);

        Bundle arguments = getIntent().getExtras();
        String str = arguments.get("hello").toString();
        Toast.makeText(MyList.this, str, Toast.LENGTH_SHORT).show();

        ArrayList<String> myStringArray = new ArrayList<String>();

        List<User> userList = db.getAllUsers();
        for (int i = 0; i < userList.size(); i++)
        {
            myStringArray.add(userList.get(i)._login + "\t" + userList.get(i)._pass);
        }

        int userId = Integer.parseInt(arguments.get("account").toString());
        for(int i = 0; i < userList.size(); i++)
        {
            if (userList.get(i).getID() == userId)
            {
                currentUser = userList.get(i);
                index = i;
            }
        }

        ArrayAdapter<String> TextAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);
        ListView textList = findViewById(R.id.textList);

        textList.setAdapter(TextAdapter);
        TextAdapter.notifyDataSetChanged();
        EditText editText = findViewById(R.id.EditText);

        textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }
        });
        

        Button delete = findViewById(R.id.DeleteButton);
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

        Button add = findViewById(R.id.AddButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStringArray.add(editText.getText().toString());
                editText.setText("");
                TextAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }
}