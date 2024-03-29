package com.example.grammarhelp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {



    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_MEANING = "meaning";
    static final String KEY_EXAMPLE = "example";
    static final String KEY_CPID = "cp_id";
    static final String KEY_COUNTERPART = "counterpart";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 4;
    static final String DATABASE_CREATE =
            "create table contacts (_id integer primary key autoincrement, "
                    + "name text not null, " +
                    "meaning text not null,example text not null, cp_id integer not null, counterpart text not null);";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    String nombre = "aremos";

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {



            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }
    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    //---insert a contact into the database---
    public long insertClone(int newID, String name,  String meaning, String example, int cp_id, String counterpart)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROWID, newID);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_MEANING, meaning);
        initialValues.put(KEY_EXAMPLE, example);
        initialValues.put(KEY_CPID, cp_id);
        initialValues.put(KEY_COUNTERPART, counterpart);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public long insertContact(String name,  String meaning, String example, int cp_id, String counterpart)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_MEANING, meaning);
        initialValues.put(KEY_EXAMPLE, example);
        initialValues.put(KEY_CPID, cp_id);
        initialValues.put(KEY_COUNTERPART, counterpart);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    //---deletes a particular contact---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    //---retrieves all the contacts---
    public Cursor getAllContacts()
    {
        return db.query(
                DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_NAME,
                        KEY_MEANING,
                        KEY_EXAMPLE,
                        KEY_CPID,
                        KEY_COUNTERPART}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]
                                {       KEY_ROWID,
                                        KEY_NAME,
                                        KEY_MEANING,
                                        KEY_EXAMPLE,
                                        KEY_CPID,
                                        KEY_COUNTERPART}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getPalabra(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]
                                {       KEY_ROWID,
                                        KEY_NAME,
                                        KEY_MEANING,
                                        KEY_EXAMPLE,
                                        KEY_CPID,
                                        KEY_COUNTERPART}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //---updates a contact---
    public boolean updateContact(long rowId, String name, String meaning, String example, int cp_id, String counterpart)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_MEANING, meaning);
        args.put(KEY_EXAMPLE, example);
        args.put(KEY_CPID, cp_id);
        args.put(KEY_COUNTERPART, counterpart);

        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public boolean ChangeIDContact(long rowId, long newid)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_ROWID, newid);


        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + newid, null) > 0;
    }

    public Cursor getWord(String palabra){
        String queryStudent = "SELECT * FROM contacts, contacts WHERE contacts.KEY_NAME = "+palabra+";";
        Cursor mCursor = db.rawQuery(queryStudent, null);

      return mCursor;
    }






}





