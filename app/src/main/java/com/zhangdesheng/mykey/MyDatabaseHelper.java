package com.zhangdesheng.mykey;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/27.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_KEY = "create table Key ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "account text, "
            + "remark text,"
            + "password text)";
    private Context mContext;

    public MyDatabaseHelper(Context context, String name ,SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        mContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_KEY);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
