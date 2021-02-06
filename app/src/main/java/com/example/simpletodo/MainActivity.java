package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    Button addbut;
    EditText editText;
    RecyclerView recycler;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addbut = findViewById(R.id.add_butt);
        editText = findViewById(R.id.edit_text);
        recycler = findViewById(R.id.recycler_view);
        items = new ArrayList<>();
        loadItem();
        ItemAdapter.OnLongClickedListener onLongClicked = new ItemAdapter.OnLongClickedListener(){

            @Override
            public void onLongClicked(int position) {
                items.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was successfully removed.", Toast.LENGTH_SHORT).show();
                saveItem();
            }
        };
        adapter = new ItemAdapter(items, onLongClicked);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        addbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addedList = editText.getText().toString();
                items.add(addedList);
                adapter.notifyItemInserted(items.size() - 1);
                editText.setText("");
                Toast.makeText(getApplicationContext(), "Item was added!", Toast.LENGTH_SHORT).show();
                saveItem();
            }
        });


    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //load item before add
    private void loadItem(){
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch(IOException e){
            Log.e("MainActivity", "Error", e);
            items = new ArrayList<>();

        }
    }

    private void saveItem(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        }
        catch(IOException e){
            Log.e("MainActivity", "Error", e);


        }
    }
}