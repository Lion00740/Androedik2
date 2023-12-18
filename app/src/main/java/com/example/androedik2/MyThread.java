package com.example.androedik2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.List;

public class MyThread {
    Handler handler;
    DatabaseHandler db;
    final Message message = Message.obtain();
    MyThread(Handler main_handler, DatabaseHandler db)
    {
        this.db = db;
        this.handler = main_handler;
    }

    public void authorization()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> userList = db.getAllUsers();
                message.obj = userList;
                handler.sendMessage(message);
            }
        }).start();
    }
    public void addElements(String login, String pass)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.addUser(new User(login, pass));
                List<User> userList = db.getAllUsers();
                message.sendingUid = 1;
                message.obj = userList;
                handler.sendMessage(message);
            }
        }).start();
    }
    public void deleteElement(User user)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.deleteUser(user);
                List<User> userList = db.getAllUsers();
                message.sendingUid = 2;
                message.obj = userList;
                handler.sendMessage(message);
            }
        }).start();
    }
}
