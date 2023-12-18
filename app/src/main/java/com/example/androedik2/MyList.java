package com.example.androedik2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
    final Looper looper = Looper.getMainLooper();
    List<User> userList;
    ArrayList<String> myStringArray;
    ArrayAdapter<String> TextAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list);
        myStringArray = new ArrayList<String>();
        Bundle arguments = getIntent().getExtras();
        userList = db.getAllUsers();
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

        TextAdapter =
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
                new MyThread(handler, db).deleteElement(userList.get(index));
            }
        });

        Button add = findViewById(R.id.AddButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editLogin = findViewById(R.id.editLogin);
                EditText editPass = findViewById(R.id.editPass);
                new MyThread(handler, db).addElements(editLogin.getText().toString(), editPass.getText().toString());
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

    final Handler handler = new Handler(looper) {
        public void handlerMessage(Message msg)
        {
            switch(msg.sendingUid)
            {
                case 1:
                    userList = (List<User>) msg.obj;
                    myStringArray.add(userList.get(userList.size() - 1)._login.toString() + "\t" + userList.get(userList.size() - 1)._pass.toString());
                    TextAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    userList = (List<User>) msg.obj;
                    myStringArray.remove(userList.get(index));
                    break;
            }
        }
    };
}