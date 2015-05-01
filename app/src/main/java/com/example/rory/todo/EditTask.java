package com.example.rory.todo;

/**
 * Created by Rory on 27/11/2014.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.rory.todo.db.TaskContract;
import com.example.rory.todo.db.TaskDBHelper;


/**
 * Created by Rory on 27/11/2014.
 */
public class EditTask extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_view);

    }




    public void Save(View view){
        EditText task = (EditText)findViewById(R.id.inputField);
        RadioButton per = (RadioButton)findViewById(R.id.radioPersonal);
        RadioButton bus = (RadioButton)findViewById(R.id.radioBusiness);

        String cate = "P";

        if(per.isChecked()){
            cate = "P";
        }
        else if(bus.isChecked()){
            cate = "B";
        }

        String taskAdd = task.getText().toString();

        TaskDBHelper helper = new TaskDBHelper(EditTask.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.clear();
        values.put(TaskContract.Columns.TASK,taskAdd);
        values.put(TaskContract.Columns.TODOCAT,cate);

        db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);


        MainActivity();

    }

    public void MainActivity(){

        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
    }

}
