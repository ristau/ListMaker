package com.bfr.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    int selectedPos;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedPos = position;
                String textToEdit;
                textToEdit = todoItems.get(position);
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);

                i.putExtra("text", textToEdit);
                i.putExtra("position", position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }


    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file;
        file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file)) ;
        } catch (FileNotFoundException e) {
            todoItems = new ArrayList();
        } catch (IOException e) {
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file;
        file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
        }
    }



    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    public void goToEditView(){
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String newText = data.getExtras().getString("text");
            int code = data.getExtras().getInt("code", 0);

            todoItems.set(selectedPos, newText);
            writeItems();
            aToDoAdapter.notifyDataSetChanged();

            // Toast the name to display temporarily on screen
           // Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
        }
    }



}
