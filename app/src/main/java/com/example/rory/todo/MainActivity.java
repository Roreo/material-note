package com.example.rory.todo;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.rory.todo.db.TaskContract;
import com.example.rory.todo.db.TaskDBHelper;

public class MainActivity extends ListActivity {
    private ListAdapter listAdapter;
    private TaskDBHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                editTask();
                return true;

            case R.id.filter_Business:
                updateUIFilterBusiness();
                return true;

            case R.id.filter_Personal:
                updateUIFilterPersonal();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }

    }



    private void updateUI() {
        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK, TaskContract.Columns.TODOCAT},
                null, null, null, null, null);

        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.task_view,
                cursor,
                new String[]{TaskContract.Columns.TASK, TaskContract.Columns.TODOCAT},
                new int[]{R.id.taskTextView, R.id.taskCatView},
                0
        );
        this.setListAdapter(listAdapter);
    }

    private void updateUIFilterPersonal() {

            helper = new TaskDBHelper(MainActivity.this);
            SQLiteDatabase sqlDB = helper.getReadableDatabase();
            Cursor cursor = sqlDB.query(TaskContract.TABLE, new String[] { TaskContract.Columns._ID, TaskContract.Columns.TASK, TaskContract.Columns.TODOCAT },
                    TaskContract.Columns.TODOCAT + " LIKE ?" ,
                    new String[] {"%" + "P" + "%"},
                    null, null, null, null);

            listAdapter = new SimpleCursorAdapter(
                    this,
                    R.layout.task_view,
                    cursor,
                    new String[]{TaskContract.Columns.TASK, TaskContract.Columns.TODOCAT},
                    new int[]{R.id.taskTextView, R.id.taskCatView},
                    0
            );
            this.setListAdapter(listAdapter);

        }


    private void updateUIFilterBusiness() {

        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE, new String[] { TaskContract.Columns._ID, TaskContract.Columns.TASK, TaskContract.Columns.TODOCAT },
                TaskContract.Columns.TODOCAT + " LIKE ?" ,
                new String[] {"%" + "B" + "%"},
                null, null, null, null);

        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.task_view,
                cursor,
                new String[]{TaskContract.Columns.TASK, TaskContract.Columns.TODOCAT},
                new int[]{R.id.taskTextView, R.id.taskCatView},
                0
        );
        this.setListAdapter(listAdapter);

    }

    public void editTask(){

        Intent nextScreen = new Intent(this, EditTask.class);
        this.startActivity(nextScreen);
    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TASK,
                task);


        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }
}