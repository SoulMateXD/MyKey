package com.zhangdesheng.mykey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout = null;

    private List<Key> keys = new ArrayList<>();

    MyDatabaseHelper dbHelper = null;

    EditText searchEditText;

    void initData(){
        dbHelper = new MyDatabaseHelper(this, "KeyStore.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Key",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                Key key = new Key(name, account, password, remark);
                keys.add(key);
            }while(cursor.moveToNext());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();  //配置listview
        ListView listView = (ListView) findViewById(R.id.main_listview);
        listView.setAdapter(new Adapter<Key>(keys, MainActivity.this, R.layout.listview_item) {
            @Override
            public void convert(KeyViewHolder viewHolder, final Key position) {
                TextView name = viewHolder.getView(R.id.item_name);
                name.setText(position.getName());
                TextView account = viewHolder.getView(R.id.item_account);
                account.setText(position.getAccount());
                TextView password = viewHolder.getView(R.id.item_password);
                password.setText(position.getPassword());
                TextView remark = viewHolder.getView(R.id.item_remark);
                remark.setText(position.getRemark());
                ImageView delete = viewHolder.getView(R.id.item_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("您确定要删除该条密钥吗？");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.delete("Key", "name = ?", new String[]{position.getName()});
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                });
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //配置drawerlayout
        ImageView startDrawer = (ImageView) findViewById(R.id.start_drawer);
        startDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        FloatingActionButton create = (FloatingActionButton) findViewById(R.id.main_create); //配置floatingbutton
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView searchIcon = (ImageView)findViewById(R.id.main_search_icon);
        searchEditText = (EditText)findViewById(R.id.main_search_edittext);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchName = searchEditText.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Key",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do{
                        String contentName = cursor.getString(cursor.getColumnIndex("name"));
                        if (contentName.equals(searchName)){
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("Name : " + cursor.getString(cursor.getColumnIndex("name")));
                            dialog.setMessage("Account : " + cursor.getString(cursor.getColumnIndex("account"))
                                    + "\n" + "Password : " + cursor.getString(cursor.getColumnIndex("password"))
                                    + "\n" + "Remark : " + cursor.getString(cursor.getColumnIndex("remark")));
                            dialog.setCancelable(false);
                            dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                        }
                    }while (cursor.moveToNext());
                }
            }
        });

    }
}
