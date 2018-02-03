package com.myantra.loginapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Myantra on 02-02-2018.
 */

public class DataModel extends SQLiteOpenHelper {

    public static final String TABLE_NAME ="Users";
    public static final String COL_1 ="userName";
    public static final String COL_2 ="EmailId";
    public static final String COL_3 ="schoolName";
    public static final String COL_4 ="phoneNumber";

    public DataModel(Context context) {
        super(context, TABLE_NAME,null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    /**OnCreating The DateBase**/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " +TABLE_NAME +"(userName PRIMARY KEY,EmailId,schoolName)");

    }

    /**OnUpgrade The Database**/

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);

    }

    /** Inserting The Data **/

    public boolean insertData(String userName,String emailId,String schoolName,String phoneNo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,userName);
        contentValues.put(COL_2,emailId);
        contentValues.put(COL_3,schoolName);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    /** GETUser From Database **/

    public UserModel getUser() {

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        UserModel userModel = new UserModel();
        if (cursor.moveToFirst()) {
            do {
                int user = cursor.getColumnIndex(COL_1);
                String userName = cursor.getString(user);
                int email = cursor.getColumnIndex(COL_2);
                String emailId = cursor.getString(email);
                int school = cursor.getColumnIndex(COL_3);
                String schoolName = cursor.getString(school);
                userModel.setEmailId(emailId);
                userModel.setSchoolName(schoolName);
                userModel.setUserName(userName);

            } while (cursor.moveToNext());
        }
        db.close();
        return userModel;
    }

}
