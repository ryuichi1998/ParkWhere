package com.example.myapplication.db.user;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.myapplication.db.carpark.CarParkDetailsDataBase;

import java.io.IOException;

public class UserRepository {
    private UserDao userDao;
    private User user;

    public UserRepository(Context context) throws IOException {
        UserDataBase dataBase = UserDataBase.getInstance(context);
        userDao = dataBase.userDao();
//        user = userDao.getUserByUserId();
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    public static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    public static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    public static class login extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private String email;
        private String pass;

        @Override
        protected Void doInBackground(User... users) {
            userDao.login(email, pass);
            return null;
        }
    }


 }
