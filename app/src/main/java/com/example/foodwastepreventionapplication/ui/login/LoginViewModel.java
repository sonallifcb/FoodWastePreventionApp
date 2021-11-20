package com.example.foodwastepreventionapplication.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Patterns;

import com.example.foodwastepreventionapplication.FWPAContract;
import com.example.foodwastepreventionapplication.FWPADbHelper;
import com.example.foodwastepreventionapplication.data.LoginRepository;
import com.example.foodwastepreventionapplication.data.Result;
import com.example.foodwastepreventionapplication.data.model.LoggedInUser;
import com.example.foodwastepreventionapplication.R;

import java.io.IOException;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, FWPADbHelper dbHelper) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result;

        Cursor cursor = db.rawQuery
                ("SELECT " + FWPAContract.Users._ID + " FROM " + FWPAContract.Users.TABLE_NAME + " WHERE " + FWPAContract.Users.COLUMN_NAME_USERNAME + "=?" + " AND " + FWPAContract.Users.COLUMN_NAME_PASSWORD + "=?", new String[]{username,password});

        Log.d("login","Cursor Count : " + cursor.getCount());

        if(cursor.getCount()>0){

            cursor.moveToNext();
            Integer Id = cursor.getInt(
                    cursor.getColumnIndexOrThrow("_id"));

            LoggedInUser User =
                    new LoggedInUser(
                            Id,
                            username);
            result = new Result.Success<>(User);
        }
        else {
            result = new Result.Error(new IOException("Error logging in"));
        }
        cursor.close();

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName(),data.getUserId())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}