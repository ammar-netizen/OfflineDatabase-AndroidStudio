package com.example.lab3_ammar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewBiodataActivity extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    TextView textId, textName, textDob, textGender, textAddress;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_biodata);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DataHelper(this);

        textId = findViewById(R.id.textViewId);
        textName = findViewById(R.id.textViewName);
        textDob = findViewById(R.id.textViewDob);
        textGender = findViewById(R.id.textViewGender);
        textAddress = findViewById(R.id.textViewAddress);
        btnBack = findViewById(R.id.buttonBack);

        // Retrieve data from database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM biodata WHERE name = '" +
                        getIntent().getStringExtra("name") + "'", null);

        if (cursor.moveToFirst()) {
            textId.setText(cursor.getString(0));
            textName.setText(cursor.getString(1));
            textDob.setText(cursor.getString(2));
            textGender.setText(cursor.getString(3));
            textAddress.setText(cursor.getString(4));
        }

        cursor.close();

        // Back button action
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}