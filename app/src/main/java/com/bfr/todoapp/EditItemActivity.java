package com.bfr.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    EditText upDateItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        upDateItem = (EditText) findViewById(R.id.udItem);
        String textToEdit = getIntent().getStringExtra("text");
        int arrPosition = getIntent().getIntExtra("position", 0);

        String newStr = textToEdit + " at position " + arrPosition;
        upDateItem.setText(newStr);

        int textLength = upDateItem.getText().length();
        upDateItem.setSelection(textLength, textLength);

    }

    public void onSaveEdit(View view) {

        Intent newData = new Intent();

        String newText = upDateItem.getText().toString();

        newData.putExtra("text", newText);
        newData.putExtra("code", 200);

        setResult(RESULT_OK, newData);
        finish();
    }
}
