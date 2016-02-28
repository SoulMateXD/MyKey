package com.zhangdesheng.mykey;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/2/28.
 */
public class EditActivity extends Activity {
    ImageButton back ;
    ImageView ok ;
    EditText name;
    EditText account ;
    EditText password ;
    EditText remark ;

    private MyDatabaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        back = (ImageButton)findViewById(R.id.edit_back);
        ok = (ImageView)findViewById(R.id.edit_ok);
        name = (EditText)findViewById(R.id.edit_name);
        account = (EditText)findViewById(R.id.edit_account);
        password = (EditText)findViewById(R.id.edit_password);
        remark = (EditText)findViewById(R.id.edit_remark);
        dbHelper = new MyDatabaseHelper(this, "KeyStore.db", null, 1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                String keyName = name.getText().toString();
                String keyAccount = account.getText().toString();
                String keyPassword = password.getText().toString();
                String keyRemark = remark.getText().toString();
                if (!keyName.equals(null)  && !keyAccount.equals(null) && !keyPassword.equals(null)
                        && !keyRemark.equals(null))
                {
                    values.put("name", keyName);
                    values.put("account", keyAccount);
                    values.put("password", keyPassword);
                    values.put("remark", keyRemark);
                    if (iscontained(values)){
                        Toast.makeText(EditActivity.this, "覆盖成功", Toast.LENGTH_LONG).show();
                    }else {
                        db.insert("Key", null, values);
                        Toast.makeText(EditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditActivity.this, "所有数据都要填写哦", Toast.LENGTH_SHORT).show();
                }
                Intent intent  = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    boolean iscontained (ContentValues values) {    //遍历数据库看有没有一样的，一样则覆盖并返回true
        dbHelper = new MyDatabaseHelper(this, "KeyStore.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Key", null, null, null, null, null, null);
        boolean iscontained = false;
        if (cursor.moveToFirst()) {
            do {
                Key contentkey;  //数据库中的数据
                contentkey = new Key(cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("account")),
                        cursor.getString(cursor.getColumnIndex("password")),
                        cursor.getString(cursor.getColumnIndex("remark")));

                if(contentkey.getName().equals(values.get("name")) ||
                        contentkey.getAccount().equals(values.get("account")) ||
                        contentkey.getPassword().equals(values.get("password")) ||
                        contentkey.getRemark().equals(values.get("remark"))){
                    db.update("Key", values, "name = ?", new String[] { contentkey.getName()});
                    iscontained = true;
                }
            }while (cursor.moveToNext());
        }
        return iscontained;
    }

}
