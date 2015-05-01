package com.example.rory.todo.db;

/**
 * Created by Rory on 26/11/2014.
 */
import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.example.rory.todolist.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TASK = "task";
        public static final String _ID = BaseColumns._ID;
        public static final String TODOCAT = "todocat";
    }
}