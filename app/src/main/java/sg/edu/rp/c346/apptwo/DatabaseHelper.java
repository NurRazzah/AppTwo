package sg.edu.rp.c346.apptwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16022738 on 26/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "family.db";
    public static final String TABLE_NAME = "family_table";
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "MEDICINE";
    //public static final String COL_3 = "PRESCRIPTION";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(NAME TEXT unique, MEDICINE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String name, String medicine){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, medicine);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }else {
            return true;

        }

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from "+  TABLE_NAME, null);
        return res;


    }

    public boolean updateData(String name, String medicine){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, medicine);
        db.update(TABLE_NAME, contentValues,"name = ?", new String[] {name});
        db.update(TABLE_NAME, contentValues,"medicine = ?", new String[] {medicine});

        return true;

    }

    public Integer deleteData(String name, String medicine){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "name = ?", new String[] {name});
    }

    public boolean check(String name){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor res = db.rawQuery("Select * from "+  TABLE_NAME, null);
        List<String> checkList = new ArrayList<>();
        String query = "SELECT "+ COL_1 +" FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                checkList.add(cursor.getString(0));
            }
            while(cursor.moveToNext());
        }
        for(int i = 0; i < checkList.size(); i++){
            if(name.equals(checkList.get(i))){
                check = true;
                break;
            }
        }
        return check;
    }
}
