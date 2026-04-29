package com.example.lab3_ammar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3_ammar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    ListView listView01;
    DataHelper dbcenter;
    Cursor cursor;

    String[] nameList;
    int[] idList;

    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ma = this;
        dbcenter = new DataHelper(this);

        listView01 = findViewById(R.id.listView1);

        binding.fab.setOnClickListener(v ->
                startActivity(new Intent(this, CreateBiodataActivity.class))
        );

        RefreshList();
    }

    public void RefreshList() {

        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata", null);

        if (cursor.getCount() == 0) {
            nameList = new String[]{"No data"};
            idList = new int[]{-1};
        } else {
            nameList = new String[cursor.getCount()];
            idList = new int[cursor.getCount()];

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                idList[i] = cursor.getInt(0);      // no
                nameList[i] = cursor.getString(1); // name
                cursor.moveToNext();
            }
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);

        listView01.setAdapter(adapter);

        listView01.setOnItemClickListener((parent, view, position, id) -> {

            final int selectedId = idList[position];

            CharSequence[] options = {
                    "View Biodata",
                    "Update Biodata",
                    "Delete Biodata"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Action");

            builder.setItems(options, (dialog, which) -> {

                switch (which) {

                    case 0:
                        startActivity(new Intent(this, ViewBiodataActivity.class)
                                .putExtra("id", selectedId));
                        break;

                    case 1:
                        startActivity(new Intent(this, UpdateBiodataActivity.class)
                                .putExtra("id", selectedId));
                        break;

                    case 2:
                        SQLiteDatabase db1 = dbcenter.getWritableDatabase();
                        db1.execSQL("DELETE FROM biodata WHERE no = ?",
                                new Object[]{selectedId});
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                        RefreshList();
                        break;
                }
            });

            builder.show();
        });

        cursor.close();
        db.close();
    }
}