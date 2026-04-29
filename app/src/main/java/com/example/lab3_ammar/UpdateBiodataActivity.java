package com.example.lab3_ammar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateBiodataActivity extends AppCompatActivity {

    DataHelper dbHelper;

    TextView textId;
    EditText editName, editDob, editGender, editAddress;
    Button buttonUpdate, buttonBack;

    int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_biodata);

        dbHelper = new DataHelper(this);

        textId = findViewById(R.id.textId);
        editName = findViewById(R.id.editName);
        editDob = findViewById(R.id.editDob);
        editGender = findViewById(R.id.editGender);
        editAddress = findViewById(R.id.editAddress);

        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonBack = findViewById(R.id.buttonBack);

        selectedId = getIntent().getIntExtra("id", -1);

        if (selectedId == -1) {
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadData();

        buttonUpdate.setOnClickListener(v -> updateData());
        buttonBack.setOnClickListener(v -> finish());
    }

    private void loadData() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM biodata WHERE no = ?",
                new String[]{String.valueOf(selectedId)}
        );

        if (cursor.moveToFirst()) {
            textId.setText(cursor.getString(0));
            editName.setText(cursor.getString(1));
            editDob.setText(cursor.getString(2));
            editGender.setText(cursor.getString(3));
            editAddress.setText(cursor.getString(4));
        }

        cursor.close();
        db.close();
    }

    private void updateData() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", editName.getText().toString());
        values.put("dob", editDob.getText().toString());
        values.put("gender", editGender.getText().toString());
        values.put("address", editAddress.getText().toString());

        int result = db.update(
                "biodata",
                values,
                "no=?",
                new String[]{String.valueOf(selectedId)}
        );

        if (result > 0) {
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();

            if (MainActivity.ma != null) {
                MainActivity.ma.RefreshList();
            }

            finish();
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}