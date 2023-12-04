package com.example.androedik2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
        textList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MyList.this);
        final EditText et = new EditText(MyList.this);
        builder.setView(et).setTitle("Укажите новый пароль пользователя").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.updateUser(userList.get(index), et.getText().toString());
                        userList.get(index).setPass(et.getText().toString());
                        myStringArray.remove(index);
                        myStringArray.add(index, userList.get(index).getLogin() + "\t" + userList.get(index).getPass());
                        textList.post(new Runnable() {
                            @Override
                            public void run() {
                                TextAdapter.notifyDataSetChanged();
                            }
                        });
                        dialogInterface.cancel();
                    }
                }).start();
            }
        });

        AlertDialog alertDialog = builder.create();

        Button delete = findViewById(R.id.DeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EditText editLogin = findViewById(R.id.editLogin);
                        for(int i = 0; i < userList.size(); i++)
                        {
                            if (editLogin.getText().toString().equals(userList.get(i)._login))
                            {
                                db.deleteUser(userList.get(i));
                                myStringArray.remove(i);
                                userList.remove(i);
                                i--;
                                textList.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        TextAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });

        Button add = findViewById(R.id.AddButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EditText editLogin = findViewById(R.id.editLogin);
                        EditText editPass = findViewById(R.id.editPass);
                        userList.add(new User(editLogin.getText().toString(), editPass.getText().toString()));
                        db.addUser(new User(editLogin.getText().toString(), editPass.getText().toString()));
                        myStringArray.add(editLogin.getText().toString() + "\t" + editPass.getText().toString());
                        textList.post(new Runnable() {
                            @Override
                            public void run() {
                                TextAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
            }
        });

        Button edit = findViewById(R.id.Edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }
}