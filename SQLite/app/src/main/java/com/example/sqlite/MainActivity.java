package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 数据库对象
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件并实例化
        initView();
    }

    private void initView() {
        Button insert = findViewById(R.id.insert_button);
        Button insert_clear = findViewById(R.id.clear_insert_button);

        Button update = findViewById(R.id.update_button);
        Button update_clear = findViewById(R.id.clear_update_button);

        Button delete = findViewById(R.id.delete_button);
        Button delete_clear = findViewById(R.id.clear_delete_button);

        Button query = findViewById(R.id.query);
        Button query_clear = findViewById(R.id.clear_query);

        //为所有按钮对象设置监听器
        insert.setOnClickListener(this);
        insert_clear.setOnClickListener(this);

        update.setOnClickListener(this);
        update_clear.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_clear.setOnClickListener(this);

        query.setOnClickListener(this);
        query_clear.setOnClickListener(this);

        // 新建一个名为test_db的数据库
        DatabaseHelper databaseHelper = new DatabaseHelper(this, "test_db", null, 1);
        db = databaseHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View view) {
        // 插入
        EditText insert_text = findViewById(R.id.insert_text);
        String insert_data = insert_text.getText().toString();

        // 更新
        EditText update_before_text = findViewById(R.id.update_before_text);
        EditText update_after_text = findViewById(R.id.update_after_text);
        String update_before_data = update_before_text.getText().toString();
        String update_after_data = update_after_text.getText().toString();

        // 删除
        EditText delete_text = findViewById(R.id.delete_text);
        String delete_data = delete_text.getText().toString();


        // 查询
        TextView textView = findViewById(R.id.query_show);


        // 判断按钮选择
        switch (view.getId()) {
            // 插入按钮
            case R.id.insert_button:
                insert(insert_data);
                break;
            // 插入清除按钮
            case R.id.clear_insert_button:
                insert_text.setText("");
                break;
            // 更新按钮
            case R.id.update_button:
                update(update_before_data, update_after_data);
                break;
            // 更新清除按钮
            case R.id.clear_update_button:
                update_before_text.setText("");
                update_after_text.setText("");
                break;
            // 删除按钮
            case R.id.delete_button:
                delete(delete_data);
                break;
            // 删除清除按钮
            case R.id.clear_delete_button:
                delete_text.setText("");
                break;
            // 查询全部按钮
            case R.id.query:
                StringBuilder textView_data = query();
                // 设置进视图
                textView.setText(textView_data.toString());
                break;
            // 清除查询按钮
            case R.id.clear_query:
                textView.setText("");
                textView.setHint("查询内容为空");
                break;
            default:
                break;
        }
    }

    /**
     * @return 查询结果并封装成StringBuilder
     */
    private StringBuilder query() {
        // 条件参数
        String[] columns = new String[]{"name"};
        // 创建游标对象
        Cursor cursor = db.query("user", columns, null, null, null, null, null);
        // 拼接结果
        StringBuilder textView_data = new StringBuilder(80);
        // 遍历游标
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            textView_data.append(name);
            textView_data.append("\n");
        }
        cursor.close();
        return textView_data;
    }

    /**
     * 删除操作
     *
     * @param delete_data WHERE some_column = some_value
     */
    private void delete(String delete_data) {
        String[] args = new String[]{delete_data};
        // DELETE FROM table_name WHERE some_column = some_value
        db.delete("user", "name = ?", args);
    }

    /**
     * 更新操作
     *
     * @param update_before_data SET column1 = value1,column2 = value2
     * @param update_after_data  WHERE some_column = some_value
     */
    private void update(String update_before_data, String update_after_data) {
        // 更新的条件
        String[] before_value = new String[]{update_before_data};
        // 更新的数据
        ContentValues after_value = new ContentValues();
        // UPDATE table_name SET column1 = value1,column2 = value2 WHERE some_column = some_value
        after_value.put("name", update_after_data);
        db.update("user", after_value, "name = ?", before_value);
    }

    /**
     * 插入操作
     *
     * @param insert_data VALUES (value1,value2,value3)
     */
    private void insert(String insert_data) {
        ContentValues insert_value = new ContentValues();
        // INSERT INTO table_name (column1,column1,column1) VALUES (value1,value2,value3)
        insert_value.put("name", insert_data);
        db.insert("user", null, insert_value);
    }
}
