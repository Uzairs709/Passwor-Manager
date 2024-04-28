package com.example.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class CredentialDatabase {
    private final String databaseName="User_Credential";

    //1st table
    private final String userInfoTable="User_Info";
    private final String userInfoKey="_User_Id";
    private final String userInfoEmail="_User_Email";
    private final String userInfoPassword="_User_Password";
    private final String userCredentialWebsite="_User_Website";
    private final int DatabaseVersion=1;
    
    //2nd Table

    private final String userCredentialTable="User_Credential";
    private final String userInfoForId="_user_id";
    private final String userCredentialKey="_id";
    private final String userCredentialEmail="_email";
    private final String userCredentialPassword="_password";
    private final String userCredentialIsDeleted="_delete_status";

    private Context classClientContext;
    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public CredentialDatabase(Context classClientContext) {
        this.classClientContext = classClientContext;
    }
    public void open() {
        myDatabaseHelper = new MyDatabaseHelper(classClientContext, databaseName, null, DatabaseVersion);
        sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
    }

    public void insertNewUser(String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put(userInfoEmail, email);
        cv.put(userInfoPassword, password);

        long result = sqLiteDatabase.insert(userInfoTable, null, cv);
        if(result == -1) {
            Toast.makeText(classClientContext, "User not Added", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(classClientContext, "User Added", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertNewSavedPassword(int user_id,String website,String email,String password){
        ContentValues cv=new ContentValues();
        cv.put(userInfoForId,user_id);
        cv.put(userCredentialWebsite,website);
        cv.put(userCredentialEmail,email);
        cv.put(userCredentialPassword,password);
        cv.put(userCredentialIsDeleted,0);

        long result =sqLiteDatabase.insert(userCredentialTable,null,cv);
        if(result==-1){
            Toast.makeText(classClientContext,"Password not Added",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(classClientContext,"Password Added Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    public void updateSavedData(int id,String website,String email,String password) {
        updateSavedData(id, website, email, password, 0);
    }

    public void deleteCredential(int id) {
        int rows = sqLiteDatabase.delete(userCredentialTable, userCredentialKey+"=?", new String[]{id+""});
        if(rows>0) {
            Toast.makeText(classClientContext, "Credential deleted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(classClientContext, "Credential not deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateSavedData(int id, String website, String email, String password,int isDeleted){
        ContentValues cv=new ContentValues();
        cv.put(userCredentialWebsite,website);
        cv.put(userCredentialPassword,password);
        cv.put(userCredentialEmail,email);
        cv.put(userCredentialIsDeleted,isDeleted);


        long result=sqLiteDatabase.update(userCredentialTable,cv,userCredentialKey+"=?", new String[]{id+""});

        if(result>0){
            Toast.makeText(classClientContext,"Update Successful",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(classClientContext,"Update not Successful",Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<UserCredential> readSavedUsersCredential(int id){
        ArrayList<UserCredential> userCredentials=new ArrayList<>();

        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+userCredentialTable+" where "+ userInfoForId+" = " + id +" And "+userCredentialIsDeleted+" = 0",null);
        int id_index=cursor.getColumnIndex(userCredentialKey);
        int email_index= cursor.getColumnIndex(userCredentialEmail);
        int pass_index=cursor.getColumnIndex(userCredentialPassword);
        int delete_index=cursor.getColumnIndex(userCredentialIsDeleted);
        int website_index=cursor.getColumnIndex(userCredentialWebsite);

        if(cursor.moveToFirst()){
            do{
                UserCredential u =new UserCredential();
                u.setId(cursor.getInt(id_index));
                u.setEmail(cursor.getString(email_index));
                u.setPassword(cursor.getString(pass_index));
                u.setDeletedStatus(cursor.getInt(delete_index));
                u.setWebsite(cursor.getString(website_index));

                userCredentials.add(u);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return userCredentials;
    }
 public ArrayList<UserCredential> readDeletedUsersCredential(int id){
        ArrayList<UserCredential> userCredentials=new ArrayList<>();

        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+userCredentialTable+" where "+ userInfoForId+" = " + id +" And "+userCredentialIsDeleted+" = 1",null);
     int id_index=cursor.getColumnIndex(userCredentialKey);
        int email_index= cursor.getColumnIndex(userCredentialEmail);
        int pass_index=cursor.getColumnIndex(userCredentialPassword);
        int delete_index=cursor.getColumnIndex(userCredentialIsDeleted);
        int website_index=cursor.getColumnIndex(userCredentialWebsite);

        if(cursor.moveToFirst()){
            do{
                UserCredential u =new UserCredential();
                u.setId(cursor.getInt(id_index));
                u.setEmail(cursor.getString(email_index));
                u.setPassword(cursor.getString(pass_index));
                u.setDeletedStatus(cursor.getInt(delete_index));
                u.setWebsite(cursor.getString(website_index));

                userCredentials.add(u);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return userCredentials;
    }

    public User returnUser(String email,String password){
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+userInfoTable+" WHERE " +userInfoEmail+ " = ? AND "+ userInfoPassword+ " = ? ", new String[]{email,password});
        User user=null;
        if(cursor!=null&&cursor.moveToFirst()){
            user=new User();
            int id_index=cursor.getColumnIndex(userInfoKey);
            int email_index=cursor.getColumnIndex(userInfoEmail);
            int pass_index= cursor.getColumnIndex(userInfoPassword);

            user.setId(cursor.getInt(id_index));
            user.setEmail(cursor.getString(email_index));
            user.setPass(cursor.getString(pass_index));

            cursor.close();
        }

        return user;
    }
    public void close() {
        sqLiteDatabase.close();
        myDatabaseHelper.close();
    }
    private class MyDatabaseHelper extends SQLiteOpenHelper {
        public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS "+userInfoTable+
                    "("+userInfoKey+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    userInfoEmail+ " TEXT NOT NULL, " +
                   userInfoPassword+" TEXT NOT NULL"+");"
            );
            db.execSQL("CREATE TABLE IF NOT EXISTS " + userCredentialTable + " ("
                    + userCredentialKey + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + userInfoForId + " INT NOT NULL, "
                    + userCredentialEmail + " TEXT NOT NULL, "
                    + userCredentialPassword + " TEXT NOT NULL, "
                    + userCredentialIsDeleted + " BOOLEAN NOT NULL, " +
                    userCredentialWebsite+" TEXT NOT NULL,"
                    + "FOREIGN KEY (" + userInfoForId + ") REFERENCES " + userInfoTable + "(" + userInfoKey + ")"
                    + ");");

        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE "+userInfoTable+ " IF EXISTS");
            db.execSQL("DROP TABLE "+userCredentialTable+ " IF EXISTS");
            onCreate(db);

        }

    }
}